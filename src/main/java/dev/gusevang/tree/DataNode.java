package dev.gusevang.tree;

import java.util.ArrayList;
import java.util.List;

public class DataNode<T> extends Node<T>{
    private List<List<T>> data;

    public DataNode(List<List<T>> data) {
        this.data = data;
    }
    @Override
    protected List<List<T>> calculate(List<List<T>> val1, List<List<T>> val2) {
        return data;
    }
}
