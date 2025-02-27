package org.dmfs.rfc3986.lexmachina.grammar;

import org.dmfs.lexmachina.tokenstream.StringTokenStream;
import org.junit.jupiter.api.Test;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Object.hasToString;

class UriGrammarTest
{
    @Test
    void testEmptyHost()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("///path")),
            hasToString("AUTHORITY_PREFIX(HOST(), ROOT_PATH(PATH(path)))"));
    }

    @Test
    void testAllParts()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://user@host:1234/abc/xyz?query#fragment")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(PORT_SEPARATOR(USERINFO_SEPARATOR(USERINFO(user), HOST(host)), PORT(1234)), FRAGMENT_SEPARATOR(QUERY_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), QUERY(query)), FRAGMENT(fragment))))"));
    }

    @Test
    void testNoUserInfo()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://host:1234/abc/xyz?query#fragment")),
            hasToString(""));
    }

    @Test
    void testNoPort()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://userinfo@host/abc/xyz?query#fragment")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(USERINFO_SEPARATOR(USERINFO(userinfo), HOST(host)), FRAGMENT_SEPARATOR(QUERY_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), QUERY(query)), FRAGMENT(fragment))))"));
    }

    @Test
    void testNoQuery()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://user@host:1234/abc/xyz#fragment")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(PORT_SEPARATOR(USERINFO_SEPARATOR(USERINFO(user), HOST(host)), PORT(1234)), FRAGMENT_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), FRAGMENT(fragment))))"));
    }

    @Test
    void testNoUserInfoNoQuery()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://host:1234/abc/xyz#fragment")),
            hasToString(""));
    }

    @Test
    void testNoPortNoQuery()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://userinfo@host/abc/xyz#fragment")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(USERINFO_SEPARATOR(USERINFO(userinfo), HOST(host)), FRAGMENT_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), FRAGMENT(fragment))))"));
    }

    @Test
    void testNoFragment()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://user@host:1234/abc/xyz?query")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(PORT_SEPARATOR(USERINFO_SEPARATOR(USERINFO(user), HOST(host)), PORT(1234)), QUERY_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), QUERY(query))))"));
    }

    @Test
    void testNoUserInfoNoFragment()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://host:1234/abc/xyz?query")),
            hasToString(""));
    }

    @Test
    void testNoPortNoFragment()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://userinfo@host/abc/xyz?query")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(USERINFO_SEPARATOR(USERINFO(userinfo), HOST(host)), QUERY_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), QUERY(query))))"));
    }

    @Test
    void testNoQueryNoFragment()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://user@host:1234/abc/xyz")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(PORT_SEPARATOR(USERINFO_SEPARATOR(USERINFO(user), HOST(host)), PORT(1234)), ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz)))))"));
    }

    @Test
    void testNoUserInfoNoQueryNoFragment()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://host:1234/abc/xyz")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(PORT_SEPARATOR(HOST(host), PORT(1234)), ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz)))))"));
    }

    @Test
    void testNoPortNoQueryNoFragment()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://userinfo@host/abc/xyz")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(USERINFO_SEPARATOR(USERINFO(userinfo), HOST(host)), ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz)))))"));
    }


    @Test
    void testRelativePath()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("abc/def/ghi/jkl")),
            hasToString("PATH_SEPARATOR(PATH(abc), PATH_SEPARATOR(PATH(def), PATH_SEPARATOR(PATH(ghi), PATH(jkl))))"));
    }

    @Test
    void testAbsolutePath()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("/abc/xyz")),
            hasToString("ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz)))"));
    }


    @Test
    void testRelativePathWithQuery()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("/abc/xyz?wert")),
            hasToString("QUERY_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), QUERY(wert))"));
    }

    @Test
    void testAbsolutePathWithQuery()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("abc/xyz?wert")),
            hasToString(""));
    }

    @Test
    void testRelativePathWithQueryAndFragment()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("/abc/xyz?wert#foobar")),
            hasToString(""));
    }


    @Test
    void testRelativePathWithFragment()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("/abc/xyz#foobar")),
            hasToString(""));
    }


    @Test
    void testHostPath()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("//host/abc/xyz")),
            hasToString("AUTHORITY_PREFIX(HOST(host), ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))))"));
    }


    @Test
    void testAbsolute()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://host/abc/xyz")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(HOST(host), ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz)))))"));
    }


    @Test
    void testSchemeAndPath()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme:/abc/xyz")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))))"));
    }

    @Test
    void testSchemeOnly()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme:")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), END_OF_URI())"));
    }

    @Test
    void testSchemeAndHost()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://host")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(HOST(host), PATH()))"));
    }


    @Test
    void testAbsoluteFull()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://host/abc/xyz?query#fragment")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(HOST(host), FRAGMENT_SEPARATOR(QUERY_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), QUERY(query)), FRAGMENT(fragment))))"));
    }


    @Test
    void testAbsoluteFullNoQuery()
    {
        assertThat(new UriGrammar().parse(new StringTokenStream("scheme://host/abc/xyz#fragment")),
            hasToString("SCHEME_SEPARATOR(SCHEME(scheme), AUTHORITY_PREFIX(HOST(host), FRAGMENT_SEPARATOR(ROOT_PATH(PATH_SEPARATOR(PATH(abc), PATH(xyz))), FRAGMENT(fragment))))"));
    }

}