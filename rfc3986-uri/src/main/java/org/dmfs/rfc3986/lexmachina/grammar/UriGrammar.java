package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.lexmachina.GrammarComposition;
import org.dmfs.lexmachina.grammar.BinaryInfixTerm;
import org.dmfs.lexmachina.grammar.LiteralTerm;
import org.dmfs.lexmachina.grammar.UnaryPrefixTerm;

import static org.dmfs.rfc3986.lexmachina.tokentype.UriToken.*;

public final class UriGrammar extends GrammarComposition
{
    public UriGrammar()
    {
        super(
            new SeparatedPrefixTerm(
                SCHEME,
                SCHEME_SEPARATOR,
                new RBinaryPrefixTerm(
                    AUTHORITY_PREFIX,
                    new Authority(),
                    new BinaryInfixTerm(
                        FRAGMENT_SEPARATOR,
                        new BinaryInfixTerm(
                            QUERY_SEPARATOR,
                            new UnaryPrefixTerm(ROOT_PATH, new PathTerm()),
                            new LiteralTerm(QUERY)),
                        new LiteralTerm(FRAGMENT)
                    ))));
    }
}
