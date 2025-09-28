package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

class DeterministicSelectTest {

    @Test
    void testSmallArray() {
        int[] arr = {7, 2, 9, 1, 5};
        Metrics m = new Metrics();
        int result = DeterministicSelect.select(arr, 2, m);
        Arrays.sort(arr);
        assertEquals(arr[2], result);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        Metrics m = new Metrics();
        int result = DeterministicSelect.select(arr, 0, m);
        assertEquals(42, result);
    }

    @Test
    void testRandomArray() {
        Random rnd = new Random(42);
        int[] arr = rnd.ints(100, -1000, 1000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        Metrics m = new Metrics();
        int k = 50;
        int result = DeterministicSelect.select(arr, k, m);
        assertEquals(expected[k], result);
    }

    @Test
    void testMultipleRandomCases() {
        Random rnd = new Random(123);
        for (int t = 0; t < 100; t++) {
            int[] arr = rnd.ints(50, -500, 500).toArray();
            int[] expected = arr.clone();
            Arrays.sort(expected);
            int k = rnd.nextInt(arr.length);
            Metrics m = new Metrics();
            int result = DeterministicSelect.select(arr, k, m);
            assertEquals(expected[k], result);
        }
    }

    @Test
    void testKOutOfBounds() {
        int[] arr = {1, 2, 3};
        Metrics m = new Metrics();
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, -1, m));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, 3, m));
    }

    @Test
    void testAllEqualElements() {
        int[] arr = {5, 5, 5, 5};
        Metrics m = new Metrics();
        int result = DeterministicSelect.select(arr, 2, m);
        assertEquals(5, result);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        Metrics m = new Metrics();
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, 0, m));
    }
}
