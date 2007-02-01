package org.gridlab.gridsphere.extras.services.wiki;


import java.util.Date;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: Snip.java,v 1.1.1.1 2007-02-01 20:08:32 kherm Exp $
 */

public class Snip {


    /**
     * Objectid
     */
    private String oid = null;

    /**
     * shortname of the note
     */
    private String name = "";
    /**
     * content of the note
     */
    private String content = "";
    /**
     * the user it belongs to
     */
    private String userid = "";

    /**
     * date note was created
     */
    private Date dateCreated = new Date();

    /**
     * date not was modified
     */
    private Date dateModified = new Date();

    private boolean privateSnip = false;


    public boolean isPrivateSnip() {
        return privateSnip;
    }

    public void setPrivateSnip(boolean privateSnip) {
        this.privateSnip = privateSnip;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}