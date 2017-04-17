package objects;

import environment.Environment;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

import static objects.Enemy.stateList.*;


/**
 * Created by ujansengupta on 3/31/17.
 */

@SuppressWarnings("FieldCanBeLocal")


public class Enemy_streptus extends Enemy
{
    private static int life = 60;
    private static PVector color = new PVector(153,0,153);
    private static int size = 20;
    private static int PursueRadius = 50;

    private static float DEFAULT_STREPTUS_SPEED = 0.5f;
    private static float StreptusContactDamage = 15;
    private static float shootingRange = 100;
    public static float BulletDamage = 10;

    private stateList state;
    private boolean followingPath;

    public Set<Bullet> bullets;


    public Enemy_streptus(PApplet app, float posX, float posY, float orientation)
    {
        super (app, color, size, posX, posY, orientation, life, PursueRadius);

        setMaxVel(DEFAULT_STREPTUS_SPEED);
        contactDamage = StreptusContactDamage;
        bullets = new HashSet<>();

        state = SEEKTOOTH;
    }

    public void setCurrentMode()
    {

    }


    public void behaviour()
    {
        if (obstacleCollisionDetected())
            avoidObstacle();

        else
            defaultBehaviour();

    }

    public void defaultBehaviour()
    {

    }

    public void avoidObstacle()
    {

    }

}
