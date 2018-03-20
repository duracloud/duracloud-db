/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.repo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.duracloud.account.db.model.AccountInfo;
import org.duracloud.account.db.model.AccountRights;
import org.duracloud.account.db.model.DuracloudGroup;
import org.duracloud.account.db.model.DuracloudUser;
import org.duracloud.account.db.model.Role;
import org.duracloud.security.domain.SecurityUserBean;

/**
 * @author: Bill Branan Date: 2/17/12
 */
public class UserFinderUtil {

    private DuracloudRepoMgr repoMgr;

    public UserFinderUtil(DuracloudRepoMgr repoMgr) {
        this.repoMgr = repoMgr;
    }

    /**
     * Retrieves the users associated with the account
     *
     * @param account for which users should be gathered
     * @return the set of users associated with an account
     */
    public Set<DuracloudUser> getAccountUsers(AccountInfo account) {

        DuracloudRightsRepo rightsRepo = repoMgr.getRightsRepo();
        List<AccountRights> acctRights =
            rightsRepo.findByAccountId(account.getId());

        Set<DuracloudUser> users = new HashSet<>();
        for (AccountRights rights : acctRights) {
            DuracloudUser user = rights.getUser();
            //ensure account is loaded.
            rights.getAccount();
            // make sure only the rights for this account are set
            Set<AccountRights> accountOnlyRightsSet = new HashSet<>();
            accountOnlyRightsSet.add(rights);
            user.setAccountRights(accountOnlyRightsSet);
            users.add(user);
        }

        List<DuracloudUser> rootUsers =
            repoMgr.getUserRepo()
                   .findByRootTrueAndEnabledTrueAndAccountNonExpiredTrueAndCredentialsNonExpiredTrueAndAccountNonLockedTrue();

        users.addAll(rootUsers);
        return users;
    }

    public Set<SecurityUserBean> convertDuracloudUsersToSecurityUserBeans(AccountInfo accountInfo,
                                                                          Set<DuracloudUser> users,
                                                                          boolean includeRootUsers) {
        // collect groups for the account
        Long accountId = accountInfo.getId();
        DuracloudGroupRepo groupRepo = repoMgr.getGroupRepo();
        Set<DuracloudGroup> groups = new HashSet<DuracloudGroup>();
        groups.addAll(groupRepo.findByAccountId(accountId));

        // collect user roles for this account
        Set<SecurityUserBean> userBeans = new HashSet<SecurityUserBean>();
        for (DuracloudUser user : users) {
            String username = user.getUsername();
            String password = user.getPassword();
            String email = user.getEmail();
            String ipLimits = annotateAddressRange(accountInfo, user.getAllowableIPAddressRange());
            Set<Role> roles = user.getRolesByAcct(accountId);

            if (roles == null) {
                roles = new HashSet<Role>();
            }

            if (roles.isEmpty()) {
                roles.add(Role.ROLE_USER);
            }

            List<String> grants = new ArrayList<String>();
            for (Role role : roles) {
                grants.add(role.name());
            }

            if (!user.isRoot() || includeRootUsers) {
                SecurityUserBean bean =
                    new SecurityUserBean(username, password, grants);
                bean.setEmail(email);
                bean.setIpLimits(ipLimits);

                if (groups != null) {
                    for (DuracloudGroup group : groups) {
                        Set<DuracloudUser> grpUsers = group.getUsers();
                        if (grpUsers.contains(user)) {
                            bean.addGroup(group.getName());
                        }
                    }
                }

                userBeans.add(bean);
            }
        }
        return userBeans;
    }

    /**
     * For a user account with an IP limitation, this method is used to update
     * the list of allowed IPs to include the IP of the DuraCloud instance itself.
     * This is required to allow the calls made between applications (like those
     * made from DurAdmin to DuraStore) to pass through the IP range check.
     *
     * @param baseRange set of IP ranges set by the user
     * @return baseRange plus the instance elastic IP, or null if baseRange is null
     */
    private String annotateAddressRange(AccountInfo accountInfo, String baseRange) {
        if (null == baseRange || baseRange.equals("")) {
            return baseRange;
        } else {
            return baseRange; // delimeter + elasticIp + "/32";
        }
    }

}
