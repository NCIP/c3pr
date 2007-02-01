
package org.gridlab.gridsphere.extras.services.poll;

import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;


public interface QuestionService extends PortletService{


        public Question getQuestionByOid(String oid);

        public void addQuestion(Question question);

        public void deleteQuestion(Question question) ;

        public void saveQuestion(Question question);

        public List getQuestions();

}