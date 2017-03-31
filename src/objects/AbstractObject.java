package objects;

import processing.core.PVector;

/**
 * Created by ujansengupta on 3/29/17.
 */

@SuppressWarnings("WeakerAccess")

public abstract class AbstractObject
{
    public PVector position, velocity, linearAcc;
    public float rotation, orientation, angularAcc;


    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }


    public PVector getVelocity() {
        return velocity;
    }

    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }


    public PVector getLinearAcc() {
        return linearAcc;
    }

    public void setLinear(PVector linear) {
        this.linearAcc = linear;
    }


    public float getAngularAcc() {
        return angularAcc;
    }

    public void setAngular(float angular) {
        this.angularAcc = angular;
    }


    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }


    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }


    public AbstractObject(PVector position, float orientation)
    {
        this.position = position;
        this.orientation = orientation;
    }


}
