/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A class of RabbitMQ configuration parameters.
 *
 * @author Andy Foster
 * Date: 2021/05/06
 */
@Entity
@Table(name = "rabbitmq_config")
public class RabbitmqConfig {

    // We do not extend BaseEntity, because we
    // don't want auto-incrementing IDs.
    @Id
    @Column(nullable = false)
    protected Long id;

    @Column(nullable = false)
    private String host;
    @Column(nullable = false)
    private Integer port;
    @Column(nullable = false)
    private String vhost;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
