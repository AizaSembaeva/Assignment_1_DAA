package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

class QuickSortTest {

    @Test
    void testSmallArray() {
        int[] arr = {5, 2, 9, 1, 3};
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        assertArrayEquals(new int[]{1, 2, 3, 5, 9}, arr);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseArray() {
        int[] arr = {9, 7, 5, 3, 1};
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {99};
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        assertArrayEquals(new int[]{99}, arr);
    }

    @Test
    void testAllEqualElements() {
        int[] arr = {2, 2, 2, 2};
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        assertArrayEquals(new int[]{2, 2, 2, 2}, arr);
    }

    @Test
    void testLargeRandomArray() {
        int[] arr = new Random().ints(10000, 0, 100000).toArray();
        int[] copy = Arrays.copyOf(arr, arr.length);
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        Arrays.sort(copy);
        assertArrayEquals(copy, arr);
    }
}
