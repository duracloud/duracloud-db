/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.common.db.error;

/**
 * Exception thrown when a requested db value does not exist
 *
 * @author Bill Branan
 */
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }

}