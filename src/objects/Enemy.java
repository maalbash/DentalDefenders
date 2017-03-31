package objects;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by ujansengupta on 3/31/17.
 */
public abstract class Enemy extends GameObject
{
    protected int RoPursue;

    public Enemy(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life, int RoPursue)
    {
        super (app, color, size, posX, posY, orientation, life);
        this.RoPursue = RoPursue;
    }

    public enum mode{
        ATTACK, FINDTOOTH, WANDER
    }

}
