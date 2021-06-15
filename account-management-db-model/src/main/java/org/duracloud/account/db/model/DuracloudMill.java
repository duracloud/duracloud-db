/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Contains basic configuration information for interacting with the Duracloud Mill.
 *
 * @author Daniel Bernstein
 * Date: 05/06/2015
 */
@Entity
public class DuracloudMill extends BaseEntity {
    @Column(nullable = false)
    private String dbName;
    @Column(nullable = false)
    private String dbHost;
    @Column(nullable = false)
    private Integer dbPort;
    @Column(nullable = false)
    private String dbUsername;
    @Column(nullable = false)
    private String dbPassword;
    @Column(nullable = false)
    private String auditQueue;
    @Column(nullable = false)
    private String auditLogSpaceId;
    @Column(nullable = false)
    private String queueType;
    @Column(nullable = true)
    private String rabbitmqExchange;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "rabbitmq_config_id", nullable = true)
    private RabbitmqConfig rabbitmqConfig;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getAuditQueue() {
        return auditQueue;
    }

    public void setAuditQueue(String auditQueue) {
        this.auditQueue = auditQueue;
    }

    public String getAuditLogSpaceId() {
        return auditLogSpaceId;
    }

    public void setAuditLogSpaceId(String auditLogSpaceId) {
        this.auditLogSpaceId = auditLogSpaceId;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    // rabbitmqExchange will always differ for both
    // GlobalProperties/DuracloudMill config, so it makes
    // sense to keep this here, rather than in RabbitmqConfig
    public String getRabbitmqExchange() {
        return rabbitmqExchange;
    }

    public void setRabbitmqExchange(String rabbitmqExchange) {
        this.rabbitmqExchange = rabbitmqExchange;
    }

    public RabbitmqConfig getRabbitmqConfig() {
        return rabbitmqConfig;
    }

    public void setRabbitmqConfig(RabbitmqConfig rabbitmqConfig) {
        this.rabbitmqConfig = rabbitmqConfig;
    }
}
