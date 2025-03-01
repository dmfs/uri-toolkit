package org.dmfs.rfc3986.lexmachina.tokentype;

import org.dmfs.lexmachina.TokenType;
import org.dmfs.lexmachina.tokentype.CharSequence;
import org.dmfs.lexmachina.tokentype.*;
import org.dmfs.rfc3986.lexmachina.grammar.FirstAndAnyNumberOf;
import org.dmfs.rfc3986.validation.CharSets;

import static org.dmfs.lexmachina.tokentype.MathTokenType.DIGIT;
import static org.dmfs.lexmachina.tokentype.TextTokenType.ASCII_CHAR;
import static org.dmfs.rfc3986.validation.CharSets.*;

public enum UriToken implements TokenType
{
    SCHEME(new FirstAndAnyNumberOf(ALPHA, SCHEME_CHAR)),
    SCHEME_SEPARATOR(new CharTokenType(':')),
    PORT_SEPARATOR(new CharTokenType(':')),
    USERINFO_SEPARATOR(new CharTokenType('@')),
    DOT(new CharTokenType('.')),
    PORT(new AnyNumberOf(CharSets.DIGIT)),
    AUTHORITY_PREFIX(new CharSequence(c -> c == '/', c -> c == '/')),
    HEX_DIGIT(new CharPredicateType(HEXDIGIT)),
    PCT_ENCODED(new CharSequence(c -> c == '%', HEXDIGIT, HEXDIGIT)),
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
    USERINFO(new AnyNumberOf(REG_NAME_CHAR)), // TODO: check
    REG_NAME(new AnyNumberOf(REG_NAME_CHAR)), // TODO: check
    HOST(new OneOf(REG_NAME, IP4, IP_LITERAL)),
    PATH(new AnyNumberOf(PCHAR)),
    FULL_PATH(new AnyNumberOf(PATHCHAR)),
    QUERY(new AnyNumberOf(QUERY_CHAR)),
    FRAGMENT(new AnyNumberOf(FRAGMENT_CHAR)),
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
