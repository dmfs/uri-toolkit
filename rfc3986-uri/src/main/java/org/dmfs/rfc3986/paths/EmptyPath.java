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

import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.UriEncoded;

import java.util.Iterator;

import static org.dmfs.jems2.iterator.EmptyIterator.emptyIterator;


/**
 * An empty {@link Path}. By definition, it has no path segments and is not absolute.
 */
public final class EmptyPath implements Path
{
    public final static EmptyPath INSTANCE = new EmptyPath();


    @Override
    public boolean isEmpty()
    {
        return true;
    }


    @Override
    public boolean isAbsolute()
    {
        return false;
    }


    @Override
    public Iterator<UriEncoded> iterator()
    {
        return emptyIterator();
    }


    @Override
    public int hashCode()
    {
        return 0;
    }


    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Path && ((Path) obj).isEmpty();
    }
}
