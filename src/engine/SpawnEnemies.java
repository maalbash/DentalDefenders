package engine;

import objects.Enemy_lactus;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;

import static java.lang.Math.random;

/**
 * Created by KinshukBasu on 07-Apr-17.
 */
public class SpawnEnemies
{

    private static long lastLactusSpawn = 0;
    private static long now;

    public static void update(PApplet app){
        addLactus(app);
    }

    public static void addLactus(PApplet app)
    {

        now = System.currentTimeMillis();
        if((now-lastLactusSpawn)>2000) {
            Enemy_lactus newLactus = new Enemy_lactus(app, 0, 0, 0);

            PVector temp = SpawnEnemies.randomEdgeLocation();
            newLactus.setPosition(temp);

            Engine.Enemies.add(newLactus);
            lastLactusSpawn = now;
        }
    }

    private static PVector randomEdgeLocation()
    {

        //Edges are numbered clockwise, starting from the left vertical edge

        int choice = (int)(random() * 5);
        PVector pos = new PVector();

        switch(choice){

            case(1): pos.x = GameConstants.SCR_OFFSET;
                     pos.y = (float)(random() * GameConstants.SCR_HEIGHT);
                     break;

            case(2): pos.x = (float)(random() * GameConstants.SCR_WIDTH);
                     pos.y = GameConstants.SCR_OFFSET;
                     break;

            case(3): pos.x = GameConstants.SCR_WIDTH-GameConstants.SCR_OFFSET;
                     pos.y = (float)(random() * GameConstants.SCR_HEIGHT);
                     break;

            case(4): pos.x = (float)(random() * GameConstants.SCR_WIDTH);
                     pos.y = GameConstants.SCR_HEIGHT-GameConstants.SCR_OFFSET;
                     break;

            default: pos.x = GameConstants.SCR_OFFSET;
                     pos.y = (float)(random() * GameConstants.SCR_HEIGHT);
                     break;
        }
        return(pos);
    }
}
