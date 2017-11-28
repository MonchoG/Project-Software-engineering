import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * DAO class
 * Used to  extract the data we need from the database
 */

public class DAO {

    private static DAO uniqueInstance = null;
    private static Connection connection = null;
    private Statement statement = null;

    /**
     * DAO constructor
     * @param db - takes DB manager as paramater to make the connection
     */
    protected DAO(DBmanager db) {
        if ((connection = db.getConnection()) == null) {
            System.err.println(">>>> CityDAO : The database doesnt exist");
        }
    }

    /**
     * getInstance
     * @param db
     * @return uniqueInstance
     *
     * Makes an instance if there is not one.
     */
    public static synchronized DAO getInstance(DBmanager db) {
        if (uniqueInstance == null) uniqueInstance = new DAO(db);
        return uniqueInstance;
    }


    /**
     * getWeather method
     *
     * @return arraylist with the data from the database
     *
     * it extracts the data we mentioned in the select statement
     */

    protected ArrayList<String> getWeather() {
        ArrayList<String> weather = new ArrayList<String>();
        statement = null;

        try {
            String sql = "SELECT timeAndDate, temperature, windspeed, humidity, pressure from measurements;";
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String time = resultSet.getString("timeAndDate");
                String temp = resultSet.getString("temperature");
                String wind = resultSet.getString("windspeed");
                String hum = resultSet.getString("humidity");
                String pres = resultSet.getString("pressure");

                weather.add(String.format("%-20.20s  %-20.20s %-20.20s %-20.20s %-20.20s", time, temp, wind, hum, pres));
            }
            System.out.println("Time             Temperature             WindSpeed           Humidity               Pressure");
            for (String s : weather) {
                System.out.println(s);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return weather;
    }
}
