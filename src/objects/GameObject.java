package objects;

import engine.Engine;
import environment.Environment;
import environment.Obstacle;
import environment.PathFollower;
import movement.Align;
import movement.Arrive;
import movement.Seek;
import movement.SteeringOutput;
import movement.Wander;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;
import utility.Movable;
import utility.Utility;

import java.lang.reflect.GenericArrayType;

/**
 * Created by KinshukBasu on 29-Mar-17.
 */

public class GameObject extends AbstractObject implements Movable
{

    protected int life;
    protected float MAX_LIFE;
    protected float size;

    protected PApplet app;
    protected PShape shape;
    protected PVector color;
    protected PVector lookAheadPosition;


    protected BreadCrumbs crumbs;
    protected boolean crumbTrail;

    protected int wanderRadius = 5;

    protected int lookAheadDistance = GameConstants.DEFAULT_LOOKAHEAD;

    public float targetRotationWander = 0;

    private static final int LIFE_BAR_WIDTH = 20;
    private static final int LIFE_BAR_HEIGHT = 3;



    public GameObject(PApplet app, PVector color, float size, float posX, float posY, float orientation, int life)
    {
        super(new PVector(posX, posY), orientation);

        this.app = app;
        setColor(color.x, color.y, color.z);
        setSize(size);
        setLife(life);

        this.lookAheadPosition = new PVector();

        setDefaults();
    }

    public void update()
    {
        if (outOfBounds())
        {
            Align(Engine.tooth.tooth.getPosition());
            Seek(Engine.tooth.tooth.getPosition());
        }

        velocity.add(linearAcc);
        rotation += angularAcc;

        position.x += velocity.x;
        position.y += velocity.y;

        orientation = (velocity.mag() > 0) ? velocity.heading() : orientation;
        orientation += rotation;

        if (crumbs != null)
            crumbs.drawCrumbs(crumbTrail);

        drawShape();
        drawLifeBar();
    }

    /* Initialize breadcrumbs */

    public void initCrumbs()
    {
        crumbs = new BreadCrumbs(this.app, this);
    }


    /* Shape Methods */


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

    public void drawLifeBar() {
        app.pushMatrix();
        app.rectMode(PConstants.CORNER);

        app.fill(255, 0, 0);
        app.rect(position.x - 10, position.y - 20, LIFE_BAR_WIDTH, LIFE_BAR_HEIGHT);

        app.fill(0, 255, 0);
        app.rect(position.x - 10, position.y - 20, (life / MAX_LIFE) * LIFE_BAR_WIDTH, LIFE_BAR_HEIGHT);

        app.rectMode(PConstants.CENTER);
        app.popMatrix();
    }

    /* Movement methods */

    @Override
    public void Seek(PVector target)
    {
        setVelocity(Seek.getKinematic(this, target).velocity);
    }

    @Override
    public void Arrive(PVector target)
    {
        setLinearAcc(Arrive.getSteering(this, target).linear);
    }

    @Override
    public void Align(PVector target)
    {
        setAngularAcc(Align.getSteering(this, target).angular);
        if (angularAcc == 0)
            rotation = 0;
    }

    @Override
    public void Wander()
    {
        SteeringOutput steering = Wander.getSteeringAlign(this, targetRotationWander, maxRot, maxAngularAcc, angularROS,angularROD);

        setVelocity(PVector.fromAngle(this.getOrientation()).mult(maxVel));
        setAngularAcc(steering.angular);

        if(steering.angular == 0)
            targetRotationWander = Wander.randomBinomial() * maxRot;
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
        return (this.position.x < GameConstants.SCR_OFFSET || this.position.x > GameConstants.SCR_WIDTH - GameConstants.SCR_OFFSET ||
                this.position.y < GameConstants.SCR_OFFSET || this.position.y > GameConstants.SCR_HEIGHT - GameConstants.SCR_OFFSET);
    }

    public boolean obstacleCollisionDetected()
    {
        lookAheadPosition = PVector.fromAngle(velocity.heading()).mult(lookAheadDistance);

        lookAheadPosition.x += position.x;
        lookAheadPosition.y += position.y;

        for (Obstacle o : Engine.staticObjects)
            if (o.contains(Utility.getGridLocation(lookAheadPosition)) && o != Engine.tooth)
                return true;

        return false;
    }

    public boolean hasLOS(PVector target)
    {
        PVector lightBeacon = new PVector(position.x, position.y);
        PVector targetVelocity = PVector.sub(target, lightBeacon).normalize().mult(maxVel);

        while (Utility.getGridIndex(lightBeacon) != Utility.getGridIndex(target))
        {
            lightBeacon.add(targetVelocity);
            if (Environment.invalidNodes.contains(Utility.getGridIndex(lightBeacon)))
                return false;
            if (Environment.toothNodes.contains(Utility.getGridIndex(lightBeacon)))
                return false;
        }

        return true;
    }

    public void takeDamage(float damage)
    {
        this.life -= damage;
    }

    public boolean isTouching(GameObject other)      //Checks to see if two game objects are touching each other
    {
        return (PVector.sub(this.position, other.position).mag() < (this.size + other.size));     //All objects considered symmetrical here
    }

    public boolean isTouching(PVector pos, int size)
    {
        return (PVector.sub(this.position, pos).mag() < (this.size + size));     //All objects considered symmetrical here
    }


    /* Getters and Setters */

    public PVector getGridLocation()
    {
        return new PVector((int) (position.x/GameConstants.TILE_SIZE.x), (int)(position.y/GameConstants.TILE_SIZE.y));
    }

    public int getGridIndex()
    {
        return (int)(position.y/GameConstants.TILE_SIZE.y) * (int) GameConstants.NUM_TILES.x + (int) (position.x/GameConstants.TILE_SIZE.x);
    }


    public void setLife(int life)
    {
        this.life = life;
        this.MAX_LIFE = life;
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


}
