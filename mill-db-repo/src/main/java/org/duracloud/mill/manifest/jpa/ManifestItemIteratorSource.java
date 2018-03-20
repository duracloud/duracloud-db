/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.mill.manifest.jpa;

import java.util.Collection;
import java.util.List;

import org.duracloud.common.collection.IteratorSource;
import org.duracloud.mill.db.model.ManifestItem;
import org.duracloud.mill.db.repo.JpaManifestItemRepo;
import org.springframework.util.CollectionUtils;

/**
 * @author Daniel Bernstein
 */
public class ManifestItemIteratorSource implements IteratorSource<ManifestItem> {

    private JpaManifestItemRepo repo;

    private Long markerId = null;
    private String account;
    private String spaceId;
    private String storeId;
    private int pageSize;

    public ManifestItemIteratorSource(JpaManifestItemRepo repo, String account, String storeId, String spaceId,
                                      int pageSize) {
        this.repo = repo;
        this.account = account;
        this.storeId = storeId;
        this.spaceId = spaceId;
        this.pageSize = pageSize;
    }

    @Override
    public Collection<ManifestItem> getNext() {
        if (markerId == null) {
            markerId = this.repo.getMinId(this.account, this.storeId, this.spaceId) - 1;
        }

        assert markerId != null;

        List<ManifestItem> items = this.repo.findByAccountAndStoreIdAndSpaceIdAndDeletedFalse(account, storeId, spaceId,
                                                                                              markerId, pageSize);

        if (!CollectionUtils.isEmpty(items)) {
            this.markerId = items.get(items.size() - 1).getId();
            return items;
        } else {
            return null;
        }
    }
}


