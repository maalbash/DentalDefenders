package objects;

import com.sun.tools.doclint.Env;
import environment.Environment;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by ujansengupta on 3/31/17.
 */


public class Enemy_enamelator extends Enemy {

    private static int life = 80;
    private static PVector color = new PVector(204,0,0);
    private static int size = 20;
    private static int PursueRadius = 50;

    public Enemy_enamelator(PApplet app, float posX, float posY, float orientation){


        //The rational here is that each lactus enemy will have the same colour, size and life.
        //Since they are default values, they need not be constructor parameters.

        super (app, color, size, posX, posY, orientation, life,PursueRadius);
    }

    public void behaviour(){}

    public void defaultBehaviour(){}

    public void avoidObstacle(){}

}
