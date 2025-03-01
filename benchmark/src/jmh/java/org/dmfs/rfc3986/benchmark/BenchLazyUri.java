package org.dmfs.rfc3986.benchmark;

import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.uris.LazyUri;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@Fork(5)
public class BenchLazyUri
{

    @Benchmark
    public boolean testRegularParser()
    {
        return new LazyUri(new Precoded(Const.regularUri)).fragment().isPresent();
    }

    @Benchmark
    public boolean testShortParser()
    {
        return new LazyUri(new Precoded(Const.shortUri)).fragment().isPresent();
    }

}
