package movement;

/**
 * Created by mohz2 on 4/8/2017.
 */
import objects.GameObject;
import objects.Player;
import processing.core.PVector;
import utility.Utility;

@SuppressWarnings("WeakerAccess")

public class Wander
{
    static float timeToTargetRotation = 10f;

    public static KinematicOutput getKinematic(GameObject character, float maxVelocity, float maxRotation)
    {
        KinematicOutput output = new KinematicOutput();

        output.velocity = PVector.fromAngle(character.getOrientation()).mult(maxVelocity);
        output.rotation = randomBinomial()*maxRotation;

        return output;
    }

    public static SteeringOutput getSteeringAlign(GameObject character, float targetRot, float maxRotation, float maxAccel, float ROS, float ROD)
    {
        SteeringOutput steering = new SteeringOutput();

        float rotation = targetRot - character.getOrientation();

        rotation = Utility.mapToRange(rotation);
        float rotationSize = Math.abs(rotation);

        if (rotationSize < ROS)
        {
            character.setRotation(0);
            return new SteeringOutput();
        }

        if (rotationSize > ROD)
            targetRot = (rotation/rotationSize) * maxRotation;
        else
            targetRot = (rotation/rotationSize) * rotationSize * maxRotation / ROD;


        steering.angular = targetRot - character.getRotation();
        steering.angular /= timeToTargetRotation;

        float angularAcc = Math.abs(steering.angular);

        if (angularAcc > maxAccel)
        {
            steering.angular /= angularAcc;
            steering.angular *= maxAccel;
        }

        return steering;
    }

    public static float randomBinomial()
    {
        return (float)(Math.random() - Math.random());
    }
}
