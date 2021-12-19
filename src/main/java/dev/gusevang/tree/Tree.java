package dev.gusevang.tree;

import java.util.ArrayList;

public class Tree<T> {
    // T - type of data in DataNode

    private Node<T> head;
    public Tree(Node<T> head)
    {
        this.head = head;
    }
    public ArrayList<ArrayList<T>> calculatingResult()
    {
        return head.makeCalculation();
    }
}
