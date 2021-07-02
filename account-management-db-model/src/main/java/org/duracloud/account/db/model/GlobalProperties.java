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
 * A grab bag of global properties.
 *
 * @author Daniel Bernstein
 * Date: 01/05/2016
 */
@Entity
public class GlobalProperties extends BaseEntity {
    /*
     * an SNS topic arn for sending noticiations to the instances
     */
    @Column(nullable = true)
    private String instanceNotificationTopicArn;

    @Column(nullable = false)
    private String cloudFrontAccountId;

    @Column(nullable = false)
    private String cloudFrontKeyId;

    @Column(nullable = false)
    private String cloudFrontKeyPath;

    @Column(nullable = false)
    private String notifierType;

    @Column(nullable = true)
    private String rabbitmqExchange;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "rabbitmq_config_id", nullable = true)
    private RabbitmqConfig rabbitmqConfig;

    public String getInstanceNotificationTopicArn() {
        return instanceNotificationTopicArn;
    }

    public void setInstanceNotificationTopicArn(String instanceNotificationTopicArn) {
        this.instanceNotificationTopicArn = instanceNotificationTopicArn;
    }

    public String getCloudFrontAccountId() {
        return cloudFrontAccountId;
    }

    public void setCloudFrontAccountId(String cloudFrontAccountId) {
        this.cloudFrontAccountId = cloudFrontAccountId;
    }

    public String getCloudFrontKeyId() {
        return cloudFrontKeyId;
    }

    public void setCloudFrontKeyId(String cloudFrontKeyId) {
        this.cloudFrontKeyId = cloudFrontKeyId;
    }

    public String getCloudFrontKeyPath() {
        return cloudFrontKeyPath;
    }

    public void setCloudFrontKeyPath(String cloudFrontKeyPath) {
        this.cloudFrontKeyPath = cloudFrontKeyPath;
    }

    public String getNotifierType() {
        return notifierType;
    }

    public void setNotifierType(String notifierType) {
        this.notifierType = notifierType;
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
