import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/** name : Db Manager class
 *  use :  Used for connecting to the database.
 *  remarks : The connection is done on local host
 *
 */

//TODO connect the database to cloud


public class DBmanager {

    private static DBmanager uniqueInstance = null;
    private static Connection connection = null;
    static final String DB_URL = "jdbc:mysql://localhost/EMPLOYEES?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // database url
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";       // driver

    /** DB manager constructor
     *  if the given database doesnt exist, it would print a message,
     *  otherwise, if it connects it prints the corresponding message.
     */
    private DBmanager() {
        if (!dbExists()) {
            System.out.println(" >>> DBManager : the database doesnt exist ");
        } else System.out.println(" >>> connected ");
    }

    /**
     *  The method creates an instance and returns it to the user
     */
    public static synchronized DBmanager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DBmanager();
        }
        return uniqueInstance;
    }

    /** dbExists method
     * Used to make connection to the database
     *
     * @return true or false, depending if the database exists
     */

    private Boolean dbExists() {
        Boolean exists = false;

        Statement statement = null;
        try {
            if (connection == null) makeConnection();
            statement = connection.createStatement();
            statement.executeQuery("USE weatherstation;");
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    exists = true;
                }
            } catch (SQLException se) {
                se.printStackTrace();
                statement = null;
            }
        }
        return exists;
    }

    /** makeConnection method
     *  reads the credentials from the property file and makes a connect statement with properties read.
     */

    public void makeConnection() {
        FileInputStream in = null;
        try {
            Properties props = new Properties();
            in = new FileInputStream("database.properties");
            props.load(in);
            in.close();

            String db_url = props.getProperty("jdbc.db_url");
            String db_params = props.getProperty("jdbc.db_params");
            String username = props.getProperty("jdbc.username","");
            String password = props.getProperty("jdbc.password", "");

            connection = DriverManager.getConnection(db_url + db_params, username, password);
        } catch(SQLException se) {
            System.err.println("Connection error....");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     *  close method
     *  used to close the connection to the database
     */
    public void close(){
        try {
            connection.close();
            uniqueInstance = null;
            connection = null;
        } catch ( SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * getConnection
     * Getter for the connection variable
     */
    public static Connection getConnection() {
        return connection;
    }
}

