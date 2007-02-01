package org.gridlab.gridsphere.extras.services.poll.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.extras.services.poll.PollService;
import org.gridlab.gridsphere.extras.services.poll.Poll;

import java.util.List;


public class PollServiceImpl implements PortletServiceProvider, PollService{

    // logging
   private static PortletLog log = SportletLog.getInstance(PollServiceImpl.class);

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
        log.info("Problems shutting down PollService.");
   }
   }

   private List queryDB(String condition) {
   List result = null;
   try {
   // try to get the address
   result = pm.restoreList("from " + Poll.class.getName() + " as poll "+condition);
   } catch (PersistenceManagerException e) {
   log.error("Could not retrieve poll(s) :"+e);
   }

   return result;

  }

   public Poll getPollByOid(String oid) {
   List result = queryDB("where poll.oid='" + oid + "'");
   if(result != null){
       return (Poll) result.get(0);
   }else {
     return null;
   }
   }

       public void addPoll(Poll poll){
           try{
                pm.create(poll);
               log.debug("OK "+poll.getTitle());
             } catch (PersistenceManagerException e){
               log.error("Error creating the poll object!"+e);
               }
       }

    public void deletePoll(Poll poll) {
         try {
             pm.delete(poll);
         } catch (PersistenceManagerException e) {
             log.error("Error deleting poll object."+e);
         }
     }

     public void savePoll(Poll poll) {
         try {
                 if (poll.getOid()==null) {
                     pm.create(poll);
                 } else {
                     pm.update(poll);
                 }
         } catch (PersistenceManagerException e) {
             log.error("Error creating/updating the poll object!"+e);
         }
     }


    public List getPolls() {
         return queryDB("");
    }


}