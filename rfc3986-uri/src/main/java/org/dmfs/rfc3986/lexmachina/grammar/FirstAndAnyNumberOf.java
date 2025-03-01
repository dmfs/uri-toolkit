package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.lexmachina.CharPredicate;
import org.dmfs.lexmachina.TokenType;

public final class FirstAndAnyNumberOf implements TokenType
{
    private final CharPredicate mFirstPredicate;
    private final CharPredicate mPredicate;

    public FirstAndAnyNumberOf(CharPredicate firstPredicate, CharPredicate predicate)
    {
        mFirstPredicate = firstPredicate;
        mPredicate = predicate;
    }

    @Override
    public int matchingChars(String string, int pos)
    {
        int len = string.length();
        if (len <= pos || !mFirstPredicate.isSatisfiedBy(string.charAt(0)))
        {
            return -1;
        }
        int index = pos + 1;
        while (index < len && mPredicate.isSatisfiedBy(string.charAt(index)))
        {
            index++;
        }
        return index - pos;
    }
}
