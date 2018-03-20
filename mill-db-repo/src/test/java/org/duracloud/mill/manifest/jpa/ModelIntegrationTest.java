/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */

package org.duracloud.mill.manifest.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.duracloud.mill.db.model.BitIntegrityReport;
import org.duracloud.mill.db.repo.JpaBitIntegrityReportRepo;
import org.duracloud.mill.test.jpa.JpaIntegrationTestBase;
import org.junit.Test;

/**
 * This test class runs tests against a live embedded mysql database.
 *
 * @author Daniel Bernstein
 * Date: August 23, 2017
 */
public class ModelIntegrationTest extends JpaIntegrationTestBase {

    private String account = "account";

    private String storeId = "store-id";

    private String spaceId = "space-id";

    private String contentId = "content-id";

    @Test
    public void testAddBitReport() throws Exception {
        long oneSecondAgo = System.currentTimeMillis() - 1000;
        Date timestamp = new Date(oneSecondAgo);
        BitIntegrityReport report = new BitIntegrityReport();

        report.setAccount(account);
        report.setCompletionDate(timestamp);
        report.setModified(timestamp);
        report.setReportContentId(contentId);
        report.setReportSpaceId(spaceId);
        report.setSpaceId(spaceId);
        report.setStoreId(storeId);

        report = getRepo().saveAndFlush(report);
        assertEquals(account, report.getAccount());
        assertEquals(storeId, report.getStoreId());
        assertEquals(spaceId, report.getSpaceId());
        assertEquals(spaceId, report.getReportSpaceId());
        assertEquals(contentId, report.getReportContentId());
        assertEquals(timestamp, report.getCompletionDate());
        assertNotNull(report.getId());

        assertEquals(timestamp, report.getModified());
    }

    private JpaBitIntegrityReportRepo getRepo() {
        return context.getBean(JpaBitIntegrityReportRepo.class);
    }

}
