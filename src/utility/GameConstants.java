package utility;

import processing.core.PVector;

/**
 * Created by ujansengupta on 3/29/17.
 */
public class GameConstants
{

    public static final int SCR_WIDTH = 1300;
    public static final int SCR_HEIGHT = 800;
    public static final float SCR_OFFSET = 20;
    public static final PVector SCR_CENTER = new PVector(SCR_WIDTH/2, SCR_HEIGHT/2);


    public static final PVector NUM_TILES = new PVector(50, 50);
    public static final PVector TILE_SIZE = new PVector(SCR_WIDTH/NUM_TILES.x, SCR_HEIGHT/NUM_TILES.y);
    public static final PVector GRAPH_CENTER = new PVector(0.5f * NUM_TILES.x, 0.5f * NUM_TILES.y);

    public static final PVector DEFAULT_OBSTACLE_COLOR = new PVector(123, 116, 214);

    public static final PVector DEFAULT_VEL = new PVector(0,0);
    public static final PVector DEFAULT_LINEAR_ACC = new PVector(0,0);
    public static final float DEFAULT_ANGULAR_ACC = 0;
    public static final float DEFAULT_ROT = 0;

    public static final float DEFAULT_MAX_VEL = 2;
    public static final float DEFAULT_MAX_linearACC = 1;

    public static final float DEFAULT_MAX_ROTATION = 2 * (float)Math.PI;
    public static final float DEFAULT_MAX_angularACC = 0.005f;

    public static final float DEFAULT_linearROS = 3;
    public static final float DEFAULT_linearROD = 15;

    public static final float DEFAULT_angularROS = 0.15f;
    public static final float DEFAULT_angularROD = 3f;

    public static final float DEFAULT_TTTVEL = 10f;
    public static final float DEFAULT_TTTROT = 100f;

    public static final float DEFAULT_BULLET_SPEED = 7;
    public static final PVector DEFAULT_BULLET_SIZE = new PVector(5, 5);

    public static final int DEFAULT_LOOKAHEAD = 50;

}
