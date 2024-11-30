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
 * A fluent interface to edit {@link ParameterList}s.
 */
public interface FluentParameterList extends ParameterList
{
    /**
     * Appends the given parameters, even if parameters with the same names already exist.
     *
     * @param parameters The parameters to append.
     * @return An updated {@link FluentParameterList} instance.
     */
    FluentParameterList alsoWith(Parameter... parameters);

    /**
     * Appends the given parameters, removing any other parameters that have the same names as the new ones.
     *
     * @param parameters The new parameters.
     * @return An updated {@link FluentParameterList} instance.
     */
    FluentParameterList ratherWith(Parameter... parameters);

    /**
     * Removes any parameters of the given {@link ParameterType}s.
     *
     * @param types The parameter parametertypes to remove.
     * @return An updated {@link FluentParameterList} instance.
     */
    FluentParameterList without(ParameterType<?>... types);
}
