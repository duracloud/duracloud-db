/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */

package org.duracloud.mill.manifest.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.duracloud.common.db.error.NotFoundException;
import org.duracloud.mill.db.model.ManifestItem;
import org.duracloud.mill.manifest.ManifestItemWriteException;
import org.duracloud.mill.manifest.ManifestStore;
import org.duracloud.mill.test.jpa.JpaIntegrationTestBase;
import org.junit.Test;

/**
 * @author Daniel Bernstein Date: June 8, 2017
 */
public class ManifestStoreIntegrationTest extends JpaIntegrationTestBase {

    private String account = "account";

    private String storeId = "store-id";

    private String spaceId = "space-id";

    private String contentId = "content-id";

    private String contentChecksum = "content-checksum";

    private String contentSize = "10001";

    private String contentMimetype = "content-mimetype";

    @Test
    public void testAdd() throws Exception {
        long oneSecondAgo = System.currentTimeMillis() - 1000;
        Date timestamp = new Date(oneSecondAgo);
        ManifestItem item = addAndRetrieveItem(timestamp);
        assertEquals(account, item.getAccount());
        assertEquals(storeId, item.getStoreId());
        assertEquals(spaceId, item.getSpaceId());
        assertEquals(contentId, item.getContentId());
        assertEquals(contentChecksum, item.getContentChecksum());
        assertEquals(contentMimetype, item.getContentMimetype());
        assertEquals(contentSize, item.getContentSize());
        assertEquals(timestamp, item.getModified());
    }

    private ManifestItem addAndRetrieveItem(Date timestamp) throws ManifestItemWriteException, NotFoundException {
        ManifestStore store = getStore();
        assertTrue(store.addUpdate(account, storeId, spaceId, contentId, contentChecksum, contentMimetype,
                contentSize,
                timestamp));
        ManifestItem item = store.getItem(account, storeId, spaceId, contentId);
        return item;
    }

    @Test
    public void testUpdate() throws Exception {

        long oneSecondAgo = System.currentTimeMillis() - 1000;
        Date timestamp = new Date(oneSecondAgo);
        ManifestItem item1 = addAndRetrieveItem(timestamp);
        assertEquals(0, item1.getVersion());

        ManifestStore store = getStore();

        // change the checksum
        String contentChecksum2 = "new checksum";
        String contentMimetype2 = "contentMimetype2";
        String contentSize2 = "contentSize2";
        long ms999ago = oneSecondAgo + 1;

        assertTrue(store.addUpdate(account, storeId, spaceId, contentId, contentChecksum2, contentMimetype2,
                contentSize2,
                new Date(ms999ago)));

        // get it
        ManifestItem item2 = store.getItem(account, storeId, spaceId, contentId);

        // verify time modified exactly matches new timestamp
        assertEquals(ms999ago, item2.getModified().getTime());
        // verify updated fields
        assertEquals(contentChecksum2, item2.getContentChecksum());
        assertEquals(contentMimetype2, item2.getContentMimetype());
        assertEquals(contentSize2, item2.getContentSize());

        // verify version autoincrement
        assertEquals(item2.getVersion(), item1.getVersion() + 1);
    }

    @Test
    public void testIgnoreUpdateDueToOutOfOrderMessage() throws Exception {

        long currentTime = System.currentTimeMillis();
        Date timestamp = new Date(currentTime);
        ManifestItem item1 = addAndRetrieveItem(timestamp);
        ManifestStore store = getStore();

        // change the checksum
        long pastTime = currentTime - 1;
        String newChecksum = "checksum-2";
        assertFalse(store.addUpdate(account, storeId, spaceId, contentId, newChecksum, contentMimetype,
                contentSize,
                new Date(pastTime)));

        // get it
        ManifestItem item2 = store.getItem(account, storeId, spaceId, contentId);

        // verify time modified exactly matches new timestamp
        assertEquals(currentTime, item2.getModified().getTime());
        // verify updated fields
        assertEquals(contentChecksum, item2.getContentChecksum());
        // verify version autoincrement
        assertEquals(item2.getVersion(), item1.getVersion());

    }

    @Test
    public void testFlagAsDeleted() throws Exception {
        long currentTime = System.currentTimeMillis();
        Date past = new Date(currentTime - 1);
        Date present = new Date(currentTime);
        ManifestItem item1 = addAndRetrieveItem(present);
        ManifestStore store = getStore();
        assertTrue(store.flagAsDeleted(account, storeId, spaceId, contentId, present));
        store.flagAsDeleted(account, storeId, spaceId, contentId, new Date(currentTime + 1));
        ManifestItem item2 = store.getItem(account, storeId, spaceId, contentId);
        assertTrue(item2.isDeleted());
    }

    @Test
    public void testFlagAsNotFound() throws Exception {
        ManifestStore store = getStore();
        Date present = new Date();
        assertTrue(store.flagAsDeleted(account, storeId, spaceId, contentId, present));
        ManifestItem item1 = store.getItem(account, storeId, spaceId, contentId);
        assertTrue(item1.isDeleted());
    }

    @Test(expected = NotFoundException.class)
    public void testThrowNotFound() throws NotFoundException {
        ManifestStore store = getStore();
        store.getItem(account, storeId, spaceId, contentId);
    }

    @Test
    public void updateMissingFromStorageProviderFlag() throws Exception {
        ManifestStore store = getStore();
        Date present = new Date();
        addAndRetrieveItem(present);
        store.updateMissingFromStorageProviderFlag(account, storeId, spaceId, contentId, true);
        ManifestItem item2 = store.getItem(account, storeId, spaceId, contentId);
        assertTrue(item2.isMissingFromStorageProvider());
    }

    @Test(expected = ManifestItemWriteException.class)
    public void updateMissingFromStorageProviderFlagNotFound() throws Exception {
        getStore().updateMissingFromStorageProviderFlag(account, storeId, spaceId, contentId, true);
    }

    private ManifestStore getStore() {
        return context.getBean(ManifestStore.class);
    }

}
