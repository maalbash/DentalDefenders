package engine;

/**
 * Created by KinshukBasu on 23-Apr-17.
 */
public class LogRecord {

    public static long Avg_time=0;
    public static float Avg_enemiesKilled=0;
    public static float Avg_playerHealth=0;
    public static float Avg_toothHealth=0;
    public static int RecordCount = 0;

    public long time;
    public int enemiesKilled;
    public int playerHealth;
    public int toothHealth;

    public LogRecord(long time, int enemiesKilled, int playerHealth, int toothHealth){

        this.time = time;
        this.enemiesKilled = enemiesKilled;
        this.playerHealth = playerHealth;
        this.toothHealth = toothHealth;

        Avg_time = ((Avg_time*RecordCount)+this.time)/(RecordCount+1);
        Avg_enemiesKilled = ((Avg_enemiesKilled*RecordCount)+this.enemiesKilled)/(RecordCount+1);
        Avg_playerHealth = ((Avg_playerHealth*RecordCount)+this.playerHealth)/(RecordCount+1);
        Avg_toothHealth = ((Avg_toothHealth*RecordCount)+this.toothHealth)/(RecordCount+1);
        ++RecordCount;
    }

    public void print(){
        System.out.println("Duration - "+this.time);
        System.out.println("Enemies killed - "+this.enemiesKilled);
        System.out.println("Player health - "+this.playerHealth);
        System.out.println("Tooth health - "+this.toothHealth);
        System.out.println("--------------------------------");
    }

    public static void printAvg(){
        System.out.println("Avg Duration - "+Avg_time);
        System.out.println("Avg Enemies killed - "+Avg_enemiesKilled);
        System.out.println("Avg Player health - "+Avg_playerHealth);
        System.out.println("Avg Tooth health - "+Avg_toothHealth);
    }
}
