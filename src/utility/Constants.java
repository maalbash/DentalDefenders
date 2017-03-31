package utility;

import processing.core.PVector;

/**
 * Created by ujansengupta on 3/29/17.
 */
public class Constants
{

    public static float DEFAULT_MAX_VEL = 3;
    public static float DEFAULT_MAX_linearACC = 1;

    public static float DEFAULT_MAX_ROTATION = 2 * (float)Math.PI;
    public static float DEFAULT_MAX_angularACC = 0.005f;

    public static float DEFAULT_linearROS = 3;
    public static float DEFAULT_linearROD = 15;

    public static float DEFAULT_angularROS = 1.5f;
    public static float DEFAULT_angularROD = 2.5f;

    public static int DEFAULT_LIFE = 100;

    public enum searchAlgo
    {
        ASTAR, DIJKSTRA
    }

}
