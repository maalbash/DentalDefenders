package game_trees;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 4/16/17.
 */
public abstract class Task
{
    public enum returnType
    {
        SUCCESS, FAILURE
    }

    public ArrayList<Task> children;

    public abstract returnType run();
}
