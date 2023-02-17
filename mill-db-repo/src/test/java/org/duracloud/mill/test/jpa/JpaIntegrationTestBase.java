/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */

package org.duracloud.mill.test.jpa;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * @author Daniel Bernstein
 * Date: June 8, 2017
 */
@RunWith(EasyMockRunner.class)
public abstract class JpaIntegrationTestBase extends EasyMockSupport {

    @ClassRule
    public static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:5.7-debian"))
        .withUsername("user")
        .withPassword("pass")
        .withDatabaseName("mill")
        .withInitScript("db_init.sql")
        .withEnv("TZ", "GMT")
        .withEnv("max_connect_errors", "666");

    protected static AnnotationConfigApplicationContext context;

    @BeforeClass
    public static void setup() {
        System.setProperty("generate.database", "true");
        System.setProperty("mill.db.port", mysql.getFirstMappedPort().toString());
        System.setProperty("mill.db.host", mysql.getHost());
        System.setProperty("mill.db.user", mysql.getUsername());
        System.setProperty("mill.db.pass", mysql.getPassword());

        context = new AnnotationConfigApplicationContext("org.duracloud.mill");
    }

}
