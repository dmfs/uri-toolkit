package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.jems2.Optional;
import org.dmfs.lexmachina.Ast;
import org.dmfs.lexmachina.Grammar;
import org.dmfs.lexmachina.TokenStream;
import org.dmfs.lexmachina.TokenType;
import org.dmfs.lexmachina.ast.BinaryNode;
import org.dmfs.lexmachina.ast.LiteralNode;

import java.util.List;

public final class SeparatedPrefixTerm implements Grammar
{
    private final TokenType mPrefix;
    private final TokenType mSeparator;
    private final Grammar mDelegate;

    public SeparatedPrefixTerm(TokenType prefix, TokenType separator, Grammar delegate)
    {
        mPrefix = prefix;
        mSeparator = separator;
        mDelegate = delegate;
    }

    @Override
    public Ast parse(TokenStream tokenStream)
    {
        Optional<List<String>> tokens = tokenStream.consume(mPrefix, mSeparator);
        if (tokens.isPresent())
        {
            List<String> tokenList = tokens.value();
            return new BinaryNode(mSeparator, new LiteralNode(mPrefix, tokenList.get(0)), mDelegate.parse(tokenStream));
        }
        return mDelegate.parse(tokenStream);
    }
}
