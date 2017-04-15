package objects;

import engine.Engine;
import processing.core.PApplet;
import processing.core.PVector;

import static objects.Enemy.stateList.*;


/**
 * Created by ujansengupta on 3/31/17.
 */

public class Enemy_lactus extends Enemy {

    private static int life = 20;
    private static PVector color = new PVector(0,179,0);
    private static int size = 20;
    private static int PursueRadius = 100;

    private static float DEFAULT_LACTUS_SPEED = 0.5f;
    private static float LACTUS_ANGULAR_ACC = 0.001f;
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

    private void setCurrentState()
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
            state = WANDER;

    }

    public void behaviour()
    {
        if (obstacleCollisionDetected())
            avoidObstacle();
        else
            defaultBehaviour();

    }

    public void defaultBehaviour()
    {
        //for now, default behaviour is "SEEK TOOTH"

        setCurrentState();

        switch (state)
        {
            case SEEKTOOTH:
                this.finalTarget = Engine.tooth.tooth;
                Align(this.finalTarget.position);
                Seek(this.finalTarget.position);
                break;

            case ATTACKPLAYER:
                this.finalTarget = Engine.player;
                Align(this.finalTarget.position);
                Seek(this.finalTarget.position);
                break;

            case WANDER:
                Wander();
                break;
        }
    }

    public void avoidObstacle()
    {
        targetRotationWander = velocity.heading() + (float) Math.PI;
        Wander();
    }

}
