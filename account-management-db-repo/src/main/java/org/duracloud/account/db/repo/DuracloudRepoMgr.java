/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.repo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author Erik Paulsson
 * Date: 7/17/13
 */
@Component(value = "repoMgr")
public class DuracloudRepoMgr {

    @Autowired
    private DuracloudUserRepo userRepo;

    @Autowired
    private DuracloudGroupRepo groupRepo;

    @Autowired
    private DuracloudAccountRepo accountRepo;

    @Autowired
    private DuracloudRightsRepo rightsRepo;

    @Autowired
    private DuracloudUserInvitationRepo userInvitationRepo;

    @Autowired
    private DuracloudStorageProviderAccountRepo storageProviderAccountRepo;

    @Autowired
    private DuracloudMillRepo duracloudMillRepo;

    public DuracloudUserRepo getUserRepo() {
        return userRepo;
    }

    public DuracloudGroupRepo getGroupRepo() {
        return groupRepo;
    }

    public DuracloudAccountRepo getAccountRepo() {
        return accountRepo;
    }

    public DuracloudRightsRepo getRightsRepo() {
        return rightsRepo;
    }

    public DuracloudUserInvitationRepo getUserInvitationRepo() {
        return userInvitationRepo;
    }

    public DuracloudStorageProviderAccountRepo getStorageProviderAccountRepo() {
        return storageProviderAccountRepo;
    }

    public Set<JpaRepository> getAllRepos() {
        Set<JpaRepository> repos = new HashSet<>();
        repos.add(userRepo);
        repos.add(groupRepo);
        repos.add(accountRepo);
        repos.add(rightsRepo);
        repos.add(userInvitationRepo);
        repos.add(storageProviderAccountRepo);
        repos.add(duracloudMillRepo);
        return repos;
    }

    public DuracloudMillRepo getDuracloudMillRepo() {
        return duracloudMillRepo;
    }

}
