package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {



//JDBC URL PARTS
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String dbAddress = "//wgudb.ucertify.com/WJ07LG4";


    private static final String jdbcURL = protocol + vendorName + dbAddress;



    private static  Connection dbConnection;

    private static final String dbUserName = "U07LG4";
    private static final String dbPassword = "53689058692";

    /**
     * startConnection starts database connection where needed
     * @return db connection
     */
    public static Connection startConnection() {

        try {
            dbConnection  = DriverManager.getConnection(jdbcURL, dbUserName, dbPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return dbConnection;

    }
}
