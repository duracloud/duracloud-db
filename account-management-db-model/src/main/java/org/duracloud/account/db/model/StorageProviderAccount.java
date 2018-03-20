/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.account.db.model;

import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;

import org.duracloud.storage.domain.StorageProviderType;

/**
 * @author Erik Paulsson
 * Date: 7/10/13
 */
@Entity
public class StorageProviderAccount extends ProviderAccount {

    /**
     * The type of storage provider - meaning the organization acting as the
     * provider of storage services.
     */
    @Enumerated(EnumType.STRING)
    private StorageProviderType providerType;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "map_key")
    @CollectionTable(name = "storage_provider_account_properties",
                     joinColumns = @JoinColumn(name = "storage_provider_account_id"))
    @Column(name = "map_value")
    private Map<String, String> properties;

    /**
     * The max GBs of storage allowable for the account.
     */
    private int storageLimit = 1;

    public StorageProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(StorageProviderType providerType) {
        this.providerType = providerType;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public int getStorageLimit() {
        return storageLimit;
    }

    public void setStorageLimit(int storageLimit) {
        this.storageLimit = storageLimit;
    }

}
