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

    protected int PURSUE_RADIUS;

    public GameObject getFinalTarget()
    {
        return finalTarget;
    }

    protected GameObject finalTarget;

    public Enemy(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life, int PursueRadius)
    {
        super (app, color, size, posX, posY, orientation, life);
        this.PURSUE_RADIUS = PursueRadius;
        this.finalTarget = Engine.tooth.tooth;
    }

    public enum modeList
    {
        ATTACKPLAYER, SEEKTOOTH, WANDER
    }

    public abstract void defaultBehaviour();

}
