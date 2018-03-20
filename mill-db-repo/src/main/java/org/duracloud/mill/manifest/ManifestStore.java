/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.mill.manifest;

import java.util.Date;
import java.util.Iterator;

import org.duracloud.common.db.error.NotFoundException;
import org.duracloud.mill.db.model.ManifestItem;

/**
 * @author Daniel Bernstein
 * Date: Sep 2, 2014
 */
public interface ManifestStore {

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @param contentId
     * @param contentChecksum
     * @param contentSize
     * @param contentMimetype
     * @param timeStamp
     * @return true if the store was updated;  false if it was not (due to the update being out of order).
     * @throws ManifestItemWriteException
     */
    public boolean addUpdate(String account,
                             String storeId,
                             String spaceId,
                             String contentId,
                             String contentChecksum,
                             String contentMimetype,
                             String contentSize,
                             Date timeStamp) throws ManifestItemWriteException;

    /**
     * @param storeId
     * @param spaceId
     * @return
     */
    public Iterator<ManifestItem> getItems(String account,
                                           String storeId,
                                           String spaceId);

    /**
     * Provides a method or ordering the results
     *
     * @param account
     * @param storeId
     * @param spaceId
     * @param ordered
     * @return
     */
    public Iterator<ManifestItem> getItems(String account,
                                           String storeId,
                                           String spaceId,
                                           boolean ordered);

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @param contentId
     * @return
     * @throws NotFoundException
     */
    public ManifestItem getItem(String account,
                                String storeId,
                                String spaceId,
                                String contentId) throws NotFoundException;

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @param contentId
     * @param eventTimestamp
     * @return true if the store was updated;  false if it was not (due to the update being out of order).
     * @throws ManifestItemWriteException
     */
    public boolean flagAsDeleted(String account,
                                 String storeId,
                                 String spaceId,
                                 String contentId,
                                 Date eventTimestamp) throws ManifestItemWriteException;

    /**
     * @param expiration
     * @return Count of items deleted.
     */
    public int purgeDeletedItemsBefore(Date expiration);

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @param contentId
     * @param flag
     * @throws ManifestItemWriteException
     */
    public void updateMissingFromStorageProviderFlag(String account,
                                                     String storeId,
                                                     String spaceId,
                                                     String contentId,
                                                     boolean flag) throws ManifestItemWriteException;

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @throws ManifestItemWriteException
     */
    public void delete(String account, String storeId, String spaceId)
        throws ManifestItemWriteException;
}
