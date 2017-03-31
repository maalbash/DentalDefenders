package engine;
/**
 * Created by KinshukBasu on 02-Mar-17.
 */

import objects.Enemy;
import objects.Enemy_lactus;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Engine extends PApplet{

    public static void main(String[] args){
        PApplet.main("engine.Engine", args);
    }


    public void settings(){
        size(1024,768);
    }

    public void setup(){
        //Enemy_lactus badguy = new Enemy_lactus();

    }



    public void draw(){
        background(255);
        stroke(0);
        fill(0);
        ellipse(width/2, height/2, 40,40);

    }
}
