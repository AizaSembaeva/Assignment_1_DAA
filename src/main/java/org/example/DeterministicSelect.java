package org.example;

import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] arr, int k, Metrics metrics) {
        ArrayUtils.guardNotEmpty(arr);
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("Index k out of bounds");
        }
        return select(arr, 0, arr.length - 1, k, metrics);
    }

    private static int select(int[] arr, int left, int right, int k, Metrics metrics) {
        while (true) {
            if (right - left < 5) {
                insertionSort(arr, left, right, metrics);
                return arr[left + k];
            }

            metrics.enterRecursion();
            int pivotIndex = medianOfMedians(arr, left, right, metrics);
            int pivotFinalIndex = ArrayUtils.partition(arr, left, right, metrics);

            metrics.exitRecursion();

            int length = pivotFinalIndex - left;
            if (k == length) {
                return arr[pivotFinalIndex];
            } else if (k < length) {
                right = pivotFinalIndex - 1;
            } else {
                k = k - length - 1;
                left = pivotFinalIndex + 1;
            }
        }
    }

    private static int medianOfMedians(int[] arr, int left, int right, Metrics metrics) {
        int n = right - left + 1;
        int numMedians = (int) Math.ceil(n / 5.0);
        int[] medians = new int[numMedians];

        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            insertionSort(arr, subLeft, subRight, metrics);
            int medianIndex = (subLeft + subRight) / 2;
            medians[i] = arr[medianIndex];
        }

        if (numMedians == 1) {
            return findIndex(arr, left, right, medians[0], metrics);
        } else {
            int mom = select(medians, numMedians / 2, metrics);
            return findIndex(arr, left, right, mom, metrics);
        }
    }

    private static int findIndex(int[] arr, int left, int right, int value, Metrics metrics) {
        for (int i = left; i <= right; i++) {
            metrics.incComparisons();
            if (arr[i] == value) return i;
        }
        return -1; // не должно случиться
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incComparisons();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else break;
            }
            arr[j + 1] = key;
        }
    }
}
