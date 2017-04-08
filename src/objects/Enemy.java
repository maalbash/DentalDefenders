package objects;

import engine.Engine;
import processing.core.PApplet;
import processing.core.PVector;
import utility.Movable;
import utility.Utility;

/**
 * Created by ujansengupta on 3/31/17.
 */
public abstract class Enemy extends GameObject
{
    public static final int DEFAULT_ENEMY_LIFE = 100;

    protected int PURSUE_RADIUS;

    public GameObject getFinalTarget() {
        return finalTarget;
    }

    protected GameObject finalTarget;

    public Enemy(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life, int RoPursue)
    {
        super (app, color, size, posX, posY, orientation, life);
        this.PURSUE_RADIUS = RoPursue;
        this.finalTarget = Engine.tooth.tooth;
    }

    public enum modeList{
        ATTACKPLAYER, SEEKTOOTH, WANDER
    }

    public abstract void defaultBehaviour();

}
