/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialRetrievalContextImpl.java,v 1.1.1.1 2007-02-01 20:41:47 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.security.gss.CredentialRetrievalContext;
import org.gridlab.gridsphere.services.util.GridSphereUserUtil;
import org.gridlab.gridsphere.portlet.User;

import java.util.Date;

/**
 * Implements the credential retrieval context interface
 */
public class CredentialRetrievalContextImpl implements CredentialRetrievalContext {

    private String oid = null;
    private String dn = null;
    private User user = null;
    private String userOid = null;
    private String userName = null;
    private String credentialName = null;
    private int credentialLifetime = -1;
    private Date dateCreated = null;
    private Date dateLastUpdated = null;
    private Date dateLastRetrieved = null;
    private long timeCreated = 0;
    private long timeLastUpdated = 0;
    private long timeLastRetrieved = 0;

    public CredentialRetrievalContextImpl() {

    }

    public CredentialRetrievalContextImpl(User user) {
        setUser(user);
        setUserName(user.getUserName());
    }

    public String getOid() {
        return oid;
    }

    /**
     * Sets the object id for this context.
     * @param oid The object id
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDn() {
        return dn;
    }

    /**
     * Sets the distinguished name (DN) of the credential in this context.
     * @param dn The DN
     */
    public void setDn(String dn) {
        this.dn = dn;
    }

    public User getUser() {
        if (user == null) {
            user = GridSphereUserUtil.getUserByOid(userOid);
        }
        return user;
    }
    /**
     * Sets the user to which this context belongs.
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
        userOid = user.getID();
    }

    /**
     * Returns the oid of the user who owns this credential context.
     * @return The oid of the user
     */
    public String getUserOid() {
        return userOid;
    }

    /**
     * Sets the oid of the  user who owns this credential context.
     * @param oid The oid of the user
     */
    public void setUserOid(String oid) {
        userOid = oid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public int getCredentialLifetime() {
        return credentialLifetime;
    }

    public void setCredentialLifetime(int credentialLifetime) {
        this.credentialLifetime = credentialLifetime;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the date this retrieval context was created.
     * @param dateCreated The date created
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        if (dateCreated != null) timeCreated = dateCreated.getTime();
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    /**
     * Sets the date this retrieval context was last updated.
     * @param dateLastUpdated The date last updated
     */
    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
        if (dateLastUpdated != null) timeLastUpdated = dateLastUpdated.getTime();
    }

    public Date getDateLastRetrieved() {
        return dateLastRetrieved;
    }

    /**
     * Sets the date this retrieval context was last retrieved.
     * @param dateLastRetrieved The date last retrieved
     */
    public void setDateLastRetrieved(Date dateLastRetrieved) {
        this.dateLastRetrieved = dateLastRetrieved;
        if (dateLastRetrieved != null) timeLastRetrieved = dateLastRetrieved.getTime();
    }


    /**
     * Returns the time this context was created.
     * @return The time created
     */
    public long getTimeCreated() {
        return timeCreated;
    }

    /**
     * Sets the time this context was created.
     * @param timeCreated The time created
     */
    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
        if (timeCreated > 0) dateCreated = new Date(timeCreated);
    }

    /**
     * Returns the time this context was last updated.
     * @return The time last updated
     */
    public long getTimeLastUpdated() {
        return timeLastUpdated;
    }

    /**
     * Sets the time this context was last uptimed.
     * @param timeLastUpdated The time last uptimed
     */
    public void setTimeLastUpdated(long timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
        if (timeLastUpdated > 0) dateLastUpdated = new Date(timeLastUpdated);
    }

    /**
     * Returns the time this context was last retrieved.
     * @return The time last retrieved
     */
    public long getTimeLastRetrieved() {
        return timeLastRetrieved;
    }

    /**
     * Sets the time this context was last retrieved.
     * @param timeLastRetrieved The time last retrieved
     */
    public void setTimeLastRetrieved(long timeLastRetrieved) {
        this.timeLastRetrieved = timeLastRetrieved;
        if (timeLastRetrieved > 0) dateLastRetrieved = new Date(timeLastRetrieved);
    }
}
