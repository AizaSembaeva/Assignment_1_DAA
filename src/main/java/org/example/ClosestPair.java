package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double closestPair(Point[] points, Metrics metrics) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }

        Point[] ptsSortedByX = points.clone();
        Arrays.sort(ptsSortedByX, Comparator.comparingDouble(p -> p.x));
        Point[] ptsSortedByY = ptsSortedByX.clone();

        return closestRecursive(ptsSortedByX, ptsSortedByY, 0, ptsSortedByX.length - 1, metrics);
    }

    private static double closestRecursive(Point[] ptsX, Point[] ptsY, int left, int right, Metrics metrics) {
        metrics.enterRecursion();

        if (right - left <= 3) {
            double d = bruteForce(ptsX, left, right, metrics);
            Arrays.sort(ptsX, left, right + 1, Comparator.comparingDouble(p -> p.y));
            metrics.exitRecursion();
            return d;
        }

        int mid = (left + right) / 2;
        double midX = ptsX[mid].x;

        Point[] leftY = new Point[mid - left + 1];
        Point[] rightY = new Point[right - mid];
        int li = 0, ri = 0;
        for (Point p : ptsY) {
            if (p.x <= midX) leftY[li++] = p;
            else rightY[ri++] = p;
        }

        double d1 = closestRecursive(ptsX, leftY, left, mid, metrics);
        double d2 = closestRecursive(ptsX, rightY, mid + 1, right, metrics);
        double d = Math.min(d1, d2);

        Point[] strip = new Point[ptsY.length];
        int stripSize = 0;
        for (Point p : ptsY) {
            if (Math.abs(p.x - midX) < d) {
                strip[stripSize++] = p;
            }
        }

        double stripMin = stripClosest(strip, stripSize, d, metrics);

        metrics.exitRecursion();
        return Math.min(d, stripMin);
    }

    private static double bruteForce(Point[] pts, int left, int right, Metrics metrics) {
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.incComparisons();
                minDist = Math.min(minDist, dist(pts[i], pts[j]));
            }
        }
        return minDist;
    }

    private static double stripClosest(Point[] strip, int size, double d, Metrics metrics) {
        double min = d;
        Arrays.sort(strip, 0, size, Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; j++) {
                metrics.incComparisons();
                min = Math.min(min, dist(strip[i], strip[j]));
            }
        }
        return min;
    }

    private static double dist(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
