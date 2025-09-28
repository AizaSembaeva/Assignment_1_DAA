package org.example;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SelectBenchmark {

    @Param({"1000", "10000", "100000"})
    private int size;

    private int[] array;

    @Setup(Level.Iteration)
    public void setup() {
        array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * size);
        }
    }

    @Benchmark
    public int benchmarkDeterministicSelect() {
        Metrics metrics = new Metrics();
        return DeterministicSelect.select(Arrays.copyOf(array, array.length), array.length / 2, metrics);
    }

    @Benchmark
    public int benchmarkArraysSort() {
        int[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);
        return copy[copy.length / 2];
    }
}
