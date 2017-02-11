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

import org.dmfs.rfc3986.parameters.FluentParameterList;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;
import org.dmfs.rfc3986.parameters.ParameterType;

import java.util.Iterator;


/**
 * A fluent decorator to edit {@link ParameterList} in a convenient manner.
 *
 * @author Marten Gajda
 */
public final class Fluent implements FluentParameterList
{
    private final ParameterList mDelegate;


    public Fluent()
    {
        this(EmptyParameterList.INSTANCE);
    }


    public Fluent(ParameterList delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public FluentParameterList alsoWith(Parameter... parameters)
    {
        return new Fluent(new Appending(mDelegate, parameters));
    }


    @Override
    public FluentParameterList ratherWith(Parameter... parameters)
    {
        return new Fluent(new Replacing(mDelegate, parameters));
    }


    @Override
    public FluentParameterList without(ParameterType<?>... types)
    {
        return new Fluent(new Removing(mDelegate, types));
    }


    @Override
    public Iterator<Parameter> iterator()
    {
        return mDelegate.iterator();
    }
}
