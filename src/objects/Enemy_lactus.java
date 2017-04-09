package objects;

import engine.Engine;
import movement.KinematicOutput;
import movement.Seek;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;

import static objects.Enemy.modeList.ATTACKPLAYER;
import static objects.Enemy.modeList.SEEKTOOTH;
import static objects.Enemy.modeList.WANDER;


/**
 * Created by ujansengupta on 3/31/17.
 */

public class Enemy_lactus extends Enemy {

    private static int life = 20;
    private static PVector color = new PVector(0,179,0);
    private static int size = 20;
    private static int PursueRadius  =50;

    private modeList mode;

    public Enemy_lactus(PApplet app, float posX, float posY, float orientation){

        //The rational here is that each lactus enemy will have the same colour, size and life.
        //Since they are default values, they need not be constructor parameters.

        super (app, color, size, posX, posY, orientation, life,PursueRadius);
        finalTarget = Engine.tooth.tooth;
        mode = SEEKTOOTH;
    }

    private void setCurrentMode()
    {

        if(PVector.sub(this.position, Engine.tooth.tooth.position).mag() < PURSUE_RADIUS){
            mode=SEEKTOOTH;
        }
        else if(PVector.sub(this.position, Engine.player.position).mag() < PURSUE_RADIUS){
            mode=ATTACKPLAYER;
        }
        else{
            mode=WANDER;
        }
    }

    public void defaultBehaviour()
    {
        //for now, default behaviour is "SEEK TOOTH"

        setCurrentMode();

        switch(mode){
            case SEEKTOOTH: this.finalTarget = Engine.tooth.tooth;
                            Seek(this.finalTarget.position);
                            break;

            case ATTACKPLAYER: this.finalTarget = Engine.player;
                               Seek(this.finalTarget.position);
                               break;

            case WANDER: this.finalTarget = Engine.tooth.tooth;
                         Seek(this.finalTarget.position);
                         break;
        }

    }


}
