/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.config;

import java.text.MessageFormat;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.duracloud.common.db.jpa.JpaConfigurationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(basePackages = {"org.duracloud.account.db"},
                       entityManagerFactoryRef = AccountJpaRepoConfig.ENTITY_MANAGER_FACTORY_BEAN,
                       transactionManagerRef = AccountJpaRepoConfig.TRANSACTION_MANAGER_BEAN)
@EnableTransactionManagement
public class AccountJpaRepoConfig {
    private static final String ACCOUNT_REPO_ENTITY_MANAGER_FACTORY_BEAN =
        "accountRepoEntityManagerFactory";
    public static final String ACCOUNT_REPO_DATA_SOURCE_BEAN =
        "accountRepoDataSource";
    public static final String TRANSACTION_MANAGER_BEAN =
        "accountJpaRepoTransactionManager";
    public static final String ENTITY_MANAGER_FACTORY_BEAN =
        ACCOUNT_REPO_ENTITY_MANAGER_FACTORY_BEAN;

    @Autowired
    private Environment env;

    @Bean(name = ACCOUNT_REPO_DATA_SOURCE_BEAN, destroyMethod = "close")
    public BasicDataSource accountRepoDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(MessageFormat.format("jdbc:mysql://{0}:{1}/{2}" +
                                               "?characterEncoding=utf8" +
                                               "&characxterSetResults=utf8",
                                               env.getProperty(ConfigConstants.MC_DB_HOST, "localhost"),
                                               env.getProperty(ConfigConstants.MC_DB_PORT, "3306"),
                                               env.getProperty(ConfigConstants.MC_DB_NAME, "name")));
        dataSource.setUsername(env.getProperty(ConfigConstants.MC_DB_USER, "user"));
        dataSource.setPassword(env.getProperty(ConfigConstants.MC_DB_PASS, "pass"));
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

    @Primary
    @Bean(name = TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager accountRepoTransactionManager(
        @Qualifier(ACCOUNT_REPO_ENTITY_MANAGER_FACTORY_BEAN) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager tm =
            new JpaTransactionManager(entityManagerFactory);
        tm.setJpaDialect(new HibernateJpaDialect());
        return tm;
    }

    @Bean(name = ACCOUNT_REPO_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean accountRepoEntityManagerFactory(
        @Qualifier(ACCOUNT_REPO_DATA_SOURCE_BEAN) DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf =
            new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPersistenceUnitName("account-repo-pu");
        emf.setPackagesToScan("org.duracloud.account.db");

        JpaConfigurationUtil.configureEntityManagerFactory(env, emf);
        return emf;
    }

}
