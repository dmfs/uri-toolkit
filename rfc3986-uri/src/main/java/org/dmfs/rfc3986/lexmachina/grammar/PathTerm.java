package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.jems2.Optional;
import org.dmfs.lexmachina.Ast;
import org.dmfs.lexmachina.Grammar;
import org.dmfs.lexmachina.TokenStream;
import org.dmfs.lexmachina.ast.BinaryNode;
import org.dmfs.lexmachina.ast.LiteralNode;

import java.util.List;

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
        Optional<List<String>> token = tokenStream.consume(PATH);
        if (token.isPresent())
        {
            Ast node = new LiteralNode(PATH, token.value().get(0));
            if ((token = tokenStream.consume(PATH_SEPARATOR)).isPresent())
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
