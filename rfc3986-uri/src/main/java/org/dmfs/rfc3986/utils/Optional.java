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
 * An optional value.
 *
 * @author Marten Gajda
 */
public interface Optional<T>
{
    /**
     * Returns whether the optional value is present.
     *
     * @return {@code true} if the value is present, {@code false} otherwise.
     */
    boolean isPresent();

    /**
     * Returns the optional value or the given default value if the optional value is not present.
     *
     * @param defaultValue
     *         The value to return if the optional value is not present.
     *
     * @return The optional value or the default value.
     */
    T value(T defaultValue);

    /**
     * Returns the optional value.
     *
     * @return The value.
     *
     * @throws NoSuchElementException
     *         if the optional value is not present.
     */
    T value() throws NoSuchElementException;
}
