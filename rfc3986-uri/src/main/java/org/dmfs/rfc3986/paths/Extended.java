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

import org.dmfs.iterators.SerialIterator;
import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.IdempotentEncoded;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * A decorator for {@link Path}s that appends path segments to the decorated {@link Path}.
 * <p>
 * Note, the result is automatically normalized, so this can be used to remove path segments, by appending {@link IdempotentEncoded#PARENT}.
 * <p>
 * Examples:
 * <pre>
 * Base path                     appended path                                   result
 *
 * (empty Path)                  (empty segment)                                 /
 * (empty Path)                  .                                               ./
 * (empty Path)                  ..                                              ../
 * (empty Path)                  a                                               a
 *
 * .                             (empty segment)                                 ./     or  .
 * .                             .                                               ./     or  ./.
 * .                             ..                                              ../    or  ./..
 * .                             a                                               a
 *
 * ..                            (empty segment)                                 ../    or  ..
 * ..                            .                                               ../    or  ../.
 * ..                            ..                                              ../../
 * ..                            a                                               ../a
 *
 * /                             (empty segment)                                 /
 * /                             .                                               /      or  /.
 * /                             ..                                              /      or  /..
 * /                             a                                               /a
 *
 * /a                            (empty segment)                                 /a/
 * /a                            .                                               /a/    or  /a/.
 * /a                            ..                                              /
 * /a                            b                                               /a/b
 *
 * /a/                           (empty segment)                                 /a/
 * /a/                           .                                               /a/    or  /a/.
 * /a/                           ..                                              /
 * /a/                           b                                               /a/b
 *
 * /a/b                          (empty segment)                                 /a/b/
 * /a/b                          .                                               /a/b/  or  /a/b/.
 * /a/b                          ..                                              /a/
 * /a/b                          c                                               /a/b/c
 *
 * a                             (empty segment)                                 a/
 * a                             .                                               a/     or  a/.
 * a                             ..                                              ./
 * a                             b                                               a/b
 *
 * a/                            (empty segment)                                 a/
 * a/                            .                                               a/     or  a/.
 * a/                            ..                                              ./
 * a/                            b                                               a/b
 * </pre>
 * <p>
 * Note that this is different from the result of {@link Resolved} <ul> <li>{@code Extended} doesn't remove the last path segment of the base path if it's not a
 * directory</li> <li>{@link Resolved} returns the path reference if it's an absolute path.</li> </ul>
 * <p>
 * The following examples illustrate these differences:
 * <pre>
 * new Extended(new StringPath("/a/b"), new StringPath("c/d"));  // results in "/a/b/c/d"
 * new Extended(new StringPath("/a/b"), new StringPath("/c/d"));  // results in "/a/b/c/d"
 *
 * new Resolved(new StringPath("/a/b"), new StringPath("c/d"));  // results in "/a/c/d"
 * new Resolved(new StringPath("/a/b"), new StringPath("/c/d"));  // results in "/c/d"
 * </pre>
 *
 * @author Marten Gajda
 */
public final class Extended implements Path
{
    private final Path mDelegate;
    private final Iterable<UriEncoded> mNewSegments;


    public Extended(Path delegate, UriEncoded... newSegments)
    {
        this(delegate, Arrays.asList(newSegments.clone()));
    }


    public Extended(Path delegate, Path newSegments)
    {
        this(delegate, (Iterable<UriEncoded>) newSegments);
    }


    private Extended(Path delegate, Iterable<UriEncoded> newSegments)
    {
        mDelegate = delegate;
        mNewSegments = newSegments;
    }


    @Override
    public boolean isEmpty()
    {
        return mDelegate.isEmpty() && !mNewSegments.iterator().hasNext();
    }


    @Override
    public boolean isAbsolute()
    {
        return mDelegate.isAbsolute();
    }


    @Override
    public Iterator<UriEncoded> iterator()
    {
        return Collections.unmodifiableList(normalize(new SerialIterator<>(mDelegate.iterator(), mNewSegments.iterator()))).iterator();
    }


    // TODO: find a good way to consolidate this method with the same method in Resolved
    private List<UriEncoded> normalize(Iterator<UriEncoded> segments)
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
