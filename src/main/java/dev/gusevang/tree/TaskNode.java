package dev.gusevang.tree;

import com.sun.jdi.DoubleValue;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaskNode <T, T1> extends Node<T1> {
    private T method;    //divide, add, ... etc.

    public TaskNode(T typeOfMethod, Node<T1> nodeleft, Node<T1> noderight) { //for zip/(reduce) operations
        method = typeOfMethod;
        right = noderight;
        left = nodeleft;
    }
    public TaskNode(T typeOfMethod, Node<T1> node) { //for map operations
        method = typeOfMethod;
        right = left = node;
    }
    @Override
    protected List<List<T1>> calculate(List<List<T1>> val1, List<List<T1>> val2) {
        if(val1 == null || val2 ==null || val1.isEmpty() || val2.isEmpty()){
           return null;
        }
        if(!(val1.get(0).get(0) instanceof Integer || val1.get(0).get(0) instanceof Double || val1.get(0).get(0) instanceof Float)){
            return null;
        }
        int y = Math.min(val1.size(), val2.size());
        List<List<Double>> result = new ArrayList<List<Double>>(y);
        //клиент-сервис система

        /*List<List<Double>> matrix = val1.stream()
                .map(line -> line.stream().map(x -> x.doubleValue()).collect(Collectors.toList()))
                .collect(Collectors.toList());*/
        List<List<Double>> vall1 = (List<List<Double>>)(Object)val1;
        //System.out.println(vall1);
        List<List<Double>> vall2 = (List<List<Double>>)(Object)val2;
        y = 0;
        if(method.equals(Map.divide)){
            for(var i : vall1) {
                result.add(Arrays.asList((double)i.get(0)/i.get(1)));
            }
        }
        else if(method.equals(Map.add)){
            for(var i : vall1) {
                result.add(Arrays.asList((double)i.get(0)+i.get(1)));
            }
        }
        else if(method.equals(Map.multiply)){
            for(List<Double> i : vall1) {
                result.add(Arrays.asList(i.get(0).doubleValue()*i.get(1).doubleValue()));
            }
        }
        else if(method.equals(Map.exponentiate)){
            for(var i : vall1) {
                result.add(Arrays.asList(Math.pow(i.get(0),i.get(1))));
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
                result.add(Arrays.asList(max));
            }
        }
        else if(method.equals(Reduce.length)){
            for(var val : vall1){
                result.add(Arrays.asList((double)val.size()));
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
                result.add(Arrays.asList(min));
            }
        }
        else if(method.equals(Reduce.overage)){
            for(var val : vall1) {
                double aver = 0L;
                for(var i : val) {
                    aver += i;
                }
                result.add(Arrays.asList(aver/val.size()));
            }
        }
        else if(method.equals(Reduce.sum)){
            for(var val : vall1) {
                double sum = 0L;
                for(var i : val) {
                    sum += i;
                }
                result.add(Arrays.asList(sum));
            }
        }
        else if(method.equals(Zip.concat)){
            int len = Math.min(vall1.size(),vall2.size());
            System.out.println("Length is " + vall1.size()+ " and " + vall2.size());
            for(int i=0;i<len;++i){
                result.add(new ArrayList<>());
                for(var val : vall1.get(i)){
                    result.get(i).add(val);
                }
                for(var val : vall2.get(i)){
                    result.get(i).add(val);
                }
            }
        }
        else if(method.equals(Product.product)){
            int len1 = vall1.size();
            int len2= vall2.size();
            int k=0;
            for(int i=0;i<len1;++i){
                for(int j=0;j<len2;++j){
                    result.add(new ArrayList<>());
                    for(var val : vall1.get(i)){
                        result.get(k).add(val);
                    }
                    for(var val : vall2.get(j)){
                        result.get(k).add(val);
                    }
                    ++k;
                }
            }
        }
        return (List<List<T1>>)(Object)result;
    }
}