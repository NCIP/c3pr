
package org.gridlab.gridsphere.extras.services.poll.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.extras.services.poll.UsersService;
import org.gridlab.gridsphere.extras.services.poll.PollUsers;

import java.util.List;


public class PollUserImpl implements PortletServiceProvider, UsersService{

    // logging
   private static PortletLog log = SportletLog.getInstance(PollUserImpl.class);

   // our persistencemanager
   private PersistenceManagerRdbms pm = null;

 /**
  * Init the service
  * @param config
  * @throws org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException

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
        log.info("Problems shutting down UsersService.");
      }
   }

   private List queryDB(String condition) {
      List result = null;
      try {
         result = pm.restoreList("from " + PollUsers.class.getName() + " as users "+condition);
      } catch (PersistenceManagerException e) {
      log.error("Could not retrieve users :"+e);
   }

   return result;

  }

     public PollUsers getPollUserByOid(String oid){
       List result = queryDB("where polluser.oid='" + oid + "'");
       if(result != null){
            return (PollUsers) result.get(0);
       }else {
           return null;
        }
     }

     public PollUsers getPollUserByUserId(String userId){
       List result = queryDB("where polluser.userId='" + userId + "'");
       if(result != null){
            return (PollUsers) result.get(0);
       }else {
           return null;
        }
     }

     public void addPollUser(PollUsers polluser){
        try{
           pm.create(polluser);
         } catch (PersistenceManagerException e){
           log.error("Error creating the polluser object!"+e);
      }
     }

     public void deletePollUser(PollUsers polluser){
       try {
             pm.delete(polluser);
         } catch (PersistenceManagerException e) {
             log.error("Error deleting polluser object."+e);
         }
     }

     public void savePollUser(PollUsers polluser){
        try {
           if (polluser.getOid()==null) {
                 pm.create(polluser);
             } else {
                 pm.update(polluser);
            }
         } catch (PersistenceManagerException e) {
             log.error("Error creating/updating the polluser object!"+e);
         }
     }

     public List getPollUsers(){

         return queryDB("");
     }
}