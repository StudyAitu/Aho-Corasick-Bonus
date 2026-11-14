
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

