package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.jems2.iterable.Seq;
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
                    new Seq<>(AUTHORITY_PREFIX),
                    new Authority(),
                    new BinaryInfixTerm(
                        new Seq<>(FRAGMENT_SEPARATOR),
                        new BinaryInfixTerm(
                            new Seq<>(QUERY_SEPARATOR),
                            new UnaryPrefixTerm(new Seq<>(ROOT_PATH), new PathTerm()),
                            new LiteralTerm(QUERY)),
                        new LiteralTerm(FRAGMENT)
                    ))));
    }
}
