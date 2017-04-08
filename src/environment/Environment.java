package environment;

import objects.GameObject;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import utility.GameConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ujansengupta on 3/31/17.
 */
public class Environment
{
    private PApplet app;

    private List<Obstacle> obstacles;
    private Set<Integer> invalidNodes;
    private static PVector tileSize;
    private static PVector numTiles; 

    private Graph gameGraph;

    public Environment(PApplet app)
    {
        this.app = app;
        tileSize = GameConstants.TILE_SIZE;
        numTiles = GameConstants.NUM_TILES;

        invalidNodes = new HashSet<>();
        obstacles = new ArrayList<>();

        createObstacles();

        gameGraph = new Graph();
        gameGraph.buildGraph(invalidNodes);

    }

    public void update()
    {
        drawObstacles();
        //drawGraph();
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
        obstacles.add(new Obstacle(app, new PVector(0.2f * numTiles.x, 0.2f * numTiles.y), new PVector(4, 15))); //top left
        obstacles.add(new Obstacle(app, new PVector(0.8f * numTiles.x, 0.2f * numTiles.y), new PVector(4, 15))); //top right
        obstacles.add(new Obstacle(app, new PVector(0.2f * numTiles.x, 0.8f * numTiles.y), new PVector(4, 15))); //bot left
        obstacles.add(new Obstacle(app, new PVector(0.8f * numTiles.x, 0.8f * numTiles.y), new PVector(4, 15))); //bot right
        //obstacles.add(new Obstacle(app, new PVector(0.5f * numTiles.x, 0.1f * numTiles.y), new PVector(10, 4)));
        //obstacles.add(new Obstacle(app, new PVector(0.5f * numTiles.x, 0.9f * numTiles.y), new PVector(10, 4)));

        /* Inner layer *//*
        obstacles.add(new Obstacle(app, new PVector(0.3f * numTiles.x, 0.5f * numTiles.y), new PVector(10, 4)));
        obstacles.add(new Obstacle(app, new PVector(0.7f * numTiles.x, 0.5f * numTiles.y), new PVector(10, 4)));
        obstacles.add(new Obstacle(app, new PVector(0.5f * numTiles.x, 0.3f * numTiles.y), new PVector(4, 10)));
        obstacles.add(new Obstacle(app, new PVector(0.5f * numTiles.x, 0.7f * numTiles.y), new PVector(4, 10)));*/


        for (Obstacle obstacle : obstacles)
            invalidNodes.addAll(obstacle.getTileIndices());
    }

    private void drawObstacles()
    {
        for (Obstacle o : obstacles)
            o.draw();
    }



}
