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

import org.dmfs.iterators.ArrayIterator;
import org.dmfs.iterators.SerialIterator;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;

import java.util.Iterator;


/**
 * A {@link ParameterList} which appends the given parameters to the decorated ones.
 *
 * @author Marten Gajda
 */
public final class Appending implements ParameterList
{
    private final ParameterList mDelegate;
    private final Parameter[] mNewParameters;


    public Appending(ParameterList delegate, Parameter... newParameters)
    {
        mDelegate = delegate;
        mNewParameters = newParameters;
    }


    @Override
    public Iterator<Parameter> iterator()
    {
        return new SerialIterator<>(mDelegate.iterator(), new ArrayIterator<>(mNewParameters));
    }
}
