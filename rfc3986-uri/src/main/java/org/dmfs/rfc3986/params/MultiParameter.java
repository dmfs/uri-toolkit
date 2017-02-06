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

package org.dmfs.rfc3986.params;

import org.dmfs.iterators.AbstractConvertedIterator;
import org.dmfs.iterators.AbstractFilteredIterator;
import org.dmfs.iterators.ConvertedIterator;
import org.dmfs.iterators.FilteredIterator;
import org.dmfs.rfc3986.UriEncoded;

import java.util.Iterator;


/**
 * The values of a parameter that may be present any number of times.
 *
 * @author Marten Gajda
 */
public final class MultiParameter<V> implements Iterable<V>
{
    private final ParamType<V> mParamType;
    private final Iterable<Pair<UriEncoded, UriEncoded>> mDelegate;


    public MultiParameter(ParamType<V> paramType, Iterable<Pair<UriEncoded, UriEncoded>> delegate)
    {
        mParamType = paramType;
        mDelegate = delegate;
    }


    @Override
    public Iterator<V> iterator()
    {
        return new ConvertedIterator<>(
                new FilteredIterator<>(
                        mDelegate.iterator(),
                        new AbstractFilteredIterator.IteratorFilter<Pair<UriEncoded, UriEncoded>>()
                        {
                            @Override
                            public boolean iterate(Pair<UriEncoded, UriEncoded> element)
                            {
                                return element.key().normalized().equals(mParamType.name());
                            }
                        }),
                new AbstractConvertedIterator.Converter<V, Pair<UriEncoded, UriEncoded>>()
                {
                    @Override
                    public V convert(Pair<UriEncoded, UriEncoded> element)
                    {
                        return mParamType.decodedValue(element.value());
                    }
                });
    }
}
