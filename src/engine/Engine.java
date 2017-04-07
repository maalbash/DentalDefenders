package engine;
/**
 * Created by KinshukBasu on 02-Mar-17.
 */

import environment.Environment;
import environment.Obstacle;
import objects.Enemy;
import objects.Enemy_lactus;
import objects.Player;
import objects.Tooth;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;

import java.util.ArrayList;
import java.util.List;

public class Engine extends PApplet
{

    Player player;
    Tooth tooth;
    Environment environment;
    Enemy_lactus lactus;

    public List<Obstacle> staticObjects;


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

        player = new Player(this);
        tooth = new Tooth(this);
        environment = new Environment(this);

        staticObjects = new ArrayList<>();

        staticObjects.add(tooth);

        for (Obstacle o : environment.getObstacles())
            staticObjects.add(o);

        lactus = new Enemy_lactus(this, width/4,height/4,0);

        frameRate(60);

    }



    public void draw()
    {
        background(105, 183, 219);
        environment.update();
        tooth.draw();
        player.update();
        System.out.println("Rotation : " + player.getRotation());
        System.out.println("Orientation : " + player.getOrientation());
        System.out.println("Angular Acc : " + player.getAngularAcc());


        lactus.update();

    }

    public void keyPressed()
    {
        //println(keyCode);
        movePlayer(keyCode);
    }

    public void keyReleased()
    {
        if (!keyPressed)
            player.stopMoving();
    }


    public void movePlayer(int keyCode)
    {
        switch (keyCode)
        {
            /* LEFT*/
            case 37:
                player.Align(PVector.sub(player.getPosition(), new PVector(player.getWanderRadius(), 0)));
                player.Seek(PVector.sub(player.getPosition(), new PVector(player.getWanderRadius(), 0)));
                break;

            /* UP */
            case 38:
                player.Align(PVector.sub(player.getPosition(), new PVector(0, player.getWanderRadius())));
                player.Seek(PVector.sub(player.getPosition(), new PVector(0, player.getWanderRadius())));
                break;

            /* RIGHT */
            case 39:
                player.Align(PVector.add(player.getPosition(), new PVector(player.getWanderRadius(), 0)));
                player.Seek(PVector.add(player.getPosition(), new PVector(player.getWanderRadius(), 0)));
                break;

            /* DOWN */
            case 40:
                player.Align(PVector.add(player.getPosition(), new PVector(0, player.getWanderRadius())));
                player.Seek(PVector.add(player.getPosition(), new PVector(0, player.getWanderRadius())));
                break;
        }
    }
}
