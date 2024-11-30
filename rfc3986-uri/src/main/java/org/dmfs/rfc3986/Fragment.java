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

package org.dmfs.rfc3986;

import org.dmfs.rfc3986.parameters.adapters.XwfueParameterList;


/**
 * The fragment component of a URI. A fragment is a {@link UriEncoded} {@link CharSequence}.
 * <p>
 * Often the fragment is structured, for instance with {@code x-www-form-urlencoded}. Use an appropriate adapter like {@link XwfueParameterList} to read these
 * structured values.
 */
public interface Fragment extends UriEncoded
{

    /**
     * Returns the encoded String representation of the fragment.
     *
     * @return A {@link String} representing the encoded fragment.
     */
    @Override
    String toString();
}
