package objects;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import utility.Constants;
import utility.Utility;

/**
 * Created by KinshukBasu on 29-Mar-17.
 */
public class GameObject extends AbstractObject
{

    private int life;

    public float maxVel, maxAcc, maxRot, maxAngularAcc, linearROS, linearROD, angularROS, angularROD, size;

    private PApplet app;
    private PShape shape;
    private PVector color;


    public GameObject(PApplet app, PVector position, float orientation, PVector color, float size)
    {
        super(position, orientation);

        this.app = app;
        this.color = color;
        this.size = size;

        setDefaults();

    }

    public void update()
    {
        velocity.x += linearAcc.x;
        velocity.y += linearAcc.y;

        rotation += angularAcc;

        position.x += velocity.x;
        position.y += velocity.y;

        orientation += rotation;

        drawShape();
    }


    public void setDefaults()
    {
        makeShape();

        this.life = Constants.DEFAULT_LIFE;
        this.maxVel = Constants.DEFAULT_MAX_VEL;
        this.maxAcc = Constants.DEFAULT_MAX_linearACC;
        this.maxRot = Constants.DEFAULT_MAX_ROTATION;
        this.maxAngularAcc = Constants.DEFAULT_MAX_angularACC;
        this.linearROS = Constants.DEFAULT_linearROS;
        this.angularROS = Constants.DEFAULT_angularROS;
        this.linearROD = Constants.DEFAULT_linearROD;
        this.angularROD = Constants.DEFAULT_angularROD;
    }
    
    public void makeShape()
    {
        PShape circle, triangle;
        shape = app.createShape(app.GROUP);
        circle = app.createShape(app.ELLIPSE, position.x, position.y, size, size);
        triangle = app.createShape(app.TRIANGLE, position.x + size/5f, position.y - size/2f,
                position.x + size/5f, position.y + size/2f, position.x + size, position.y);

        shape.addChild(circle);
        shape.addChild(triangle);
    }

    public void drawShape()
    {
        app.pushMatrix();
        shape.rotate(orientation);
        PShape[] children = shape.getChildren();

        for (PShape child : children)
        {
            child.setStroke(app.color(color.x, color.y, color.z, 255));
            child.setFill(app.color(color.x, color.y, color.z, 255));
        }

        app.shape(shape, position.x, position.y);
        shape.resetMatrix();
        app.popMatrix();
    }
}
