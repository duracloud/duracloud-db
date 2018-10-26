/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.repo;

import org.duracloud.account.db.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dbernstein
 * Date: 10/25/2018
 */
@Repository(value = "emailTemplateRepo")
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Long> {

    /**
     * This method returns a single email template instance
     *
     * @param template
     * @return email template
     */
    public EmailTemplate findByTemplate(EmailTemplate.Templates template);

}
