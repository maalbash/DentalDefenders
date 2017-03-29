
/**
 * Created by KinshukBasu on 02-Mar-17.
 */

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class test extends PApplet{

    public static void main(String[] args){
        PApplet.main("test", args);
    }


    public void settings(){
        size(1024,768);
    }

    public void setup(){

    }



    public void draw(){
        background(255);
        stroke(0);
        fill(0);
        ellipse(width/2, height/2, 40,40);

    }
}
