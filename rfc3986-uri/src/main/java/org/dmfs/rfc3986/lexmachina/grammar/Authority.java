package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.lexmachina.GrammarComposition;
import org.dmfs.lexmachina.grammar.BinaryInfixTerm;
import org.dmfs.lexmachina.grammar.LiteralTerm;

import static org.dmfs.rfc3986.lexmachina.tokentype.UriToken.*;

public final class Authority extends GrammarComposition
{
    public Authority()
    {
        super(new BinaryInfixTerm(
            PORT_SEPARATOR,
            new SeparatedPrefixTerm(
                USERINFO,
                USERINFO_SEPARATOR,
                new LiteralTerm(HOST)),
            new LiteralTerm(PORT)));
    }
}
