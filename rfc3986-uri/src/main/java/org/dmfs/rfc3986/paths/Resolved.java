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

import org.dmfs.jems2.iterator.Concat;
import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * A {@link Path} decorator that resolves a {@link Path} within the context of the decorated {@link Path}
 */
public final class Resolved implements Path
{
    private final Path mBase;
    private final Path mReference;


    public Resolved(Path base, Path reference)
    {
        mBase = base;
        mReference = reference;
    }


    @Override
    public boolean isEmpty()
    {
        return mBase.isEmpty() && mReference.isEmpty();
    }


    @Override
    public boolean isAbsolute()
    {
        return mBase.isAbsolute() || mReference.isAbsolute();
    }


    @Override
    public Iterator<UriEncoded> iterator()
    {
        if (mReference.isEmpty())
        {
            // nothing to add, just normalize this
            return Collections.unmodifiableList(normalize(mBase.iterator())).iterator();
        }
        if (mReference.isAbsolute())
        {
            // Just normalize the refernce
            return Collections.unmodifiableList(normalize(mReference.iterator())).iterator();
        }
        LinkedList<UriEncoded> segments = normalize(mBase.iterator());
        if (!segments.isEmpty())
        {
            UriEncoded last = segments.getLast();
            // if the base path is not terminated by a directory, remove the last segment
            if (!IdempotentEncoded.EMPTY.equals(last) && !IdempotentEncoded.CURRENT.equals(last) && !IdempotentEncoded.PARENT.equals(last))
            {
                segments.removeLast();
            }
        }
        return Collections.unmodifiableList(normalize(new Concat<>(segments.iterator(), mReference.iterator()))).iterator();
    }


    // TODO: find a good way to consolidate this method with the same method in Normalized
    private LinkedList<UriEncoded> normalize(Iterator<UriEncoded> segments)
    {
        LinkedList<UriEncoded> pathSegments = new LinkedList<>();
        int count = 0;
        int backSteps = 0;
        boolean isAbsolute = false;
        boolean endWithEmptySegment = false;
        boolean singleDot = false;
        while (segments.hasNext())
        {
            UriEncoded segment = segments.next();

            if (IdempotentEncoded.EMPTY.equals(segment))
            {
                if (count == 0)
                {
                    // this is an absolute Path, it starts with a "/"
                    isAbsolute = true;
                }
                else
                {
                    // an empty segment has been added, which means we add an empty segment if this is the last segment
                    endWithEmptySegment = true;
                }
            }
            else if (IdempotentEncoded.CURRENT.equals(segment))
            {
                // insert a dot if the path is empty and relative (not absolute), otherwise just append a "/"
                if (pathSegments.isEmpty() && !isAbsolute)
                {
                    singleDot = true;
                }
                else
                {
                    endWithEmptySegment = true;
                }
            }
            else if (IdempotentEncoded.PARENT.equals(segment))
            {
                // go back in the hierarchy
                if (backSteps > 0)
                {
                    // we have segments that we can remove
                    pathSegments.removeLast();
                    backSteps -= 1;
                }
                else if (!isAbsolute)
                {
                    // no segments to remove
                    pathSegments.addLast(segment);
                }
                // when going back in the hierarchy we always append a "/"
                endWithEmptySegment = true;
                // also we only append a "." if no other segment is left
                singleDot = pathSegments.isEmpty() && !isAbsolute;
            }
            else
            {
                pathSegments.addLast(segment);
                backSteps += 1;
                endWithEmptySegment = false;
                singleDot = false;
            }
            count += 1;
        }
        if (isAbsolute)
        {
            pathSegments.addFirst(IdempotentEncoded.EMPTY);
        }
        if (singleDot)
        {
            pathSegments.addLast(IdempotentEncoded.CURRENT);
            pathSegments.addLast(IdempotentEncoded.EMPTY);
        }
        else if (endWithEmptySegment && (pathSegments.size() <= 1 || !IdempotentEncoded.EMPTY.equals(pathSegments.getLast())))
        {
            pathSegments.addLast(IdempotentEncoded.EMPTY);
        }

        return pathSegments;
    }

}
