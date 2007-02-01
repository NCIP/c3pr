package org.gridlab.gridsphere.extras.services.poll;

import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;


public interface ChoiceService extends PortletService{


     public Choice getChoiceByOid(String oid);

     public void addChoice(Choice choice);

     public void deleteChoice(Choice choice);

     public void saveChoice(Choice choice);

     public List getChoices();
}