package objects;

import engine.Engine;
import environment.Environment;
import environment.Obstacle;
import environment.PathFollower;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

import java.util.*;

/**
 * Created by bash on 4/17/2017.
 */


@SuppressWarnings("Duplicates")

public class AIplayer extends GameObject {


    private PApplet app;

    //TODO remove debugging prints

    private enum STATUS {
        IDLE,
        SHOOTING_BACK
    }

    public PathFollower pathFollower;


    //TWEAKABLE PARAMETERS
    public static float YELLOW_ZONE, RED_ZONE;
    public long bulletInterval = 500;


    /* Player properties */
    public static PVector color = new PVector(109, 69, 1);
    public static float size = 20;
    private static float DEFAULT_X = GameConstants.SCR_WIDTH/2 + 90;
    private static float DEFAULT_Y = GameConstants.SCR_HEIGHT/2 + 90;
    private static int  patrolTargetIterator = 1;
    private static PVector[] PatrolTargets = {new PVector(DEFAULT_X,DEFAULT_Y), new PVector(GameConstants.SCR_WIDTH/2 + 90,GameConstants.SCR_HEIGHT/2 - 90), new PVector(GameConstants.SCR_WIDTH/2 - 90,GameConstants.SCR_HEIGHT/2 - 90), new PVector(GameConstants.SCR_WIDTH/2 - 90, GameConstants.SCR_HEIGHT/2 + 90)};


    private static float DEFAULT_ORIENTATION = 0;

    public static int getDefaultPlayerLife() {
        return DEFAULT_PLAYER_LIFE;
    }

    public static final int DEFAULT_PLAYER_LIFE = 100;
    private static float DEFAULT_PLAYER_MAXVEL = 1f;
    private static STATUS status;
    private static boolean followingPath;
    private static Enemy enemycurrentlyHighestPriority;
    private static Enemy enemyPreviouslyHighestPriority;
    private long lastBulletTime;
    private static float enemyPerceptionRadius = 100f;
    private static int minimumRampageLimit = 4;



    public Set<Bullet> bullets;
    public int enemiesKilled = 0;

    public PVector playerTarget;
    public static float BulletDamage = 10;


    public AIplayer(PApplet app)
    {
        super(app, color, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);
        this.app = app;
        status = STATUS.IDLE;
        YELLOW_ZONE = 400f;
        RED_ZONE = 200f;
        setMaxVel(DEFAULT_PLAYER_MAXVEL);
        bullets = new HashSet<>();

        pathFollower = new PathFollower(this, Environment.numTiles, Environment.tileSize);
        enemycurrentlyHighestPriority = null;
        enemyPreviouslyHighestPriority = null;

        playerTarget = new PVector(PatrolTargets[patrolTargetIterator].x,PatrolTargets[patrolTargetIterator].y);
    }

    public void update()
    {
        behaviour();

        super.update();
        updateBullets();
    }

    public void setStatus()
    {
        if (isSurrounded())
            bulletInterval = 100;
        else
            bulletInterval = 500;


        enemycurrentlyHighestPriority = getEnemyWithHighestPriority();

        if (enemycurrentlyHighestPriority == null)
        {
            followingPath = false;
            status = STATUS.IDLE;
            return;
        }
        else
        {
            if (enemyPreviouslyHighestPriority != enemycurrentlyHighestPriority)
                followingPath = false;

            playerTarget = enemycurrentlyHighestPriority.position;

            app.ellipse(enemycurrentlyHighestPriority.position.x, enemycurrentlyHighestPriority.position.y, 50, 50);
            enemyPreviouslyHighestPriority = enemycurrentlyHighestPriority;

            status = STATUS.SHOOTING_BACK;
        }

        if (followingPath && hasLOS(enemycurrentlyHighestPriority.getPosition()) && !Environment.invalidNodes.contains(getGridIndex()))
            followingPath = false;

    }

    public void behaviour()
    {
        //depending on the current status of the player, perform a different action
        setStatus();

        if (followingPath && !pathFollower.reachedTarget) {
            //pathFollower.renderSearch();
            pathFollower.followPath();
        }

        else if (followingPath)
            followingPath = false;

        else if (obstacleCollisionDetected())
            avoidObstacle();

        else
            defaultBehavior();

    }


    public void defaultBehavior()
    {
        switch (status)
        {
            case SHOOTING_BACK:
                shootingBack();
                break;
            case IDLE:
                patrolTooth();
                break;
        }
    }

