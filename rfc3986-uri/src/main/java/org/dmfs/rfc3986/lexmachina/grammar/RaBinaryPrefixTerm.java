package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.jems2.Optional;
import org.dmfs.lexmachina.Ast;
import org.dmfs.lexmachina.Grammar;
import org.dmfs.lexmachina.TokenStream;
import org.dmfs.lexmachina.TokenType;
import org.dmfs.lexmachina.ast.BinaryNode;


/**
 *
 */
public final class RaBinaryPrefixTerm implements Grammar
{
    private final Iterable<TokenType> mTokenTypes;
    private final Grammar mLeftDelegate;
    private final Grammar mRightDelegate;

    public RaBinaryPrefixTerm(Iterable<TokenType> tokenTypes, Grammar delegate)
    {
        this(tokenTypes, delegate, delegate);
    }

    public RaBinaryPrefixTerm(Iterable<TokenType> tokenTypes, Grammar leftDelegate, Grammar rightDelegate)
    {
        mTokenTypes = tokenTypes;
        mLeftDelegate = leftDelegate;
        mRightDelegate = rightDelegate;
    }

    @Override
    public Ast parse(TokenStream tokenStream)
    {
        for (TokenType tokenType : mTokenTypes)
        {
            Optional<?> token = tokenStream.consume(tokenType);
            if (token.isPresent())
            {
                return new BinaryNode(tokenType, mLeftDelegate.parse(tokenStream), this.parse(tokenStream));
            }
        }
        return mRightDelegate.parse(tokenStream);
    }
}
