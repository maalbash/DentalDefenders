package utility;

import processing.core.PVector;

/**
 * Created by ujansengupta on 4/7/17.
 */
public interface Movable
{
    void Seek(PVector target);

    void Arrive(PVector target);

    void Align(PVector target);

    void Wander();

    void stopMoving();

    boolean outOfBounds();

}
