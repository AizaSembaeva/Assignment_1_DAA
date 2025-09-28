package org.example;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    @Test
    void testTwoPoints() {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4)
        };
        Metrics m = new Metrics();
        double d = ClosestPair.closestPair(pts, m);
        assertEquals(5.0, d, 1e-9);
    }

    @Test
    void testSquare() {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(0, 1),
                new ClosestPair.Point(1, 0),
                new ClosestPair.Point(1, 1)
        };
        Metrics m = new Metrics();
        double d = ClosestPair.closestPair(pts, m);
        assertEquals(1.0, d, 1e-9);
    }

    @Test
    void testBruteForceComparisonSmallN() {
        Random rnd = new Random(42);
        for (int n = 10; n <= 2000; n *= 10) {  // проверяем на n=10,100,1000
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
            }

            Metrics m = new Metrics();
            double fast = ClosestPair.closestPair(pts, m);
            double slow = bruteForce(pts);

            assertEquals(slow, fast, 1e-6, "Mismatch for n=" + n);
        }
    }

    @Test
    void testLargeNFastOnly() {
        Random rnd = new Random(123);
        int n = 100_000;  // большое количество точек
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rnd.nextDouble() * 10_000, rnd.nextDouble() * 10_000);
        }

        Metrics m = new Metrics();
        double fast = ClosestPair.closestPair(pts, m);

        assertTrue(fast > 0, "Distance must be positive");
        assertTrue(m.getComparisons() > 0, "Comparisons should be tracked");
    }

    private double bruteForce(ClosestPair.Point[] pts) {
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }
}
