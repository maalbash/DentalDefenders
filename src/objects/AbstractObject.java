package objects;

import processing.core.PVector;

/**
 * Created by ujansengupta on 3/29/17.
 */

@SuppressWarnings("WeakerAccess")

public abstract class AbstractObject {
    PVector position, velocity, linear;
    float rotation;

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

    public PVector getLinear() {
        return linear;
    }

    public void setLinear(PVector linear) {
        this.linear = linear;
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

    float orientation;

    public AbstractObject(){
        this.position = new PVector();
        this.velocity = new PVector();
        this.linear = new PVector();
    }

    public AbstractObject(PVector position, PVector velocity, PVector linear, float rotation, float orientation) {
        this.position = position;
        this.velocity = velocity;
        this.linear = linear;
        this.rotation = rotation;
        this.orientation = orientation;
    }

}
