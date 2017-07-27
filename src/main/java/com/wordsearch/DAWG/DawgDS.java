package com.wordsearch.DAWG;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nilay on 7/26/2017.
 */
public class DawgDS implements Serializable{

    private Node rootNode;
    private int nodeCount;
    private ArrayList<Character> alphabet;

    public DawgDS() {
//        alphabet = new ArrayList<>();
//        for (int i = 0; i < 26; i++) {
//            alphabet.add((char)((int)'a' + i));
//        }
        rootNode = new Node(null,(char) 0);
        nodeCount++;
    }

    public void addWord(String word, Node curr) {
        if (word.length() == curr.getCharDepth()) {
            curr.setWord(true);
            nodeCount++;
            return;
        }
        Character c = word.charAt(curr.getCharDepth());
        if (curr.searchChildren(c) == null) {
            curr.getChildren().add(new Node(curr, c));
        }
        addWord(word, curr.searchChildren(c));
    }

    public boolean checkWord(String word, Node curr) {
        if (word.length() == curr.getCharDepth()) {
            return curr.isWord();
        }
        Character c = word.charAt(curr.getCharDepth());
        if (curr.searchChildren(c) == null) {
            return false;
        }
        return checkWord(word, curr.searchChildren(c));
    }



    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public ArrayList<Character> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<Character> alphabet) {
        this.alphabet = alphabet;
    }
}
