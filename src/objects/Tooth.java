package objects;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;

/**
 * Created by ujansengupta on 4/5/17.
 */

public class Tooth extends GameObject
{
    private static int life = 200;
    private static PVector color = new PVector(227, 220, 175);
    private static float size = 30;
    private static float posX = GameConstants.SCR_WIDTH/2;
    private static float posY = GameConstants.SCR_HEIGHT/2;
    private static float orientation = 0;

    public Tooth(PApplet app)
    {
        super(app, color, size, posX, posY, orientation, life);
        this.makeTooth();
    }

    public void makeTooth()
    {
        app.rectMode(PConstants.CENTER);
        PShape square = app.createShape(app.RECT, posX, posY, size, size);
        changeShape(square);
        app.rectMode(PConstants.CORNER);
    }
}
