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
public final class RBinaryPrefixTerm implements Grammar
{
    private final TokenType mTokenType;
    private final Grammar mLeftDelegate;
    private final Grammar mRightDelegate;

    public RBinaryPrefixTerm(TokenType tokenType, Grammar leftDelegate, Grammar rightDelegate)
    {
        mTokenType = tokenType;
        mLeftDelegate = leftDelegate;
        mRightDelegate = rightDelegate;
    }

    @Override
    public Ast parse(TokenStream tokenStream)
    {
        Optional<?> token = tokenStream.consumeOne(mTokenType);
        if (token.isPresent())
        {
            return new BinaryNode(mTokenType, mLeftDelegate.parse(tokenStream), mRightDelegate.parse(tokenStream));
        }
        return mRightDelegate.parse(tokenStream);
    }
}
