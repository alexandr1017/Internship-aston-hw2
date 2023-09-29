package com.alexandr1017.edtechschool.util.utilDb;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataSource {

    private static final String URL_KEY = "db.url";
    public static final String USER_KEY = "db.user";
    public static final String PASS_KEY = "db.pass";

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASS_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}