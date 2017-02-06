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

package org.dmfs.rfc3986.paths;

import org.dmfs.iterators.ArrayIterator;
import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;

import java.util.Iterator;


/**
 * A path that's assembled from its individual (encoded) path segments
 * <p>
 * Note: If the first segment equals {@link IdempotentEncoded#EMPTY} the result is an absolute Path. A {@link IdempotentEncoded#EMPTY} element at the
 * essentially appends a '/' to the end of the String representation.
 *
 * @author Marten Gajda
 */
public final class StructuredPath implements Path
{
    private final UriEncoded[] mPathSegments;


    public StructuredPath(UriEncoded... segments)
    {
        mPathSegments = segments.clone();
    }


    @Override
    public boolean isEmpty()
    {
        return mPathSegments.length == 0;
    }


    @Override
    public boolean isAbsolute()
    {
        return mPathSegments.length > 0 && IdempotentEncoded.EMPTY.equals(mPathSegments[0]);
    }


    @Override
    public Iterator<UriEncoded> iterator()
    {
        return new ArrayIterator<>(mPathSegments);
    }
}
