package com.alexandr1017.edtechschool.util.utilDb;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DataSource {
    private static final String URL_KEY = "db.url";
    public static final String USER_KEY = "db.user";
    public static final String PASS_KEY = "db.pass";
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        try {
            config.setJdbcUrl(PropertiesUtil.get(URL_KEY));
            config.setUsername(PropertiesUtil.get(USER_KEY));
            config.setPassword(PropertiesUtil.get(PASS_KEY));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setMinimumIdle(0);
            config.setIdleTimeout(28000*1000);
            config.setMaxLifetime(28000*1000);

            ds = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DataSource() {
    }

    public static HikariDataSource getDataSource() {
        return ds;
    }

    public static void setJdbcUrl(String url) {
        config.setJdbcUrl(url);
    }

    public static void setUsername(String username) {
        config.setUsername(username);
    }

    public static void setPassword(String password) {
        config.setPassword(password);
    }
}
