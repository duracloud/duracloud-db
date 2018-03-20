/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.repo;

import java.util.List;

import org.duracloud.account.db.model.DuracloudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Erik Paulsson
 * Date: 7/3/13
 */
@Repository(value = "userRepo")
public interface DuracloudUserRepo extends JpaRepository<DuracloudUser, Long> {

    /**
     * This method returns a single user with the given username
     *
     * @param username of user
     * @return user
     */
    public DuracloudUser findByUsername(String username);

    /**
     * Returns all enabled, non-locked root users with non-expired credentials.
     *
     * @return
     */
    public List<DuracloudUser>
        findByRootTrueAndEnabledTrueAndAccountNonExpiredTrueAndCredentialsNonExpiredTrueAndAccountNonLockedTrue();
}
