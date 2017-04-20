package objects;

import engine.Engine;
import environment.Environment;
import environment.PathFollower;
import processing.core.PApplet;
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
        SHOOTING_BACK
    }

    private enum PRIORITY {
        TOOTH,
        PLAYER
    }

    public GameObject finalTarget;
    public PathFollower pathFollower;

    /* Player properties */
    public static float GREEN_ZONE,YELLOW_ZONE,RED_ZONE;
    public static PVector color = new PVector(109, 69, 1);
    public static float size = 20;
    private static float DEFAULT_X = GameConstants.SCR_WIDTH/2 + 90;
    private static float DEFAULT_Y = GameConstants.SCR_HEIGHT/2 + 90;
    private static PVector DEFAULT_POS = new PVector(DEFAULT_X,DEFAULT_Y);
    private static float DEFAULT_ORIENTATION = 0;
    private static final int DEFAULT_PLAYER_LIFE = 100;
    private static float DEFAULT_PLAYER_MAXVEL = 2f;
    private static STATUS status;
    private static PRIORITY priority;
    private static boolean pathFollowing;



    public Set<Bullet> bullets;
    public int bulletCount = 0;
    public int enemiesKilled = 0;

    public PVector playerTarget;
    public static float BulletDamage = 10;


    public AIplayer(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life) {
        super(app, color, size, posX, posY, orientation, life);
        this.app = app;

        setMaxVel(DEFAULT_PLAYER_MAXVEL);
        bullets = new HashSet<>();
        playerTarget = new PVector(DEFAULT_X, DEFAULT_Y);
    }

    public void shoot() {
        bullets.add(new Bullet(app, getPosition(), getOrientation(), GameConstants.DEFAULT_BULLET_SIZE, color, Bullet.Origin.PLAYER));
    }

    public void update(){

        //updating the position and orientation of the player

        Align(playerTarget);
        Arrive(playerTarget);

        super.update();

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


    public void setStatus(){
        //player should according to different parameters, perform an action. this method sets the state of the player.
        Enemy e = getEnemyWithHighestPriority();


    }

    public void behaviour(){
        //depending on the current status of the player, perform a different action
        setStatus();
        switch (status)
        {
            case AVOIDING_OBSTACLE:

                break;
            case DEFENDING_TOOTH:
                defendTooth();
                break;
            case SHOOTING_BACK:
                shootingBack();
                break;
            case IDLE:
                defaultPosition();
                break;
            default:

        }
    }


    public void defaultPosition(){
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

    public boolean checkObstacle(){
        // this method checks if an obstacle appears in front of the player, then delegates to avoidObstacle method if one is found.
        return false;
    }

    public void avoidObstacle(){
        //path follow to target
        pathFollower.findPath(getGridLocation(), Utility.getGridLocation(playerTarget));
        pathFollowing = true;
    }


    public void defendTooth(){
        // if not LOS, then get LOS and shoot at enemy
    }

    public void shootingBack(){
        // probably has LOS, but if not then get LOS and shoot at enemy
    }

    public Enemy getEnemyWithHighestPriority() {
        float highestPrioritySoFar = 0;
        Enemy enemyWithHighestPriority = null;
        for (Iterator<Enemy> j = Engine.Enemies.iterator(); j.hasNext(); ) {
            Enemy e = j.next();
            //uncomment next two line if we want to set an enemy with highest priority even when none are in the danger zone
//            if(e.enemyPriority > highestPrioritySoFar)
//                enemyWithHighestPriority = e;
            if(e.position.dist(this.getPosition()) <= YELLOW_ZONE) {
                if(e.enemyPriority < 10) {
                    e.enemyPriority += 10;
                }
                if (highestPrioritySoFar < e.enemyPriority) {
                    highestPrioritySoFar = e.enemyPriority;
                    enemyWithHighestPriority = e;
                }
            }else{
                if(e.enemyPriority > 10)
                    e.enemyPriority -= 10;
            }
        }
        return enemyWithHighestPriority;
    }
}
