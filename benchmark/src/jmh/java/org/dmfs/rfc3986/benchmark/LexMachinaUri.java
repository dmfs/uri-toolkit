package org.dmfs.rfc3986.benchmark;

import org.dmfs.lexmachina.Ast;
import org.dmfs.lexmachina.tokenstream.StringTokenStream;
import org.dmfs.rfc3986.lexmachina.grammar.UriGrammar;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@Fork(5)
public class LexMachinaUri
{

    @Benchmark
    public Ast testRegularParse()
    {
        return new UriGrammar().parse(new StringTokenStream(Const.regularUri));
    }

    @Benchmark
    public Ast testShortParse()
    {
        return new UriGrammar().parse(new StringTokenStream(Const.shortUri));
    }

}
