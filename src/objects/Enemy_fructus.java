package objects;

import engine.Engine;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;

import static objects.Enemy.modeList.ATTACKPLAYER;
import static objects.Enemy.modeList.SEEKTOOTH;


/**
 * Created by ujansengupta on 3/31/17.
 */

public class Enemy_fructus extends Enemy{

    private static int life = 40;
    private static PVector color = new PVector(0,0,204);
    private static int size = 20;
    private static int PursueRadius  =200;
    private modeList mode;

    public Enemy_fructus(PApplet app, float posX, float posY, float orientation)
    {

        //The rational here is that each fructus enemy will have the same colour, size and life.
        //Since they are default values, they need not be constructor parameters.
        super (app, color, size, posX, posY, orientation, life,PursueRadius);
        mode = SEEKTOOTH;
    }


    private void setCurrentMode()
    {
        if(PVector.sub(this.position, Engine.player.position).mag()<this.PURSUE_RADIUS)
        {
            this.mode = ATTACKPLAYER;
        }
        else
        {
            this.mode = SEEKTOOTH;
        }

    }

    public void defaultBehaviour()
    {
        //for now, default behaviour is "SEEK TOOTH"

        setCurrentMode();

        switch (mode)
        {
            case SEEKTOOTH:
                this.finalTarget = new PVector(GameConstants.SCR_WIDTH / 2, GameConstants.SCR_HEIGHT / 2);
                Seek(this.finalTarget);
                break;
            case ATTACKPLAYER:
                this.finalTarget = Engine.player.getPosition();
                Seek(this.finalTarget);
                break;
        }
    }
}
