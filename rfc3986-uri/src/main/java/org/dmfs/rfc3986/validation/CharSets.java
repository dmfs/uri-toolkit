/*
 * Copyright 2017 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.rfc3986.validation;

/**
 */
public final class CharSets
{
    /**
     * {@link BitMapCharSet} for alphabet characters.
     */
    public final static CharSet ALPHA = new BitMapCharSet(0, 0, 0x07FFFFFE, 0x07FFFFFE);

    /**
     * {@link BitMapCharSet} for digits.
     */
    public final static CharSet DIGIT = new BitMapCharSet(0, 0x03FF0000, 0, 0);

    /**
     * {@link BitMapCharSet} for unreserved characters as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * unreserved    = ALPHA / DIGIT / "-" / "." / "_" / "~"
     * </pre>
     */
    public final static CharSet UNRESERVED = new BitMapCharSet(0, 0x03FF6000, 0x87FFFFFE, 0x47FFFFFE);

    /**
     * {@link BitMapCharSet} for scheme characters  as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * scheme        = ALPHA *( ALPHA / DIGIT / "+" / "-" / "." )
     * </pre>
     */
    public final static CharSet SCHEME_CHAR = new BitMapCharSet(0, 0x03FF6800, 0x07FFFFFE, 0x07FFFFFE);

    /**
     * {@link BitMapCharSet} for percent encoded characters as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * pct-encoded   = "%" HEXDIG HEXDIG
     * </pre>
     */
    public final static CharSet PCT_ENCODED = new BitMapCharSet(0, 0x03FF0020, 0x0000007E, 0x0000007E);

    /**
     * {@link BitMapCharSet} for general delimiter characters as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * gen-delims    = ":" / "/" / "?" / "#" / "[" / "]" / "@"
     * </pre>
     */
    public final static CharSet GEN_DELIMS = new BitMapCharSet(0, 0x84008008, 0x28000001, 0x00000000);

    /**
     * {@link BitMapCharSet} of characters that terminate the host part of an authority <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986,
     * Appendix A</a>.
     * <pre>
     * gen-delims    = ":" / "/" / "?" / "#"
     * </pre>
     */
    public final static CharSet HOST_TERMINATOR_CHARS = new BitMapCharSet(0, 0x84008008, 0x00000000, 0x00000000);

    /**
     * {@link BitMapCharSet} for sub-delimiter characters as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * sub-delims    = "!" / "$" / "&amp;" / "'" / "(" / ")"
     *               / "*" / "+" / "," / ";" / "="
     * </pre>
     */
    public final static CharSet SUB_DELIMS = new BitMapCharSet(0, 0x28001FD2, 0x00000000, 0x00000000);

    /**
     * {@link BitMapCharSet} for characters of a registered name as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * reg-name      = *( unreserved / pct-encoded / sub-delims )
     * </pre>
     */
    public final static CharSet REG_NAME_CHAR = new BitMapCharSet(0, 0x2BFF7FF2, 0x87FFFFFE, 0x47FFFFFE);

    /**
     * {@link BitMapCharSet} for pchar characters  as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * pchar         = unreserved / pct-encoded / sub-delims / ":" / "@"
     * </pre>
     */
    public final static CharSet PCHAR = new BitMapCharSet(0, 0x2FFF7FF2, 0x87FFFFFF, 0x47FFFFFE);

    // segments may contain any PCHAR
    public final static CharSet SEGMENT_CHAR = PCHAR;

    /**
     * {@link BitMapCharSet} for query characters  as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * query         = *( pchar / "/" / "?" )
     * </pre>
     */
    public final static CharSet QUERY_CHAR = new BitMapCharSet(0, 0xAFFFFFF2, 0x87FFFFFF, 0x47FFFFFE);

    /**
     * {@link BitMapCharSet} for fragment characters  as per <a href="https://tools.ietf.org/html/rfc3986#appendix-A">RFC 3986, Appendix A</a>.
     * <pre>
     * fragment         = *( pchar / "/" / "?" )
     * </pre>
     */
    // this equals QUERY_CHAR
    public final static CharSet FRAGMENT_CHAR = QUERY_CHAR;


    private CharSets()
    {
    }
}
