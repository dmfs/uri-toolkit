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

package org.dmfs.rfc3986.uris;

import org.dmfs.rfc3986.Authority;
import org.dmfs.rfc3986.Fragment;
import org.dmfs.rfc3986.Path;
import org.dmfs.rfc3986.Query;
import org.dmfs.rfc3986.Scheme;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.paths.EmptyPath;
import org.dmfs.rfc3986.utils.Optional;

import static org.dmfs.rfc3986.utils.Absent.absent;


/**
 * Special case of a {@link Uri} that doesn't contain any values.
 *
 * @author Marten Gajda
 */
public final class EmptyUri implements Uri
{
    public final static EmptyUri INSTANCE = new EmptyUri();


    @Override
    public Optional<? extends Scheme> scheme()
    {
        return absent();
    }


    @Override
    public Optional<? extends Authority> authority()
    {
        return absent();
    }


    @Override
    public Path path()
    {
        return EmptyPath.INSTANCE;
    }


    @Override
    public Optional<? extends Query> query()
    {
        return absent();
    }


    @Override
    public Optional<? extends Fragment> fragment()
    {
        return absent();
    }


    @Override
    public boolean isHierarchical()
    {
        return false;
    }


    @Override
    public boolean isAbsolute()
    {
        return false;
    }
}
