package engine;
/**
 * Created by KinshukBasu on 02-Mar-17.
 */

import environment.Environment;
import environment.Obstacle;
import objects.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Engine extends PApplet
{

    public static AIplayer player;        //Changed these 2 to static, since only one instance of each, and to provide ease of access
    //public static AIplayer player;
    public static Tooth tooth;
    public static Environment environment;

    public static List<Obstacle> staticObjects;
    public static List<Enemy>  Enemies;

    public static void main(String[] args){
        PApplet.main("engine.Engine", args);
    }


    public void settings()
    {
        size(GameConstants.SCR_WIDTH, GameConstants.SCR_HEIGHT);
    }

    public void setup()
    {
        rectMode(PConstants.CENTER);

        player = new AIplayer(this);

        tooth = new Tooth(this);
        environment = new Environment(this);
        Enemies = new ArrayList<>();
        staticObjects = new ArrayList<>();

        staticObjects.add(tooth);

        for (Obstacle o : environment.getObstacles())
            staticObjects.add(o);

        frameRate(60);

    }

    public void updateEnemies()
    {
        for (Enemy e : Engine.Enemies){
            e.update();
        }
    }

    public void enemyBehaviour()
    {

        LinkedList<Enemy> EnemiesToRemove = new LinkedList<>();

        for(Enemy e : Engine.Enemies)
        {
            e.behaviour();
            e.update();
            if(Utility.checkTargetReached(e,e.getFinalTarget().getPosition()))  //Checks if enemy has touched player/tooth
            {
                EnemiesToRemove.add(e);
                e.getFinalTarget().takeDamage(e.contactDamage);
            }
        }

        for(Enemy e : EnemiesToRemove){
            Engine.Enemies.remove(e);
        }
    }



    public void draw()
    {
        /*if (player.getLife() <= 0 || tooth.tooth.getLife() <= 0)
        {
            System.out.println(" You killed : " + player.enemiesKilled + " enemies");
            return;
        }*/

        background(105, 183, 219);
        environment.update();
        tooth.update();


        player.behaviour();
        player.update();

        SpawnEnemies.update(this);
        enemyBehaviour();
        //updateEnemies();              //No need for this as enemy behaviour already updates enemies

        if(player.getLife() <= 0 || tooth.tooth.getLife() <= 0){
            noLoop();
        }
    }


    /*
    public void mouseMoved()
    {
        player.updateTarget();
    }
    public void mousePressed()
    {
        player.shoot();
    }
    */


}
