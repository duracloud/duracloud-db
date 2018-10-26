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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dbernstein
 */
@Entity
public class EmailTemplate extends BaseEntity {

    public enum Templates {
        USER_CREATED("user-created"),
        PASSWORD_RESET("password-reset"),
        INVITATION_REDEEMED("invitation-redeemed"),
        USER_ADDED_TO_ACCOUNT("user-added-to-account");

        private String templateName;

        private Templates(String templateName) {
            this.templateName = templateName;
        }

        public String getTemplateName() {
            return templateName;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(EmailTemplate.class);
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String body;
    @Enumerated(EnumType.STRING)
    private Templates template;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Templates getTemplate() {
        return template;
    }

    public void setTemplate(Templates template) {
        this.template = template;
    }
}
