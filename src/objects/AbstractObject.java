package objects;

import processing.core.PVector;

/**
 * Created by ujansengupta on 3/29/17.
 */

@SuppressWarnings("WeakerAccess")

public abstract class AbstractObject
{
    protected PVector position, velocity, linearAcc;
    protected float rotation, orientation, angularAcc;
    protected float maxVel, maxAcc, maxRot, maxAngularAcc, linearROS, linearROD, angularROS, angularROD;

    public AbstractObject(PVector position, float orientation)
    {
        this.position = position;
        this.orientation = orientation;
    }


    /* Getters and Setters */

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

    public void setLinearAcc(PVector linear) {
        this.linearAcc = linear;
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


    public float getAngularAcc() {
        return angularAcc;
    }

    public void setAngularAcc(float angular) {
        this.angularAcc = angular;
    }




    public void setMaxVel(float maxVel) {
        this.maxVel = maxVel;
    }

    public float getMaxVel() {
        return maxVel;
    }


    public void setMaxAcc(float maxAcc) {
        this.maxAcc = maxAcc;
    }

    public float getMaxAcc() {
        return maxAcc;
    }


    public void setMaxRot(float maxRot) {
        this.maxRot = maxRot;
    }

    public float getMaxRot() {
        return maxRot;
    }


    public void setMaxAngularAcc(float maxAngularAcc) {
        this.maxAngularAcc = maxAngularAcc;
    }

    public float getMaxAngularAcc() {
        return maxAngularAcc;
    }


    public void setLinearROS(float linearROS) {
        this.linearROS = linearROS;
    }

    public float getLinearROS() {
        return linearROS;
    }


    public void setAngularROS(float angularROS) {
        this.angularROS = angularROS;
    }

    public float getAngularROS() {
        return angularROS;
    }


    public void setAngularROD(float angularROD) {
        this.angularROD = angularROD;
    }

    public float getAngularROD() {
        return angularROD;
    }


}
