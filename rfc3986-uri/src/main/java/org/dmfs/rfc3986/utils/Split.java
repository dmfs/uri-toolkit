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

package org.dmfs.rfc3986.utils;

import org.dmfs.iterators.AbstractBaseIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} that iterates the elements of a CharSequence of a comma (or other character) separated value list .
 * <p>
 * Example:
 * <p>
 * <pre>
 * <code>
 * 	Iterator&lt;CharSequence&gt; i = new Split("a, b,def,123", ',');
 * 	i.next(); // returns &quot;a&quot;
 * 	i.next(); // returns &quot; b&quot;
 * 	i.next(); // returns &quot;def&quot;
 * 	i.next(); // returns &quot;123&quot;
 * 	i.hasNext(); // false
 * </code>
 * </pre>
 * <p>
 * Iterating an empty CharSequence or a CharSequence without (unquoted) separators will return exactly one element.
 * <p>
 * Example:
 * <p>
 * <pre>
 * <code>
 * 	Iterator&lt;CharSequence&gt; i = new Split("", ',');
 * 	i.next(); // returns &quot;&quot;
 * 	i.hasNext(); // false
 * </code>
 * </pre>
 * <p>
 * TODO: replace with the implementation in Iterators once it has been released
 *
 * @author Marten Gajda
 */
public final class Split extends AbstractBaseIterator<CharSequence>
{
    private final CharSequence mValue;
    private final char mSeparator;
    private final int mLimit;

    private int mLastSeparatorPos = -1;
    private int mNextSeparatorPos = -1;
    private int mSplitCount;


    /**
     * Creates an {@link Iterator} that iterates all segments of the given CharSequence which are separated by the given <code>separator</code>.
     *
     * @param value
     *         The CharSequence that contains a list of values.
     * @param separator
     *         The separator that separates the values.
     */
    public Split(CharSequence value, char separator)
    {
        this(value, separator, Integer.MAX_VALUE);
    }


    /**
     * Creates an {@link Iterator} that iterates all segments of the given CharSequence which are separated by the given <code>separator</code>.
     *
     * @param value
     *         The CharSequence that contains a separated list of values.
     * @param separator
     *         The separator to scan for.
     * @param maxSplits
     *         The maximum number of splits to perform. The iterator will return at most this number +1 elements (the last iterated element containing the rest
     *         of the {@link CharSequence}.
     */
    public Split(CharSequence value, char separator, int maxSplits)
    {
        mValue = value;
        mSeparator = separator;
        mLimit = maxSplits;
    }


    @Override
    public boolean hasNext()
    {
        if (mNextSeparatorPos == -1)
        {
            findNextSeparator();
        }
        return mLastSeparatorPos < mValue.length();
    }


    @Override
    public CharSequence next()
    {
        if (mLastSeparatorPos >= mValue.length())
        {
            throw new NoSuchElementException("Last element has already been iterated.");
        }
        if (mNextSeparatorPos == -1)
        {
            findNextSeparator();
        }
        CharSequence result = mValue.subSequence(mLastSeparatorPos + 1, mNextSeparatorPos);
        findNextSeparator();
        return result;
    }


    /**
     * Move {@link #mNextSeparatorPos} to the next (unquoted) separator (or the end of the {@link CharSequence} if no other separator exists in {@link
     * #mValue}).
     */
    private void findNextSeparator()
    {
        mLastSeparatorPos = mNextSeparatorPos;
        if (mSplitCount >= mLimit)
        {
            // "fast forward" to the last position of the CharSequence
            mNextSeparatorPos = mValue.length();
            return;
        }
        while (++mNextSeparatorPos < mValue.length())
        {
            char c = mValue.charAt(mNextSeparatorPos);
            if (c == mSeparator)
            {
                // count this split
                mSplitCount++;
                return;
            }
        }
    }
}
