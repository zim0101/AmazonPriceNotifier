package amazon_price_notifier.database;

import java.sql.*;

public class DatabaseConnector {

    protected final String databaseSource = JDBCProperty.get("source");

    protected final String host = JDBCProperty.get("host");

    protected final String port = JDBCProperty.get("port");

    protected final String databaseName = JDBCProperty.get("database");

    protected final String url =
            "jdbc:"+databaseSource+"://"+host+":"+port+"/"+databaseName;

    protected final String username = JDBCProperty.get("username");

    protected final String password = JDBCProperty.get("password");

    public Connection connection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
