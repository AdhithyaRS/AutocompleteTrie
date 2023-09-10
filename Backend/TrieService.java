package com.milky.trackerWeb.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TrieService {

    private class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        String email;  // To store the email of the user when the node marks the end of a word

        TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
            email = null;
        }
    }

    private final TrieNode root;

    public TrieService() {
        this.root = new TrieNode();
    }

    // Insert word into trie
    public void insert(String key, String email) {
        TrieNode node = root;
        key = key.toLowerCase();

        for (int level = 0; level < key.length(); level++) {
            char ch = key.charAt(level);
            if (!node.children.containsKey(ch)) {
                node.children.put(ch, new TrieNode());
            }
            node = node.children.get(ch);
        }

        node.isEndOfWord = true;
        node.email = email;
    }

    // Search for word in trie and return associated email if found
    public String search(String key) {
        TrieNode node = root;
        key = key.toLowerCase();

        for (int level = 0; level < key.length(); level++) {
            char ch = key.charAt(level);
            if (!node.children.containsKey(ch)) {
                return null;  // Not found
            }
            node = node.children.get(ch);
        }

        if (node != null && node.isEndOfWord) {
            return node.email;
        } else {
            return null;  // Not found
        }
    }

    // This function would be used to get all suggestions based on the prefix
    public Map<String, String> getSuggestions(String prefix) {
        Map<String, String> results = new HashMap<>();
        TrieNode node = root;
        prefix = prefix.toLowerCase();

        // Traverse till the end of the prefix in the trie
        for (int level = 0; level < prefix.length(); level++) {
            char ch = prefix.charAt(level);
            if (!node.children.containsKey(ch)) {
                return results;  // No suggestions if prefix doesn't exist
            }
            node = node.children.get(ch);
        }

        // If the prefix is a complete word in itself
        if (node.isEndOfWord) {
            results.put(prefix, node.email);
        }

        // Recursively find all strings with the given prefix
        findAllWordsWithPrefix(node, prefix, results);

        return results;
    }

    private void findAllWordsWithPrefix(TrieNode node, String current, Map<String, String> results) {
        if (node.isEndOfWord) {
            results.put(current, node.email);
        }

        for (char ch : node.children.keySet()) {
            findAllWordsWithPrefix(node.children.get(ch), current + ch, results);
        }
    }
}
