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

package org.dmfs.rfc3986.uris;

import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.encoding.Precoded;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marten
 */
public final class Bench
{
    @Test
    public void bench() throws InterruptedException
    {
        String uriStr = "http://www.example.com/my/path/to/file.txt?q=search&filter=none#field1=set&field2=clear";
        run(new JavaUriBench(), uriStr);
        run(new OurUriBench(), uriStr);
        run(new JavaUriBench(), uriStr);
        run(new OurUriBench(), uriStr);
        run(new JavaUriBench(), uriStr);
        run(new OurUriBench(), uriStr);
    }


    private void run(Benchmark b, String uriString) throws InterruptedException
    {
        List<Object> list = new ArrayList<>(10000);
        Runtime rt = Runtime.getRuntime();
        System.gc();
        Thread.sleep(1000);
        long total = rt.totalMemory();
        long max = rt.maxMemory();
        long free = rt.freeMemory();
        long start = System.currentTimeMillis();

        b.bench(list, 10000, uriString);

        int count = list.size();
        start -= System.currentTimeMillis();
        total -= rt.totalMemory();
        max -= rt.maxMemory();
        free -= rt.freeMemory();

        System.out.println(
                String.format("Class %50s, count: %10d, dtotal %10d, dmax %10d, dfree %10d, dtime %5d, bytesperuri %5d", b.getClass().getCanonicalName(), count,
                        -total, -max,
                        -free, -start, free / count));
        System.gc();
        Thread.sleep(1000);
    }


    private interface Benchmark
    {
        void bench(List<Object> results, int count, String uriStr);
    }


    private final static class JavaUriBench implements Benchmark
    {

        @Override
        public void bench(List<Object> results, int count, String uriStr)
        {
            for (int i = 0; i < count; ++i)
            {
                results.add(URI.create(uriStr));
            }
        }
    }


    private final static class OurUriBench implements Benchmark
    {

        @Override
        public void bench(List<Object> results, int count, String uriStr)
        {
            for (int i = 0; i < count; ++i)
            {
                Uri uri = new LazyUri(new Precoded(uriStr));
                results.add(uri);
                uri.fragment().isPresent();
            }
        }
    }

}

