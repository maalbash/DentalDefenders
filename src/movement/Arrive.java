package movement;

import objects.GameObject;
import processing.core.PVector;

public class Arrive
{

    public static KinematicOutput getKinematic(GameObject character, PVector target)
    {
        KinematicOutput output = new KinematicOutput();
        output.velocity = PVector.sub(target, character.getPosition());

        if (output.velocity.mag() < character.getLinearROS())
            return new KinematicOutput();                   // return default kinematic output with no velocity or rotation

        output.velocity.div(character.getTimeToTargetVel());

        if (output.velocity.mag() > character.getMaxVel())
        {
            output.velocity.normalize();
            output.velocity.mult(character.getMaxVel());
        }

        return output;
    }


    public static SteeringOutput getSteering(GameObject character, PVector target)
    {

        SteeringOutput output = new SteeringOutput();
        float targetSpeed;

        PVector direction = new PVector(target.x - character.getPosition().x, target.y - character.getPosition().y);
        float distance = direction.mag();

        if (distance < character.getLinearROS())
        {
            character.setVelocity(new PVector(0, 0));       // reset character velocity
            return new SteeringOutput();                    // return default steering output with no linear or angular acceleration
        }

        if (distance > character.getLinearROD())
            targetSpeed = character.getMaxVel();
        else
            targetSpeed = character.getMaxVel() * direction.mag() / character.getLinearROD();

        direction.normalize();
        direction.mult(targetSpeed);

        output.linear = PVector.sub(direction, character.getVelocity());             //linear acceleration

        output.linear.div(character.getTimeToTargetVel());

        if (Math.abs(output.linear.mag()) > character.getMaxAcc())
        {
            output.linear.normalize();
            output.linear.mult(character.getMaxAcc());
        }

        return output;
    }
}
