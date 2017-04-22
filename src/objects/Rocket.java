package objects;

import engine.Engine;
import environment.PathFollower;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

/**
 * Created by ujansengupta on 4/22/17.
 */
public class Rocket extends GameObject
{
    public PathFollower pathFollower;
    public static float rocketSize = 10;

    public Rocket(PApplet app, float posX, float posY, float orientation, PVector color)
    {
        super(app, color, rocketSize, posX, posY, orientation, 10);
        setMaxVel(1.5f);
        pathFollower = new PathFollower(this, GameConstants.NUM_TILES, GameConstants.TILE_SIZE);
        pathFollower.findPath(this.getGridLocation(), Engine.player.getGridLocation());
    }

}
