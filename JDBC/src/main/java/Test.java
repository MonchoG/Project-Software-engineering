import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        DAO cdb = new DAO(DBmanager.getInstance());
        System.out.println("Weatherstation jdbc test");

        System.out.println("Enter refresh to refresh the database or quit to abort the program");
        Scanner keyboard = new Scanner(System.in);
        boolean bol = true;
        String in;
        while (bol){
             in = keyboard.next();
            if (in.equals("quit")) bol = false;
            else cdb.getWeather();
        }
        cdb.getWeather();
        cdb.getTime();
        cdb.getTemp();
        cdb.getWindspeed();
        cdb.getHumidity();
        cdb.getPressure();
    }
}
