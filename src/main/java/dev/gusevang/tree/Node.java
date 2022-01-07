package dev.gusevang.tree;

import java.util.ArrayList;
import java.util.List;

public abstract class Node<T> {
    protected Node<T> left = null;
    protected Node<T> right = null;

    protected abstract List<List<T>> calculate(List<List<T>> t1, List<List<T>> t2);

    public List<List<T>> makeCalculation() {
        List<List<T>> leftResult = new ArrayList<List<T>>();
        List<List<T>> rightResult = new ArrayList<List<T>>();

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
}