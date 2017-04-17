package objects;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import environment.Environment;
import engine.Engine;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

import java.util.*;

/**
 * Created by ujansengupta on 3/31/17.
 */
public class Player extends GameObject
{

    private PApplet app;

    /* Player properties */

    public static PVector color = new PVector(109, 69, 1);
    public static float size = 20;
    private static float DEFAULT_X = GameConstants.SCR_WIDTH/2 + 90;
    private static float DEFAULT_Y = GameConstants.SCR_HEIGHT/2 + 90;
    private static float DEFAULT_ORIENTATION = 0;
    private static final int DEFAULT_PLAYER_LIFE = 100;
    private static float DEFAULT_PLAYER_MAXVEL = 2f;

    public Set<Bullet> bullets;
    public int bulletCount = 0;
    public int enemiesKilled = 0;

    public PVector playerTarget;
    public static float BulletDamage = 10;     //TODO - Decide how much damage, and in which class to declare this variable


    public Player(PApplet app)
    {
        super (app, color, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);
        this.app = app;

        setMaxVel(DEFAULT_PLAYER_MAXVEL);
        bullets = new HashSet<>();
        playerTarget = new PVector(app.mouseX, app.mouseY);
    }

    public void shoot()
    {
        bullets.add(new Bullet(app, getPosition(), getOrientation(), GameConstants.DEFAULT_BULLET_SIZE, color, Bullet.Origin.PLAYER));
    }
    
    public void update()
    {
        Align(playerTarget);
        Arrive(playerTarget);

        super.update();

        for (Iterator<Bullet> i = bullets.iterator(); i.hasNext(); )
        {
            Bullet b = i.next();
            boolean bulletRemoved = false;

            for(Iterator<Enemy> j = Engine.Enemies.iterator(); j.hasNext(); )
            {
                Enemy e = j.next();
                if(b.hasHit(e))
                {
                    i.remove();
                    bulletRemoved = true;
                    e.takeDamage(Player.BulletDamage);

                    if(e.getLife()<=0)
                    {
                        j.remove();
                        System.out.println(enemiesKilled++);
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

    public void updateTarget()
    {
        if (!Environment.invalidNodes.contains(Utility.getGridIndex(new PVector(app.mouseX, app.mouseY))))
            playerTarget.set(app.mouseX, app.mouseY);
    }


}

