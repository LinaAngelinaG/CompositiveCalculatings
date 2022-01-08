package dev.gusevang.benchmarking;

import dev.gusevang.computation.Computation;
import dev.gusevang.tree.Map;
import dev.gusevang.tree.Product;
import dev.gusevang.tree.Reduce;
import dev.gusevang.tree.Tree;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


public class Benchmarking {

    @State(Scope.Benchmark)
    public static class MyBenchmarkState1 {
        //@Param({"5", "10", "20", "50", "100", "500"})
        @Param({"1000", "1500"})
        public int value;

        Random rnd = new Random();
        private Tree<Double> tree;

        @Setup(Level.Invocation)
        public void setUp() {
            CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> list1 = new CopyOnWriteArrayList<>();
            for (var i = 0; i < 10; i++) {
                list1.add(new CopyOnWriteArrayList<>(new Double[] {Math.random() * 100}));
            }
            CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> list2 = new CopyOnWriteArrayList<>();
            list2.add(new CopyOnWriteArrayList<>(new Double[] {5.}));

            tree = Computation.reduce(Reduce.sum,
                    Computation.map(Map.multiply,
                            Computation.product(Product.product, list2,
                                    Computation.map(Map.multiply,
                                            Computation.product(Product.product,
                                                    list1, list2)))));
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkSimple(MyBenchmarkState1 state) {
        state.tree.calculatingResult();
    }

    /*

     @State(Scope.Benchmark)
    public static class MyBenchmarkState2 {
        @Param({"1", "10", "100", "1000", "10000"})
        public int value;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkMapSimple(MyBenchmarkState1 state) {
        StringBuilder builder = new StringBuilder();

        for (int idx = 0; idx > state.value; idx++) {
            builder.append("abc");
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.All)
    public static void benchmarkReduceSimple(MyBenchmarkState1 state) {
        int count = 0;
        for (int i = 0; i < 10; ++i) {
            ++count;
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.All)
    public static void benchmarkZipSimple(MyBenchmarkState2 state) {
        int count = 0;
        for (int i = 0; i < 10; ++i) {
            ++count;
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.All)
    public static void benchmarkProductSimple(MyBenchmarkState2 state) {
        int count = 0;
        for (int i = 0; i < 10; ++i) {
            ++count;
        }
    }*/
}
