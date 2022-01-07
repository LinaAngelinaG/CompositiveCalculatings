package dev.gusevang.tree;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    // T - type of data in DataNode

    private Node<T> head;
    public Tree(Node<T> head)
    {
        this.head = head;
    }
    public List<List<T>> calculatingResult()
    {
        return head.makeCalculation();
    }
    public void toHead(Node<T> newHead){  head = (newHead != null ? newHead : head);}

    public Node<T> getHead() {
        return head;
    }
}
