package org.dmfs.rfc3986.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

import java.net.URI;

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@Fork(5)
public class JdkUri
{

    @Benchmark
    public URI testRegularUriParser()
    {
        return URI.create(Const.regularUri);
    }


    @Benchmark
    public URI testShortUriParser()
    {
        return URI.create(Const.shortUri);
    }

}
