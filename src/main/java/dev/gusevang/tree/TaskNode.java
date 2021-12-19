package dev.gusevang.tree;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TaskNode <T, T1> extends Node<T1> {
    private T method;    //divide, add, ... etc.
    public TaskNode(T typeOfMethod, Node<T1> nodeleft, Node<T1> noderight) { //for zip(reduce) operations
        method = typeOfMethod;
        right = noderight;
        left = nodeleft;
    }
    public TaskNode(T typeOfMethod, Node<T1> node) { //for map operations
        method = typeOfMethod;
        right = left = node;
    }
    @Override
    protected ArrayList<ArrayList<T1>> calculate(ArrayList<ArrayList<T1>> val1, ArrayList<ArrayList<T1>> val2) {
        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        if(val1 == null || val2 ==null || val1.isEmpty() || val2.isEmpty()){
           return null;
        }
        if(!(val1.get(0).get(0) instanceof Integer || val1.get(0).get(0) instanceof Double || val1.get(0).get(0) instanceof Float)){
            return null;
        }
        ArrayList<ArrayList<Double>> vall1 = (ArrayList<ArrayList<Double>>)(Object)val1;
        ArrayList<ArrayList<Double>> vall2 = (ArrayList<ArrayList<Double>>)(Object)val2;
        int y = 0;
        if(method.equals(Map.divide)){
            for(var i : vall1) {
                result.get(y).add(i.get(0)/i.get(1));
                ++y;
            }
        }
        else if(method.equals(Map.add)){
            for(var i : vall1) {
                result.get(y).add(i.get(0)+i.get(1));
                ++y;
            }
        }
        else if(method.equals(Map.multiply)){
            for(var i : vall1) {
                result.get(y).add(i.get(0)*i.get(1));
                ++y;
            }
        }
        else if(method.equals(Map.exponentiate)){
            for(var i : vall1) {
                result.get(y).add(Math.pow(i.get(0),i.get(1)));
                ++y;
            }
        }
        else if(method.equals(Reduce.max)){
            for(var val : vall1) {
                double max = 0L;
                int counter = 0;
                for(var i : val) {
                    if(counter == 0 || i > max) {
                        max = i;
                        counter = 1;
                    }
                }
                result.get(y).add(max);
                ++y;
            }
        }
        else if(method.equals(Reduce.length)){
            for(var val : vall1){
                result.get(y).add((double)val.size());
                ++y;
            }
        }
        else if(method.equals(Reduce.min)){
            for(var val : vall1) {
                double min = 0L;
                int counter = 0;
                for(var i : val) {
                    if(counter == 0 || i < min) {
                        min = i;
                        counter = 1;
                    }
                }
                result.get(y).add(min);
                ++y;
            }
        }
        else if(method.equals(Reduce.overage)){
            for(var val : vall1) {
                double aver = 0L;
                for(var i : val) {
                    aver += i;
                }
                result.get(y).add(aver/val.size());
                ++y;
            }
        }
        else if(method.equals(Reduce.sum)){
            for(var val : vall1) {
                double sum = 0L;
                for(var i : val) {
                    sum += i;
                }
                result.get(y).add(sum);
                ++y;
            }
        }
        return (ArrayList<ArrayList<T1>>)(Object)result;
    }
}