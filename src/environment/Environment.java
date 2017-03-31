package environment;

import objects.GameObject;
import processing.core.PApplet;
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

    private Graph gameGraph;

    public Environment(PApplet app)
    {
        this.app = app;

        invalidNodes = new HashSet<>();
        obstacles = new ArrayList<>();

        createObstacles();

        gameGraph = new Graph();
        gameGraph.buildGraph(invalidNodes);

    }

    public void update()
    {
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

    private void createObstacles()
    {

        /* Outer layer */
        obstacles.add(new Obstacle(this.app, new PVector(0.05f * GameConstants.NUM_TILES.x, 0.35f * GameConstants.NUM_TILES.y), new PVector(4, 20), GameConstants.DEFAULT_OBSTACLE_COLOR));
        obstacles.add(new Obstacle(this.app, new PVector(0.88f * GameConstants.NUM_TILES.x, 0.35f * GameConstants.NUM_TILES.y), new PVector(4, 20), GameConstants.DEFAULT_OBSTACLE_COLOR));
        obstacles.add(new Obstacle(this.app, new PVector(0.31f * GameConstants.NUM_TILES.x, 0.05f * GameConstants.NUM_TILES.y), new PVector(20, 4), GameConstants.DEFAULT_OBSTACLE_COLOR));
        obstacles.add(new Obstacle(this.app, new PVector(0.31f * GameConstants.NUM_TILES.x, 0.88f * GameConstants.NUM_TILES.y), new PVector(20, 4), GameConstants.DEFAULT_OBSTACLE_COLOR));

        /* Inner layer */
        obstacles.add(new Obstacle(this.app, new PVector(0.475f * GameConstants.NUM_TILES.x, 0.175f * GameConstants.NUM_TILES.y), new PVector(4, 10), GameConstants.DEFAULT_OBSTACLE_COLOR));
        obstacles.add(new Obstacle(this.app, new PVector(0.475f * GameConstants.NUM_TILES.x, 0.65f * GameConstants.NUM_TILES.y), new PVector(4, 10), GameConstants.DEFAULT_OBSTACLE_COLOR));
        obstacles.add(new Obstacle(this.app, new PVector(0.20f * GameConstants.NUM_TILES.x, 0.475f * GameConstants.NUM_TILES.y), new PVector(10, 4), GameConstants.DEFAULT_OBSTACLE_COLOR));
        obstacles.add(new Obstacle(this.app, new PVector(0.60f * GameConstants.NUM_TILES.x, 0.475f * GameConstants.NUM_TILES.y), new PVector(10, 4), GameConstants.DEFAULT_OBSTACLE_COLOR));


        for (Obstacle obstacle : obstacles)
            invalidNodes.addAll(obstacle.getTileIndices());
    }

    private void drawObstacles()
    {
        for (Obstacle o : obstacles)
            o.draw();
    }



}
