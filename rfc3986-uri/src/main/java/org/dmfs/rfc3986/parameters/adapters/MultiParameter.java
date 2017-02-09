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

package org.dmfs.rfc3986.parameters.adapters;

import org.dmfs.iterators.AbstractConvertedIterator;
import org.dmfs.iterators.AbstractFilteredIterator;
import org.dmfs.iterators.ConvertedIterator;
import org.dmfs.iterators.FilteredIterator;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;
import org.dmfs.rfc3986.parameters.ParameterType;

import java.util.Iterator;


/**
 * The values of a {@link Parameter} that may be present any number of times.
 *
 * @author Marten Gajda
 */
public final class MultiParameter<V> implements Iterable<V>
{
    private final ParameterType<V> mParameterType;
    private final Iterable<Parameter> mDelegate;


    public MultiParameter(ParameterType<V> parameterType, ParameterList delegate)
    {
        mParameterType = parameterType;
        mDelegate = delegate;
    }


    @Override
    public Iterator<V> iterator()
    {
        return new ConvertedIterator<>(
                new FilteredIterator<>(
                        mDelegate.iterator(),
                        new AbstractFilteredIterator.IteratorFilter<Parameter>()
                        {
                            @Override
                            public boolean iterate(Parameter element)
                            {
                                // TODO: get rid of the toString conversion and use something like an `Equalable`
                                // TODO: maybe move this check to ParameterType
                                return element.name().toString().equals(mParameterType.name().toString());
                            }
                        }),
                new AbstractConvertedIterator.Converter<V, Parameter>()
                {
                    @Override
                    public V convert(Parameter element)
                    {
                        return mParameterType.value(element);
                    }
                });
    }
}
