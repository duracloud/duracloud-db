/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author Erik Paulsson Date: 7/10/13
 */
@Entity
public class AccountInfo extends BaseEntity implements Comparable<AccountInfo> {
    public enum AccountStatus {
        PENDING, ACTIVE, INACTIVE;
    }

    /*
     * The subdomain of duracloud.org which will be used to access the instance associated with this account
     */
    private String subdomain;

    /*
     * The display name of the account
     */
    private String acctName;

    /*
     * The name of the organization responsible for the content in this account
     */
    private String orgName;

    /*
     * The name of the department (if applicable) of the organization responsible for the content in this account
     */
    private String department;

    /*
     * The current status of this account
     */
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    /**
     * The StorageProviderAccount which is used for primary storage
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "primary_storage_provider_account_id", nullable = false, columnDefinition = "bigint(20)")
    private StorageProviderAccount primaryStorageProviderAccount;

    /**
     * The StorageProviderAccounts which are used for secondary storage
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_info_id", nullable = true, columnDefinition = "bigint(20)")
    private Set<StorageProviderAccount> secondaryStorageProviderAccounts;

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public StorageProviderAccount getPrimaryStorageProviderAccount() {
        return primaryStorageProviderAccount;
    }

    public void setPrimaryStorageProviderAccount(
        StorageProviderAccount primaryStorageProviderAccount) {
        this.primaryStorageProviderAccount = primaryStorageProviderAccount;
    }

    public Set<StorageProviderAccount> getSecondaryStorageProviderAccounts() {
        return secondaryStorageProviderAccounts;
    }

    public void setSecondaryStorageProviderAccounts(
        Set<StorageProviderAccount> secondaryStorageProviderAccounts) {
        this.secondaryStorageProviderAccounts = secondaryStorageProviderAccounts;
    }

    @Override
    public int compareTo(AccountInfo o) {
        return this.acctName.compareTo(o.acctName);
    }
}
