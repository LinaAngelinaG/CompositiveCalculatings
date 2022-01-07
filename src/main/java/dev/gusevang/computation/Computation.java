package dev.gusevang.computation;
import dev.gusevang.tree.*;

import java.util.List;
import java.util.TreeSet;

public class Computation<T> {

    public static<T> Tree<T> map(Map type, Tree<T> tree){
        TaskNode<Map,T> task = new TaskNode(type, tree.getHead());
        tree.toHead(task);
        return tree;
    };

    public static<T> Tree<T> map(Map type, List<List<T>> data){
        TaskNode<Map,T> task = new TaskNode(type, new DataNode(data));
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> zip(Zip type, Tree<T> tree1, Tree<T> tree2){
        TaskNode<Zip,T> task = new TaskNode(type, tree1.getHead(), tree2.getHead());
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> zip(Zip type, List<List<T>> data1, Tree<T> tree2){
        TaskNode<Zip,T> task = new TaskNode(type, new DataNode(data1), tree2.getHead());
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> zip(Zip type, Tree<T> tree1, List<List<T>> data2){
        TaskNode<Zip,T> task = new TaskNode(type,tree1.getHead(), new DataNode(data2));
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> zip(Zip type, List<List<T>> data1, List<List<T>> data2){
        TaskNode<Zip,T> task = new TaskNode(type, new DataNode(data1),new DataNode(data2) );
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> reduce(Reduce type, List<List<T>> data){
        TaskNode<Reduce,T> task = new TaskNode(type, new DataNode(data));
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> reduce(Reduce type, Tree<T> tree){
        TaskNode<Reduce,T> task = new TaskNode(type, tree.getHead());
        tree.toHead(task);
        return tree;
    };

    public static<T> Tree<T> product(Product type, Tree<T> tree1, Tree<T> tree2){
        TaskNode<Product,T> task = new TaskNode(type, tree1.getHead(), tree2.getHead());
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> product(Product type, List<List<T>> data1, Tree<T> tree2){
        TaskNode<Product,T> task = new TaskNode(type, new DataNode(data1), tree2.getHead());
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> product(Product type, Tree<T> tree1, List<List<T>> data2){
        TaskNode<Product,T> task = new TaskNode(type,tree1.getHead(), new DataNode(data2));
        Tree<T> tree = new Tree<>(task);
        return tree;
    };

    public static<T> Tree<T> product(Product type, List<List<T>> data1, List<List<T>> data2){
        TaskNode<Product,T> task = new TaskNode(type, new DataNode(data1),new DataNode(data2) );
        Tree<T> tree = new Tree<>(task);
        return tree;
    };
}