    public boolean isSurrounded()
    {
        int enemyCount = 0;

        for (Enemy e : Engine.Enemies)
            if (PVector.sub(position, e.getPosition()).mag() < enemyPerceptionRadius)
                enemyCount++;

        return (enemyCount >= minimumRampageLimit);
    }

    public void patrolTooth()
    {
        if(this.getPosition().dist(this.playerTarget) <= 5f)
        {
            patrolTargetIterator = (patrolTargetIterator + 1) % 4;
            updateTarget(PatrolTargets[patrolTargetIterator]);
        }
        Align(playerTarget);
        Arrive(playerTarget);
    }

    public void updateTarget(PVector updatedTarget)
    {
        // this method is called frequently to update the final target
        if (!Environment.invalidNodes.contains(Utility.getGridIndex(new PVector(updatedTarget.x, updatedTarget.y))))
            playerTarget.set(updatedTarget.x, updatedTarget.y);
    }

    public void avoidObstacle(){
        //path follow to target
        try {
            updateTarget(enemycurrentlyHighestPriority.position);
        }catch(NullPointerException e){
            updateTarget(PatrolTargets[patrolTargetIterator]);
            //System.out.println("enemy is null");
        }

        try {
            followingPath = true;
            pathFollower.findPath(this.getGridLocation(), Utility.getGridLocation(playerTarget));
            pathFollower.followPath();
        }catch(NullPointerException e){
            System.out.println("Null pointer exception bro");
        }
    }

    public void shootingBack()
    {
        // probably has LOS, but if not then get LOS and shoot at enemy
        this.stopMoving();
        if(enemycurrentlyHighestPriority.getLife() > 0)
        {
            if(hasLOS(enemycurrentlyHighestPriority.getPosition()))
            {
                stopMoving();
                setOrientation(PVector.sub(enemycurrentlyHighestPriority.getPosition(), position).heading());
                shoot();
            }
            else
            {
                try {
                    followingPath = true;
                    pathFollower.findPath(getGridLocation(), Utility.getGridLocation(enemycurrentlyHighestPriority.position));
                    pathFollower.followPath();
                }catch(NullPointerException e){
                    System.out.println("Null in moving to LOS");
                }

            }
        }
    }

    public Enemy getEnemyWithHighestPriority()
    {
        float highestPrioritySoFar = 0;
        Enemy enemyWithHighestPriority = null;


        for (Iterator<Enemy> j = Engine.Enemies.iterator(); j.hasNext();)
        {
            Enemy e = j.next();

            if(e instanceof Enemy_enamelator)
            {
                return e;
            }

            if(e.position.dist(Engine.tooth.tooth.getPosition()) <= RED_ZONE)
            {
                return e;
            }

            if(e.position.dist(Engine.tooth.tooth.getPosition()) <= YELLOW_ZONE)
            {
                if(e.enemyPriority < 10)
                {
                    e.enemyPriority += 10;
                }
                if (highestPrioritySoFar < e.enemyPriority)
                {
                    highestPrioritySoFar = e.enemyPriority;
                    enemyWithHighestPriority = e;
                }
            }
            else
            {
                if(e.enemyPriority > 10)
                    e.enemyPriority -= 10;
            }
        }
        return enemyWithHighestPriority;
    }

    //Overrode this method to avoid tooth collisions- may be a temporary fix
    public boolean obstacleCollisionDetected()
    {
        lookAheadPosition = PVector.fromAngle(velocity.heading()).mult(lookAheadDistance);

        lookAheadPosition.x += position.x;
        lookAheadPosition.y += position.y;

        for (Obstacle o : Engine.staticObjects)
            if (o.contains(Utility.getGridLocation(lookAheadPosition)))
                return true;

        return false;
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
        for (Iterator<Bullet> i = bullets.iterator(); i.hasNext(); ) {
            Bullet b = i.next();
            boolean bulletRemoved = false;

            for(Iterator<Enemy> j = Engine.Enemies.iterator(); j.hasNext(); ){
                Enemy e = j.next();
                if(b.hasHit(e))
                {
                    i.remove();
                    bulletRemoved = true;
                    e.takeDamage(AIplayer.BulletDamage);

                    if(e.getLife()<=0)
                    {
                        enemiesKilled++;
                        j.remove();
                        this.playerTarget = PatrolTargets[patrolTargetIterator];
                    }
                    break;
                }
            }

            if(bulletRemoved)
                break;


            if (b.outOfBounds() || Environment.toothNodes.contains(Utility.getGridIndex(b.position)))
                i.remove();

            else
                b.update();
        }

    }
}
