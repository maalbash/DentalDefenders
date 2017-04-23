package objects;

import engine.Engine;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static objects.Enemy.stateList.*;


/**
 * Created by ujansengupta on 3/31/17.
 */

@SuppressWarnings("FieldCanBeLocal, Duplicates")

public class Enemy_streptus extends Enemy
{
    private static int life = 60;
    private static PVector color = new PVector(153,0,153);
    private static int size = 20;
    private static int PursueRadius = 200;

    private static float DEFAULT_STREPTUS_SPEED = 0.5f;
    private static float StreptusContactDamage = 15;
    private static float shootingRange = 200;
    public static float BulletDamage = 10;
    private static long bulletInterval = 1000;

    public static int toothDamage = 0;
    public static int playerDamage = 0;

    private stateList state;
    private boolean followingPath;
    private long lastBulletTime;

    public Set<Bullet> bullets;


    public Enemy_streptus(PApplet app, float posX, float posY, float orientation)
    {
        super (app, color, size, posX, posY, orientation, life, PursueRadius);

        this.enemyPriority = 3;

        setMaxVel(DEFAULT_STREPTUS_SPEED);
        contactDamage = StreptusContactDamage;
        bullets = new HashSet<>();
        lastBulletTime = 0;

        state = SEEKTOOTH;
    }

    public void behaviour()
    {
        setCurrentState();

        if (followingPath && !pathFollower.reachedTarget)
            pathFollower.followPath();

        else if (followingPath)
            followingPath = false;

        else if (obstacleCollisionDetected())
            avoidObstacle();

        else
            defaultBehaviour();
    }

    public void setCurrentState()
    {
        float playerdist, toothdist;

        playerdist = PVector.sub(this.position, Engine.player.position).mag();
        toothdist = PVector.sub(this.position, Engine.tooth.tooth.position).mag();

        if (followingPath)
        {
            if (playerdist < this.PURSUE_RADIUS && hasLOS(Engine.player.getPosition()))
            {
                followingPath = false;
                state = ATTACKPLAYER;
            }
            else if(toothdist <= shootingRange)
            {
                followingPath = false;
                state = SHOOTTOOTH;
            }
        }
        else if(playerdist < this.PURSUE_RADIUS && hasLOS(Engine.player.getPosition()))
        {
            state = ATTACKPLAYER;
            this.stopMoving();
        }
        else if(toothdist <= shootingRange)
        {
            state = SHOOTTOOTH;
            this.stopMoving();
        }
        else
        {
            state = SEEKTOOTH;
        }
    }

    public void defaultBehaviour()
    {
        switch(state)
        {
            case SEEKTOOTH:
                this.finalTarget = Engine.tooth.tooth;
                Align(this.finalTarget.position);
                Seek(this.finalTarget.position);
                break;

            case ATTACKPLAYER:
                this.finalTarget = Engine.player;
                Align(this.finalTarget.position);
                this.shoot();
                this.updateBullets();
                break;

            case SHOOTTOOTH:
                this.finalTarget = Engine.tooth.tooth;
                Align(this.finalTarget.position);
                this.shoot();
                this.updateBullets();
                break;
        }
    }

    public void shoot()
    {
        long now = System.currentTimeMillis();
        if(now - lastBulletTime >= bulletInterval)
        {
            bullets.add(new Bullet(app, getPosition(), getOrientation(), GameConstants.DEFAULT_BULLET_SIZE, color, Bullet.Origin.ENEMY));
            lastBulletTime = now;
        }
    }

    public void updateBullets()
    {
        for (Iterator<Bullet> i = bullets.iterator(); i.hasNext(); )
        {
            Bullet b = i.next();

            if(b.hasHit(Engine.tooth))
            {
                Engine.tooth.tooth.takeDamage(BulletDamage);
                toothDamage+=BulletDamage;
                //System.out.println(Engine.tooth.tooth.getLife());
                i.remove();
            }
            else if(b.hasHit(Engine.player)){
                Engine.player.takeDamage(BulletDamage);
                playerDamage+=BulletDamage;
                i.remove();
            }
            else if (b.outOfBounds()) {
                i.remove();
            }
            else
                b.update();
        }
    }


    public void avoidObstacle()
    {
        if (state != ATTACKPLAYER)
        {
            pathFollower.findPath(getGridLocation(), Utility.getGridLocation(finalTarget.position));
            followingPath = true;
        }
    }

}
