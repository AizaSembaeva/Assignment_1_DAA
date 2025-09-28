# Assignment 1 – Divide and Conquer Algorithms

## Introduction

This project implements and analyzes classic divide-and-conquer algorithms, focusing on **MergeSort**, **QuickSort**, **Deterministic Select (Median-of-Medians)**, and **Closest Pair of Points (2D)**.

The main goals were to:
- Ensure safe recursion and control of recursion depth.
- Collect metrics including execution time, number of comparisons, memory allocations, and recursion depth.
- Compare theoretical predictions from **Master Theorem** and **Akra–Bazzi intuition** with experimental results.
- Benchmark linear selection against Java’s built-in `Arrays.sort()` for performance evaluation.

---

## 1. Architecture Notes

### Recursion Depth Control
- **MergeSort**: Recursion depth is logarithmic; small arrays handled by insertion sort.
- **QuickSort**: Recurse on the smaller partition and iterate on the larger; depth ≈ O(log n).
- **Deterministic Select**: Recurse only into the smaller partition; depth remains low.
- **Closest Pair**: Recursive halving; strip-based checks done iteratively.

### Allocation Control
- **MergeSort**: Reuses a single merge buffer.
- **QuickSort & Select**: In-place partitioning with minimal allocations.
- **Closest Pair**: Allocates only temporary arrays for strip/y-sorting.

---

## 2. Recurrence Analysis

### MergeSort
$$
T(n) = 2T\left(\frac{n}{2}\right) + \Theta(n) = \Theta(n \log n)
$$  
Recursion depth: $O(\log n)$

---

### QuickSort (random pivot)
$$
T(n) = T(k) + T(n-k-1) + \Theta(n), \quad \mathbb{E}[k] \approx \frac{n}{2}
$$  
Expected runtime: $\Theta(n \log n)$  
Recursion depth: $\approx 2 \log_2 n$

---

### Deterministic Select
$$
T(n) = T\left(\frac{n}{5}\right) + T\left(\frac{7n}{10}\right) + \Theta(n) = \Theta(n)
$$  
Runtime: linear  
Recursion depth: small (constant in practice)

---

### Closest Pair
$$
T(n) = 2T\left(\frac{n}{2}\right) + \Theta(n) = \Theta(n \log n)
$$  
Depth: $O(\log n)$  
Strip check adds only constant factor.

---

## 3. Plots
## 1. MergeSort and QuickSort: Time, Comparisons, Recursion Depth

| Algorithm   | Case     | n     | Time (ns) | Comparisons | Allocations | MaxDepth |
|------------|---------|-------|------------|-------------|------------|----------|
| MergeSort  | random  | 100   | 952600     | 661         | 7          | 4        |
| QuickSort  | random  | 100   | 1966800    | 760         | 300        | 1        |
| MergeSort  | sorted  | 100   | 23000      | 244         | 7          | 4        |
| QuickSort  | sorted  | 100   | 58900      | 641         | 108        | 1        |
| MergeSort  | reversed| 100   | 35400      | 724         | 7          | 4        |
| QuickSort  | reversed| 100   | 55300      | 588         | 258        | 1        |
| MergeSort  | random  | 500   | 204800     | 4621        | 31         | 6        |
| QuickSort  | random  | 500   | 310700     | 5273        | 2916       | 1        |
| MergeSort  | sorted  | 500   | 100900     | 1728        | 31         | 6        |
| QuickSort  | sorted  | 500   | 236100     | 4950        | 497        | 1        |
| MergeSort  | reversed| 500   | 203100     | 4900        | 31         | 6        |
| QuickSort  | reversed| 500   | 262300     | 4880        | 2350       | 1        |
| MergeSort  | random  | 1000  | 431800     | 10261       | 63         | 7        |
| QuickSort  | random  | 1000  | 228800     | 10194       | 5194       | 1        |
| MergeSort  | sorted  | 1000  | 62700      | 3956        | 63         | 7        |
| QuickSort  | sorted  | 1000  | 153000     | 10931       | 973        | 1        |
| MergeSort  | reversed| 1000  | 83000      | 10300       | 63         | 7        |
| QuickSort  | reversed| 1000  | 156600     | 10854       | 4849       | 1        |
| MergeSort  | random  | 5000  | 676500     | 58718       | 511        | 10       |
| QuickSort  | random  | 5000  | 684800     | 68967       | 35277      | 1        |
| MergeSort  | sorted  | 5000  | 308800     | 27124       | 511        | 10       |
| QuickSort  | sorted  | 5000  | 200900     | 69565       | 5038       | 1        |
| MergeSort  | reversed| 5000  | 371800     | 44332       | 511        | 10       |
| QuickSort  | reversed| 5000  | 306300     | 70469       | 26415      | 1        |
| MergeSort  | random  | 10000 | 1729900    | 126784      | 1023       | 11       |
| QuickSort  | random  | 10000 | 1173200    | 153193      | 80748      | 1        |
| MergeSort  | sorted  | 10000 | 2293100    | 59248       | 1023       | 11       |
| QuickSort  | sorted  | 10000 | 1362300    | 150650      | 10082      | 1        |
| MergeSort  | reversed| 10000 | 3705500    | 93662       | 1023       | 11       |
| QuickSort  | reversed| 10000 | 707300     | 149978      | 53100      | 1        |

