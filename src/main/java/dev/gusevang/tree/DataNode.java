package dev.gusevang.tree;

import java.util.ArrayList;

public class DataNode<T> extends Node<T>{
    private ArrayList<ArrayList<T>> data;

    public DataNode(ArrayList<ArrayList<T>> data) {
        this.data = data;
    }
    @Override
    protected ArrayList<ArrayList<T>> calculate(ArrayList<ArrayList<T>> val1, ArrayList<ArrayList<T>> val2) {
        return data;
    }
}
