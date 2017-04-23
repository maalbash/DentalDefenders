package engine;

import objects.*;
import processing.core.PApplet;
import processing.core.PVector;
import utility.GameConstants;

import static java.lang.Math.random;

/**
 * Created by KinshukBasu on 07-Apr-17.
 */
public class SpawnEnemies
{

    private static int lactusSpawnInterval = 2000;
    private static int fructusSpawnInterval = 5000;
    private static int streptusSpawnInterval = 7000;
    private static int enamelatorSpawnInterval = 15000;

    private static int MaxLactus = 7;
    private static int MaxFructus = 5;
    private static int MaxStreptus = 3;
    private static int MaxEnamelator = 1;


    private static long lastLactusSpawn = 0;
    private static long lastFructusSpawn = 0;
    private static long lastStreptusSpawn = 0;
    private static long lastEnamelatorSpawn = 0;
    private static long now = 0;

    private static int LactusCount;
    private static int FructusCount;
    private static int StreptusCount;
    private static int EnamelatorCount;

    public static void update(PApplet app)
    {
        if(now==0){
            now = System.currentTimeMillis();
            lastLactusSpawn = lastFructusSpawn = lastStreptusSpawn = lastEnamelatorSpawn = now;
        }

        LactusCount = FructusCount = StreptusCount = EnamelatorCount = 0;

        for(Enemy e : Engine.Enemies){
            if(e instanceof Enemy_lactus)
                LactusCount++;
            else if(e instanceof Enemy_fructus)
                FructusCount++;
            else if(e instanceof Enemy_streptus)
                StreptusCount++;
            else if(e instanceof Enemy_enamelator)
                EnamelatorCount++;
        }

        if(LactusCount < MaxLactus)
            addLactus(app);
        if(FructusCount < MaxFructus)
            addFructus(app);

        if(StreptusCount < MaxStreptus)
            addStreptus(app);
        if(EnamelatorCount < MaxEnamelator)
            addEnamelator(app);

    }

    public static void addLactus(PApplet app){

        now = System.currentTimeMillis();
        if((now-lastLactusSpawn) > lactusSpawnInterval) {
            Enemy_lactus newLactus = new Enemy_lactus(app, 0, 0, 0);

            PVector temp = SpawnEnemies.randomEdgeLocation();
            newLactus.setPosition(temp);

            Engine.Enemies.add(newLactus);
            lastLactusSpawn = now;
        }
    }

    public static void addFructus(PApplet app){
        now = System.currentTimeMillis();
        if((now-lastFructusSpawn) > fructusSpawnInterval) {
            Enemy_fructus newFructus = new Enemy_fructus(app, 0, 0, 0);

            PVector temp = SpawnEnemies.randomEdgeLocation();
            newFructus.setPosition(temp);

            Engine.Enemies.add(newFructus);
            lastFructusSpawn = now;
        }
    }

    public static void addStreptus(PApplet app){
        now = System.currentTimeMillis();
        if((now-lastStreptusSpawn) > streptusSpawnInterval) {
            Enemy_streptus newStreptus = new Enemy_streptus(app, 0, 0, 0);

            PVector temp = SpawnEnemies.randomEdgeLocation();
            newStreptus.setPosition(temp);

            Engine.Enemies.add(newStreptus);
            lastStreptusSpawn = now;
        }
    }

    public static void addEnamelator(PApplet app)
    {
        now = System.currentTimeMillis();
        if((now-lastEnamelatorSpawn) > enamelatorSpawnInterval)
        {
            Enemy_enamelator newEnamelator = new Enemy_enamelator(app, 0, 0, 0);

            PVector temp = SpawnEnemies.randomEdgeLocation();
            newEnamelator.setPosition(temp);

            Engine.Enemies.add(newEnamelator);
            lastEnamelatorSpawn = now;
        }
    }


    private static PVector randomEdgeLocation(){

        //Edges are numbered clockwise, starting from the left vertical edge

        int choice = (int)(random()*5);
        PVector pos = new PVector();

        switch(choice){

            case(0): pos.x = GameConstants.SCR_OFFSET;
                     pos.y = (float)(random()*GameConstants.SCR_HEIGHT);
                     break;

            case(1): pos.x = (float)(random()*GameConstants.SCR_WIDTH);
                     pos.y = GameConstants.SCR_OFFSET;
                     break;

            case(2): pos.x = GameConstants.SCR_WIDTH-GameConstants.SCR_OFFSET;
                     pos.y = (float)(random()*GameConstants.SCR_HEIGHT);
                     break;

            case(3): pos.x = (float)(random()*GameConstants.SCR_WIDTH);
                     pos.y = GameConstants.SCR_HEIGHT-GameConstants.SCR_OFFSET;
                     break;

            default: pos.x = GameConstants.SCR_OFFSET;
                     pos.y = (float)(random()*GameConstants.SCR_HEIGHT);
                     break;
        }
        return(pos);
    }
}
