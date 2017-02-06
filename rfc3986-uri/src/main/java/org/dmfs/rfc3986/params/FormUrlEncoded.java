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
import org.dmfs.iterators.ConvertedIterator;
import org.dmfs.iterators.EmptyIterator;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.params.utils.UriFormEncodedPair;
import org.dmfs.rfc3986.utils.Optional;
import org.dmfs.rfc3986.utils.Split;

import java.util.Iterator;


/**
 * A {@link Parametrized} that parses the adapted {@link UriEncoded} as a {@code x-www-form-urlencoded} structure.
 *
 * @author Marten Gajda
 */
public final class FormUrlEncoded implements Parametrized<UriEncoded, UriEncoded>
{
    private final static AbstractConvertedIterator.Converter<Pair<UriEncoded, UriEncoded>, CharSequence> CONVERTER = new AbstractConvertedIterator.Converter<Pair<UriEncoded, UriEncoded>, CharSequence>()
    {
        @Override
        public Pair<UriEncoded, UriEncoded> convert(CharSequence element)
        {
            return new UriFormEncodedPair(new Precoded(element));
        }
    };

    private final CharSequence mDelegate;


    public FormUrlEncoded(Optional<UriEncoded> optionalDelegate)
    {
        this(optionalDelegate.value(IdempotentEncoded.EMPTY));
    }


    public FormUrlEncoded(UriEncoded delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Iterator<Pair<UriEncoded, UriEncoded>> iterator()
    {
        if (mDelegate.length() == 0)
        {
            return EmptyIterator.instance();
        }

        return new ConvertedIterator<>(new Split(mDelegate, '&'), CONVERTER);
    }
}
