package org.example;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] arr, Metrics metrics) {
        quickSort(arr, 0, arr.length - 1, metrics);
    }

    private static void quickSort(int[] arr, int left, int right, Metrics metrics) {
        while (left < right) {
            metrics.enterRecursion();

            int pivotIndex = left + random.nextInt(right - left + 1);
            swap(arr, pivotIndex, right, metrics);

            int p = partition(arr, left, right, metrics);

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

    private static int partition(int[] arr, int left, int right, Metrics metrics) {
        int pivot = arr[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            metrics.incComparisons();
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j, metrics);
            }
        }

        swap(arr, i + 1, right, metrics);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j, Metrics metrics) {
        if (i != j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            metrics.incAllocations();
        }
    }
}