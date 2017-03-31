package movement;

import objects.GameObject;
import processing.core.PVector;

@SuppressWarnings("WeakerAccess")

public class Seek
{
    public static KinematicOutput getKinematic(GameObject character, PVector target)
    {
        KinematicOutput output = new KinematicOutput();
        output.velocity = PVector.sub(target, character.getPosition());

        if (output.velocity.mag() > character.getMaxVel())
        {
            output.velocity.normalize();
            output.velocity.mult(character.getMaxVel());
        }

        return output;
    }

}
