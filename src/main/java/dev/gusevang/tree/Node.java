package dev.gusevang.tree;

import java.util.ArrayList;

public abstract class Node<T> {
    protected Node<T> left = null;
    protected Node<T> right = null;

    protected abstract ArrayList<ArrayList<T>> calculate(ArrayList<ArrayList<T>> t1, ArrayList<ArrayList<T>> t2);

    public ArrayList<ArrayList<T>> makeCalculation() {
        ArrayList<ArrayList<T>> leftResult = new ArrayList<ArrayList<T>>();
        ArrayList<ArrayList<T>> rightResult = new ArrayList<ArrayList<T>>();

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