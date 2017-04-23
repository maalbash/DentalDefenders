package engine;

import objects.*;

/**
 * Created by KinshukBasu on 23-Apr-17.
 */
public class LogRecord {

    public static long Avg_time=0;
    public static float Avg_enemiesKilled=0;
    public static float Avg_playerHealth=0;
    public static float Avg_toothHealth=0;
    public static int RecordCount = 0;


    public static float AvgBoss_toothDamagePercentage = 0;
    public static float AvgBoss_playerDamagePercentage = 0;
    public static float AvgLactus_toothDamagePercentage = 0;
    public static float AvgLactus_playerDamagePercentage = 0;
    public static float AvgFructus_toothDamagePercentage = 0;
    public static float AvgFructus_playerDamagePercentage = 0;
    public static float AvgStreptus_toothDamagePercentage = 0;
    public static float AvgStreptus_playerDamagePercentage = 0;


    public long time;
    public int enemiesKilled;
    public int playerHealth;
    public int toothHealth;

    public float Boss_toothDamagePercentage;
    public float Boss_playerDamagePercentage;

    public float Lactus_toothDamagePercentage;
    public float Lactus_playerDamagePercentage;

    public float Fructus_toothDamagePercentage;
    public float Fructus_playerDamagePercentage;

    public float Streptus_toothDamagePercentage;
    public float Streptus_playerDamagePercentage;

    public LogRecord(long time, int enemiesKilled, int playerHealth, int toothHealth){

        this.time = time;
        this.enemiesKilled = enemiesKilled;
        this.playerHealth = playerHealth;
        this.toothHealth = toothHealth;

        this.Boss_playerDamagePercentage = (Enemy_enamelator.playerDamage*100/(AIplayer.DEFAULT_PLAYER_LIFE - playerHealth));
        this.Boss_toothDamagePercentage = (Enemy_enamelator.toothDamage*100/(Tooth.life - toothHealth));

        this.Lactus_playerDamagePercentage = (Enemy_lactus.playerDamage*100/(AIplayer.DEFAULT_PLAYER_LIFE - playerHealth));
        this.Lactus_toothDamagePercentage = (Enemy_lactus.toothDamage*100/(Tooth.life - toothHealth));

        this.Fructus_playerDamagePercentage = (Enemy_fructus.playerDamage*100/(AIplayer.DEFAULT_PLAYER_LIFE - playerHealth));
        this.Fructus_toothDamagePercentage = (Enemy_fructus.toothDamage*100/(Tooth.life - toothHealth));

        this.Streptus_playerDamagePercentage = (Enemy_streptus.playerDamage*100/(AIplayer.DEFAULT_PLAYER_LIFE - playerHealth));
        this.Streptus_toothDamagePercentage = (Enemy_streptus.toothDamage*100/(Tooth.life - toothHealth));

        Enemy_enamelator.playerDamage = 0;
        Enemy_enamelator.toothDamage = 0;
        Enemy_lactus.playerDamage = 0;
        Enemy_lactus.toothDamage = 0;
        Enemy_fructus.playerDamage = 0;
        Enemy_fructus.toothDamage = 0;
        Enemy_streptus.playerDamage = 0;
        Enemy_streptus.toothDamage = 0;

        Avg_time = ((Avg_time*RecordCount)+this.time)/(RecordCount+1);
        Avg_enemiesKilled = ((Avg_enemiesKilled*RecordCount)+this.enemiesKilled)/(RecordCount+1);
        Avg_playerHealth = ((Avg_playerHealth*RecordCount)+this.playerHealth)/(RecordCount+1);
        Avg_toothHealth = ((Avg_toothHealth*RecordCount)+this.toothHealth)/(RecordCount+1);

        AvgBoss_playerDamagePercentage = ((AvgBoss_playerDamagePercentage*RecordCount)+this.Boss_playerDamagePercentage)/(RecordCount+1);
        AvgBoss_toothDamagePercentage = ((AvgBoss_toothDamagePercentage*RecordCount)+this.Boss_toothDamagePercentage)/(RecordCount+1);

        AvgLactus_playerDamagePercentage = ((AvgLactus_playerDamagePercentage*RecordCount)+this.Lactus_playerDamagePercentage)/(RecordCount+1);
        AvgLactus_toothDamagePercentage = ((AvgLactus_toothDamagePercentage*RecordCount)+this.Lactus_toothDamagePercentage)/(RecordCount+1);

        AvgFructus_playerDamagePercentage = ((AvgFructus_playerDamagePercentage*RecordCount)+this.Fructus_playerDamagePercentage)/(RecordCount+1);
        AvgFructus_toothDamagePercentage = ((AvgFructus_toothDamagePercentage*RecordCount)+this.Fructus_toothDamagePercentage)/(RecordCount+1);

        AvgStreptus_playerDamagePercentage = ((AvgStreptus_playerDamagePercentage*RecordCount)+this.Streptus_playerDamagePercentage)/(RecordCount+1);
        AvgStreptus_toothDamagePercentage = ((AvgStreptus_toothDamagePercentage*RecordCount)+this.Streptus_toothDamagePercentage)/(RecordCount+1);



        ++RecordCount;
    }

    public void print(){
        System.out.println("Duration - "+this.time);
        System.out.println("Enemies killed - "+this.enemiesKilled);
        System.out.println("Player health - "+this.playerHealth);
        System.out.println("Tooth health - "+this.toothHealth);
        System.out.println("Boss Damage to player - "+this.Boss_playerDamagePercentage+"%");
        System.out.println("Boss Damage to tooth - "+this.Boss_toothDamagePercentage+"%");
        System.out.println("--------------------------------");
    }

    public static void printAvg(){
        System.out.println("Avg Duration - "+Avg_time);
        System.out.println("Avg Enemies killed - "+Avg_enemiesKilled);
        System.out.println("Avg Player health - "+Avg_playerHealth);
        System.out.println("Avg Tooth health - "+Avg_toothHealth);

        System.out.println("Avg % damage to player(Lactus) - "+AvgLactus_playerDamagePercentage+"%");
        System.out.println("Avg % damage to tooth(Lactus) - "+AvgLactus_toothDamagePercentage+"%");

        System.out.println("Avg % damage to player(Fructus) - "+AvgFructus_playerDamagePercentage+"%");
        System.out.println("Avg % damage to tooth(Fructus) - "+AvgFructus_toothDamagePercentage+"%");

        System.out.println("Avg % damage to player(Streptus) - "+AvgStreptus_playerDamagePercentage+"%");
        System.out.println("Avg % damage to tooth(Streptus) - "+AvgStreptus_toothDamagePercentage+"%");

        System.out.println("Avg % damage to player(Boss) - "+AvgBoss_playerDamagePercentage+"%");
        System.out.println("Avg % damage to tooth(Boss) - "+AvgBoss_toothDamagePercentage+"%");
    }
}
