package objects;

import engine.Engine;
import environment.Environment;
import environment.Obstacle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;
import utility.Utility;

/**
 * Created by ujansengupta on 4/8/17.
 */
public class Bullet
{
    public enum Origin
    {
        PLAYER, ENEMY
    }

    public PShape shape;
    public PVector color;
    public PVector size;
    public PVector position;
    public PVector velocity;
    public PApplet app;
    public Origin origin;

    public int bodyOffset = 10;


    public Bullet(PApplet app, PVector position, float orientation, PVector size, PVector color, Origin origin)
    {
        this.app = app;
        this.color = color;
        this.size = size;
        this.position = new PVector(position.x, position.y);
        this.origin = origin;

        velocity = PVector.fromAngle(orientation);
        velocity.setMag(GameConstants.DEFAULT_BULLET_SPEED);

        shape = app.createShape(PConstants.ELLIPSE, 0, 0, size.x, size.y);
        shape.setFill(app.color(color.x, color.y, color.z));
    }


    public void update()
    {
        position.add(velocity);
        app.shape(shape, position.x, position.y);
    }

    public boolean outOfBounds()
    {
        return ((Environment.invalidNodes.contains(Utility.getGridIndex(this.position))) ||
                (this.position.x < GameConstants.SCR_OFFSET || this.position.x > GameConstants.SCR_WIDTH - GameConstants.SCR_OFFSET ||
                this.position.y < GameConstants.SCR_OFFSET || this.position.y > GameConstants.SCR_HEIGHT - GameConstants.SCR_OFFSET));
    }

    public boolean hasHit(GameObject obj)
    {
        return (PVector.sub(this.position,obj.position).mag() <= (this.size.x + obj.size));
    }

    public boolean hasHit(Obstacle obj)
    {
        return(obj.getTileIndices().contains(Utility.getGridIndex(this.position)));
    }

}
