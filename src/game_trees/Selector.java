package game_trees;

/**
 * Created by ujansengupta on 4/16/17.
 */
public class Selector extends Task
{
    @Override
    public Task.returnType run()
    {
        for (Task t : children)
            if (t.run() == returnType.SUCCESS)
                return returnType.SUCCESS;

        return returnType.FAILURE;
    }
}
