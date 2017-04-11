package environment;

import movement.Align;
import movement.Seek;
import objects.GameObject;
import processing.core.PVector;

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

    private GraphSearch search;


    private float targetSwitchDistance = 2f;

    private int pathOffset = 4;         //Actually 4. The +1 is to account for the 0 based indexing of the path.

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

    public void followPath(PVector startNode, PVector endNode)
    {
        this.path.clear();
        targetIndex = 0;

        int startIndex = (int) (startNode.y * numTiles.y + startNode.x);
        int endIndex = (int) (endNode.y * numTiles.y + endNode.x);


        for (; Environment.invalidNodes.contains(endIndex); endIndex--);
        for (; Environment.invalidNodes.contains(startIndex); startIndex--);

        pathIndices = search.aStarSearch(startIndex, endIndex, Environment.gameGraph);

        Collections.reverse(path);                                   // Since the path is returned in the reverse order

        for (int index : pathIndices) {
            this.path.add(new PVector((index % numTiles.x) * tileSize.x + tileSize.x / 2, (index / numTiles.x) * tileSize.y + tileSize.y / 8));
        }

        this.currentTarget = this.path.get(targetIndex);
    }

    public void update()
    {
        if (path != null && path.size() > 0)
        {
            character.Seek(currentTarget);

            if (character.getVelocity().mag() < targetSwitchDistance  && targetIndex < path.size() - 1)
            {
                targetIndex += pathOffset;

                if (targetIndex >= path.size())
                    targetIndex = (path.size() - 1);

                currentTarget = path.get(targetIndex);
            }
        }
    }
}
