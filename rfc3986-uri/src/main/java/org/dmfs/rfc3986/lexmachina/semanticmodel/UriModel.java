package org.dmfs.rfc3986.lexmachina.semanticmodel;

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.optional.Absent;
import org.dmfs.jems2.optional.Present;
import org.dmfs.lexmachina.Expression;
import org.dmfs.lexmachina.SemanticModel;
import org.dmfs.lexmachina.TokenType;
import org.dmfs.lexmachina.expression.LiteralExpression;
import org.dmfs.rfc3986.Authority;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.Precoded;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static org.dmfs.jems2.optional.Absent.absent;
import static org.dmfs.rfc3986.lexmachina.tokentype.UriToken.*;

public final class UriModel implements SemanticModel
{
    @Override
    public Expression literal(TokenType tokenType, String s)
    {
        if (PORT.equals(tokenType))
        {
            return s.isEmpty() ? new LiteralExpression(Absent.absent()) : new LiteralExpression(new Present<>(Integer.parseInt(s)));
        }
        if (HOST.equals(tokenType))
        {
            return new LiteralExpression(new Authority()
            {

                @Override
                public Optional<UriEncoded> userInfo()
                {
                    return absent();
                }

                @Override
                public UriEncoded host()
                {
                    return new Precoded(s);
                }

                @Override
                public Optional<Integer> port()
                {
                    return absent();
                }
            });
        }
        if (USERINFO.equals(tokenType))
        {
            return new LiteralExpression(s);
        }
        throw new IllegalArgumentException("unknown tokentype " + tokenType);
    }

    @Override
    public UnaryOperator<Object> unary(TokenType tokenType)
    {
        return null;
    }

    @Override
    public BinaryOperator<Object> binary(TokenType tokenType)
    {
        if (PORT_SEPARATOR.equals(tokenType))
        {
            return (authority, optionalPort) -> new Authority()
            {
                @Override
                public Optional<UriEncoded> userInfo()
                {
                    return ((Authority) authority).userInfo();
                }

                @Override
                public UriEncoded host()
                {
                    return ((Authority) authority).host();
                }

                @Override
                public Optional<Integer> port()
                {
                    return (Optional<Integer>) optionalPort;
                }
            };
        }
        if (USERINFO_SEPARATOR.equals(tokenType))
        {
            return (userInfo, authority) -> new Authority()
            {
                @Override
                public Optional<UriEncoded> userInfo()
                {
                    return new Present<>(new Precoded((String) userInfo));
                }

                @Override
                public UriEncoded host()
                {
                    return ((Authority) authority).host();
                }

                @Override
                public Optional<Integer> port()
                {
                    return ((Authority) authority).port();
                }
            };
        }
        throw new IllegalArgumentException("unknown tokentype " + tokenType);
    }
}
