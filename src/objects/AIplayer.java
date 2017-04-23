package objects;

import engine.Engine;
import environment.Environment;
import environment.PathFollower;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by bash on 4/17/2017.
 */


@SuppressWarnings("Duplicates")

public class AIplayer extends GameObject {


    private PApplet app;


    private enum STATUS {
        IDLE,
        AVOIDING_OBSTACLE,
        DEFENDING_TOOTH,
        SHOOTING_BACK,

    }

    private enum PRIORITY {
        TOOTH,
        PLAYER
    }

    public GameObject finalTarget;
    public PathFollower pathFollower;


    //TWEAKABLE PARAMETERS
    public static float GREEN_ZONE,RED_ZONE;
    public static float YELLOW_ZONE = 400;
    private static long bulletInterval = 500;


    /* Player properties */
    public static PVector color = new PVector(109, 69, 1);
    public static float size = 20;
    private static float DEFAULT_X = GameConstants.SCR_WIDTH/2 + 90;
    private static float DEFAULT_Y = GameConstants.SCR_HEIGHT/2 + 90;
    private static PVector DEFAULT_POS = new PVector(DEFAULT_X,DEFAULT_Y);
    private static float DEFAULT_ORIENTATION = 0;
    private static final int DEFAULT_PLAYER_LIFE = 100;
    private static float DEFAULT_PLAYER_MAXVEL = 1f;
    private static STATUS status;
    private static PRIORITY priority;
    private static boolean pathFollowing;
    private static PRIORITY currPriorityAsset;
    private static float shootingOffset = 2f;
    private static Enemy enemycurrentlyHighestPriority;
    private long lastBulletTime;



    public Set<Bullet> bullets;
    public int bulletCount = 0;
    public int enemiesKilled = 0;

    public PVector playerTarget;
    public static float BulletDamage = 10;


    public AIplayer(PApplet app)
    {
        super(app, color, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);

        this.app = app;

        status = STATUS.IDLE;
        currPriorityAsset = PRIORITY.PLAYER;

        YELLOW_ZONE = 500f;
        setMaxVel(DEFAULT_PLAYER_MAXVEL);

        bullets = new HashSet<>();
        playerTarget = new PVector(DEFAULT_X, DEFAULT_Y);
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

    public void update()
    {
        //updating the position and orientation of the player

        //Align(playerTarget);
        //Arrive(playerTarget);

        behaviour();
        super.update();
        updateBullets();

    }

    public void updateBullets(){
        for (Iterator<Bullet> i = bullets.iterator(); i.hasNext(); ) {
            Bullet b = i.next();
            boolean bulletRemoved = false;

            for(Iterator<Enemy> j = Engine.Enemies.iterator(); j.hasNext(); ){
                Enemy e = j.next();
                if(b.hasHit(e))
                {
                    i.remove();
                    bulletRemoved = true;
                    e.takeDamage(Player.BulletDamage);

                    if(e.getLife()<=0)
                    {
                        j.remove();
                    }
                    break;
                }
            }

            if(bulletRemoved){
                break;
            }

            if (b.outOfBounds()) {
                i.remove();
                bulletRemoved = true;
            }
            else
                b.update();
        }
    }


    public void setStatus()
    {
        //player should, according to different parameters, perform an action. this method sets the state of the player.
        if ((float) Engine.tooth.tooth.getLife() / (float) Engine.tooth.life <= 0.25f)
        {
            currPriorityAsset = PRIORITY.TOOTH;
        }
        else
        {
            if((float) this.getLife() / (float) DEFAULT_PLAYER_LIFE <= 0.25f)
                currPriorityAsset = PRIORITY.PLAYER;
        }

        enemycurrentlyHighestPriority = getEnemyWithHighestPriority();
        if(obstacleCollisionDetected())
        {
            status = STATUS.AVOIDING_OBSTACLE;
        }
        else
        {
            if (enemycurrentlyHighestPriority == null) {
                //System.out.println("No enemy priority :/");
                status = STATUS.IDLE;
            }
            else
            {
                status = STATUS.SHOOTING_BACK;
            }
        }
    }

    public void behaviour(){
        //depending on the current status of the player, perform a different action
        setStatus();
        //System.out.println(status);

        switch (status)
        {
            case AVOIDING_OBSTACLE:
                //Align(playerTarget);
                //Arrive(playerTarget);
                avoidObstacle();
                break;
            case DEFENDING_TOOTH:
                Align(playerTarget);
                Arrive(playerTarget);
                defendTooth();
                break;
            case SHOOTING_BACK:
                //Align(playerTarget);
                //Arrive(playerTarget);
                shootingBack();
                break;
            case IDLE:
                Wander();
                //defaultPosition();
                break;

        }
    }


    public void defaultPosition()
    {
        //if the status is idle, return to default location.
        updateTarget(DEFAULT_POS);
        update();
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
            playerTarget = enemycurrentlyHighestPriority.position;
        }catch(NullPointerException e){
            playerTarget = DEFAULT_POS;
        }

        try {
            pathFollower.findPath(getGridLocation(), Utility.getGridLocation(playerTarget));
            pathFollowing = true;
            pathFollower.followPath();
        }catch(NullPointerException e){
            System.out.println("Null pointer exception bro");
        }
    }


    public void defendTooth(){
        // if not LOS, then get LOS and shoot at enemy
    }

    public void shootingBack(){
        // probably has LOS, but if not then get LOS and shoot at enemy
        if(enemycurrentlyHighestPriority.getLife() > 0)
        {
            if(hasLOS(enemycurrentlyHighestPriority.getPosition()))
            {
                PVector shootingPoint = PVector.add(enemycurrentlyHighestPriority.getPosition(), PVector.mult(enemycurrentlyHighestPriority.getVelocity(), shootingOffset));
                PVector dir = PVector.sub(shootingPoint, this.getPosition());
                this.setOrientation(dir.heading());
                shoot();
            }
            else
            {
                //TODO - Add the get to LOS code here
            }
        }
    }

    public Enemy getEnemyWithHighestPriority()
    {
        float highestPrioritySoFar = 0;
        Enemy enemyWithHighestPriority = null;

        //Trying to stick to the last enemy if it's still alive and in the yellow zone
//        if(enemycurrentlyHighestPriority != null && enemycurrentlyHighestPriority.life>0
//                && enemycurrentlyHighestPriority.position.dist(Engine.tooth.tooth.position)<=YELLOW_ZONE)
//        {
//            return enemycurrentlyHighestPriority;
//        }

        for (Iterator<Enemy> j = Engine.Enemies.iterator(); j.hasNext(); )
        {
            Enemy e = j.next();
            //uncomment next two line if we want to set an enemy with highest priority even when none are in the danger zone
//            if(e.enemyPriority > highestPrioritySoFar)
//                enemyWithHighestPriority = e;

            if(e.position.dist(Engine.tooth.tooth.getPosition()) <= YELLOW_ZONE)
            {
                if(e.enemyPriority < 10) {
                    e.enemyPriority += 10;
                }
                if (highestPrioritySoFar < e.enemyPriority) {
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
}
