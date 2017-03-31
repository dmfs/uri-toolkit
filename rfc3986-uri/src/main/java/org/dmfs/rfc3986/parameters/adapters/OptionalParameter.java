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
import org.dmfs.optional.Optional;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;
import org.dmfs.rfc3986.parameters.ParameterType;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * The value of a {@link Parameter} that can be present once or not at all.
 *
 * @author Marten Gajda
 */
public final class OptionalParameter<V> implements Optional<V>
{
    private final Iterable<V> mDelegate;
    private V mValue;
    private Boolean mIsPresent;


    public OptionalParameter(final ParameterType<V> parameterType, final ParameterList delegate)
    {
        mDelegate = new Iterable<V>()
        {
            @Override
            public Iterator<V> iterator()
            {
                return new ConvertedIterator<>(
                        new FilteredIterator<>(
                                delegate.iterator(),
                                new AbstractFilteredIterator.IteratorFilter<Parameter>()
                                {
                                    @Override
                                    public boolean iterate(Parameter element)
                                    {
                                        // TODO: get rid of the toString conversion and use something like an `Equalable`
                                        // TODO: maybe move this check to ParameterType
                                        return element.name().toString().equals(parameterType.name().toString());
                                    }
                                }),
                        new AbstractConvertedIterator.Converter<V, Parameter>()
                        {
                            @Override
                            public V convert(Parameter element)
                            {
                                return parameterType.value(element);
                            }
                        });
            }
        };
    }


    @Override
    public boolean isPresent()
    {
        if (mIsPresent == null)
        {
            mIsPresent = mDelegate.iterator().hasNext();
        }
        return mIsPresent;
    }


    @Override
    public V value(V defaultValue)
    {
        return isPresent() ? value() : defaultValue;
    }


    @Override
    public V value() throws NoSuchElementException
    {
        if (mValue == null)
        {
            Iterator<V> iterator = mDelegate.iterator();
            if (!iterator.hasNext())
            {
                throw new NoSuchElementException("No value present. Better call \"isPresent()\" beforehand.");
            }
            mValue = iterator.next();
        }
        return mValue;
    }
}
