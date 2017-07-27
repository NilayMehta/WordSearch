package com.wordsearch.DAWG;

import com.wordsearch.WordSearch;

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
            WordSearch.getNodeEndings().get(word.length() - 1).add(curr);
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

    public void optimize() {
        int totalRemoved = 0;
        for (int i = WordSearch.getNodeEndings().size() - 1; i >= 0; i--) {
            ArrayList<Node> nodeEndings = WordSearch.getNodeEndings().get(i);

            System.out.println("**********************************************************************");
            System.out.println("Optimizing for index " + i);
            System.out.println("**********************************************************************");

            for(int j = 0; j < nodeEndings.size() - 1; j++) {
                Node one = nodeEndings.get(j);
                for (int l = j + 1; l < nodeEndings.size(); l++) {
                    Node two = nodeEndings.get(l);
                    if(one.equals(two)) {
                        Node oneParent = one.getParent();
                        boolean removed = oneParent.removeChildNode(one.getValue());
                        oneParent.getChildren().add(two);
                        nodeCount--;
                        totalRemoved++;
                        System.out.println("Running total Nodes Removed Count: " + totalRemoved);
                    }
                }
            }
        }
        System.out.println("**********************************************************************");
        System.out.println("Total Nodes Removed " + totalRemoved);
        System.out.println("**********************************************************************");
    }

}
