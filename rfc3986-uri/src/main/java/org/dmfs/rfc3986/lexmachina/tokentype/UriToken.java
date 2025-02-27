package org.dmfs.rfc3986.lexmachina.tokentype;

import org.dmfs.lexmachina.TokenType;
import org.dmfs.lexmachina.tokentype.*;

import static org.dmfs.lexmachina.tokentype.MathTokenType.DIGIT;
import static org.dmfs.lexmachina.tokentype.MathTokenType.INTEGER;
import static org.dmfs.lexmachina.tokentype.TextTokenType.ASCII_CHAR;
import static org.dmfs.rfc3986.validation.CharSets.*;

public enum UriToken implements TokenType
{
    SCHEME(new SequenceOf(new CharSetToken(ALPHA), new Optional(new OneOrMoreOf(new CharSetToken(SCHEME_CHAR))))), // FIXME
    SCHEME_SEPARATOR(new CharTokenType(':')),
    PORT_SEPARATOR(new CharTokenType(':')),
    USERINFO_SEPARATOR(new CharTokenType('@')),
    DOT(new CharTokenType('.')),
    PORT(new Optional(INTEGER)),
    AUTHORITY_PREFIX(new StringTokenType("//")),
    HEX_DIGIT(new OneOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F')),
    DEC_OCTET(new OneOf(
        new SequenceOf(new StringTokenType("25"), new OneOf('0', '1', '2', '3', '4', '5')),
        new SequenceOf(new CharTokenType('2'), new OneOf('0', '1', '2', '3', '4'), DIGIT),
        new SequenceOf(new CharTokenType('1'), DIGIT, DIGIT),
        new SequenceOf(new OneOf('1', '2', '3', '4', '5', '6', '7', '8', '9'), DIGIT),
        DIGIT)),
    IP4(new SequenceOf(DEC_OCTET, DOT, DEC_OCTET, DOT, DEC_OCTET, DOT, DEC_OCTET)),
    IP6(new SequenceOf(new CharTokenType('x'))), // FIXME
    IP_FUTURE(new SequenceOf(new CharTokenType('v'), new OneOrMoreOf(HEX_DIGIT), DOT, new OneOrMoreOf(ASCII_CHAR))), //FIXME
    IP_LITERAL(new SequenceOf(new CharTokenType('['), new OneOf(IP6, IP_FUTURE), new CharTokenType(']'))),
    USERINFO(new Optional(new OneOrMoreOf(new CharSetToken(REG_NAME_CHAR)))), //FIXME
    REG_NAME(new Optional(new OneOrMoreOf(new CharSetToken(REG_NAME_CHAR)))), //FIXME
    HOST(new OneOf(IP_FUTURE, IP4, REG_NAME)),
    PATH(new Optional(new OneOrMoreOf(new CharSetToken(PCHAR)))), //FIXME
    QUERY(new Optional(new OneOrMoreOf(new CharSetToken(QUERY_CHAR)))), //FIXME
    FRAGMENT(new Optional(new OneOrMoreOf(new CharSetToken(FRAGMENT_CHAR)))), //FIXME
    ROOT_PATH(new CharTokenType('/')),
    PATH_SEPARATOR(new CharTokenType('/')),
    QUERY_SEPARATOR(new CharTokenType('?')),
    FRAGMENT_SEPARATOR(new CharTokenType('#'));


    private final TokenType mDelegate;


    UriToken(char value)
    {
        this(new CharTokenType(value));
    }

    UriToken(String value)
    {
        this(new StringTokenType(value));
    }

    UriToken(TokenType delegate)
    {
        mDelegate = delegate;
    }

    @Override
    public int matchingChars(String string, int pos)
    {
        return mDelegate.matchingChars(string, pos);
    }

}
