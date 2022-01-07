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

    /*public static<T> Tree<T> zip(Tree<T> tree1, Tree<T> tree2){

    };*/

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

    /*public static<T> Tree<T> product(Map type, List<List<T>> data){

    };*/

    /*public static class Map{
        public Map(dev.gusevang.tree.Map type, Tree tree, Computation t){


        }
        public Map(dev.gusevang.tree.Map type, List<List<Double>> data){


        }
    }

    public class Zip{
        public Zip(Tree tree1, Tree tree2){

        }
    }

    public class Reduce{
        public Reduce(dev.gusevang.tree.Reduce type, Tree tree){

        }
        public Reduce(dev.gusevang.tree.Reduce type, List<Double> data){

        }
    }

    public class Product{

    }*/
}
