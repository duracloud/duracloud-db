/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.mill.db.repo;

import java.util.Date;
import java.util.List;

import org.duracloud.mill.db.model.ManifestItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Daniel Bernstein
 */
@Repository(value = "manifestItemRepo")
public interface JpaManifestItemRepo extends JpaRepository<ManifestItem, Long> {

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @param pageable
     * @return
     */
    Page<ManifestItem> findByAccountAndStoreIdAndSpaceIdAndDeletedFalseOrderByContentIdAsc(String account,
                                                                                           String storeId,
                                                                                           String spaceId,
                                                                                           Pageable pageable);

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @param lastId  The last id of the previous page. If there was no previous
     *                page, we recommend that you use the minimum id minus 1 in the
     *                unpaged set for optimum performance.
     * @param limit
     * @return
     */
    @Query(nativeQuery = true, value = "select * from manifest_item where account = ?1 and store_id=?2 and "
                                       + "space_id = ?3 and deleted = false and id > ?4 order by id limit ?5")
    List<ManifestItem> findByAccountAndStoreIdAndSpaceIdAndDeletedFalse(String account,
                                                                        String storeId,
                                                                        String spaceId,
                                                                        long lastId,
                                                                        int limit);

    /**
     * Returns the minimum id value for the specified data set.
     *
     * @param account
     * @param storeId
     * @param spaceId
     * @return
     */
    @Query(nativeQuery = true,
           value = "select ifnull(min(id),0) from manifest_item where account = ?1 and store_id=?2 and "
                   + "space_id = ?3 and deleted = false")
    long getMinId(String account, String storeId, String spaceId);

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @param contentId
     * @return
     */
    ManifestItem findByAccountAndStoreIdAndSpaceIdAndContentId(String account,
                                                               String storeId,
                                                               String spaceId,
                                                               String contentId);

    /**
     * @param expiration
     */
    @Modifying
    @Query(nativeQuery = true, value = "delete from manifest_item where deleted = true and modified < ?1 limit 50000")
    int deleteFirst50000ByDeletedTrueAndModifiedBefore(Date expiration);

    void deleteByAccountAndStoreIdAndSpaceId(String account,
                                             String storeId,
                                             String spaceId);

    /**
     * Returns an array with the following values:  [item_count,byte_count]
     *
     * @param account
     * @param storeId
     * @param spaceId
     * @return
     */
    @Query(nativeQuery = true,
           value = "select count(*) objectCount, sum(content_size) as byteCount "
                   + "from manifest_item "
                   + "where account = :account and store_id = :storeId and "
                   + "space_id= :spaceId and deleted=false")
    public Object[] getStorageStatsByAccountAndStoreIdAndSpaceId(@Param("account") String account,
                                                                 @Param("storeId") String storeId,
                                                                 @Param("spaceId") String spaceId);

}
