package game_trees;

/**
 * Created by ujansengupta on 4/15/17.
 */
public class DecisionTreeNode
{
    public DecisionTreeNode leftChild, rightChild;
    public int treeLevel;

    public DecisionTreeNode(int treeLevel)
    {
        this.treeLevel = treeLevel;

        leftChild = new DecisionTreeNode(treeLevel + 1);
        rightChild = new DecisionTreeNode(treeLevel + 1);
    }

    public void evaluate()
    {

    }

}
