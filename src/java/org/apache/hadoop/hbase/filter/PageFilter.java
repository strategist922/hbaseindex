/**
 * Copyright 2009 The Apache Software Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hbase.filter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.hbase.KeyValue;

/**
 * Implementation of Filter interface that limits results to a specific page
 * size. It terminates scanning once the number of filter-passed rows is >
 * the given page size.
 * 
 * <p>
 * Note that this filter cannot guarantee that the number of results returned
 * to a client are <= page size. This is because the filter is applied
 * separately on different region servers. It does however optimize the scan of
 * individual HRegions by making sure that the page size is never exceeded
 * locally.
 * </p>
 */
public class PageFilter implements Filter {
  private long pageSize = Long.MAX_VALUE;
  private int rowsAccepted = 0;

  /**
   * Default constructor, filters nothing. Required though for RPC
   * deserialization.
   */
  public PageFilter() {
    super();
  }

  /**
   * Constructor that takes a maximum page size.
   * 
   * @param pageSize Maximum result size.
   */
  public PageFilter(final long pageSize) {
    this.pageSize = pageSize;
  }

  public void reset() {
    rowsAccepted = 0;
  }

  public boolean filterAllRemaining() {
    return this.rowsAccepted > this.pageSize;
  }

  public boolean filterRowKey(byte[] rowKey, int offset, int length) {
    this.rowsAccepted++;
    return filterAllRemaining();
  }

  public void readFields(final DataInput in) throws IOException {
    this.pageSize = in.readLong();
  }

  public void write(final DataOutput out) throws IOException {
    out.writeLong(pageSize);
  }

  public ReturnCode filterKeyValue(KeyValue v) {
    return filterAllRemaining() ? ReturnCode.NEXT_ROW : ReturnCode.INCLUDE;
  }

  public boolean filterRow() {
    return filterAllRemaining();
  }
}