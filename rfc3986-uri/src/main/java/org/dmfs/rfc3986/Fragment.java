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

/**
 * A fragment is essentially a {@link UriEncoded} {@link CharSequence}.
 * <p>
 * However, often the fragment is structured for example with {@code x-www-form-urlencoded}. Use an appropriate adapter to read these structured values.
 *
 * @author Marten Gajda
 */
public interface Fragment extends UriEncoded
{
    @Override
    String toString();
}
