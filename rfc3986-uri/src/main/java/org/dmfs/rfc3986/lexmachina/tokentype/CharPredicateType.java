package org.dmfs.rfc3986.lexmachina.tokentype;

import org.dmfs.lexmachina.CharPredicate;
import org.dmfs.lexmachina.TokenType;

public final class CharPredicateType implements TokenType
{
    private final CharPredicate mPredicate;

    public CharPredicateType(CharPredicate predicate)
    {
        mPredicate = predicate;
    }

    @Override
    public int matchingChars(String string, int pos)
    {
        return pos < string.length() && mPredicate.isSatisfiedBy(string.charAt(pos)) ? 1 : -1;
    }
}
