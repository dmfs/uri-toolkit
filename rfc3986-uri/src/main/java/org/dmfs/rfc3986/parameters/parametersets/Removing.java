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

import org.dmfs.jems2.iterator.Sieved;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;
import org.dmfs.rfc3986.parameters.ParameterType;

import java.util.Iterator;


/**
 * {@link ParameterList} decorator that removes any parameters of the given {@link ParameterType}s.
 */
public final class Removing implements ParameterList
{
    private final ParameterList mDelegate;
    private final ParameterType<?>[] mRemovedTypes;


    public Removing(ParameterList delegate, ParameterType<?>... removedTypes)
    {
        mDelegate = delegate;
        mRemovedTypes = removedTypes;
    }


    @Override
    public Iterator<Parameter> iterator()
    {
        return new Sieved<>(
            element -> {
                for (ParameterType<?> param : mRemovedTypes)
                {
                    // TODO: get rid of the toString conversion and use something like an `Equalable`
                    // TODO: maybe move this check to ParameterType
                    if (param.name().toString().equals(element.name().toString()))
                    {
                        return false;
                    }
                }
                return true;
            },

            mDelegate.iterator());
    }
}
