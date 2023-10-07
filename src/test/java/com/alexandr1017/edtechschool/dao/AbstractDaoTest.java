package com.alexandr1017.edtechschool.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.startupcheck.IndefiniteWaitOneShotStartupCheckStrategy;
import org.testcontainers.containers.startupcheck.MinimumDurationRunningStartupCheckStrategy;
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.time.Duration;

@Testcontainers
public abstract class AbstractDaoTest {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;


    public static HikariDataSource getDataSource() {
        return ds;
    }

    @Container
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>()
            .withCopyFileToContainer(MountableFile.forClasspathResource("sql/createScheama.sql"), "/docker-entrypoint-initdb.d/");

    @BeforeAll
    public static void setUp() {
        config.setJdbcUrl(mysqlContainer.getJdbcUrl());
        config.setUsername(mysqlContainer.getUsername());
        config.setPassword(mysqlContainer.getPassword());
        ds = new HikariDataSource(config);
        mysqlContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        getDataSource().close();
        mysqlContainer.stop();
    }
}