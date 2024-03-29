import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;

import static jdk.nashorn.internal.objects.Global.Infinity;

/**
 * DAO class
 * Used to  extract the data we need from the database
 */

public class DAO {

    private static DAO uniqueInstance = null;
    private static Connection connection = null;
    private Statement statement = null;
    private String beginDate;
    private String endDate;
    private int beginDay;
    private double NaN = -Infinity;

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
            String sql = "SELECT timeAndDate, temperature, light, pressure from measurements;";
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String time = resultSet.getString("timeAndDate");
                String temp = resultSet.getString("temperature");
                String light = resultSet.getString("light");
                String pres = resultSet.getString("pressure");

                weather.add(String.format("%-20.20s  %-20.20s %-20.20s %-20.20s", time, temp, light, pres));
            }
//            System.out.println("Time             Temperature             Light           Pressure ");
//            for (String s : weather) {
//                System.out.println(s);
//            }
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
     * getDay
     * Gets all measurements of last day and puts them in an ArrayList
     *
     * @param value value obtained from the database
     * @return ArrayList full with measurements of last day
     */

    protected double[] getDay(String value) {
        double[] dayArray = new double[25];
        try {
            //Selecting the measurements from last day, showing a measurement per hour
            String sql = "SELECT CAST(timeAndDate AS Date) as Date,\n" +
                    "extract(hour FROM timeAndDate) as hour,\n" +
                    "AVG(temperature) as temperature,\n" +
                    "AVG(light) as light,\n" +
                    "AVG(pressure) as pressure,\n" +
                    "AVG(humidity) as humidity\n" +
                    "FROM measurements\n" +
                    "WHERE timeAndDate > (NOW() - INTERVAL 1 day)\n" +
                    "GROUP BY CAST(TimeAndDate AS Date) ,\n" +
                    "extract(hour FROM timeAndDate)\n" +
                    "ORDER By timeAndDate desc ;";

            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);


            //check for a measurement
            if (!result.next()) {
                for (int i = 0; i <= 24; i++) {
                    dayArray[i] = NaN;
                }
                return dayArray;
            }
            //beginTime
            int i = result.getInt("hour");
            //endTime (in this case equal to the beginTime)
            int end = i;
            boolean zero = false;
            int count = 0;
            int index = 0;
            //go back so it doesn't skip this measurement in the while loop
            result.previous();
            while (result.next() && index <= 24) {

                //Check if zero has been reached, if so go back to 24 hours
                if (i == -1) {
                    if (!zero) {
                        i = 23;
                        zero = true;
                    }
                }
                //check if a measurement is on that hour
                if (i == result.getInt("hour")) {
                    dayArray[index] = (result.getDouble(value));
                } else {
                    //if not then add 0 to that day
                    dayArray[index] = NaN;
                    //no values have been added so stay at the same value
                    result.previous();
                }
                i--;
                index++;
            }
            if (zero) {
                //add zeros from last measurement until the endTime
                while (i >= end && index <= 24) {
                    dayArray[index] = NaN;
                    i--;
                    index++;
                }
            } else {
                //add zeroes from last measurement until zero
                while (i >= 0 && index <= 24) {
                    dayArray[index] = NaN;
                    i--;
                    index++;
                }
                //set the index to 24 and then add zeros from 24 until the endTime
                i = 24;
                while (i >= end && index <= 24) {
                    dayArray[index] = NaN;
                    i--;
                    index++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayArray;
    }

    /**
     * getWeek
     * Gets all measurements of last week and puts them in an ArrayList
     *
     * @param value value obtained from the database
     * @return ArrayList full with measurements of last week
     */

    protected double[] getWeek(String value) {
        double[] weekArray = new double[8];
        try {
            //Selecting the measurements from last week, showing a measurement per day
            String sql = "SELECT CAST(timeAndDate AS Date) as Date,\n" +
                    "extract(day FROM timeAndDate) as day,\n" +
                    "AVG(temperature) as temperature,\n" +
                    "AVG(light) as light,\n" +
                    "AVG(pressure) as pressure,\n" +
                    "AVG(humidity) as humidity\n" +
                    "FROM measurements\n" +
                    "WHERE timeAndDate > (NOW() - INTERVAL 7 day)\n" +
                    "GROUP BY CAST(TimeAndDate AS Date),\n" +
                    "extract(day FROM timeAndDate)\n" +
                    "ORDER By timeAndDate desc ;";
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            //check for a measurement
            if (!result.next()) {
                for (int i = 0; i <= 7; i++) {
                    weekArray[i] = NaN;
                }
                return weekArray;
            }
            boolean zero = false;
            //beginDay
            int i = result.getInt("day");
            //endDay
            int end = i - 7;
            //if end is negative then substract it from 31
            if (end <= 0) {
                end += 31;
            }
            int index = 0;
            //go back so it doesn't skip this measurement in the while loop
            result.previous();
            while (result.next() && index <= 7) {
                //Check if zero has been reached, if so go back to 30 days
                if (i == 0) {
                    if (!zero) {
                        i = 31;
                        zero = true;
                    }
                }
                //Check if there is a measurement on that day
                if (i == result.getInt("day")) {
                    weekArray[index] = (result.getDouble(value));
                } else {
                    //if not then add 0 to that day
                    weekArray[index] = (NaN);
                    //no values have been added so stay at the same value
                    result.previous();
                }
                i--;
                index++;

            }
            if (!zero) {
                //add zeros from last measurement until the endDay
                while (i >= end && index <= 7) {
                    weekArray[index] = (NaN);
                    i--;
                    index++;
                }
            } else {
                //add zeroes from last measurement until zero
                while (i >= 0 && index <= 7) {
                    weekArray[index] = (NaN);
                    i--;
                    index++;
                }
                //set the index to 7 and then add zeros from 7 until the endDay
                i = 31;
                while (i >= end && index <= 7) {
                    weekArray[index] = (NaN);
                    i--;
                    index++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weekArray;
    }

    /**
     * getMonth
     * Gets all measurements of last week and puts them in an ArrayList
     *
     * @param value value obtained from the database
     * @return ArrayList full with measurements of last month
     */

    protected double[] getMonth(String value) {
        double[] monthArray = new double[32];
        try {
            //Selecting the measurements from last month, showing a measurement per day
            String sql = "SELECT CAST(timeAndDate AS Date) as Date,\n" +
                    "extract(day FROM timeAndDate) as day,\n" +
                    "AVG(temperature) as temperature,\n" +
                    "AVG(light) as light,\n" +
                    "AVG(pressure) as pressure,\n" +
                    "AVG(humidity) as humidity\n" +
                    "FROM measurements\n" +
                    "WHERE timeAndDate > (NOW() - INTERVAL 30 day)\n" +
                    "GROUP BY CAST(timeAndDate AS Date),\n" +
                    "extract(day FROM timeAndDate)\n" +
                    "ORDER By timeAndDate desc ;";
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            //check for a measurement
            if (!result.next()) {
                for (int i = 0; i <= 30; i++) {
                    monthArray[i] = (NaN);
                }
                return monthArray;
            }
            //beginDay
            result.next();
            int i = result.getInt("day");
            //endDay
            int end = i;
            boolean zero = false;
            int index = 0;
            //go back so it doesn't skip this measurement in the while loop
            result.previous();
            while (result.next()) {
                //Check if zero has been reached, if so go back to 30 days
                if (i == 0) {
                    if(!zero) {
                        i = 31;
                        zero = true;
                    }
                };
                //Check if there is a measurement on that day
                if (i == result.getInt("day")) {
                    monthArray[index] = (result.getDouble(value));
                } else {
                    //if not then add 0 to that day
                    monthArray[index] = (NaN);
                    //no values have been added so stay at the same value
                    result.previous();
                }
                i--;
                index++;
            }
            if (zero) {
                //add zeros from last measurement until the endDay
                while (i >= end) {
                    monthArray[index] = (NaN);
                    i--;
                    index++;
                }
            } else {
                //add zeroes from last measurement until zero
                while (i >= 0) {
                    monthArray[index] = (NaN);
                    i--;
                    index++;
                }
                //set the index to 30 and then add zeros from 30 until the endDay
                i = 30;
                while (i >= end) {
                    monthArray[index] = (NaN);
                    i--;
                    index++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthArray;
    }

    protected String getBeginDate() {
        return beginDate;
    }

    protected String getEndDate() {
        return endDate;
    }

    protected void custom() {
        PopupController pc = new PopupController();
        pc.display();
        beginDate = pc.getBeginDate();
        endDate = pc.getEndDate();

    }

    protected int getBeginDay() {
        return beginDay;
    }

    protected ArrayList<Double> getCustom(String value) {
        ArrayList<Double> customArray = new ArrayList<>();
        String beginDay = getBeginDate().substring(8, 10);
        String endDay = getEndDate().substring(8, 10);
        this.beginDay = Integer.parseInt(beginDay);

        try {

            String sql = "SELECT CAST(timeAndDate AS Date) as Date,\n" +
                    "extract(day FROM timeAndDate) as day,\n" +
                    "AVG(temperature) as temperature,\n" +
                    "AVG(light) as light,\n" +
                    "AVG(pressure) as pressure,\n" +
                    "AVG(humidity) as humidity\n" +
                    "FROM measurements\n" +
                    "WHERE (timeAndDate >= '" + beginDate + "') AND (timeAndDate <= '" + endDate + "')\n" +
                    "GROUP BY CAST(TimeAndDate AS Date) ,\n" +
                    "extract(day FROM timeAndDate);";

            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            //check for the last measurement
            if (getBeginDate().charAt(0) != 'n' && getEndDate().charAt(0) != 'n') {
                result.next();
                //beginDay
                int i = Integer.parseInt(beginDay);
                //endDay
                int end = Integer.parseInt(endDay);
                int zero = 0;
                //go back so it doesn't skip this measurement in the while loop
                result.previous();

                while (result.next()) {
                    if (i == 30) {
                        i = 0;
                        zero += 1;
                    }
                    if (i == result.getInt("day")) {
                        customArray.add(result.getDouble(value));
                    } else {
                        customArray.add(NaN);
                        result.previous();
                    }
                    i++;

                }
                while (zero > 0) {
                    while (i <= 0) {
                        customArray.add(NaN);
                        i++;
                    }
                    zero--;
                }
                i = 0;
                while (i <= end) {
                    customArray.add(NaN);
                    i++;
                }
            } else {
                return customArray;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customArray;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * getLight
     * getter for the light
     *
     * @return returns the last measurement humidity
     */
    protected double getLight() {
        double light = 0;
        statement = null;

        try {
            String sql = "SELECT light from measurements order by timeAndDate desc limit 1;";
            statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                light = result.getDouble("light");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return light;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pressure;
    }

}
