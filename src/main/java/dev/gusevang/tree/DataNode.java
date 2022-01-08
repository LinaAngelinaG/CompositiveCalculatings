package dev.gusevang.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataNode<T> extends Node<T>{
    private CopyOnWriteArrayList<CopyOnWriteArrayList<T>> data;

    public DataNode(CopyOnWriteArrayList<CopyOnWriteArrayList<T>> data) {
        this.data = data;
    }
    @Override
    protected CopyOnWriteArrayList<CopyOnWriteArrayList<T>> calculate(CopyOnWriteArrayList<CopyOnWriteArrayList<T>> val1, CopyOnWriteArrayList<CopyOnWriteArrayList<T>> val2) {
        return data;
    }

    @Override
    protected CopyOnWriteArrayList<CopyOnWriteArrayList<T>> calculateThreaded(CopyOnWriteArrayList<CopyOnWriteArrayList<T>> val1, CopyOnWriteArrayList<CopyOnWriteArrayList<T>> val2) {
        return data;
    }
}
