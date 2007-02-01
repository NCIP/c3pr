/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: Photo.java,v 1.1.1.1 2007-02-01 20:07:57 kherm Exp $
 */
package org.gridlab.gridsphere.extras.services.photoalbum;


public class Photo{

    public String oid;
    public String url = new String();
    public String title = new String();
    public String desc = new String();

    public Photo() {
    }

    public Photo(String title){
        this.title = title;
    }
    public Photo(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
       this.desc = desc ;
    }
}



