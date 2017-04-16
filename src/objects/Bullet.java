package objects;

import engine.Engine;
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
    public PShape shape;
    public PVector color;
    public PVector size;
    public PVector position;
    public PVector velocity;
    public PApplet app;

    public Bullet(PApplet app, PVector position, float orientation, PVector size, PVector color)
    {
        this.app = app;
        this.color = color;
        this.size = size;
        this.position = new PVector(position.x, position.y);

        velocity = PVector.fromAngle(orientation);
        velocity.setMag(GameConstants.DEFAULT_BULLET_SPEED);

        shape = app.createShape(PConstants.ELLIPSE, 0, 0, size.x, size.y);
        shape.setFill(app.color(color.x, color.y, color.z));
    }


    public void update()
    {
        app.shape(shape, position.x, position.y);
        position.add(velocity);
    }

    public boolean outOfBounds()
    {
        for (Obstacle o : Engine.staticObjects)
            if (o.contains(Utility.getGridLocation(this.position)))
                return true;

        return (this.position.x < GameConstants.SCR_OFFSET || this.position.x > GameConstants.SCR_WIDTH - GameConstants.SCR_OFFSET ||
                this.position.y < GameConstants.SCR_OFFSET || this.position.y > GameConstants.SCR_HEIGHT - GameConstants.SCR_OFFSET);
    }

    public boolean hasHit(GameObject obj){

        if(PVector.sub(this.position,obj.position).mag() <= (this.size.x+obj.size)){
            return true;
        }
        return false;
    }

}
