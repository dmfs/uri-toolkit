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
 * An {@link Optional} that's never present.
 *
 * @author Marten Gajda
 */
public final class Absent<T> implements Optional<T>
{
    private final static Optional<?> INSTANCE = new Absent<Void>();


    @SuppressWarnings("unchecked")
    public static <T> Absent<T> absent()
    {
        return (Absent<T>) INSTANCE;
    }


    @Override
    public boolean isPresent()
    {
        return false;
    }


    @Override
    public T value(T defaultValue)
    {
        return defaultValue;
    }


    @Override
    public T value() throws NoSuchElementException
    {
        throw new NoSuchElementException("No value is present in this Optional. Better call isPresent() next time.");
    }
}
