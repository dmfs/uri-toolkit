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

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.iterator.EmptyIterator;
import org.dmfs.jems2.iterator.Mapped;
import org.dmfs.jems2.optional.Present;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;
import org.dmfs.rfc3986.parameters.parameters.UrlEncodedParameter;
import org.dmfs.rfc3986.utils.Split;

import java.util.Iterator;


/**
 * {@link ParameterList} adapter that interpret the adapted {@link UriEncoded} as an {@code x-www-form-urlencoded} structure.
 */
public final class XwfueParameterList implements ParameterList
{
    private final Optional<UriEncoded> mDelegate;


    public XwfueParameterList(UriEncoded delegate)
    {
        this(new Present<>(delegate));
    }


    public XwfueParameterList(Optional<UriEncoded> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Iterator<Parameter> iterator()
    {
        if (!mDelegate.isPresent() || mDelegate.value().length() == 0)
        {
            return EmptyIterator.emptyIterator();
        }

        return new Mapped<>(param -> new UrlEncodedParameter(new Precoded(param)), new Split(mDelegate.value(), '&'));
    }
}
