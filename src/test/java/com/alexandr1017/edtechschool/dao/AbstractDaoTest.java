package com.alexandr1017.edtechschool.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@Testcontainers
public abstract class AbstractDaoTest {
    @Container
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>()
            .withCopyFileToContainer(MountableFile.forClasspathResource("sql/createScheama.sql"), "/docker-entrypoint-initdb.d/");

    @BeforeAll
    public static void setUp() {
        mysqlContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mysqlContainer.stop();
    }
}