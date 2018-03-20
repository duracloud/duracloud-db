/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.common.db.jpa;

import java.util.Properties;

import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author Daniel Bernstein
 */
public class JpaConfigurationUtil {

    private JpaConfigurationUtil() {
        // Prevents instantiation, as only static methods are available
    }

    public static void configureEntityManagerFactory(Environment env, LocalContainerEntityManagerFactoryBean emf) {
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        String hbm2ddlAuto = env.getProperty("hibernate.hbm2ddl.auto", "none");
        String showSql = env.getProperty("hibernate.show_sql", "false");
        va.setGenerateDdl(!"none".equals(hbm2ddlAuto));
        va.setDatabase(Database.MYSQL);
        emf.setJpaVendorAdapter(va);
        Properties props = new Properties();
        if (!hbm2ddlAuto.equals("none")) {
            props.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        }

        props.setProperty("hibernate.dialect",
                          "org.hibernate.dialect.MySQL5InnoDBDialect");
        props.setProperty("hibernate.physical_naming_strategy",
                          "org.duracloud.common.db.hibernate.PhysicalNamingStrategyImpl");
        props.setProperty("hibernate.implicit_naming_strategy",
                          "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyHbmImpl");
        props.setProperty("hibernate.cache.provider_class",
                          "org.hibernate.cache.HashtableCacheProvider");
        props.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        props.setProperty("hibernate.id.new_generator_mappings", "false");
        props.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
        props.setProperty("jadira.usertype.databaseZone", "jvm");
        props.setProperty("hibernate.show_sql", showSql);
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.show_comments", "false");
        emf.setJpaProperties(props);
    }
}
