package objects;

import movement.KinematicOutput;
import movement.Seek;
import processing.core.PApplet;
import processing.core.PVector;
import utility.Movable;

/**
 * Created by ujansengupta on 3/31/17.
 */
public abstract class Enemy extends GameObject implements Movable
{
    public static final int DEFAULT_ENEMY_LIFE = 100;

    protected int PURSUE_RADIUS;

    public Enemy(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life, int RoPursue)
    {
        super (app, color, size, posX, posY, orientation, life);
        this.PURSUE_RADIUS = RoPursue;
    }

    public enum mode{
        ATTACK, FINDTOOTH, WANDER
    }

    public abstract void defaultBehaviour();


}
