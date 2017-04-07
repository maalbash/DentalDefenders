package engine;
/**
 * Created by KinshukBasu on 02-Mar-17.
 */

import environment.Environment;
import objects.Enemy;
import objects.Enemy_lactus;
import objects.Player;
import objects.Tooth;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;

public class Engine extends PApplet
{

    Player player;
    Tooth tooth;
    Environment environment;

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
    }



    public void draw()
    {
        //background(105, 183, 219);
        environment.update();
        tooth.draw();
        player.update();

    }
}
