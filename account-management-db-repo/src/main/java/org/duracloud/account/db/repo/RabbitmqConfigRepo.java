/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.repo;

import org.duracloud.account.db.model.RabbitmqConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Andy Foster
 * Date: 2021/05/06
 */
@Repository(value = "rabbitmqConfigRepo")
public interface RabbitmqConfigRepo extends JpaRepository<RabbitmqConfig, Long> {

}
