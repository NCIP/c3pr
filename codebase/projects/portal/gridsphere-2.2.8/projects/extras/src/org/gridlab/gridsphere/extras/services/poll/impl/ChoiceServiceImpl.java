package org.gridlab.gridsphere.extras.services.poll.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.extras.services.poll.ChoiceService;
import org.gridlab.gridsphere.extras.services.poll.Choice;

import java.util.List;


public class ChoiceServiceImpl implements PortletServiceProvider, ChoiceService{


    // logging
   private static PortletLog log = SportletLog.getInstance(ChoiceServiceImpl.class);

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
        log.info("Problems shutting down ChoiceService.");
   }
   }

   private List queryDB(String condition) {
       List result = null;
   try {
        // try to get the address
        result = pm.restoreList("from " + Choice.class.getName() + " as choice "+condition);
   } catch (PersistenceManagerException e) {
       log.error("Could not retrieve choice(s) :"+e);
 }

  return result;

  }

   public Choice getChoiceByOid(String oid) {
   List result = queryDB("where choice.oid='" + oid + "'");
   if(result != null){
       return (Choice) result.get(0);
   }else {
     log.error("Error finding choice object");
     return null;
   }
   }

   public void addChoice(Choice choice){
  try{
       pm.create(choice);
      log.debug(choice.getChoice());
    } catch (PersistenceManagerException e){
      log.error("Error creating the choice object!"+e);
      }
  }
    public void deleteChoice(Choice choice) {
         try {
             pm.delete(choice);
         } catch (PersistenceManagerException e) {
             log.error("Error deleting choice object."+e);
         }
     }

     public void saveChoice(Choice choice) {
         try {
                 if (choice.getOid()==null) {
                   //  photoalbum.setUserid(user.getID());
                     pm.create(choice);
                 } else {
                     pm.update(choice);
                 }
         } catch (PersistenceManagerException e) {
             log.error("Error creating/updating the choice object!"+e);
         }
     }

    public List getChoices() {
         return queryDB("");
    }

}