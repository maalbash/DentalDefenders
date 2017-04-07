package objects;

import environment.Obstacle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;

/**
 * Created by ujansengupta on 4/5/17.
 */

public class Tooth extends Obstacle
{
    private PApplet app;
    public static int life = 200;
    public static PVector size = new PVector(50, 50);
    public static PVector center = GameConstants.GRAPH_CENTER;


    public Tooth(PApplet app)
    {
        super(app, center, size);
        this.app = app;
        color = new PVector(227, 182, 48);

        this.shape = app.createShape(PConstants.RECT, (int) center.x * GameConstants.TILE_SIZE.x, (int) center.y * GameConstants.TILE_SIZE.y,
                2 * GameConstants.TILE_SIZE.x, 2 * GameConstants.TILE_SIZE.y, cornerRadius);

        shape.setFill(app.color(color.x, color.y, color.z));

    }

}
