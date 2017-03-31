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
    private static int cornerRadius = 20;
    private PVector centerPosition;
    private Set<PVector> tileLocations;
    private Set<Integer> tileIndices;

    public PVector color;
    public PVector corner;
    public PVector size;

    private PApplet app;

    public Obstacle(PApplet theApplet, PVector upperLeft, PVector size, PVector color)
    {
        this.app = theApplet;
        this.color = color;
        this.corner = upperLeft;
        this.size = size;

        tileLocations = new HashSet<>();                                                  // The set containing tile locations as PVectors
        tileIndices = new HashSet<>();                                                    // The set containing tile locations as integer indices

        for (int i = (int)corner.y; i < (int)corner.y + size.y; i++)
        {
            for (int j = (int)corner.x; j < (int)corner.x + size.x; j++)
            {
                tileLocations.add(new PVector(j,i));
                tileIndices.add(i * (int) GameConstants.NUM_TILES.y + j);
            }
        }

        centerPosition = new PVector(corner.x + size.x/2, corner.y + size.y/2);
    }

    public void draw()
    {
        app.fill(color.x, color.y, color.z, 100);
        app.rect((int) corner.x * GameConstants.TILE_SIZE.x, (int) corner.y * GameConstants.TILE_SIZE.y, size.x * GameConstants.TILE_SIZE.x, size.y * GameConstants.TILE_SIZE.y, cornerRadius);

        app.fill(color.x, color.y, color.z);
        app.rect(((int)corner.x + 1) * GameConstants.TILE_SIZE.x, ((int)corner.y + 1) * GameConstants.TILE_SIZE.y, (size.x - 2) * GameConstants.TILE_SIZE.x, (size.y - 2) * GameConstants.TILE_SIZE.y, cornerRadius);
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
