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
     *
     * @param db - takes DB manager as paramater to make the connection
     */
    protected DAO(DBmanager db) {
        if ((connection = db.getConnection()) == null) {
            System.err.println(">>>> CityDAO : The database doesnt exist");
        }
    }

    /**
     * getInstance
     *
     * @param db
     * @return uniqueInstance
     * <p>
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
     * <p>
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

    /**
     * get Time
     * getter for the timestamp
     *
     * @return returns the last measurement timestamp
     */

    protected String getTime() {
        String time = null;
        statement = null;

        try {
            String sql = "SELECT timeAndDate from measurements order by timeAndDate desc limit 1;";
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                time = result.getString("timeAndDate");
            }
            System.out.println(time);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * getTemp
     * getter for the temperature
     *
     * @return returns the last measurement temperature
     */
    protected double getTemp() {
        double temp = 0;
        statement = null;

        try {
            String sql = "SELECT temperature from measurements order by timeAndDate desc limit 1;";
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                temp = result.getDouble("temperature");
            }
            System.out.println(temp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * getWindspeed
     * getter for the wind speed
     *
     * @return returns the last measurement windspeed
     */
    protected double getWindspeed() {
        double windSpeed = 0;
        statement = null;

        try {
            String sql = "SELECT windspeed from measurements order by timeAndDate desc limit 1;";
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                windSpeed = result.getDouble("windspeed");
            }
            System.out.println(windSpeed);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return windSpeed;
    }

    /**
     * getHumidity
     * getter for the humidity
     *
     * @return returns the last measurement humidity
     */
    protected double getHumidity() {
        double humidity = 0;
        statement = null;

        try {
            String sql = "SELECT humidity from measurements order by timeAndDate desc limit 1;";
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                humidity = result.getDouble("humidity");
            }
            System.out.println(humidity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return humidity;
    }


    /**
     * getPressure
     * getter for the pressure
     *
     * @return returns the last measurement pressure
     */
    protected double getPressure() {
        double pressure = 0;
        statement = null;

        try {
            String sql = "SELECT pressure from measurements order by timeAndDate desc limit 1;";
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                pressure = result.getDouble("pressure");
            }
            System.out.println(pressure);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pressure;
    }
}
