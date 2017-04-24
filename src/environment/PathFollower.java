package environment;

import movement.Align;
import movement.Seek;
import objects.GameObject;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ujansengupta on 3/25/17.
 */

public class PathFollower
{
    private PVector numTiles;
    private PVector tileSize;

    private GameObject character;
    private ArrayList<Integer> pathIndices;
    private ArrayList<PVector> path;
    private PVector currentTarget;
    private int targetIndex = 0;
    private int characterGridIndex;
    private int targetGridIndex;

    private GraphSearch search;

    private int pathOffset = 2;         //Actually 3. The +1 is to account for the 0 based indexing of the path.

    public boolean reachedTarget;

    public PathFollower(GameObject character, PVector numTiles, PVector tileSize)
    {
        this.character = character;
        this.numTiles = numTiles;
        this.tileSize = tileSize;

        search = new GraphSearch();

        path = new ArrayList<>();
        pathIndices = new ArrayList<>();
    }

    public int getTargetIndex()
    {
        return targetIndex;
    }

    public void findPath(PVector startNode, PVector endNode)
    {
        this.path.clear();
        targetIndex = 1;
        reachedTarget = false;

        int startIndex = (int) (startNode.y * numTiles.y + startNode.x);
        int endIndex = (int) (endNode.y * numTiles.y + endNode.x);


        if (Environment.invalidNodes.contains(startIndex))
            startIndex = checkNearestValidNode(startIndex, 1);

        if (Environment.invalidNodes.contains(endIndex))
            endIndex = checkNearestValidNode(endIndex, 1);

        try {
            pathIndices = search.aStarSearch(startIndex, endIndex, Environment.gameGraph);
        }catch(Exception e){
            System.out.println("Unable to find path");
        }

        Collections.reverse(pathIndices);                                   // Since the path is returned in the reverse order

        for (int index : pathIndices) {
            this.path.add(new PVector((index % numTiles.x) * tileSize.x + tileSize.x / 2, (index / numTiles.x) * tileSize.y + tileSize.y / 8));
        }

        this.currentTarget = this.path.get(targetIndex);

        //renderSearch();
    }

    public int checkNearestValidNode(int index, int layer)
    {
        int nodeAbove = index - (int) (GameConstants.NUM_TILES.x * layer);
        int nodeBelow = index + (int) (GameConstants.NUM_TILES.x * layer);

        if (!Environment.invalidNodes.contains(nodeAbove))
            return nodeAbove;

        if (!Environment.invalidNodes.contains(nodeAbove - 1))
            return (nodeAbove - 1);

        if (!Environment.invalidNodes.contains(nodeAbove + 1))
            return (nodeAbove + 1);

        if (!Environment.invalidNodes.contains(nodeBelow))
            return nodeBelow;

        if (!Environment.invalidNodes.contains(nodeBelow - 1))
            return (nodeBelow - 1);

        if (!Environment.invalidNodes.contains(nodeBelow + 1))
            return (nodeBelow + 1);

        if (!Environment.invalidNodes.contains(index - layer))
            return (index - 1);

        if (!Environment.invalidNodes.contains(index + layer))
            return (index + 1);

        return checkNearestValidNode(index, layer+1);


    }

    public void followPath()
    {
        if (path != null && path.size() > 0)
        {
            character.Align(currentTarget);
            character.Seek(currentTarget);

            characterGridIndex = character.getGridIndex();
            targetGridIndex = Utility.getGridIndex(currentTarget);

            if (characterGridIndex == targetGridIndex && targetIndex < path.size())
            {
                targetIndex += pathOffset;

                if (targetIndex >= path.size())
                {
                    targetIndex = (path.size() - 1);
                    reachedTarget = true;
                }

                currentTarget = path.get(targetIndex);
            }
        }
    }

    public void renderSearch()
    {
        if (pathIndices != null)
        {
            float alpha = 200;
            for (int node : search.getClosedList())
            {
                Environment.colorNode(node, new PVector(71, 153 , 131), alpha);
            }

            for (int node : pathIndices)
            {
                Environment.colorNode(node, new PVector(153, 71 , 97), alpha + 25);
            }

            Environment.colorNode(pathIndices.get(0), new PVector(255, 0, 0), alpha);
            Environment.colorNode(pathIndices.get(pathIndices.size() - 1), new PVector(0, 255, 0), alpha);
        }
    }
}
