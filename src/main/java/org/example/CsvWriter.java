package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter implements AutoCloseable {
    private final FileWriter writer;

    public CsvWriter(String filename) throws IOException {
        this.writer = new FileWriter(filename);
        writer.write("n,time,comparisons,allocations,maxDepth\n");
    }

    public void writeRow(int n, long time, Metrics metrics) throws IOException {
        writer.write(String.format("%d,%d,%d,%d,%d\n",
                n,
                time,
                metrics.getComparisons(),
                metrics.getAllocations(),
                metrics.getMaxDepth()
        ));
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
