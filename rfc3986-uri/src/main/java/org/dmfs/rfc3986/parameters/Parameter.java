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
 * A parameter. Parameters have a name and a text representation of the parameter value.
 *
 * @author Marten Gajda
 */
public interface Parameter
{
    /**
     * Returns the name of the parameter.
     *
     * @return The parameter name,
     */
    CharSequence name();

    /**
     * Returns the text value of the parameter.
     *
     * @return A {@link CharSequence} containing the parameter value in its text form.
     */
    CharSequence textValue();
}
