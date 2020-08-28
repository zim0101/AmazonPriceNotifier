package com.amazon_price_notifier.database;

import java.sql.*;
import java.util.Map;

/**
 * This class can be used for connecting different databases using the
 * DBConfig class static methods. The main purpose of this class is to return
 * database connection according to the given configuration.
 * Example usage:
 *
 * Connection connection = new DBConnector(DBConfig.primary()).connections();
 */
public class DBConnector {

    /**
     * Database source. for example: mysql
     */
    protected final String databaseSource;

    /**
     * Database host. for example: localhost
     */
    protected final String host;

    /**
     * Database port. for example: 3306
     */
    protected final String port;

    /**
     * Database name
     */
    protected final String databaseName;

    /**
     * Final DB connection url
     */
    protected final String url;

    /**
     * Database username
     */
    protected final String username;

    /**
     * Database password
     */
    protected final String password;

    /**
     * DBConnector constructor
     *
     * @param databaseConfig Map of database configuration
     */
    public DBConnector(Map<String, String> databaseConfig) {
        this.databaseSource = databaseConfig.get("source");
        this.host = databaseConfig.get("host");
        this.port = databaseConfig.get("port");
        this.databaseName = databaseConfig.get("database");
        this.username = databaseConfig.get("username");
        this.password = databaseConfig.get("password");
        this.url = "jdbc:"+databaseSource+"://"+host+":"+port+"/"+databaseName;
    }

    /**
     * Build and return connection with the given configuration. That's mean
     * this method can be used for connecting different databases.
     *
     * @return Connection object
     * @throws SQLException SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
