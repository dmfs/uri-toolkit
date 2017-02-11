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

package org.dmfs.rfc3986.parameters.parameters;

import org.dmfs.rfc3986.encoding.Precoded;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author marten
 */
public class UrlEncodedParameterTest
{

    @Test
    public void name() throws Exception
    {
        assertEquals("test", new UrlEncodedParameter(new Precoded("test")).name().toString());
        assertEquals("testa", new UrlEncodedParameter(new Precoded("test%61")).name().toString());
        assertEquals("test=", new UrlEncodedParameter(new Precoded("test%3D")).name().toString());
        assertEquals("test", new UrlEncodedParameter(new Precoded("test=123")).name().toString());
        assertEquals("testa", new UrlEncodedParameter(new Precoded("test%61=123%34")).name().toString());
        assertEquals("test=", new UrlEncodedParameter(new Precoded("test%3D=123%3D")).name().toString());
    }


    @Test
    public void textValue() throws Exception
    {
        assertEquals("", new UrlEncodedParameter(new Precoded("test")).textValue().toString());
        assertEquals("", new UrlEncodedParameter(new Precoded("test%61")).textValue().toString());
        assertEquals("", new UrlEncodedParameter(new Precoded("test%3D")).textValue().toString());
        assertEquals("123", new UrlEncodedParameter(new Precoded("test=123")).textValue().toString());
        assertEquals("1234", new UrlEncodedParameter(new Precoded("test%61=123%34")).textValue().toString());
        assertEquals("123=", new UrlEncodedParameter(new Precoded("test%3D=123%3D")).textValue().toString());
    }

}