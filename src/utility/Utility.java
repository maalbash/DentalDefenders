package utility;

import objects.Player;
import processing.core.PConstants;
import processing.core.PVector;

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

    public static boolean checkTargetReached(Player player, PVector target) {
        return((Math.abs(target.x - player.getPosition().x) <= 1.f) && (Math.abs(target.y - player.getPosition().y) <= 1.f));
    }
}
