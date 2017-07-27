package com.wordsearch.DAWG;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nilay on 7/26/2017.
 */
public class Node implements Serializable{
    private Node parent;
    private Character value;
    private boolean isWord;
    private List<Node> children;
    private int charDepth;

    public Node(Node parent, Character value, boolean isWord) {
        this.parent = parent;
        this.value = value;
        this.isWord = isWord;
        this.children = new LinkedList<Node>();
    }

    public Node(Node parent, Character value) {
        this.parent = parent;
        this.value = value;
        this.children = new LinkedList<Node>();
        this.isWord = false;
        charDepth = (parent == null) ? 0 : parent.charDepth + 1;
    }

}
