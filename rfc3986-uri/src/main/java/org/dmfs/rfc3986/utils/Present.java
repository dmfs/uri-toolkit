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

package org.dmfs.rfc3986.utils;

import java.util.NoSuchElementException;


/**
 * A special {@link Optional} that's always present. As a consequence this doesn't take {@code null} values.
 *
 * @author Marten Gajda
 */
public final class Present<T> implements Optional<T>
{
    private final T mValue;


    public Present(T value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("A Present value can't be null");
        }
        mValue = value;
    }


    @Override
    public boolean isPresent()
    {
        return true;
    }


    @Override
    public T value(T defaultValue)
    {
        return mValue;
    }


    @Override
    public T value() throws NoSuchElementException
    {
        return mValue;
    }
}
