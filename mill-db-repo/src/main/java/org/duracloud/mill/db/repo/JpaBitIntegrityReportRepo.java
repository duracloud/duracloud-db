/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.mill.db.repo;

import org.duracloud.mill.db.model.BitIntegrityReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Daniel Bernstein
 */
@Repository(value = "bitIntegrityReportRepo")
public interface JpaBitIntegrityReportRepo
    extends JpaRepository<BitIntegrityReport, Long> {

    /**
     * @param storeId
     * @param spaceId
     * @param pageable
     * @return
     */

    Page<BitIntegrityReport> findByStoreIdAndSpaceIdAndDisplayTrueOrderByCompletionDateDesc(String storeId,
                                                                                            String spaceId,
                                                                                            Pageable pageable);

    /**
     * @param account
     * @param storeId
     * @param spaceId
     * @return
     */
    BitIntegrityReport findFirstByAccountAndStoreIdAndSpaceIdOrderByCompletionDateDesc(String account,
                                                                                       String storeId,
                                                                                       String spaceId);
}
