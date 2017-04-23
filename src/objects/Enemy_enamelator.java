package objects;

//import com.sun.tools.doclint.Env;
import engine.Engine;
import environment.Environment;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static objects.Enemy.stateList.ATTACKPLAYER;
import static objects.Enemy.stateList.SEEKTOOTH;
import static objects.Enemy.stateList.SHOOTTOOTH;

/**
 * Created by ujansengupta on 3/31/17.
 */

@SuppressWarnings("FieldCanBeLocal, Duplicates")



public class Enemy_enamelator extends Enemy {

    private static int life = 80;
    private static PVector color = new PVector(204,0,0);
    private static int size = 30;
    private static int PursueRadius = 50;

    private static float DEFAULT_ENAMELATOR_SPEED = 0.4f;
    private static float EnamelatorContactDamage = 15;
    private static float shootingRange = 100;
    private static float bulletDamage = 10;
    private static long bulletInterval = 3000;
    private static float rocketDamage = 15;
    private static int rocketInterval = 7000;
    private static int rocketPathRefreshInterval = 1500;

    public Set<Bullet> bullets;
    private stateList state;
    private boolean followingPath;
    private long lastBulletTime;
    private long lastRocketTime;
    private long lastRocketRefreshTime;
    public Rocket rocket;

    public Enemy_enamelator(PApplet app, float posX, float posY, float orientation)
    {
        super (app, color, size, posX, posY, orientation, life,PursueRadius);

        this.enemyPriority = 5;

        setMaxVel(DEFAULT_ENAMELATOR_SPEED);
        contactDamage = EnamelatorContactDamage;
        bullets = new HashSet<>();
        lastBulletTime = 0;
        lastRocketTime = app.millis();
        lastRocketRefreshTime = app.millis();


        state = SEEKTOOTH;
    }

    public void behaviour()
    {
        setCurrentState();
        updateBullets();
        updateRocket();

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
        if (hasLOS(Engine.player.getPosition()))
            state = ATTACKPLAYER;

        else if (PVector.sub(this.position, Engine.tooth.tooth.position).mag() < shootingRange)
        {
            state = SHOOTTOOTH;
            this.stopMoving();
        }

        else
            state = SEEKTOOTH;
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
                break;

            case SHOOTTOOTH:
                this.finalTarget = Engine.tooth.tooth;
                Align(this.finalTarget.position);
                this.shoot();
                break;
        }
    }

    public void shoot()
    {
        long now = System.currentTimeMillis();
        if(now-lastBulletTime >= bulletInterval)
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
                Engine.tooth.tooth.takeDamage(bulletDamage);
                //System.out.println(Engine.tooth.tooth.getLife());
                i.remove();
            }
            else if(b.hasHit(Engine.player)){
                Engine.player.takeDamage(bulletDamage);
                i.remove();
            }
            else if (b.outOfBounds()) {
                i.remove();
            }
            else
                b.update();
        }
    }

    public void updateRocket()
    {
        long now = app.millis();

        if (rocket != null)
        {
            if (rocket.getGridIndex() == Engine.player.getGridIndex())
            {
                Engine.player.takeDamage(rocketDamage);
                rocket = null;
            }
            else if (rocket.pathFollower.reachedTarget)
                rocket = null;
            else
            {
                if (now - lastRocketRefreshTime > rocketPathRefreshInterval)
                {
                    rocket.pathFollower.findPath(rocket.getGridLocation(), Engine.player.getGridLocation());
                    lastRocketRefreshTime = now;
                }

                rocket.pathFollower.followPath();
                rocket.update();
            }
        }
        else if (now - lastRocketTime > rocketInterval)
        {
            rocket = new Rocket(app, position.x, position.y, orientation, color);
            rocket.pathFollower.followPath();
            rocket.update();

            lastRocketTime = now;
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
