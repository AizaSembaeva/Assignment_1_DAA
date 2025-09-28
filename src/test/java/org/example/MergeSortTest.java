package org.example;

import org.example.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class MergeSortTest {

    @Test
    void testSmallArray() {
        int[] arr = {5, 2, 9, 1, 3};
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertArrayEquals(new int[]{1, 2, 3, 5, 9}, arr);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testAllEqualElements() {
        int[] arr = {5, 5, 5, 5};
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertArrayEquals(new int[]{5, 5, 5, 5}, arr);
    }

    @Test
    void testReverseArray() {
        int[] arr = {5, 4, 3, 2, 1};
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testRandomArray() {
        Random rnd = new Random();
        int[] arr = rnd.ints(1000, 0, 10000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics m = new Metrics();
        MergeSort.sort(arr, m);

        assertArrayEquals(expected, arr);
        assertTrue(m.getComparisons() > 0);
    }
}
