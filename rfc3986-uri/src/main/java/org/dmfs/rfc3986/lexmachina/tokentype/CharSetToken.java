package org.dmfs.rfc3986.lexmachina.tokentype;

import org.dmfs.lexmachina.TokenTypeComposition;
import org.dmfs.rfc3986.validation.CharSet;

public final class CharSetToken extends TokenTypeComposition
{
    public CharSetToken(CharSet charSet)
    {
        super((c, pos) -> pos < c.length() && charSet.contains(c.charAt(pos)) ? 1 : -1);
    }
}
