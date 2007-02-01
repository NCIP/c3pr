/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialContextImpl.java,v 1.1.1.1 2007-02-01 20:41:45 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.security.gss.CredentialMapping;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.security.gss.CredentialException;
import org.gridlab.gridsphere.services.util.GridSphereUserUtil;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import java.util.List;
import java.util.Date;
import java.util.Vector;
import java.util.Iterator;

/**
 * Implements the credential context interface
 */
public class CredentialContextImpl implements CredentialContext {

    private static PortletLog log = SportletLog.getInstance(CredentialContextImpl.class);

    private String oid = null;
    private String dn = null;
    private User user = null;
    private String userOid = null;
    private GSSCredential credential = null;
    private String label = null;
    private Date dateCreated = null;
    private Date dateLastUpdated = null;
    private Date dateLastActivated = null;
    private long timeCreated = 0;
    private long timeLastUpdated = 0;
    private long timeLastActivated = 0;
    private List credentialMappings = new Vector(1);

    /**
     * Constructs a default credential context.
     */
    public CredentialContextImpl() {
    }

    /**
     * Constructs a credential context with a given user and distinguished name (DN).
     * @param user The suser
     * @param dn The distinguished name.
     */
    public CredentialContextImpl(User user, String dn) {
        setUser(user);
        setDn(dn);
    }

    /**
     * Constructs a credential context with a given user and credential.
     * Throws an exception if unable to use the given credential.
     * @param user The user
     * @param credential The credential.
     * @throws CredentialException
     */
    public CredentialContextImpl(User user, GSSCredential credential)
            throws CredentialException {
        setUser(user);
        try {
            dn = credential.getName().toString();
            this.credential = credential;
        } catch (GSSException e) {
            log.error("Error getting gss credential dn", e);
            throw new CredentialException(e.getMessage());
        }
    }

    public String getOid() {
        return oid;
    }

    public String getDn() {
        return dn;
    }

    /**
     * Sets the distinguished name of the credential described by this context.
     * @param dn The distinguished name for this context
     */
    public void setDn(String dn) {
        this.dn = dn;
    }


    /**
     * Sets the object id of the context.
     * @param oid The object id
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    public User getUser() {
        if (user == null) {
            user = GridSphereUserUtil.getUserByOid(userOid);
        }
        return user;
    }

    /**
     * Sets the user who owns this credential context.
     * @param user The user that owns this credential
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
     * Sets the oid of the user who owns this credential context.
     * @param oid The oid of the user
     */
    public void setUserOid(String oid) {
        userOid = oid;
    }

    public GSSCredential getCredential() {
        if (credential == null) {
            credential = CredentialManagerServiceImpl.getCredential(dn);
        }
        return credential;
    }

    public int getRemainingLifetime() {
        int value = 0;
        if (credential == null) {
            credential = CredentialManagerServiceImpl.getCredential(dn);
        }
        if (credential != null) {
            try {
                value = credential.getRemainingLifetime();
            } catch (GSSException e) {
                log.error("Error getting gss credential remaining lifetime", e);
            }
        }
        return value;
    }

    public boolean isActive() {
        boolean isActive = false;
        if (credential == null) {
            credential = CredentialManagerServiceImpl.getCredential(dn);
        }
        try {
            isActive = (credential != null && credential.getRemainingLifetime() > 0);
        } catch (GSSException e) {
            log.error("Error getting gss credential remaining lifetime", e);
        }
        return isActive;
    }

    public void activate(GSSCredential credential)
            throws CredentialException {
        if (credential == null) {
            throw new CredentialException("No credential was provided for this context : " + dn);
        }
        try {
            if (credential.getRemainingLifetime() == 0) {
                throw new CredentialException("Credential is expired for this context : " + dn);
            }
            String credDn = credential.getName().toString();
            if (!credDn.equals(dn)) {
                throw new CredentialException("Credential dn [" + credDn + "] is invalid for this context : " + dn);
            }
            dateLastActivated = new Date();
        } catch (GSSException e) {
            log.error("Unknown error for credential context : " + dn, e);
            throw new CredentialException("Unknown error for credential context : " + dn, e);
        }
        CredentialManagerServiceImpl.setCredential(dn, credential);
        this.credential = credential;
    }

    public void deactivate() {
        CredentialManagerServiceImpl.removeCredential(dn);
        credential = null;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFileName() {
        return CredentialManagerServiceImpl.getCredentialFileName(dn);
    }

    public List getCredentialMappings() {
        return credentialMappings;
    }

    public void setCredentialMappings(List credentialMappings) {
        this.credentialMappings = credentialMappings;
    }

    public CredentialMapping getCredentialMapping(String resourceDn) {
        CredentialMapping mapping = null;
        int position = findCredentialMapping(resourceDn);
        if (position > -1) {
            mapping = (CredentialMapping)credentialMappings.get(position);
        }
        return mapping;
    }

    public boolean hasCredentialMapping(String resourceDn) {
        return findCredentialMapping(resourceDn) > -1;
    }

    public CredentialMapping putCredentialMapping(String resourceDn) {
        CredentialMapping mapping = null;
        int position = findCredentialMapping(resourceDn);
        if (position == -1) {
            mapping = new CredentialMappingImpl(dn, resourceDn);
            credentialMappings.add(mapping);
        } else {
            mapping = (CredentialMapping)credentialMappings.get(position);
        }
        return mapping;
    }

    public CredentialMapping removeCredentialMapping(String resourceDn) {
        CredentialMapping mapping = null;
        int position = findCredentialMapping(resourceDn);
        if (position > -1) {
            mapping = (CredentialMapping)credentialMappings.remove(position);
        }
        return mapping;
    }

    public int findCredentialMapping(String resourceDn) {
        int position = -1;
        int ii = 0;
        for (Iterator mappings = credentialMappings.iterator(); mappings.hasNext(); ++ii) {
            CredentialMapping mapping = (CredentialMapping) mappings.next();
            if (mapping.getResourceDn().equals(resourceDn)) {
                position = ii;
                break;
            }
        }
        return position;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the date this context was created.
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
     * Sets the date this context was last updated.
     * @param dateLastUpdated The date last updated
     */
    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
        if (dateLastUpdated != null) timeLastUpdated = dateLastUpdated.getTime();
    }

    public Date getDateLastActivated() {
        return dateLastActivated;
    }

    /**
     * Sets the date this context was last activated.
     * @param dateLastActivated The date last activated
     */
    public void setDateLastActivated(Date dateLastActivated) {
        this.dateLastActivated = dateLastActivated;
        if (dateLastActivated != null) timeLastActivated = dateLastActivated.getTime();
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
     * Returns the time this context was last activated.
     * @return The time last activated
     */
    public long getTimeLastActivated() {
        return timeLastActivated;
    }

    /**
     * Sets the time this context was last activated.
     * @param timeLastActivated The time last activated
     */
    public void setTimeLastActivated(long timeLastActivated) {
        this.timeLastActivated = timeLastActivated;
        if (timeLastActivated > 0) dateLastActivated = new Date(timeLastActivated);
    }
}
