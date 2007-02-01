/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PhotoService.java,v 1.1.1.1 2007-02-01 20:07:58 kherm Exp $
 */
package org.gridlab.gridsphere.extras.services.photoalbum;

import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;


public interface PhotoService extends PortletService {


     public void deletePhoto(Photo photo);

     public void addPhoto(Photo photo);

     public Photo getPhoto(String oid);

     public void savePhoto(Photo photo);

    // public void applyDesc(Photo photo, String desc); 

     public List getPhotos();

}
