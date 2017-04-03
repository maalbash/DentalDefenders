package objects;

import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;

/**
 * Created by ujansengupta on 3/31/17.
 */
public class Player extends GameObject
{

    PApplet app;

    /* Player properties */

    private static PVector color = new PVector(109, 69, 1);
    private static float size = 20;
    private static float DEFAULT_X = GameConstants.SCR_WIDTH/2;
    private static float DEFAULT_Y = GameConstants.SCR_HEIGHT/2;
    private static float DEFAULT_ORIENTATION = 0;

    private static final int DEFAULT_PLAYER_LIFE = 100;


    public Player(PApplet app)
    {
        super (app, color, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);

    }

    public Player(PApplet app, float posX, float posY, float orientation)
    {
        super (app, color, size, posX, posY, orientation, DEFAULT_PLAYER_LIFE);
    }
}

