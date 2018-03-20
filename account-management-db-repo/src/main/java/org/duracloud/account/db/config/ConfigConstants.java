/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.config;

/**
 * @author Daniel Bernstein
 */
public class ConfigConstants {

    private ConfigConstants() {
        // Ensures class is not instantiated, as only static values exist
    }

    public static final String MC_DB_NAME = "db.name";
    public static final String MC_DB_HOST = "db.host";
    public static final String MC_DB_PORT = "db.port";
    public static final String MC_DB_PASS = "db.pass";
    public static final String MC_DB_USER = "db.user";
}