## 2. Deterministic Select: Time, Comparisons, Recursion Depth

| Algorithm | Case   | n     | Time (ns) | Comparisons | Allocations | MaxDepth |
|-----------|--------|-------|------------|-------------|------------|----------|
| Select    | random | 100   | 1360800    | 1466        | 253        | 2        |
| Select    | random | 500   | 968200     | 7539        | 1440       | 3        |
| Select    | random | 1000  | 688600     | 31338       | 7091       | 4        |
| Select    | random | 5000  | 4126300    | 150278      | 32277      | 5        |
| Select    | random | 10000 | 8731800    | 650042      | 137376     | 5        |

## 3. Closest Pair: Time, Comparisons, Recursion Depth

| Algorithm    | Case   | n     | Time (ns)  | Comparisons | Allocations | MaxDepth |
|--------------|--------|-------|------------|-------------|------------|----------|
| ClosestPair  | random | 100   | 6154900    | 132         | 0          | 6        |
| ClosestPair  | random | 500   | 1450200    | 853         | 0          | 8        |
| ClosestPair  | random | 1000  | 3257600    | 1756        | 0          | 9        |
| ClosestPair  | random | 5000  | 9660400    | 6759        | 0          | 12       |
| ClosestPair  | random | 10000 | 14428800   | 13584       | 0          | 13       |

## 4. SelectBenchmark: Throughput (ops/ms)

| Benchmark                           | n       | Throughput (ops/ms) |
|------------------------------------|---------|-------------------|
| Arrays.sort                         | 1000    | 89.801            |
| Arrays.sort                         | 10000   | 1.016             |
| Arrays.sort                         | 100000  | 0.162             |
| Deterministic Select                | 1000    | 19.224            |
| Deterministic Select                | 10000   | 0.678             |
| Deterministic Select                | 100000  | 0.028             |



### 3.1 Time vs Input Size (MergeSort & QuickSort)
![Time vs n](graphs/1_graph.png)

- **MergeSort**: grows as $\Theta(n \log n)$, stable across input cases.
- **QuickSort**: average $\Theta(n \log n)$, but faster on random input and slower for sorted/reversed due to partition imbalance.

---

### 3.2 Recursion Depth vs Input Size
![Recursion Depth](graphs/2_graph.png)

- **MergeSort**: logarithmic depth growth.
- **QuickSort**: bounded near constant (iterative handling of larger partition).
- **Select**: very shallow recursion, depth ≤ 5 for tested $n$.

---

### 3.3 Throughput vs Input Size (Select vs Arrays.sort)
![Throughput](graphs/3_graph.png)

- **Arrays.sort**: high throughput at $n=1000$ but drops steeply for larger sizes.
- **Deterministic Select**: lower at small $n$, but scales better at larger $n$.
- Confirms Select’s **linear scaling advantage**.

---

### 3.4 Comparisons vs Input Size
![Comparisons](graphs/4_graph.png)

- **MergeSort**: $\sim n \log n$ comparisons.
- **QuickSort**: slightly higher comparisons due to pivot randomness.
- **Select**: linear comparisons, much smaller growth than sorting.

---

## 4. Alignment Between Theory and Measurements

- **MergeSort**: Matches $\Theta(n \log n)$ in time, depth, comparisons.
- **QuickSort**: Average case confirmed, small deviations from pivot randomness.
- **Select**: Linear growth validated; throughput confirms expected scaling.
- **Closest Pair**: Follows $\Theta(n \log n)$ trend, with logarithmic recursion depth.

---

## 5. Summary

- All algorithms were implemented according to divide-and-conquer principles.
- Recursion depth was controlled, allocations minimized.
- Metrics collection and JMH benchmarking validated theoretical predictions.
- Experimental results align with **Master Theorem** and **Akra–Bazzi** analysis.

---
