package engine;
/**
 * Created by KinshukBasu on 02-Mar-17.
 */

import objects.Enemy;
import objects.Enemy_lactus;
import objects.Player;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Engine extends PApplet
{

    Player player;

    public static void main(String[] args){
        PApplet.main("engine.Engine", args);
    }


    public void settings()
    {
        size(1024,768);
    }

    public void setup()
    {
        player = new Player(this);
    }



    public void draw()
    {
        background(105, 183, 219);
        player.update();

    }
}
