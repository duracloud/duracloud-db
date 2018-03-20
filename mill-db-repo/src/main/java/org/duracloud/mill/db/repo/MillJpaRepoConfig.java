/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.mill.db.repo;

import java.text.MessageFormat;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.duracloud.common.db.jpa.JpaConfigurationUtil;
import org.duracloud.mill.manifest.ManifestStore;
import org.duracloud.mill.manifest.jpa.JpaManifestStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Daniel Bernstein
 */
@Configuration
@EnableJpaRepositories(basePackages = {"org.duracloud.mill"},
                       entityManagerFactoryRef = MillJpaRepoConfig.ENTITY_MANAGER_FACTORY_BEAN,
                       transactionManagerRef = MillJpaRepoConfig.TRANSACTION_MANAGER_BEAN)
@EnableTransactionManagement
public class MillJpaRepoConfig {
    private static final String MILL_REPO_ENTITY_MANAGER_FACTORY_BEAN =
        "millRepoEntityManagerFactory";
    public static final String MILL_REPO_DATA_SOURCE_BEAN =
        "millRepoDataSource";
    public static final String TRANSACTION_MANAGER_BEAN =
        "millJpaRepoTransactionManager";
    public static final String ENTITY_MANAGER_FACTORY_BEAN =
        MILL_REPO_ENTITY_MANAGER_FACTORY_BEAN;

    @Autowired
    private Environment env;

    @Bean(name = MILL_REPO_DATA_SOURCE_BEAN, destroyMethod = "close")
    public BasicDataSource millRepoDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(MessageFormat.format("jdbc:mysql://{0}:{1}/{2}" +
                                               "?useLegacyDatetimeCode=false" +
                                               "&serverTimezone=GMT" +
                                               "&characterEncoding=utf8" +
                                               "&characxterSetResults=utf8",
                                               env.getProperty("mill.db.host", "localhost"),
                                               env.getProperty("mill.db.port", "3306"),
                                               env.getProperty("mill.db.name", "mill")));
        dataSource.setUsername(env.getProperty("mill.db.user", "user"));
        dataSource.setPassword(env.getProperty("mill.db.pass", "pass"));
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");

        return dataSource;
    }

    @Bean(name = TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager millRepoTransactionManager(
        @Qualifier(MILL_REPO_ENTITY_MANAGER_FACTORY_BEAN) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager tm =
            new JpaTransactionManager(entityManagerFactory);
        tm.setJpaDialect(new HibernateJpaDialect());
        return tm;
    }

    @Bean(name = MILL_REPO_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean millRepoEntityManagerFactory(
        @Qualifier(MILL_REPO_DATA_SOURCE_BEAN) DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf =
            new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPersistenceUnitName("mill-repo-pu");
        emf.setPackagesToScan("org.duracloud.mill");

        JpaConfigurationUtil.configureEntityManagerFactory(env, emf);
        if (Boolean.parseBoolean(env.getProperty("generate.database", "false"))) {
            Properties properties = new Properties();
            properties.setProperty("javax.persistence.schema-generation.database.action", "create");
            emf.setJpaProperties(properties);
        }
        return emf;
    }

    @Bean
    public ManifestStore manifestStore(JpaManifestItemRepo manifestRepo) {
        return new JpaManifestStore(manifestRepo);
    }
}
