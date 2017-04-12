package objects;

import engine.Engine;
import processing.core.PApplet;
import processing.core.PVector;
import utility.Utility;

import static objects.Enemy.stateList.ATTACKPLAYER;
import static objects.Enemy.stateList.SEEKTOOTH;
import static objects.Enemy.stateList.WANDER;


/**
 * Created by ujansengupta on 3/31/17.
 */

public class Enemy_lactus extends Enemy {

    private static int life = 20;
    private static PVector color = new PVector(0,179,0);
    private static int size = 20;
    private static int PursueRadius = 100;

    private static float DEFAULT_LACTUS_SPEED = 1f;
    private static float LACTUS_ANGULAR_ACC = 0.0005f;
    private static float LACTUS_ANGULAR_ROS = 1.5f;
    private static float LACTUS_ANGULAR_ROD = 2.5f;

    private stateList state;

    public Enemy_lactus(PApplet app, float posX, float posY, float orientation){

        //The rational here is that each lactus enemy will have the same colour, size and life.
        //Since they are default values, they need not be constructor parameters.

        super (app, color, size, posX, posY, orientation, life, PursueRadius);
        this.setMaxVel(DEFAULT_LACTUS_SPEED);
        this.setMaxAngularAcc(LACTUS_ANGULAR_ACC);
        this.setAngularROS(LACTUS_ANGULAR_ROS);
        this.setAngularROD(LACTUS_ANGULAR_ROD);

        finalTarget = Engine.tooth.tooth;
        state = SEEKTOOTH;
    }

    private void setCurrentMode()
    {

        if(PVector.sub(this.position, Engine.tooth.tooth.position).mag() < PURSUE_RADIUS)
        {
            state = SEEKTOOTH;
        }
        else if(PVector.sub(this.position, Engine.player.position).mag() < PURSUE_RADIUS)
        {
            state = ATTACKPLAYER;
        }
        else
        {
            state = WANDER;
        }
    }

    public void defaultBehaviour()
    {
        //for now, default behaviour is "SEEK TOOTH"

        setCurrentMode();

        if (obstacleCollisionDetected() && !isFollowingPath)
        {
            pathFollower.followPath(getGridLocation(), Utility.getGridLocation(this.finalTarget.position));
            isFollowingPath = true;
        }
        else
        {
            isFollowingPath = false;

            switch (state)
            {
                case SEEKTOOTH:
                    this.finalTarget = Engine.tooth.tooth;
                    Seek(this.finalTarget.position);
                    break;

                case ATTACKPLAYER:
                    this.finalTarget = Engine.player;
                    Seek(this.finalTarget.position);
                    break;

                case WANDER:
                    Wander();
                    break;
            }
        }

    }


}
