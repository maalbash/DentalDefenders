package environment;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import utility.GameConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ujansengupta on 3/12/17.
 */
public class Obstacle
{
    protected static int cornerRadius = 20;
    private Set<PVector> tileLocations;
    private Set<Integer> tileIndices;
    private static PVector tileSize = GameConstants.TILE_SIZE;

    public PVector color;
    public PVector center;
    public PVector size;
    public PShape shape;

    private PApplet app;

    public Obstacle(PApplet theApplet, PVector center, PVector size)
    {
        this.app = theApplet;
        this.color = GameConstants.DEFAULT_OBSTACLE_COLOR;
        this.center = center;
        this.size = size;

        this.shape = app.createShape(PConstants.RECT, (int) center.x * tileSize.x, (int) center.y * tileSize.y,
                (size.x - 1) * tileSize.x, (size.y - 1) * tileSize.y, cornerRadius);

        shape.setFill(app.color(color.x, color.y, color.z));

        tileLocations = new HashSet<>();                                                  // The set containing tile locations as PVectors
        tileIndices = new HashSet<>();                                                    // The set containing tile locations as integer indices

        for (int i = (int) (this.center.y - size.y/2); i < Math.ceil (this.center.y + size.y/2); i++)
        {
            for (int j = (int) (this.center.x - size.x/2); j < Math.ceil(this.center.x + size.x/2); j++)
            {
                tileLocations.add(new PVector(j,i));
                tileIndices.add(i * (int) GameConstants.NUM_TILES.y + j);
            }
        }
    }

    public void draw()
    {
        app.shape(shape);
    }

    public boolean contains(PVector point)
    {
        return ((point.x > (center.x - Math.ceil(size.x/2 + 1)) && point.x < (center.x + Math.floor(size.x/2 - 1)))
                && (point.y > (center.y - Math.ceil(size.y/2 + 1)) && point.y < (center.y + Math.floor(size.y/2 - 1))));

    }



    public Set<PVector> getTileLocations()
    {
        return tileLocations;
    }

    public Set<Integer> getTileIndices()
    {
        return tileIndices;
    }

    public PVector getCenter()
    {
        return center;
    }

    public PVector getColor()
    {
        return color;
    }
}
