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

package org.dmfs.rfc3986.parameters;

/**
 * The type of a parameter.
 *
 * @author Marten Gajda
 */
public interface ParameterType<T>
{
    /**
     * Returns the name of the parameter.
     *
     * @return
     */
    CharSequence name();

    /**
     * Returns the parsed value of the given {@link Parameter}.
     *
     * @param parameter
     *         The {@link Parameter} to parse.
     *
     * @return
     */
    T value(Parameter parameter);

    /**
     * Returns a parameter of this type with the given value.
     *
     * @param value
     *
     * @return
     */
    Parameter parameter(T value);
}