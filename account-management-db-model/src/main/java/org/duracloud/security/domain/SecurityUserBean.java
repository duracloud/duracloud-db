/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.security.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Andrew Woods
 * Date: Mar 28, 2010
 */
public class SecurityUserBean {
    private String username;
    private String password;
    private String email;
    private String ipLimits;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private List<String> grantedAuthorities;
    private List<String> groups;

    public static final String SCHEMA_VERSION = "1.3";

    public SecurityUserBean() {
        this("unknown",
             "unknown",
             "",
             "",
             false,
             false,
             false,
             false,
             new ArrayList<>(),
             new ArrayList<>());
    }

    public SecurityUserBean(String username,
                            String password,
                            List<String> grantedAuthorities) {
        this(username,
             password,
             "",
             "",
             true,
             true,
             true,
             true,
             grantedAuthorities,
             new ArrayList<>());
    }

    public SecurityUserBean(String username,
                            String password,
                            String email,
                            String ipLimits,
                            boolean enabled,
                            boolean accountNonExpired,
                            boolean credentialsNonExpired,
                            boolean accountNonLocked,
                            List<String> grantedAuthorities,
                            List<String> groups) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.ipLimits = ipLimits;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.grantedAuthorities = grantedAuthorities;
        this.groups = groups;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getIpLimits() {
        return ipLimits;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public List<String> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setUsername(String username) {
        if (!StringUtils.isBlank(username)) {
            this.username = username;
        }
    }

    public void setPassword(String password) {
        if (!StringUtils.isBlank(password)) {
            this.password = password;
        }
    }

    public void setEmail(String email) {
        if (!StringUtils.isBlank(email)) {
            this.email = email;
        }
    }

    public void setIpLimits(String ipLimits) {
        if (!StringUtils.isBlank(ipLimits)) {
            this.ipLimits = ipLimits;
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setGrantedAuthorities(List<String> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public void addGrantedAuthority(String grantedAuthority) {
        if (null == this.grantedAuthorities) {
            this.grantedAuthorities = new ArrayList<String>();
        }
        this.grantedAuthorities.add(grantedAuthority);
    }

    public String getTopAuthorityDisplay() {
        List<String> authrorities = getGrantedAuthorities();
        if (authrorities.contains("ROLE_OWNER")) {
            return "Owner";
        } else if (authrorities.contains("ROLE_ADMIN")) {
            return "Administrator";
        } else {
            return "User";
        }
    }

    public void addGroup(String group) {
        if (null == this.groups) {
            groups = new ArrayList<>();
        }
        groups.add(group);
    }

    public List<String> getGroups() {
        return groups;
    }
}
