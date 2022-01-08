package dev.gusevang.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tree<T> {
    // T - type of data in DataNode

    private Node<T> head;
    public Tree(Node<T> head)
    {
        this.head = head;
    }
    public CopyOnWriteArrayList<CopyOnWriteArrayList<T>> calculatingResult()
    {
        return head.makeCalculation();
    }
    public CopyOnWriteArrayList<CopyOnWriteArrayList<T>> calculatingResultThreaded()
    {
        return head.makeCalculationThreaded();
    }
    public void toHead(Node<T> newHead){  head = (newHead != null ? newHead : head);}

    public Node<T> getHead() {
        return head;
    }
}
