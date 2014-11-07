/*
 * Copyright (c) 2008-2014 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb;

import java.util.Arrays;

/**
 * A {@code BSONCallback} for the creation of {@code LazyWritableDBObject} instances.
 */
public class LazyWriteableDBCallback extends LazyDBCallback {

    /**
     * Construct an instance.
     *
     * @param collection the DBCollection containing the document.  This parameter is no longer used.
     */
    public LazyWriteableDBCallback(final DBCollection collection) {
        super(collection);
    }

    @Override
    public Object createObject(final byte[] bytes, final int offset) {
        LazyDBObject document = new LazyWriteableDBObject(bytes, offset, this);
        if (document.keySet().containsAll(Arrays.asList("$id", "$ref"))) {
            return new DBRef((String) document.get("$ref"), document.get("$id"));
        }
        return document;
    }
}