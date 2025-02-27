package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.lexmachina.context.EmptyContext;
import org.dmfs.lexmachina.tokenstream.StringTokenStream;
import org.dmfs.rfc3986.lexmachina.semanticmodel.UriModel;
import org.junit.jupiter.api.Test;

import static org.dmfs.jems2.confidence.Jems2.absent;
import static org.dmfs.jems2.confidence.Jems2.present;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Composite.allOf;
import static org.saynotobugs.confidence.core.quality.Composite.has;
import static org.saynotobugs.confidence.core.quality.Function.maps;
import static org.saynotobugs.confidence.core.quality.Grammar.to;
import static org.saynotobugs.confidence.core.quality.Object.hasToString;
import static org.saynotobugs.confidence.core.quality.Object.instanceOf;

class AuthorityTest
{
    @Test
    void testAllPresent()
    {
        assertThat(new Authority().parse(new StringTokenStream("userinfo@host:123")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, present(hasToString("userinfo"))),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("host")),
                    has("port", org.dmfs.rfc3986.Authority::port, present(123))
                )))));
    }

    @Test
    void testNoUserInfo()
    {
        assertThat(new Authority().parse(new StringTokenStream("host:123")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, absent()),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("host")),
                    has("port", org.dmfs.rfc3986.Authority::port, present(123))
                )))));
    }

    @Test
    void testNoPort()
    {
        assertThat(new Authority().parse(new StringTokenStream("userinfo@host")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, present(hasToString("userinfo"))),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("host")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }

    @Test
    void testNoUserInfoNoPort()
    {
        assertThat(new Authority().parse(new StringTokenStream("host")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, absent()),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("host")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }


    @Test
    void testEmptyUserInfo()
    {
        assertThat(new Authority().parse(new StringTokenStream("@host:123")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has((org.dmfs.rfc3986.Authority authority) -> authority.userInfo(), present(hasToString(""))),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("host")),
                    has("port", org.dmfs.rfc3986.Authority::port, present(123))
                )))));
    }


    @Test
    void testEmptyUserInfoNoPort()
    {
        assertThat(new Authority().parse(new StringTokenStream("@host")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, present(hasToString(""))),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("host")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }


    @Test
    void testEmptyHost()
    {
        assertThat(new Authority().parse(new StringTokenStream("userinfo@:123")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, present(hasToString("userinfo"))),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("")),
                    has("port", org.dmfs.rfc3986.Authority::port, present(123))
                )))));
    }

    @Test
    void testNoUserInfoNoHost()
    {
        assertThat(new Authority().parse(new StringTokenStream(":123")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, absent()),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("")),
                    has("port", org.dmfs.rfc3986.Authority::port, present(123))
                )))));
    }

    @Test
    void testNoPortNoHost()
    {
        assertThat(new Authority().parse(new StringTokenStream("userinfo@")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, present(hasToString("userinfo"))),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }

    @Test
    void testNoUserInfoNoPortNoHost()
    {
        assertThat(new Authority().parse(new StringTokenStream("")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, absent()),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }


    @Test
    void testEmptyPort()
    {
        assertThat(new Authority().parse(new StringTokenStream("userinfo@host:")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, present(hasToString("userinfo"))),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("host")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }

    @Test
    void testNoUserInfoEmptyPort()
    {
        assertThat(new Authority().parse(new StringTokenStream("host:")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, absent()),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("host")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }


    @Test
    void testEmptyHostEmptyPort()
    {
        assertThat(new Authority().parse(new StringTokenStream("userinfo@:")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, present(hasToString("userinfo"))),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }

    @Test
    void testNoUserInfoNoHostEmptyPort()
    {
        assertThat(new Authority().parse(new StringTokenStream(":")).expression(new UriModel()),
            maps(new EmptyContext(),
                to(instanceOf(org.dmfs.rfc3986.Authority.class, allOf(
                    has("fragment", org.dmfs.rfc3986.Authority::userInfo, absent()),
                    has("host", org.dmfs.rfc3986.Authority::host, hasToString("")),
                    has("port", org.dmfs.rfc3986.Authority::port, absent())
                )))));
    }


}