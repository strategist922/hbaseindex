/*
 * Copyright 2008 The Apache Software Foundation
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
/**Provides row-level filters applied to HRegion scan results during calls to
 * {@link org.apache.hadoop.hbase.client.ResultScanner#next()}. 

<p>Since HBase 0.20.0, {@link org.apache.hadoop.hbase.filter.Filter} is the new
Interface used filtering.  It replaces the deprecated
{@link org.apache.hadoop.hbase.filter.RowFilterInterface}.
Filters run the extent of a table unless you wrap your filter in a
{@link org.apache.hadoop.hbase.filter.WhileMatchFilter}.
The latter returns as soon as the filter stops matching.
</p>
*/
package org.apache.hadoop.hbase.filter;
