package com.amazon_price_notifier.database;

import java.sql.*;
import java.util.HashMap;

public class DatabaseConnector {

    protected final String databaseSource;

    protected final String host;

    protected final String port;

    protected final String databaseName;

    protected final String url;

    protected final String username;

    protected final String password;

    public DatabaseConnector(HashMap<String, String> databaseConfig) {
        this.databaseSource = databaseConfig.get("source");
        this.host = databaseConfig.get("host");
        this.port = databaseConfig.get("port");
        this.databaseName = databaseConfig.get("database");
        this.username = databaseConfig.get("username");
        this.password = databaseConfig.get("password");
        this.url = "jdbc:"+databaseSource+"://"+host+":"+port+"/"+databaseName;
    }

    public Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
