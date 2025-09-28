package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class ExperimentRunner {

    private static final int[] DEFAULT_SIZES = {100, 500, 1000, 5000, 10000};

    public static void main(String[] args) throws IOException {
        // Парсим аргументы
        String algo = "all";
        String outFile = null;
        int n = -1;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--algo":
                    algo = args[++i].toLowerCase();
                    break;
                case "--n":
                    n = Integer.parseInt(args[++i]);
                    break;
                case "--out":
                    outFile = args[++i];
                    break;
            }
        }

        if (algo.equals("all")) {
            runSortingExperiments(outFile == null ? "sorting_results.csv" : outFile);
            runSelectExperiments("select_results.csv");
            runClosestPairExperiments("closest_results.csv");
        } else if (algo.equals("mergesort") || algo.equals("quicksort")) {
            if (n <= 0) {
                for (int size : DEFAULT_SIZES) {
                    runSortCase(algo, "random", size, outFile == null ? algo + "_results.csv" : outFile);
                }
            } else {
                runSortCase(algo, "random", n, outFile == null ? algo + "_results.csv" : outFile);
            }
        } else if (algo.equals("select")) {
            runSelectExperiments(outFile == null ? "select_results.csv" : outFile);
        } else if (algo.equals("closest")) {
            runClosestPairExperiments(outFile == null ? "closest_results.csv" : outFile);
        } else {
            System.err.println("Unknown algo: " + algo);
        }
    }

    private static void runSortingExperiments(String filename) throws IOException {
        try (FileWriter out = new FileWriter(filename)) {
            out.write("algorithm,case,n,time,comparisons,allocations,maxDepth\n");

            Random rnd = new Random(42);
            for (int n : DEFAULT_SIZES) {
                int[] randomArr = rnd.ints(n, -100000, 100000).toArray();

                runSort(out, "MergeSort", "random", randomArr);
                runSort(out, "QuickSort", "random", randomArr);

                int[] sortedArr = Arrays.copyOf(randomArr, randomArr.length);
                Arrays.sort(sortedArr);
                runSort(out, "MergeSort", "sorted", sortedArr);
                runSort(out, "QuickSort", "sorted", sortedArr);

                int[] reversedArr = Arrays.copyOf(sortedArr, sortedArr.length);
                for (int i = 0; i < reversedArr.length / 2; i++) {
                    int tmp = reversedArr[i];
                    reversedArr[i] = reversedArr[reversedArr.length - 1 - i];
                    reversedArr[reversedArr.length - 1 - i] = tmp;
                }
                runSort(out, "MergeSort", "reversed", reversedArr);
                runSort(out, "QuickSort", "reversed", reversedArr);
            }
        }
    }

    private static void runSortCase(String algo, String caseType, int n, String filename) throws IOException {
        try (FileWriter out = new FileWriter(filename, true)) {
            int[] arr = new Random(42).ints(n, -100000, 100000).toArray();
            runSort(out, capitalize(algo), caseType, arr);
        }
    }

    private static void runSort(FileWriter out, String algo, String caseType, int[] arr) throws IOException {
        int[] copy = Arrays.copyOf(arr, arr.length);
        Metrics m = new Metrics();
        long t1 = System.nanoTime();
        if (algo.equals("MergeSort")) {
            MergeSort.sort(copy, m);
        } else {
            QuickSort.sort(copy, m);
        }
        long t2 = System.nanoTime();
        out.write(String.format("%s,%s,%d,%d,%d,%d,%d\n",
                algo, caseType, arr.length, (t2 - t1),
                m.getComparisons(), m.getAllocations(), m.getMaxDepth()));
    }

    private static void runSelectExperiments(String filename) throws IOException {
        try (FileWriter out = new FileWriter(filename)) {
            out.write("algorithm,case,n,time,comparisons,allocations,maxDepth\n");

            Random rnd = new Random(123);
            for (int n : DEFAULT_SIZES) {
                int[] arr = rnd.ints(n, -100000, 100000).toArray();
                int k = n / 2;

                Metrics m = new Metrics();
                long t1 = System.nanoTime();
                DeterministicSelect.select(Arrays.copyOf(arr, arr.length), k, m);
                long t2 = System.nanoTime();

                out.write(String.format("Select,random,%d,%d,%d,%d,%d\n",
                        n, (t2 - t1), m.getComparisons(), m.getAllocations(), m.getMaxDepth()));
            }
        }
    }

    private static void runClosestPairExperiments(String filename) throws IOException {
        try (FileWriter out = new FileWriter(filename)) {
            out.write("algorithm,case,n,time,comparisons,allocations,maxDepth\n");

            Random rnd = new Random(321);
            for (int n : DEFAULT_SIZES) {
                ClosestPair.Point[] pts = new ClosestPair.Point[n];
                for (int i = 0; i < n; i++) {
                    pts[i] = new ClosestPair.Point(rnd.nextDouble() * 10000, rnd.nextDouble() * 10000);
                }

                Metrics m = new Metrics();
                long t1 = System.nanoTime();
                ClosestPair.closestPair(pts, m);
                long t2 = System.nanoTime();

                out.write(String.format("ClosestPair,random,%d,%d,%d,%d,%d\n",
                        n, (t2 - t1), m.getComparisons(), m.getAllocations(), m.getMaxDepth()));
            }
        }
    }

    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}
