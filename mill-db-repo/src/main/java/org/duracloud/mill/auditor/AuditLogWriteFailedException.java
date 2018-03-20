/*
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 *     http://duracloud.org/license/
 */
package org.duracloud.mill.auditor;

/**
 * @author Daniel Bernstein
 * March 11, 2014
 */
public class AuditLogWriteFailedException extends Exception {

    private static final long serialVersionUID = 1L;
    private AuditLogItem logItem;

    public AuditLogWriteFailedException(Exception ex, AuditLogItem logItem) {
        super(ex);
        this.logItem = logItem;
    }

    public AuditLogWriteFailedException(String message, AuditLogItem logItem) {
        super(message);
        this.logItem = logItem;
    }

    public AuditLogItem getLogItem() {
        return logItem;
    }
}
