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

import org.dmfs.optional.Optional;


/**
 * An authority.
 * <p>
 * An authority has a host, optional user info and an optional port.
 *
 * @author Marten Gajda
 */
public interface Authority
{
    /**
     * Returns the {@link Optional} user info part of the authority in encoded form.
     *
     * @return The {@link UriEncoded} user info.
     */
    Optional<? extends UriEncoded> userInfo();

    /**
     * Returns the host of this authority.
     *
     * @return The {@link UriEncoded} host.
     */
    UriEncoded host();

    /**
     * The {@link Optional} port number.
     *
     * @return An {@link Optional} port number (as an {@link Integer}).
     */
    Optional<Integer> port();
}
