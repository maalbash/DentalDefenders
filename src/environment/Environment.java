package environment;

import engine.Engine;
import objects.GameObject;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import utility.GameConstants;

import java.util.*;

/**
 * Created by ujansengupta on 3/31/17.
 */
public class Environment
{
    private static PApplet app;

    public static List<Obstacle> obstacles;
    public static Set<Integer> invalidNodes;
    public static Set<Integer> toothNodes;
    public static PVector tileSize;
    public static PVector numTiles;

    public static Graph gameGraph;

    public Environment(PApplet theApp)
    {
        app = theApp;
        tileSize = GameConstants.TILE_SIZE;
        numTiles = GameConstants.NUM_TILES;

        invalidNodes = new HashSet<>();
        toothNodes = new HashSet<>();
        obstacles = new ArrayList<>();

        createObstacles();
        toothNodes.addAll(Engine.tooth.getTileIndices());

        gameGraph = new Graph();
        gameGraph.buildGraph(invalidNodes);

    }

    public void update()
    {
        //drawGraph();
        drawInvalidNodes();

        drawObstacles();
    }


    /* Getters and Setters */

    public Set<Integer> getInvlaidNodes()
    {
        return invalidNodes;
    }

    public List<Obstacle> getObstacles()
    {
        return obstacles;
    }

    public Graph getGameGraph() {
        return gameGraph;
    }


    /* Helper methods */
    
    public void drawGraph()
    {
        app.rectMode(PConstants.CORNER);

        for (int i = 0; i < numTiles.y; i++)
        {
            for (int j = 0; j < numTiles.x; j++)
            {
                app.noFill();
                app.rect(j * tileSize.x, i * tileSize.y, tileSize.x, tileSize.y);
            }
        }

        app.rectMode(PConstants.CENTER);

    }

    private void createObstacles()
    {

        /* Clockwise from left */

        /* Outer layer */
        obstacles.add(new Obstacle(app, new PVector(0.3f * numTiles.x, 0.3f * numTiles.y), new PVector(4, 15))); //top left
        obstacles.add(new Obstacle(app, new PVector(0.7f * numTiles.x, 0.3f * numTiles.y), new PVector(4, 15))); //top right
        obstacles.add(new Obstacle(app, new PVector(0.3f * numTiles.x, 0.7f * numTiles.y), new PVector(4, 15))); //bot left
        obstacles.add(new Obstacle(app, new PVector(0.7f * numTiles.x, 0.7f * numTiles.y), new PVector(4, 15))); //bot right

        /* Tooth nodes are not considered invalid */

        for (Obstacle obstacle : obstacles)
            invalidNodes.addAll(obstacle.getTileIndices());

    }

    private void drawObstacles()
    {
        obstacles.forEach(Obstacle::draw);
    }

    private void drawInvalidNodes()
    {
        app.rectMode(PConstants.CORNER);

        for (int i : invalidNodes)
            colorNode(i, new PVector(255, 0, 0), 255);

        for (int i: toothNodes)
            colorNode(i, new PVector(255, 0, 0), 255);


        app.rectMode(PConstants.CENTER);

    }

    public static void colorNode(int index, PVector color, float alpha)
    {
        app.fill(color.x, color.y, color.z, alpha);
        app.rect((index % numTiles.x) * tileSize.x, (float)Math.floor(index / numTiles.y) * tileSize.y, tileSize.x, tileSize.y);
        app.noFill();
    }

}
