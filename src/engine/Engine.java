package engine;
/**
 * Created by KinshukBasu on 02-Mar-17.
 */

import environment.Environment;
import environment.Obstacle;
import objects.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import sun.awt.image.ImageWatched;
import utility.GameConstants;
import utility.Utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Engine extends PApplet
{

    public static boolean LOGGER_MODE = true;
    public static int loopCtr = 0;
    public static int maxLoop = 10;


    public static long beginTime;
    public static LinkedList<LogRecord> logData;

    public static AIplayer player;        //Changed these 2 to static, since only one instance of each, and to provide ease of access
    public static Tooth tooth;
    public static Environment environment;
    //public static Player player;

    public static List<Obstacle> staticObjects;
    public static List<Enemy>  Enemies;

    public static long timer;
    public static float currentHP, hpLastTime;
    public static boolean PLAYER_CURRENTLY_POWERED_UP;

    public static void main(String[] args){
        PApplet.main("engine.Engine", args);
    }


    public void settings()
    {
        size(GameConstants.SCR_WIDTH, GameConstants.SCR_HEIGHT);
    }

    public void setup()
    {
        rectMode(PConstants.CENTER);
        beginTime = System.currentTimeMillis();
        logData = new LinkedList<LogRecord>();

        tooth = new Tooth(this);
        environment = new Environment(this);
        player = new AIplayer(this);
        //player = new Player(this);

        Enemies = new ArrayList<>();
        staticObjects = new ArrayList<>();

        staticObjects.add(tooth);

        for (Obstacle o : environment.getObstacles())
            staticObjects.add(o);

        frameRate(60);

        timer = 0;
        currentHP = player.getDefaultPlayerLife();
        hpLastTime = player.getDefaultPlayerLife();
        PLAYER_CURRENTLY_POWERED_UP = false;
    }


    public void LogDamageByEnemyType(Enemy e) {

        if (e instanceof Enemy_streptus) {
            if (e.getFinalTarget() instanceof AIplayer) {
                Enemy_streptus.playerDamage += e.contactDamage;
            } else
                Enemy_streptus.toothDamage += e.contactDamage;
        } else if (e instanceof Enemy_lactus) {
            if (e.getFinalTarget() instanceof AIplayer) {
                Enemy_lactus.playerDamage += e.contactDamage;
            } else
                Enemy_lactus.toothDamage += e.contactDamage;
        } else if (e instanceof Enemy_fructus) {
            if (e.getFinalTarget() instanceof AIplayer) {
                Enemy_fructus.playerDamage += e.contactDamage;
            } else
                Enemy_fructus.toothDamage += e.contactDamage;
        } else if (e instanceof Enemy_enamelator) {
            if (e.getFinalTarget() instanceof AIplayer) {
                Enemy_enamelator.playerDamage += e.contactDamage;
            } else
                Enemy_enamelator.toothDamage += e.contactDamage;
        }
    }

    public void draw()
    {

        timer++;
        currentHP = player.getLife();
        //difficultyAdjustment();

        background(105, 183, 219);

        environment.update();
        tooth.update();
        player.update();

        SpawnEnemies.update(this);
        enemyBehaviour();

        if(player.getLife() <= 0 || tooth.tooth.getLife() <= 0){
            EndGameandLog();

        }
    }


    public void enemyBehaviour()
    {

        LinkedList<Enemy> EnemiesToRemove = new LinkedList<>();

        for(Enemy e : Engine.Enemies)
        {
            e.behaviour();
            e.update();
            if(Utility.checkTargetReached(e,e.getFinalTarget().getPosition()))  //Checks if enemy has touched player/tooth
            {
                EnemiesToRemove.add(e);
                e.getFinalTarget().takeDamage(e.contactDamage);
                LogDamageByEnemyType(e);
            }
        }

        for(Enemy e : EnemiesToRemove){
            Engine.Enemies.remove(e);
        }
    }

    public void difficultyAdjustment()
    {
        if(timer - 0 >= 100)
        {
            timer = 0;
            //TODO - check to see if the default hp loss value is adequate, maybe increase and decrease enemy spawn time
            if(HpBelow25percent() && !PLAYER_CURRENTLY_POWERED_UP)
            {
                powerup();
            }

            if(PLAYER_CURRENTLY_POWERED_UP && !HpBelow25percent())
            {
                powerdown();
            }

            if (hpLastTime - currentHP >= GameConstants.MAJOR_HP_LOSS)
            {
                hpLastTime = currentHP+10;
                player.setLife((int) currentHP + 10);
                player.BulletDamage = 20;
            }
            else if (hpLastTime - currentHP >= GameConstants.DEFAULT_HP_LOSS)
            {
                hpLastTime = currentHP;
                player.BulletDamage = 20;
            }
            else if(hpLastTime - currentHP < GameConstants.SMALL_HP_LOSS)
            {
                hpLastTime = currentHP;
                player.BulletDamage = 5;
            }
        }
    }

    public boolean HpBelow25percent(){
        return ((float)player.getLife() / (float)player.getDefaultPlayerLife()) <= 0.3f ;
    }

    public void powerup()
    {
        PLAYER_CURRENTLY_POWERED_UP = true;
        player.setColor(0,0,0);
        for(Enemy e: Enemies){
            if(e instanceof Enemy_fructus)
                ((Enemy_fructus) e).setFructusContactDamage(5);
            if(e instanceof Enemy_lactus)
                ((Enemy_lactus) e).setLactusContactDamage(5);
            if(e instanceof Enemy_streptus)
                ((Enemy_streptus)e).BulletDamage = 5;
            if(e instanceof Enemy_enamelator)
                ((Enemy_enamelator)e).setBulletDamage(5);
        }
        player.BulletDamage = 20;
    }

    public void powerdown(){
        PLAYER_CURRENTLY_POWERED_UP = false;
        player.setColor(0,0,0);
        for(Enemy e: Enemies){
            if(e instanceof Enemy_fructus)
                ((Enemy_fructus) e).setFructusContactDamage(15);
            if(e instanceof Enemy_lactus)
                ((Enemy_lactus) e).setLactusContactDamage(10);
            if(e instanceof Enemy_streptus)
                ((Enemy_streptus)e).BulletDamage = 10;
            if(e instanceof Enemy_enamelator)
                ((Enemy_enamelator)e).setBulletDamage(10);
        }
        player.BulletDamage=10;
    }

    public void EndGameandLog()
    {
        long now = System.currentTimeMillis();
        LogRecord newEntry = new LogRecord((now-beginTime),player.enemiesKilled,player.getLife(),tooth.tooth.getLife());
        logData.add(newEntry);
        newEntry.print();

        drawText("GAME OVER", width/2, height/2, this);

        if(LOGGER_MODE)
        {
            if(loopCtr < maxLoop)
            {
                this.settings();
                this.setup();
                ++loopCtr;
            }

            if(loopCtr>=maxLoop)
            {
                LogRecord.printAvg();
                noLoop();
            }
        }
        else
        {
            noLoop();
        }
    }

    public static void drawText(String text, float positionX, float positionY, PApplet app)
    {
        app.pushMatrix();
        app.textSize(GameConstants.TEXT_SIZE);
        app.fill(GameConstants.TEXT_COLOR.x, GameConstants.TEXT_COLOR.y, GameConstants.TEXT_COLOR.z);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text(text, positionX, positionY, 5);
        app.popMatrix();
    }


    /*public void mouseMoved()
    {
        player.updateTarget();
    }
    public void mousePressed()
    {
        player.shoot();
    }*/


}
