
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class Solution {

    class Pair<K, V> {

        K treeNode;
        V vertical_level;

        public Pair(K treeNode, V vertical_level) {
            this.treeNode = treeNode;
            this.vertical_level = vertical_level;
        }
    }
    final int MIN_TREE_NODE_VALUE = -100;
    final int MAX_TREE_NODE_VALUE = 100;

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        Map<Integer, List<Integer>> map_vertical_levels = new HashMap<>();

        queue.add(new Pair<>(root, 0));
        map_vertical_levels.put(0, new ArrayList<>());
        map_vertical_levels.get(0).add(root.val);

        int minVertical = MAX_TREE_NODE_VALUE;
        int maxVertical = MIN_TREE_NODE_VALUE;

        while (!queue.isEmpty()) {

            int totalNodes_current_horizontal_level = queue.size();
            Map<Integer, List<Integer>> visited_current_horizontal_level = new HashMap<>();

            while (totalNodes_current_horizontal_level-- > 0) {

                Pair<TreeNode, Integer> pair = queue.remove();
                minVertical = Math.min(minVertical, pair.vertical_level);
                maxVertical = Math.max(maxVertical, pair.vertical_level);

                if (pair.treeNode.left != null) {
                    int vertical = pair.vertical_level - 1;
                    queue.add(new Pair(pair.treeNode.left, vertical));
                    visited_current_horizontal_level.putIfAbsent(vertical, new ArrayList<>());
                    visited_current_horizontal_level.get(vertical).add(pair.treeNode.left.val);
                }

                if (pair.treeNode.right != null) {
                    int vertical = pair.vertical_level + 1;
                    queue.add(new Pair(pair.treeNode.right, vertical));
                    visited_current_horizontal_level.putIfAbsent(vertical, new ArrayList<>());
                    visited_current_horizontal_level.get(vertical).add(pair.treeNode.right.val);
                }
            }

            //sort by value: nodes added at current horizontal level.
            for (int key : visited_current_horizontal_level.keySet()) {
                map_vertical_levels.putIfAbsent(key, new ArrayList<>());
                Collections.sort(visited_current_horizontal_level.get(key));
                map_vertical_levels.get(key).addAll(visited_current_horizontal_level.get(key));
            }
        }

        List<List<Integer>> nodesInVerticalOrder = new ArrayList<>();
        for (int i = minVertical; i <= maxVertical; i++) {
            nodesInVerticalOrder.add(map_vertical_levels.get(i));
        }

        return nodesInVerticalOrder;
    }

}

class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
