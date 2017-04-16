package game_trees;

/**
 * Created by ujansengupta on 4/16/17.
 */
public class Sequence extends Task
{
    @Override
    public returnType run()
    {
        for (Task t : children)
            if (t.run() == returnType.FAILURE)
                return returnType.FAILURE;

        return returnType.SUCCESS;

    }
}
