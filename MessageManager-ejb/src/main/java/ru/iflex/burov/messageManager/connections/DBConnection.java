package ru.iflex.burov.messageManager.connections;

import ru.iflex.burov.config.MyConfigHelper;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance = new DBConnection();
    private static DataSource dataSource;
    private static Connection connection;

    private static String dataSourceName;

    private DBConnection() {
        dataSourceName = MyConfigHelper.getInstance().getConfigurations().getDataSourceName();
        try {
            InitialContext context = new InitialContext();
            System.out.println();
            System.out.println();
            System.out.println("------------------------------------------------------------------");
            System.out.println("dataSource: " + dataSourceName);
            System.out.println("------------------------------------------------------------------");
            System.out.println();
            System.out.println();
            dataSource = (DataSource) context.lookup(dataSourceName);
            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection == null) {
            try {
                InitialContext context = new InitialContext();
                DataSource dataSource = (DataSource) context.lookup("MM_DS");
                try {
                    connection = dataSource.getConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}