package objects;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by ujansengupta on 3/31/17.
 */

//TODO discuss details about parameters for each enemy
public class Enemy_enamelator extends Enemy {

    public Enemy_enamelator(PApplet app, float size, float posX, float posY, float orientation, int life){

        //The rational here is that each lactus enemy will have the same colour, size and life.
        //Since they are default values, they need not be constructor parameters.

        //int life = 100;
        //PVector color = new PVector(0,0);
        //float size = 20
        super (app, new PVector(0,0), 20, posX, posY, orientation, 100);
    }
}
