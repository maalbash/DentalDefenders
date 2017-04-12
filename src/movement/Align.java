package movement;

import objects.GameObject;
import processing.core.PVector;
import utility.Utility;

/**
 * Created by ujansengupta on 3/26/17.
 */
public class Align
{
    public static float maxAngularAcc = 0.1f;

    public static SteeringOutput getSteering(GameObject character, PVector target)
    {
        float targetRotation;
        SteeringOutput output = new SteeringOutput();

        PVector direction = new PVector(target.x - character.getPosition().x, target.y - character.getPosition().y);

        if (direction.mag() == 0)
            return new SteeringOutput();


        float targetOrientation = direction.heading();
        float rotation = Utility.mapToRange(targetOrientation - character.getOrientation());
        float rotationSize = Math.abs(rotation);

        if (rotationSize < character.getAngularROS())
            return new SteeringOutput();


        if (rotationSize > character.getAngularROD())
            targetRotation = (rotation/rotationSize) * character.getMaxRot();
        else
            targetRotation = (rotation/rotationSize) * rotationSize * character.getMaxRot() / character.getAngularROD();


        output.angular = targetRotation - character.getRotation();
        output.angular /= character.getTimeToTargetRot();

        float angularAcc = Math.abs(output.angular);

        if (angularAcc > maxAngularAcc)
        {
            output.angular /= angularAcc;
            output.angular *= maxAngularAcc;
        }

        return output;
    }
}
