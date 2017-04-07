package environment;

import processing.core.PApplet;
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
    private PVector centerPosition;
    private Set<PVector> tileLocations;
    private Set<Integer> tileIndices;
    private static PVector tileSize = GameConstants.TILE_SIZE;

    public PVector color;
    public PVector center;
    public PVector size;

    private PApplet app;

    public Obstacle(PApplet theApplet, PVector center, PVector size)
    {
        this.app = theApplet;
        this.color = GameConstants.DEFAULT_OBSTACLE_COLOR;
        this.center = center;
        this.size = size;


        tileLocations = new HashSet<>();                                                  // The set containing tile locations as PVectors
        tileIndices = new HashSet<>();                                                    // The set containing tile locations as integer indices

        for (int i = (int) this.center.y; i < (int) this.center.y + size.y; i++)
        {
            for (int j = (int) this.center.x; j < (int) this.center.x + size.x; j++)
            {
                tileLocations.add(new PVector(j,i));
                tileIndices.add(i * (int) GameConstants.NUM_TILES.y + j);
            }
        }

        centerPosition = new PVector(this.center.x + size.x/2, this.center.y + size.y/2);
    }

    public void draw()
    {
        /*app.fill(color.x, color.y, color.z, 100);
        app.rect((int) center.x * tileSize.x, (int) center.y * tileSize.y, size.x * tileSize.x, size.y * tileSize.y, cornerRadius);*/

        app.fill(color.x, color.y, color.z);
        app.rect((int) center.x * tileSize.x, (int) center.y * tileSize.y, (size.x - 1) * tileSize.x, (size.y - 1) * tileSize.y, cornerRadius);
        app.noFill();
    }


    Set<PVector> getTileLocations()
    {
        return tileLocations;
    }

    Set<Integer> getTileIndices()
    {
        return tileIndices;
    }

    PVector getCenter()
    {
        return centerPosition;
    }

    PVector getColor()
    {
        return color;
    }
}
