package utility;

import processing.core.PVector;

/**
 * Created by ujansengupta on 3/29/17.
 */
public class GameConstants
{

    public static final int SCR_WIDTH = 1000;
    public static final int SCR_HEIGHT = 800;
    public static final float SCR_OFFSET = 20;

    public static final PVector NUM_TILES = new PVector(50, 50);
    public static final PVector TILE_SIZE = new PVector(SCR_WIDTH/NUM_TILES.x, SCR_HEIGHT/NUM_TILES.y);

    public static final PVector DEFAULT_OBSTACLE_COLOR = new PVector(123, 116, 214);

    public static final float DEFAULT_MAX_VEL = 3;
    public static final float DEFAULT_MAX_linearACC = 1;

    public static final float DEFAULT_MAX_ROTATION = 2 * (float)Math.PI;
    public static final float DEFAULT_MAX_angularACC = 0.005f;

    public static final float DEFAULT_linearROS = 3;
    public static final float DEFAULT_linearROD = 15;

    public static final float DEFAULT_angularROS = 1.5f;
    public static final float DEFAULT_angularROD = 2.5f;

    public static final int DEFAULT_LIFE = 100;

    public static final heuristic DEFAULT_HEURISTIC = heuristic.EUCLIDEAN;

    public enum searchType
    {
        ASTAR, DIJKSTRA
    }

    public enum heuristic {
        MANHATTAN, EUCLIDEAN
    }

}
