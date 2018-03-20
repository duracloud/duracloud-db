/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.mill.db.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Represents a space's byte and object count at a moment in time.
 *
 * @author Daniel Bernstein
 */
@Entity
public class SpaceStats extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String accountId;
    @Column(nullable = false, length = 63)
    private String spaceId;
    @Column(nullable = false, length = 10)
    private String storeId;
    @Column(nullable = false)
    private long byteCount = 0;
    @Column(nullable = false)
    private long objectCount = 0;

    public SpaceStats() {
    }

    public SpaceStats(Date modified, String account, String storeId, String spaceId, long byteCount, long objectCount) {
        setModified(modified);
        setAccountId(account);
        setStoreId(storeId);
        setSpaceId(spaceId);
        setByteCount(byteCount);
        setObjectCount(objectCount);
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getByteCount() {
        return byteCount;
    }

    public void setByteCount(long byteCount) {
        this.byteCount = byteCount;
    }

    public long getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(long objectCount) {
        this.objectCount = objectCount;
    }
}
