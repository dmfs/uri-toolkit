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

package org.dmfs.rfc3986.validation;

/**
 * A {@link CharSequence} that comes with a guarantee about the characters it contains.
 * <p>
 * The characters are validated lazily on the first access to any of the methods, not up-front.
 *
 * @author Marten Gajda
 */
public final class Validated implements CharSequence
{
    private final CharSequence mDelegate;
    private final CharSet mCharSet;
    private boolean mValidated;


    public Validated(CharSequence delegate, CharSet charSet)
    {
        mDelegate = delegate;
        mCharSet = charSet;
    }


    @Override
    public int length()
    {
        validate();
        return mDelegate.length();
    }


    @Override
    public char charAt(int i)
    {
        validate();
        return mDelegate.charAt(i);
    }


    @Override
    public CharSequence subSequence(int i, int i1)
    {
        validate();
        return mDelegate.subSequence(i, i1);
    }


    @Override
    public String toString()
    {
        validate();
        return mDelegate.toString();
    }


    private void validate()
    {
        if (!mValidated)
        {
            CharSequence delegate = mDelegate;
            CharSet charSet = mCharSet;
            final int count = delegate.length();
            for (int i = 0; i < count; ++i)
            {
                if (!charSet.contains(delegate.charAt(i)))
                {
                    throw new IllegalArgumentException(String.format("Illegal char '%c' at position %d in '%s'", delegate.charAt(i), i, delegate.toString()));
                }
            }
            mValidated = true;
        }
    }
}
