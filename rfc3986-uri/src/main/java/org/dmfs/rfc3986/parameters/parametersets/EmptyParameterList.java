/*
 * Copyright 2017 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.rfc3986.parameters.parametersets;

import org.dmfs.jems2.iterator.EmptyIterator;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;

import java.util.Iterator;


/**
 * {@link ParameterList} that doesn't contain any parameters.
 */
public final class EmptyParameterList implements ParameterList
{
    public static final ParameterList INSTANCE = new EmptyParameterList();


    @Override
    public Iterator<Parameter> iterator()
    {
        return EmptyIterator.emptyIterator();
    }
}
