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

import org.duracloud.mill.db.model.SpaceStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Daniel Bernstein
 */
@Repository(value = "spaceStatsRepo")
public interface JpaSpaceStatsRepo extends JpaRepository<SpaceStats, Long> {
    public static final String INTERVAL_DAY = "%Y-%m-%d";
    public static final String INTERVAL_WEEK = "%Y-%u";
    public static final String INTERVAL_MONTH = "%Y-%m";

    @Query(nativeQuery = true,
           value = "select"
                   + "    unix_timestamp(date_format(min(modified), '%Y-%m-%d 23:59:59')) as modified, "
                   + "    account_id,"
                   + "    store_id,"
                   + "    space_id,"
                   + "    avg(byte_count) as byte_count,"
                   + "    avg(object_count) as object_count,"
                   + "    date_format(modified, :interval) "
                   + "from space_stats  "
                   + "where  account_id = :accountId and  "
                   + "    store_id = :storeId and  "
                   + "    space_id = :spaceId and  "
                   + "    modified between :start and :end "
                   + "group by   date_format(modified, :interval), "
                   + "           account_id, "
                   + "           store_id,  "
                   + "           space_id")
    public List<Object[]> getByAccountIdAndStoreIdAndSpaceId(
        @Param("accountId") String accountId,
        @Param("storeId") String storeId,
        @Param("spaceId") String spaceId,
        @Param("start") Date start,
        @Param("end") Date end,
        @Param("interval") String interval);

    @Query(nativeQuery = true,
           value = "select a.modified, a.account_id, a.store_id, sum(a.byte_count), sum(a.object_count) from (select"
                   + "    unix_timestamp(date_format(min(modified), '%Y-%m-%d 23:59:59')) as modified, "
                   + "    account_id,"
                   + "    store_id,"
                   + "    space_id,"
                   + "    avg(byte_count) as byte_count,"
                   + "    avg(object_count) as object_count,"
                   + "    date_format(modified, :interval) "
                   + "from space_stats  "
                   + "where  account_id = :accountId and  "
                   + "    store_id = :storeId and  "
                   + "    modified between :start and :end "
                   + "group by   date_format(modified, :interval), "
                   + "           account_id, "
                   + "           store_id,  "
                   + "           space_id) a "
                   + "group by a.modified, a.account_id, a.store_id")
    public List<Object[]> getByAccountIdAndStoreId(@Param("accountId") String accountId,
                                                   @Param("storeId") String storeId,
                                                   @Param("start") Date start,
                                                   @Param("end") Date end,
                                                   @Param("interval") String interval);

    @Query(nativeQuery = true,
           value = "select"
                   + "    unix_timestamp(date_format(min(modified), '%Y-%m-%d 23:59:59')) as modified, "
                   + "    account_id,"
                   + "    store_id,"
                   + "    space_id,"
                   + "    avg(byte_count) as byte_count,"
                   + "    avg(object_count) as object_count,"
                   + "    date_format(modified, '%Y-%m-%d 00:00:00') "
                   + "from space_stats  "
                   + "where  account_id = :accountId and  "
                   + "    store_id = :storeId and  "
                   + "    modified between :start and :end "
                   + "group by   date_format(modified, '%Y-%m-%d'), "
                   + "           account_id, "
                   + "           store_id,  "
                   + "           space_id")
    public List<Object[]> getByAccountIdAndStoreIdAndDay(@Param("accountId") String accountId,
                                                         @Param("storeId") String storeId,
                                                         @Param("start") Date start,
                                                         @Param("end") Date end);

}
