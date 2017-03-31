package objects;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")

public class BreadCrumbs
{

    public PApplet app;

    private int prevTime;
    private ArrayList<PVector> crumbList = new ArrayList<>();

    private GameObject object;

    public BreadCrumbs(PApplet app, GameObject character)
    {
        this.app = app;
        object = character;
        prevTime = app.millis();

    }

    public void drawCrumbs(boolean drawTrail)
    {

        int currTime = app.millis();

        if (currTime-prevTime > 100)
        {
            crumbList.add(new PVector(object.getPosition().x, object.getPosition().y));
            prevTime = currTime;

            if (crumbList.size() > 5 && drawTrail)
            {
                crumbList.remove(0);
            }
        }


        PShape breadCrumb;

        for (PVector crumb : crumbList)
        {
            breadCrumb = app.createShape(PApplet.ELLIPSE, crumb.x, crumb.y, 2, 2);
            breadCrumb.setFill(0);

            app.shape(breadCrumb);
        }
    }

    public void clearCrumbs()
    {
        crumbList.clear();
    }



}
