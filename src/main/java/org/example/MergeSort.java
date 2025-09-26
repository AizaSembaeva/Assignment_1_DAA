package org.example;

import org.example.Metrics;

public class MergeSort {

    private static final int CUTOFF = 16;

    public static void sort(int[] arr, Metrics metrics) {
        int[] buffer = new int[arr.length];
        sort(arr, buffer, 0, arr.length - 1, metrics);
    }

    private static void sort(int[] arr, int[] buffer, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        if (right - left <= CUTOFF) {
            insertionSort(arr, left, right, metrics);
            metrics.exitRecursion();
            return;
        }

        int mid = (left + right) / 2;

        sort(arr, buffer, left, mid, metrics);
        sort(arr, buffer, mid + 1, right, metrics);
        merge(arr, buffer, left, mid, right, metrics);

        metrics.exitRecursion();
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics metrics) {
        metrics.incAllocations();

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            metrics.incComparisons();
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }

        while (i <= mid) buffer[k++] = arr[i++];
        while (j <= right) buffer[k++] = arr[j++];

        for (i = left; i <= right; i++) {
            arr[i] = buffer[i];
        }
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
