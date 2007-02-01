/**
 * $Id: PasswordImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.services.core.security.password.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;

import java.util.Date;

public class PasswordImpl implements PasswordEditor {

    private String oid = null;
    private SportletUserImpl user;
    // used for JSR/Tomcat/Realm
    private String userName;
    private String value = "";
    private String hint = "";
    private long lifetime = -1;
    private Date dateExpires = null;
    private Date dateCreated = null;
    private Date dateLastModified = null;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getLifetime() {
        return this.lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    public Date getDateExpires() {
        return this.dateExpires;
    }

    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLastModified() {
        return this.dateLastModified;
    }

    public void setDateLastModified(Date dateModified) {
        this.dateLastModified = dateModified;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = (SportletUserImpl) user;
        this.userName = user.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Castor method for setting user object.
     */
    public void setSportletUser(SportletUserImpl user) {
        this.user = user;
    }

    /**
     * Castor method for getting user object.
     */
    public SportletUserImpl getSportletUser() {
        return this.user;
    }

}

