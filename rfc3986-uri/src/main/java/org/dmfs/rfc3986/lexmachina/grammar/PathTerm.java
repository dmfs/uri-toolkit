package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.jems2.Optional;
import org.dmfs.lexmachina.Ast;
import org.dmfs.lexmachina.Grammar;
import org.dmfs.lexmachina.TokenStream;
import org.dmfs.lexmachina.ast.BinaryNode;
import org.dmfs.lexmachina.ast.LiteralNode;

import static org.dmfs.rfc3986.lexmachina.tokentype.UriToken.PATH;
import static org.dmfs.rfc3986.lexmachina.tokentype.UriToken.PATH_SEPARATOR;


/**
 *
 */
public final class PathTerm implements Grammar
{

    @Override
    public Ast parse(TokenStream tokenStream)
    {
        Optional<String> token = tokenStream.consumeOne(PATH);
        if (token.isPresent())
        {
            Ast node = new LiteralNode(PATH, token.value());
            if (tokenStream.consumeOne(PATH_SEPARATOR).isPresent())
            {
                return new BinaryNode(PATH_SEPARATOR, node, this.parse(tokenStream));
            }
            else
            {
                return node;
            }
        }
        throw new RuntimeException("we should not be here, path always matches");
    }
}
