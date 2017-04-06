package engine;
/**
 * Created by KinshukBasu on 02-Mar-17.
 */

import objects.Enemy;
import objects.Enemy_lactus;
import objects.Player;
import objects.Tooth;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;

public class Engine extends PApplet
{

    Player player;
    Tooth tooth;
    Enemy_lactus lactus;

    public static void main(String[] args){
        PApplet.main("engine.Engine", args);
    }


    public void settings()
    {
        size(GameConstants.SCR_WIDTH, GameConstants.SCR_HEIGHT);
    }

    public void setup()
    {
        player = new Player(this);
        tooth = new Tooth(this);
        lactus = new Enemy_lactus(this, width/4,height/4,0);
    }



    public void draw()
    {
        background(105, 183, 219);
        tooth.update();
        player.update();
        lactus.update();

    }
}
