package utility;

import processing.core.PConstants;

/**
 * Created by ujansengupta on 3/30/17.
 */
public class Utility
{
    public static float mapToRange(float rotation) {
        float r = rotation % (2 * PConstants.PI);
        if (Math.abs(r) <= Math.PI)
            return r;
        else {
            if (r > Math.PI)
                return (r - 2 * PConstants.PI);
            else
                return (r + 2 * PConstants.PI);
        }
    }
}
