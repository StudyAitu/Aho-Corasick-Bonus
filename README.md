
# Aho-Corasick Algorithm — Bonus Task

**Student:** Muratbek Ibrai  
**Group:** SE-2529  
**Course:** Design and Analysis of Algorithms

## 1. Project Description

This project implements the Aho-Corasick string matching algorithm in Java.

The algorithm searches for multiple patterns simultaneously in a single text.

The implementation includes:
- Trie construction
- Failure link construction using BFS
- Output handling
- Searching for all pattern occurrences
- KMP implementation for performance comparison
- Benchmark experiments

## 2. Project Structure

```text
Aho-Corasick-Bonus/
├── src/
│   └── Main.java
├── results/
│   └── benchmark_results.csv
├── README.md
└── .gitignore
```

## 3. How the Algorithm Works

### Trie Construction

All patterns are inserted into a Trie.

Each node represents a prefix of one or more patterns. When a complete pattern is inserted, its ID is stored in the output list of the final node.

### Failure Links

Failure links are constructed using Breadth-First Search (BFS).

A failure link points to the longest proper suffix of the current node's string that is also a prefix in the Trie.

These links allow the algorithm to continue searching without restarting from the root.

### Output Handling

Each node stores the patterns ending at that state.

When failure links are constructed, the output lists from failure states are inherited. This allows the algorithm to find suffix patterns as well.

### Searching

The algorithm processes the text character by character.

It follows Trie transitions and uses failure links when necessary. Every pattern found at the current state is counted.

The algorithm also supports overlapping matches.

## 4. Time and Space Complexity

Let:

- N = text length
- P = number of patterns
- S = total length of all patterns
- K = alphabet size
- Z = number of reported matches

### Aho-Corasick

Trie construction: O(S)

Failure link construction with a fixed-size transition array: O(S * K + output propagation)

Text searching: O(N + Z)

Space complexity: O(S * K + output storage)

For this implementation, K = 26.

### KMP

For one pattern, KMP has:

- Time complexity: O(N + M)
- Space complexity: O(M)

For P patterns searched separately:

O(P * N + S)

## 5. Experimental Setup

The implementation tests:

| Parameter | Values |
|---|---|
| Number of patterns | 10, 100, 1000 |
| Text length | 1,000 |
| Text length | 10,000 |
| Text length | 100,000 |
| Number of runs | 5 |
| Timer | System.nanoTime() |

The average execution time is calculated from five independent runs.

The benchmark includes preprocessing and searching for both algorithms.

All test data is generated using fixed random seeds to make the experiments reproducible.

## 6. Experimental Results

The program saves all measurements in:

`results/benchmark_results.csv`

Insert the actual average measurements from the program here:

| Patterns | Text Length | Aho-Corasick (ns) | KMP (ns) |
|---:|---:|---:|---:|
| 10 | 1,000 | TODO | TODO |
| 10 | 10,000 | TODO | TODO |
| 10 | 100,000 | TODO | TODO |
| 100 | 1,000 | TODO | TODO |
| 100 | 10,000 | TODO | TODO |
| 100 | 100,000 | TODO | TODO |
| 1,000 | 1,000 | TODO | TODO |
| 1,000 | 10,000 | TODO | TODO |
| 1,000 | 100,000 | TODO | TODO |

## 7. Aho-Corasick vs KMP

Aho-Corasick is designed to search for multiple patterns simultaneously.

It constructs a single Trie and uses failure links to avoid restarting the search for every pattern.

KMP searches for one pattern at a time. Therefore, searching for many patterns requires running the algorithm repeatedly.

Aho-Corasick is generally more suitable when the number of patterns is large.

However, it requires additional memory for the Trie, failure links, and output information.

KMP is simpler and can be more convenient when searching for only one or a small number of patterns.

## 8. Conclusion

The project demonstrates how Aho-Corasick can efficiently search for multiple patterns in one text.

The experiments compare its performance with separate KMP searches using different numbers of patterns and text sizes.

The benchmark results help demonstrate the practical differences between the two algorithms.

## 9. How to Run

Requirements:
- Java JDK 17 or newer

Run:

```bash
javac -d out src/Main.java
java -cp out Main
```

The program performs correctness tests, executes benchmarks, and generates the CSV results file.
