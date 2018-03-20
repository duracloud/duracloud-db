/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.common.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Bernstein
 * Date: May 22, 2015
 */
public class WriteOnlyStringSetTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        int capacity = 100;

        WriteOnlyStringSet set = new WriteOnlyStringSet(capacity);
        String idPrefix = StringUtils.repeat("a", 200);
        for (int i = 0; i < capacity; i++) {
            set.add(idPrefix.concat(i + ""));
        }

        assertEquals(capacity, set.size());

        for (int i = 0; i < capacity; i++) {
            assertTrue(set.contains(idPrefix.concat(i + "")));
        }

        for (int i = 0; i < capacity; i++) {
            set.remove(idPrefix.concat(i + ""));
        }

        for (int i = 0; i < capacity; i++) {
            assertFalse(set.contains(idPrefix.concat(i + "")));
        }

        assertEquals(0, set.size());
    }

    @Test
    public void testAdditionOf1MillionItemsTakesLessThan200Seconds() {
        long start = System.currentTimeMillis();
        int capacity = 1000 * 1000 * 1;

        WriteOnlyStringSet set = new WriteOnlyStringSet(capacity);
        String idPrefix = StringUtils.repeat("a", 200);
        for (int i = 0; i < capacity; i++) {
            set.add(idPrefix.concat(i + ""));
        }

        assertEquals(capacity, set.size());
        assertTrue(System.currentTimeMillis() - start < 20 * 1000);
    }

    /**
     * Verifies that the error captured in https://jira.duraspace.org/browse/DURACHRON-201
     * has been resolved. Prior to this change, the two Strings in this test were
     * considered to have matching checksum values, and so would only result in 1 entry in
     * the WriteOnlyStringSet rather than the expected 2.
     *
     * @throws Exception
     */
    @Test
    public void testStringConversionBugFix() throws Exception {
        WriteOnlyStringSet set = new WriteOnlyStringSet(2);
        String original = "cdf/Bag-cdf_8368.zip:322eb98df5965a2230c12268d9ea9684";
        set.add(original);
        String duplicate = "utkcomm/Bag-utkcomm_16230.zip:d0f56f9bc4da0d8ec6012001d21088f6";
        set.add(duplicate);
        assertEquals(2, set.size());
    }

}
