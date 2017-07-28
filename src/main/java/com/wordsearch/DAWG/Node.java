package com.wordsearch.DAWG;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Nilay on 7/26/2017.
 */
public class Node implements Serializable{
    private Node parent;
    private Character value;
    private boolean isWord;
    private List<Node> children;
    private int childrenCount;
    private int charDepth;

    public Node(Node parent, Character value, boolean isWord) {
        this.parent = parent;
        this.value = value;
        this.isWord = isWord;
        this.children = new LinkedList<>();
        this.childrenCount = 0;
    }

    public Node(Node parent, Character value) {
        this.parent = parent;
        this.value = value;
        this.children = new LinkedList<>();
        this.isWord = false;
        this.childrenCount = 0;
        charDepth = (parent == null) ? 0 : parent.charDepth + 1;
    }

    public Node getChildNode(Character c) {
        boolean found = false;
        ListIterator<Node> listIterator = children.listIterator();
        while (!found && listIterator.hasNext()) {
            Node curr = listIterator.next();
            if (c == curr.getValue()) {
                found = true;
                return curr;
            }
        }
        return null;
    }

    public boolean removeChildNode(Character c) {
//        System.out.println("Children Node size before remove: " + children.size());
        ListIterator<Node> listIterator = children.listIterator();
        while (listIterator.hasNext()) {
            Node curr = listIterator.next();
            if (c == curr.getValue()) {
                listIterator.remove();
//                System.out.println("Children Node size after renove: " + children.size());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (childrenCount != node.childrenCount) return false;
        if (!value.equals(node.value)) return false;
        return children != null ? children.equals(node.children) : node.children == null;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + childrenCount;
        return result;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
        this.value = value;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public int getCharDepth() {
        return charDepth;
    }

    public void setCharDepth(int charDepth) {
        this.charDepth = charDepth;
    }
}
