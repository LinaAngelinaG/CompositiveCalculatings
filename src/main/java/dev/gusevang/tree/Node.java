package dev.gusevang.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Node<T> {
    protected Node<T> left = null;
    protected Node<T> right = null;

    protected abstract CopyOnWriteArrayList<CopyOnWriteArrayList<T>> calculate(CopyOnWriteArrayList<CopyOnWriteArrayList<T>> t1, CopyOnWriteArrayList<CopyOnWriteArrayList<T>> t2);
    protected abstract CopyOnWriteArrayList<CopyOnWriteArrayList<T>> calculateThreaded(CopyOnWriteArrayList<CopyOnWriteArrayList<T>> t1, CopyOnWriteArrayList<CopyOnWriteArrayList<T>> t2);

    public CopyOnWriteArrayList<CopyOnWriteArrayList<T>> makeCalculation() {
        CopyOnWriteArrayList<CopyOnWriteArrayList<T>> leftResult = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<CopyOnWriteArrayList<T>> rightResult = new CopyOnWriteArrayList<>();

        if (left != null) {
            leftResult = left.makeCalculation();
        }
        if (right != null)
        {
            rightResult = right.makeCalculation();
        }
        if(left != null && right != null)
        {
            return this.calculate(leftResult, rightResult);
        }
        else if(left != null)
        {
            return this.calculate(leftResult, null);
        }
        else
        {
            return this.calculate(null, rightResult);
        }
    }

    public CopyOnWriteArrayList<CopyOnWriteArrayList<T>> makeCalculationThreaded() {
        CopyOnWriteArrayList<CopyOnWriteArrayList<T>> leftResult = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<CopyOnWriteArrayList<T>> rightResult = new CopyOnWriteArrayList<>();

        if (left != null) {
            leftResult = left.makeCalculationThreaded();
        }
        if (right != null)
        {
            rightResult = right.makeCalculationThreaded();
        }
        if(left != null && right != null)
        {
            return this.calculateThreaded(leftResult, rightResult);
        }
        else if(left != null)
        {
            return this.calculateThreaded(leftResult, null);
        }
        else
        {
            return this.calculateThreaded(null, rightResult);
        }
    }
}