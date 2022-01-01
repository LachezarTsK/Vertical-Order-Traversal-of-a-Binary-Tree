
function TreeNode(val, left, right) {
    this.val = (val === undefined ? 0 : val);
    this.left = (left === undefined ? null : left);
    this.right = (right === undefined ? null : right);
}

//Pair is applied as a Linked List Node.
function Pair(treeNode = null, vertical_level = - 1) {
    this.treeNode = treeNode;
    this.vertical_level = vertical_level;
    this.next = null;
}

/**
 * @param {TreeNode} root
 * @return {number[][]}
 */
var verticalTraversal = function (root) {

    if (root === null) {
        return [];
    }
    const MAX_NUMBER_OF_TREE_NODES = 1000;

    let head_queuePairs = new Pair(root, 0);
    let tail_queuePairs = head_queuePairs;
    const map_vertical_levels = new Map();
    map_vertical_levels.set(0, [root.val]);
    let countAddedNodes_horizontal_level = 1;

    let minVertical = MAX_NUMBER_OF_TREE_NODES;
    let maxVertical = -MAX_NUMBER_OF_TREE_NODES;


    while (head_queuePairs !== null) {

        let totalNodes_current_horizontal_level = countAddedNodes_horizontal_level;

        countAddedNodes_horizontal_level = 0;
        const visited_current_horizontal_level = new Map();

        while (totalNodes_current_horizontal_level-- > 0) {

            let pair = head_queuePairs;
            minVertical = Math.min(minVertical, pair.vertical_level);
            maxVertical = Math.max(maxVertical, pair.vertical_level);

            if (pair.treeNode.left !== null) {

                let vertical = pair.vertical_level - 1;
                if (!visited_current_horizontal_level.has(vertical)) {
                    visited_current_horizontal_level.set(vertical, []);
                }

                visited_current_horizontal_level.get(vertical).push(pair.treeNode.left.val);
                tail_queuePairs.next = new Pair(pair.treeNode.left, vertical);
                tail_queuePairs = tail_queuePairs.next;
                countAddedNodes_horizontal_level++;
            }

            if (pair.treeNode.right !== null) {

                let vertical = pair.vertical_level + 1;
                if (!visited_current_horizontal_level.has(vertical)) {
                    visited_current_horizontal_level.set(vertical, []);
                }

                visited_current_horizontal_level.get(vertical).push(pair.treeNode.right.val);
                tail_queuePairs.next = new Pair(pair.treeNode.right, vertical);
                tail_queuePairs = tail_queuePairs.next;
                countAddedNodes_horizontal_level++;
            }

            head_queuePairs = head_queuePairs.next;
        }

        //sort by value: nodes added at current horizontal level.
        for (let vertical of visited_current_horizontal_level.keys()) {
            if (!map_vertical_levels.has(vertical)) {
                map_vertical_levels.set(vertical, []);
            }
            const update = [...map_vertical_levels.get(vertical), ...visited_current_horizontal_level.get(vertical).sort((x, y) => x - y)];
            map_vertical_levels.set(vertical, update);
        }

    }

    const nodesInVerticalOrder = [];
    for (let i = minVertical; i <= maxVertical; i++) {
        nodesInVerticalOrder.push(map_vertical_levels.get(i));
    }
    return nodesInVerticalOrder;
};
