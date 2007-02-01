/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PhotoServiceImpl.java,v 1.1.1.1 2007-02-01 20:08:02 kherm Exp $
 */
package org.gridlab.gridsphere.extras.services.photoalbum.impl;

import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.extras.services.photoalbum.PhotoService;
import org.gridlab.gridsphere.extras.services.photoalbum.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PhotoServiceImpl implements PortletServiceProvider, PhotoService {


        private List photolist = new ArrayList();

             // logging
        private static PortletLog log = SportletLog.getInstance(PhotoServiceImpl.class);

            // our persistencemanager
        private PersistenceManagerRdbms pm = null;

          /**
           * Init the service
           * @param config
           * @throws PortletServiceUnavailableException

           */

        public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
              // create the persistencemanager
           this.pm = PersistenceManagerFactory.createPersistenceManagerRdbms("extras");
        }


          /**
           * Destroy the service and free ressources.
          */

        public void destroy() {
            try {
                 pm.destroy();
            } catch (PersistenceManagerException e) {
                 log.info("Problems shutting down PhotoService.");
            }
        }

        private List queryDB(String condition) {
                List result = null;
            try {
                 result = pm.restoreList("from " + Photo.class.getName() + " as photo "+condition);
            } catch (PersistenceManagerException e) {
                log.error("Could not retrieve photo(s) :"+e);
          }

           return result;

       }

        public Photo getPhoto(String oid) {
            List result = queryDB("where photo.oid='" + oid + "'");
            if(result != null){
                return (Photo) result.get(0);
            }else {
              log.error("Error finding photo object");
              return null;
            }
        }

        public void deletePhoto(Photo photo) {
            try {
                pm.delete(photo);
            } catch (PersistenceManagerException e) {
                log.error("Error deleting photo object."+e);
            }
        }

       public void addPhoto(Photo photo){
           try{
                pm.create(photo);
             } catch (PersistenceManagerException e){
               log.error("Error creating the photo object!"+e);
               }
       }

        public void savePhoto(Photo photo) {
            try {
                    if (photo.getOid()==null) {
                        pm.create(photo);
                    } else {
                        pm.update(photo);
                    }
            } catch (PersistenceManagerException e) {
                log.error("Error creating/updating the photo object!"+e);
            }
        }

       public List getPhotos() {
            return queryDB("");
       }
}



