import dev.gusevang.tree.*;
import dev.gusevang.computation.Computation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        ArrayList<ArrayList<Integer>> arr1 = new ArrayList<ArrayList<Integer>>();
        arr1.get(0).add(1);
        arr1.get(1).add(3);
        arr1.get(2).add(5);
        arr1.get(3).add(8);

        ArrayList<ArrayList<Integer>> arr2 = new ArrayList<ArrayList<Integer>>();
        arr1.get(0).add(10);
        arr1.get(1).add(5);
        arr1.get(2).add(7);
        arr1.get(3).add(9);

        DataNode<Integer> dnode1 = new DataNode<Integer>(arr1);
        DataNode<Integer> dnode2 = new DataNode<Integer>(arr2);

        SubTaskNode<Map, int> node1 = new SubTaskNode<Map, int>(Map.multiply, new List<Node<int>>{ dnode1, dnode2 });
        SubTaskNode<Map, int> node3 = new SubTaskNode<Map, int>(Map.add, new List<Node<int>> { node1, dnode2 });
        SubTaskNode<Map, int> node2 = new SubTaskNode<Map,int>(Map.divide, new List<Node<int>> { node3, dnode2 });

        //SubTaskNode<Reduce, int> node4 = new SubTaskNode<Reduce, int>(Reduce.sum, new List<Node<int>> { node1, dnode2 });
        // SubTaskNode<Reduce> node3 = new SubTaskNode<Reduce>(Reduce.max, new List<Node>{ dnode1, dnode2 },2);

        TreeOfAlgrorithm<int> tree = new TreeOfAlgrorithm<int>(node2);
        List<int> result = tree.calculatingResult();
        result.ForEach(Console.WriteLine);*/
        Integer integer=3;
        Double d = integer.doubleValue();
        List<Double> arr1 = Arrays.asList(1.,2.,3.);
        List<Double> arr2 = Arrays.asList(1.,2.,3.);
        List<Double> arr3 = Arrays.asList(3.,1.,4.);
        List<List<Double>> arr = Arrays.asList(Arrays.asList(1.,3.),Arrays.asList(2.,3.), Arrays.asList(3.,3.));
        DataNode<Double> data1 = new DataNode<Double>(arr);
        DataNode<Double> data2 = new DataNode<Double>(Arrays.asList(arr3));
        //TaskNode<Map,Double> node1 = new TaskNode<Map, Double>(Map.multiply, data1);
        TaskNode<Reduce,Double> node1 = new TaskNode<>(Reduce.max, data2);
        Tree tree = new Tree(node1);
        List<List<Double>> res = tree.calculatingResult();
        for(var i : res){
            System.out.println(i);
        }
        Tree newTree = Computation.reduce(Reduce.max,Arrays.asList(arr3));
        res = newTree.calculatingResult();
        for(var i : res){
            System.out.println(i);
        }
        Tree newTree1 = Computation.map(Map.multiply,arr);
        res = newTree1.calculatingResult();
        for(var i : res){
            System.out.println(i);
        }
        //Computation.Map t = new Computation.Map(Map.add, arr);
        //Computation.Map t1 = new Computation.Map(Map.add, tree, new Computation(t));

    }

}