package org.example;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] arr, Metrics metrics) {
        ArrayUtils.guardNotEmpty(arr);
        quickSort(arr, 0, arr.length - 1, metrics);
    }

    private static void quickSort(int[] arr, int left, int right, Metrics metrics) {
        while (left < right) {
            metrics.enterRecursion();

            int pivotIndex = left + random.nextInt(right - left + 1);
            ArrayUtils.swap(arr, pivotIndex, right, metrics);

            int p = ArrayUtils.partition(arr, left, right, metrics);

            metrics.exitRecursion();

            if (p - left < right - p) {
                quickSort(arr, left, p - 1, metrics);
                left = p + 1;
            } else {
                quickSort(arr, p + 1, right, metrics);
                right = p - 1;
            }
        }
    }
}
