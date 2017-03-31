package objects;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by ujansengupta on 3/31/17.
 */
public abstract class Enemy extends GameObject
{

    public Enemy(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life)
    {
        super (app, color, size, posX, posY, orientation, life);
    }

}
