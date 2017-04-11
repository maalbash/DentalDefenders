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
import utility.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Engine extends PApplet
{

    public static Player player;        //Changed these 2 to static, since only one instance of each, and to provide ease of access
    public static Tooth tooth;
    public static Environment environment;

    public static List<Obstacle> staticObjects;
    public static List<Enemy>  Enemies;

    public static PVector playerTarget;


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
        Enemies = new ArrayList<>();
        playerTarget  = new PVector(0, 0);
        staticObjects = new ArrayList<>();

        staticObjects.add(tooth);

        for (Obstacle o : environment.getObstacles())
            staticObjects.add(o);

        frameRate(60);

    }

    public void drawEnemies(){
        for (Enemy e : Engine.Enemies){
            e.update();
        }
    }

    public void enemyBehaviour(){

        LinkedList<Enemy> EnemiesToRemove = new LinkedList<Enemy>();
        /*
        Iterator<Enemy> tracker = Enemies.listIterator();
        Enemy e;
        while(tracker.hasNext()){
            e = tracker.next();
            e.defaultBehaviour();
            e.update();
        }
        */

        for(Enemy e : Engine.Enemies){
            e.defaultBehaviour();
            e.update();
            if(Utility.checkTargetReached(e,e.getFinalTarget().getPosition())){
                EnemiesToRemove.add(e);
            }
        }
        for(Enemy e : EnemiesToRemove){
            Engine.Enemies.remove(e);
        }
    }



    public void draw()
    {
        background(105, 183, 219);
        environment.update();
        tooth.draw();

        player.Arrive(playerTarget);
        player.update();

        SpawnEnemies.update(this);
        enemyBehaviour();
        drawEnemies();

        /*
        System.out.println("Rotation : " + player.getRotation());
        System.out.println("Orientation : " + player.getOrientation());
        System.out.println("Angular Acc : " + player.getAngularAcc());
        */

    }

    /*public void keyPressed()
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
            *//* LEFT*//*
            case 37:
                player.Align(PVector.sub(player.getPosition(), new PVector(player.getWanderRadius(), 0)));
                player.Seek(PVector.sub(player.getPosition(), new PVector(player.getWanderRadius(), 0)));
                break;

            *//* UP *//*
            case 38:
                player.Align(PVector.sub(player.getPosition(), new PVector(0, player.getWanderRadius())));
                player.Seek(PVector.sub(player.getPosition(), new PVector(0, player.getWanderRadius())));
                break;

            *//* RIGHT *//*
            case 39:
                player.Align(PVector.add(player.getPosition(), new PVector(player.getWanderRadius(), 0)));
                player.Seek(PVector.add(player.getPosition(), new PVector(player.getWanderRadius(), 0)));
                break;

            *//* DOWN *//*
            case 40:
                player.Align(PVector.add(player.getPosition(), new PVector(0, player.getWanderRadius())));
                player.Seek(PVector.add(player.getPosition(), new PVector(0, player.getWanderRadius())));
                break;
        }
    }*/


    public void mouseMoved()
    {
        playerTarget.set(mouseX, mouseY);
        //player.Align(playerTarget);
    }
    public void mousePressed()
    {
        player.shoot();
    }
}
