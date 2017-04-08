package objects;

import engine.Engine;
import environment.Obstacle;
import movement.Align;
import movement.Arrive;
import movement.Seek;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;
import utility.Movable;

/**
 * Created by KinshukBasu on 29-Mar-17.
 */

public class GameObject extends AbstractObject implements Movable
{

    protected int life;
    protected float size;

    protected PApplet app;
    protected PShape shape;
    protected PVector color;

    protected BreadCrumbs crumbs;
    protected boolean crumbTrail;

    protected int scr_width = GameConstants.SCR_WIDTH;
    protected int scr_height = GameConstants.SCR_HEIGHT;

    protected static PVector tileSize = GameConstants.TILE_SIZE;

    protected int wanderRadius = 20;

    //protected PVector targetPosition;



    public GameObject(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life)
    {
        super(new PVector(posX, posY), orientation);

        this.app = app;
        setColor(color.x, color.y, color.z);
        setSize(size);
        setLife(life);

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

        if (outOfBounds())
        {
            position.x -= velocity.x;
            position.y -= velocity.y;
        }

        if (crumbs != null)
            crumbs.drawCrumbs(crumbTrail);

        drawShape();
    }

    /* Initialize breadcrumbs */

    public void initCrumbs()
    {
        crumbs = new BreadCrumbs(this.app, this);
    }



    /* Getters and Setters */

    public PVector getGridLocation()
    {
        return new PVector((int) (position.x/GameConstants.TILE_SIZE.x), (int)(position.y/GameConstants.TILE_SIZE.y));
    }


    public void setLife(int life)
    {
        this.life = life;
    }

    public int getLife()
    {
        return life;
    }


    public void setSize(float size)
    {
        this.size = size;
        makeShape();
    }

    public float getSize() { return size; }


    public void setColor(float colorX, float colorY, float colorZ) {
        color = new PVector(colorX, colorY, colorZ);
    }

    public PVector getColor() { return color;}


    public int getWanderRadius() {
        return wanderRadius;
    }

    public void setWanderRadius(int wanderRadius) {
        this.wanderRadius = wanderRadius;
    }


    /*public PVector getTargetPosition()
    {
        return targetPosition;
    }

    public void setTargetPosition(PVector targetPosition)
    {
        this.targetPosition = targetPosition;
    }*/


    public void setDefaults()
    {
        makeShape();

        this.velocity = GameConstants.DEFAULT_VEL;
        this.rotation = GameConstants.DEFAULT_ROT;
        this.linearAcc = GameConstants.DEFAULT_LINEAR_ACC;
        this.angularAcc = GameConstants.DEFAULT_ANGULAR_ACC;

        this.maxVel = GameConstants.DEFAULT_MAX_VEL;
        this.maxAcc = GameConstants.DEFAULT_MAX_linearACC;
        this.maxRot = GameConstants.DEFAULT_MAX_ROTATION;
        this.maxAngularAcc = GameConstants.DEFAULT_MAX_angularACC;
        this.linearROS = GameConstants.DEFAULT_linearROS;
        this.angularROS = GameConstants.DEFAULT_angularROS;
        this.linearROD = GameConstants.DEFAULT_linearROD;
        this.angularROD = GameConstants.DEFAULT_angularROD;
        this.timeToTargetRot = GameConstants.DEFAULT_TTTROT;
        this.timeToTargetVel = GameConstants.DEFAULT_TTTVEL;
    }


    public void setCrumbTrail(boolean crumbTrail)
    {
        this.crumbTrail = crumbTrail;
    }


    /* Helper methods */

    public void makeShape()
    {
        PShape circle, triangle;
        float posX = 0, posY = 0;

        shape = app.createShape(PConstants.GROUP);
        circle = app.createShape(PConstants.ELLIPSE, posX, posY, size, size);
        triangle = app.createShape(PConstants.TRIANGLE, posX + size/7f, posY - size/3f,
                posX + size/7f, posY + size/3f, posX + size, posY);

        shape.addChild(circle);
        shape.addChild(triangle);
    }

    public void changeShape(PShape newShape)
    {
        shape = newShape;
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


    /* Movement methods */

    @Override
    public void Seek(PVector target)
    {
        this.setVelocity(Seek.getKinematic(this, target).velocity);
    }

    @Override
    public void Arrive(PVector target)
    {
        this.setLinearAcc(Arrive.getSteering(this, target).linear);
    }

    @Override
    public void Align(PVector target)
    {
        this.setAngularAcc(Align.getSteering(this, target).angular);
        if (angularAcc == 0)
            rotation = 0;
    }

    @Override
    public void Wander() {

    }

    @Override
    public void stopMoving()
    {
        this.velocity.setMag(0);
        this.linearAcc.setMag(0);
        this.angularAcc = 0;
        this.rotation = 0;

    }

    @Override
    public boolean outOfBounds()
    {
        for (Obstacle o : Engine.staticObjects)
            if (o.contains(getGridLocation()))
                return true;

        return (this.position.x < GameConstants.SCR_OFFSET || this.position.x > GameConstants.SCR_WIDTH - GameConstants.SCR_OFFSET ||
                this.position.y < GameConstants.SCR_OFFSET || this.position.y > GameConstants.SCR_HEIGHT - GameConstants.SCR_OFFSET);
    }


}
