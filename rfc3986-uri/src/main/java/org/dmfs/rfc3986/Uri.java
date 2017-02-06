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

import org.dmfs.rfc3986.utils.Optional;


/**
 * A URI as specified in <a href="https://tools.ietf.org/html/rfc3986">RFC 3986</a>.
 * <p>
 * For convenience this represents a URI-reference as specified in <a href="https://tools.ietf.org/html/rfc3986#section-4.1">RFC 3986, Section 4.1</a>. For
 * brevity and as per common usage it's still called just {@code Uri} instead of {@code UriReference}.
 * <p>
 * As a consequence, {@link #scheme()} returns an {@link Optional}, because in contrast to a URI, a relative reference doesn't have a scheme.
 * <p>
 * {@link #isAbsolute()} may be used to distinguish between real URIs that have a scheme ({@link #isAbsolute()} returns {@code true}) and relative references
 * ({@link #isAbsolute()} returns {@code false}).
 *
 * @author Marten Gajda
 */
public interface Uri
{
    /**
     * Returns the {@link Optional} {@link Scheme} of the URI reference.
     *
     * @return An {@link Optional} {@link Scheme}.
     */
    Optional<? extends Scheme> scheme();

    /**
     * Returns the {@link Optional} {@link Authority} of the URI reference.
     *
     * @return An {@link Optional} {@link Authority}.
     */
    Optional<? extends Authority> authority();

    /**
     * Returns the {@link Path} of this URI reference.
     *
     * @return A {@link Path}. The path is always present, but may be empty.
     */
    Path path();

    /**
     * Returns the {@link Optional} {@link Query} of the URI reference.
     *
     * @return An {@link Optional} {@link Query}.
     */
    Optional<? extends Query> query();

    /**
     * Returns the {@link Optional} {@link Fragment} of the URI reference.
     *
     * @return An {@link Optional} {@link Fragment}.
     */
    Optional<? extends Fragment> fragment();

    /**
     * Returns whether this URI reference is hierarchical.
     *
     * @return {@code true} if the URI is hierarchical, {@code false} otherwise.
     */
    boolean isHierarchical();

    /**
     * Returns whether this URI reference is absolute, i.e. a URI. A URI reference is absolute if it has a scheme, otherwise it's a relative reference as per <a
     * href="https://tools.ietf.org/html/rfc3986#section-4.2">RFC 3986, Section 4.2</a>
     *
     * @return {@code true} if the URI is absolute, {@code false} otherwise.
     */
    boolean isAbsolute();
}
