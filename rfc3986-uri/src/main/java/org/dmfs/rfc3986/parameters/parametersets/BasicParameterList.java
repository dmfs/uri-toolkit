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

import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;

import java.util.Arrays;
import java.util.Iterator;


/**
 * Basic {@link ParameterList} containing  all {@link Parameter}s passed in an array or {@link Iterable}.
 *
 * @author Marten Gajda
 */
public final class BasicParameterList implements ParameterList
{
    private final Iterable<Parameter> mParameters;


    public BasicParameterList(Parameter... parameters)
    {
        this(Arrays.asList(parameters.clone()));
    }


    /**
     * Creates a {@link ParameterList} from the given {@link Iterable} or {@link Parameter}s. The given {@link Iterable} must be immutable.
     *
     * @param parameters
     *         An immutable {@link Iterable} of {@link Parameter}s.
     */
    public BasicParameterList(Iterable<Parameter> parameters)
    {
        mParameters = parameters;
    }


    @Override
    public Iterator<Parameter> iterator()
    {
        return mParameters.iterator();
    }
}
