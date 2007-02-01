
package org.gridlab.gridsphere.extras.services.poll.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.extras.services.poll.QuestionService;
import org.gridlab.gridsphere.extras.services.poll.Question;

import java.util.List;


public class QuestionServiceImpl implements PortletServiceProvider, QuestionService{

             // logging
        private static PortletLog log = SportletLog.getInstance(QuestionServiceImpl.class);

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
                 log.info("Problems shutting down QuestionService.");
            }
        }

        private List queryDB(String condition) {
                List result = null;
            try {
                 // try to get the address
                 result = pm.restoreList("from " + Question.class.getName() + " as question "+condition);
            } catch (PersistenceManagerException e) {
                log.error("Could not retrieve question(s) :"+e);
          }

           return result;

       }

        public Question getQuestionByOid(String oid) {
            List result = queryDB("where question.oid='" + oid + "'");
            if(result != null){
                return (Question) result.get(0);
            }else {
              log.error("Error finding question object");
              return null;
            }
        }

        public void addQuestion(Question question){
           try{
                pm.create(question);
             } catch (PersistenceManagerException e){
               log.error("Error creating the question object!"+e);
               }
       }

    public void deleteQuestion(Question question) {
         try {
             pm.delete(question);
         } catch (PersistenceManagerException e) {
             log.error("Error deleting question object."+e);
         }
     }

     public void saveQuestion(Question question) {
         try {
                 if (question.getOid()==null) {
                   //  photoalbum.setUserid(user.getID());
                     pm.create(question);
                 } else {
                     pm.update(question);
                 }
         } catch (PersistenceManagerException e) {
             log.error("Error creating/updating the question object!"+e);
         }
     }

    public List getQuestions() {
         return queryDB("");
    }

}