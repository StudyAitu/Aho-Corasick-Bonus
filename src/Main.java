
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final int ALPHABET_SIZE = 26;
    private static final int RUNS = 5;

    // =========================
    // AHO-CORASICK ALGORITHM
    // =========================

    static class Node {
        int[] next = new int[ALPHABET_SIZE];
        int failure = 0;
        List<Integer> output = new ArrayList<>();

        Node() {
            Arrays.fill(next, -1);
        }
    }

    static class AhoCorasick {

        private final List<Node> trie = new ArrayList<>();
        private final String[] patterns;

        AhoCorasick(String[] patterns) {
            this.patterns = patterns;
            trie.add(new Node());

            buildTrie();
            buildFailureLinks();
        }

        // Step 1: Insert all patterns into the Trie
        private void buildTrie() {

            for (int id = 0; id < patterns.length; id++) {

                String pattern = patterns[id];
                int current = 0;

                for (char ch : pattern.toCharArray()) {

                    int index = ch - 'a';

                    if (trie.get(current).next[index] == -1) {
                        trie.get(current).next[index] = trie.size();
                        trie.add(new Node());
                    }

                    current = trie.get(current).next[index];
                }

                trie.get(current).output.add(id);
            }
        }

        // Step 2: Construct failure links using BFS
        private void buildFailureLinks() {

            Queue<Integer> queue = new ArrayDeque<>();

            // Initialize root transitions
            for (int c = 0; c < ALPHABET_SIZE; c++) {

                int child = trie.get(0).next[c];

                if (child != -1) {
                    trie.get(child).failure = 0;
                    queue.add(child);
                } else {
                    trie.get(0).next[c] = 0;
                }
            }

            while (!queue.isEmpty()) {

                int current = queue.poll();

                for (int c = 0; c < ALPHABET_SIZE; c++) {

                    int child = trie.get(current).next[c];

                    if (child != -1) {

                        int fallback =
                                trie.get(trie.get(current).failure).next[c];

                        trie.get(child).failure = fallback;

                        // Inherit matches from the failure state
                        trie.get(child).output.addAll(
                                trie.get(fallback).output
                        );

                        queue.add(child);

                    } else {

                        trie.get(current).next[c] =
                                trie.get(trie.get(current).failure).next[c];
                    }
                }
            }
        }

        // Step 3: Search for all pattern occurrences
        long search(String text) {

            int current = 0;
            long matches = 0;

            for (int i = 0; i < text.length(); i++) {

                int index = text.charAt(i) - 'a';

                current = trie.get(current).next[index];

                // Count all patterns ending at this position
                matches += trie.get(current).output.size();
            }

            return matches;
        }

        int getNodeCount() {
            return trie.size();
        }
    }

    // =========================
    // KMP ALGORITHM
    // =========================

    static long kmpSearch(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;
        long matches = 0;

        while (i < n) {

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                matches++;
                j = lps[j - 1];
            } else if (i < n &&
                    text.charAt(i) != pattern.charAt(j)) {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return matches;
    }

    // Build the Longest Prefix Suffix array
    static int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int len = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;

            } else {

                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // Run KMP separately for every pattern
    static long searchWithKMP(String text, String[] patterns) {

        long totalMatches = 0;

        for (String pattern : patterns) {
            totalMatches += kmpSearch(text, pattern);
        }

        return totalMatches;
    }
