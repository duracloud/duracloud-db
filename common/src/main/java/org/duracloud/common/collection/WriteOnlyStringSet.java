/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.common.collection;

import java.security.MessageDigest;

import gnu.trove.set.hash.THashSet;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * This class gives you an efficient way to write to an in memory set-like structure of tens of
 * millions of strings. The catch is that once you write the strings to the set,
 * you can't retrieve them. However you can ask if the set contains any string. Additionally
 * you can remove strings from the set as well as determine the set's size.
 *
 * An example use case:  You're comparing two large sets (A and B) of objects both of which won't fit in memory.
 * You want to be able to load A (each element of which can be reduced to a string value) into memory
 * and then iterate over B, checking for existence in A (probably of a single property of each B element) before
 * performing some further logic on that B element.
 *
 * @author Daniel Bernstein
 * Date: May 22, 2015
 */
public class WriteOnlyStringSet {
    private THashSet<String> set;

    public WriteOnlyStringSet(int capacity) {
        set = new THashSet<String>(capacity);
    }

    private static final MessageDigest MD5 = DigestUtils.getMd5Digest();

    public void add(String string) {
        if (string == null) {
            return;
        }

        set.add(getMd5String(string));
    }

    private String getMd5String(String string) {
        return checksumBytesToString(MD5.digest(string.getBytes()));
    }

    /**
     * Converts a message digest byte array into a String based
     * on the hex values appearing in the array.
     */
    public static String checksumBytesToString(byte[] digestBytes) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < digestBytes.length; i++) {
            String hex = Integer.toHexString(0xff & digestBytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean contains(String string) {
        if (string == null) {
            return false;
        }
        return set.contains(getMd5String(string));
    }

    public int size() {
        return set.size();
    }

    /**
     * @param string
     */
    public boolean remove(String string) {
        return set.remove(getMd5String(string));
    }
}
