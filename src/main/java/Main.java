import dev.gusevang.tree.DataNode;
import dev.gusevang.tree.Map;
import dev.gusevang.tree.Method;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
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
        result.ForEach(Console.WriteLine);
    }
}
