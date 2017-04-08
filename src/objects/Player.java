package objects;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;

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

    public Set<Bullet> bullets;
    public int bulletCount = 0;


    public Player(PApplet app)
    {
        super (app, color, size, DEFAULT_X, DEFAULT_Y, DEFAULT_ORIENTATION, DEFAULT_PLAYER_LIFE);
        bullets = new HashSet<>();
        this.app = app;
    }

    public Player(PApplet app, float posX, float posY, float orientation)
    {
        super (app, color, size, posX, posY, orientation, DEFAULT_PLAYER_LIFE);
        bullets = new HashSet<>();
        this.app = app;
    }

    public void shoot()
    {
        bullets.add(new Bullet(app, getPosition(), getOrientation(), GameConstants.DEFAULT_BULLET_SIZE, color));
    }
    
    public void update()
    {
        super.update();

        for (Iterator<Bullet> i = bullets.iterator(); i.hasNext(); )
        {
            Bullet b = i.next();
            if (b.outOfBounds())
                i.remove();
            else
                b.update();
        }



    }


}

