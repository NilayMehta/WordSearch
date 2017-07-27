package com.wordsearch.DAWG;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nilay on 7/26/2017.
 */

public class DawgDS implements Serializable{

    private Node rootNode;
    private int nodeCount;
//    private ArrayList<Character> alphabet;

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
        char c = word.charAt(curr.getCharDepth());
        Node node = curr.getChildNode(c);
        if (node == null) {
            curr.getChildren().add(new Node(curr, c));
            node = curr.getChildNode(c);
        }
        addWord(word, node);
    }

    public boolean checkWord(String word, Node curr) {
        if (word.length() == curr.getCharDepth()) {
            return curr.isWord();
        }
        Character c = word.charAt(curr.getCharDepth());
        Node node = curr.getChildNode(c);
        if (node == null) {
            return false;
        }
        return checkWord(word, node);
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

}
