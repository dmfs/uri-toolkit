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

package org.dmfs.rfc3986.parameters;

import org.dmfs.rfc3986.parameters.parametersets.BasicParameterList;
import org.dmfs.rfc3986.parameters.parametersets.Fluent;
import org.dmfs.rfc3986.parameters.parametertypes.BasicParameterType;
import org.dmfs.rfc3986.parameters.valuetypes.TextValueType;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 */
public class FluentTest
{

    @Test
    public void alsoWith() throws Exception
    {
        ValueType<CharSequence> valueType = new TextValueType();
        ParameterType<CharSequence> textParam = new BasicParameterType<>("test", valueType);

        FluentParameterList fluentParams = new Fluent();
        fluentParams = fluentParams.alsoWith(textParam.parameter("123"));
        fluentParams = fluentParams.alsoWith(textParam.parameter("abc"));
        fluentParams = fluentParams.alsoWith(textParam.parameter("def"));

        // all values should have been added
        Iterator<Parameter> params = fluentParams.iterator();
        Parameter param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("123", param.textValue().toString());

        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("abc", param.textValue().toString());

        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("def", param.textValue().toString());

        assertFalse(params.hasNext());
    }


    @Test
    public void alsoWith2() throws Exception
    {
        ValueType<CharSequence> valueType = new TextValueType();
        ParameterType<CharSequence> textParam = new BasicParameterType<>("test", valueType);
        ParameterType<CharSequence> textParam2 = new BasicParameterType<>("q", valueType);

        FluentParameterList fluentParams = new Fluent(new BasicParameterList(textParam.parameter("x"), textParam2.parameter("y")));
        fluentParams = fluentParams.alsoWith(textParam.parameter("123"));
        fluentParams = fluentParams.alsoWith(textParam.parameter("abc"));
        fluentParams = fluentParams.alsoWith(textParam.parameter("def"));

        // all values should have been added
        Iterator<Parameter> params = fluentParams.iterator();
        Parameter param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("x", param.textValue().toString());

        param = params.next();
        assertEquals("q", param.name().toString());
        assertEquals("y", param.textValue().toString());

        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("123", param.textValue().toString());

        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("abc", param.textValue().toString());

        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("def", param.textValue().toString());

        assertFalse(params.hasNext());
    }


    @Test
    public void ratherWith() throws Exception
    {
        ValueType<CharSequence> valueType = new TextValueType();
        ParameterType<CharSequence> textParam = new BasicParameterType<>("test", valueType);

        FluentParameterList fluentParams = new Fluent();
        fluentParams = fluentParams.ratherWith(textParam.parameter("123"));

        Iterator<Parameter> params = fluentParams.iterator();
        Parameter param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("123", param.textValue().toString());
        assertFalse(params.hasNext());

        fluentParams = fluentParams.ratherWith(textParam.parameter("abc"));

        // the last value should replace the previous one
        params = fluentParams.iterator();
        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("abc", param.textValue().toString());
        assertFalse(params.hasNext());

        fluentParams = fluentParams.ratherWith(textParam.parameter("def"));

        // the last value should replace the previous one
        params = fluentParams.iterator();
        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("def", param.textValue().toString());
        assertFalse(params.hasNext());
    }


    @Test
    public void ratherWith2() throws Exception
    {
        ValueType<CharSequence> valueType = new TextValueType();
        ParameterType<CharSequence> textParam = new BasicParameterType<>("test", valueType);
        ParameterType<CharSequence> textParam2 = new BasicParameterType<>("q", valueType);

        FluentParameterList fluentParams = new Fluent(new BasicParameterList(textParam.parameter("x"), textParam2.parameter("y")));
        fluentParams = fluentParams.ratherWith(textParam.parameter("123"));

        Iterator<Parameter> params = fluentParams.iterator();
        Parameter param = params.next();
        assertEquals("q", param.name().toString());
        assertEquals("y", param.textValue().toString());

        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("123", param.textValue().toString());
        assertFalse(params.hasNext());

        fluentParams = fluentParams.ratherWith(textParam.parameter("abc"));

        // the last value should replace the previous one
        params = fluentParams.iterator();
        param = params.next();
        assertEquals("q", param.name().toString());
        assertEquals("y", param.textValue().toString());

        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("abc", param.textValue().toString());
        assertFalse(params.hasNext());

        fluentParams = fluentParams.ratherWith(textParam.parameter("def"));

        // the last value should replace the previous one
        params = fluentParams.iterator();
        param = params.next();
        assertEquals("q", param.name().toString());
        assertEquals("y", param.textValue().toString());

        param = params.next();
        assertEquals("test", param.name().toString());
        assertEquals("def", param.textValue().toString());
        assertFalse(params.hasNext());
    }


    @Test
    public void without() throws Exception
    {
        ValueType<CharSequence> valueType = new TextValueType();
        ParameterType<CharSequence> textParam = new BasicParameterType<>("test", valueType);
        ParameterType<CharSequence> textParam2 = new BasicParameterType<>("q", valueType);

        FluentParameterList fluentParams = new Fluent(new BasicParameterList(textParam.parameter("x"), textParam2.parameter("y")));
        fluentParams = fluentParams.without(textParam);

        Iterator<Parameter> params = fluentParams.iterator();
        Parameter param = params.next();
        assertEquals("q", param.name().toString());
        assertEquals("y", param.textValue().toString());
        assertFalse(params.hasNext());

        fluentParams = fluentParams.without(textParam);

        params = fluentParams.iterator();
        param = params.next();
        assertEquals("q", param.name().toString());
        assertEquals("y", param.textValue().toString());
        assertFalse(params.hasNext());

        fluentParams = fluentParams.without(textParam2);
        params = fluentParams.iterator();
        assertFalse(params.hasNext());
    }
}