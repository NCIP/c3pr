package com.semanticbits.ctms.portlets.c3pr;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.service.ParticipantService;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import java.util.List;


/**
 * Created by IntelliJ IDEA. User: kherm Date: Feb 2, 2007 Time: 11:02:42 AM To
 * change this template use File | Settings | File Templates.
 */
public class C3prPortlet extends ActionPortlet {

    public void init(PortletConfig config)
            throws javax.portlet.PortletException {
        super.init(config); // To change body of overridden methods use File |
        // Settings | File Templates.
        DEFAULT_VIEW_PAGE = "c3pr_home.jsp";
    }

    public void showResult(ActionFormEvent event) {
        TextFieldBean name = event.getTextFieldBean("name");
        /*
           * event.getActionResponse().setRenderParameter("helloname", name.getValue());
           * CheckBoxBean bold = event.getCheckBoxBean("bold"); if (bold.isSelected()) {
           * TextBean helloname = event.getTextBean("nameTB");
           * helloname.setStyle(TextBean.MSG_BOLD); }
           */
        String[] configFiles = new String[]{"applicationContext-core.xml",
                "applicationContext.xml", "applicationContext-configProperties.xml",
                };
        BeanFactory factory = new ClassPathXmlApplicationContext(configFiles);

        ActionRequest request = event.getActionRequest();
        //Instantiate an object
        ParticipantService participantService = (ParticipantService) factory.getBean("participantService");

        Participant participant = new Participant();
        participant.setLastName(name.getValue());
        try {
            java.util.ArrayList participants = (java.util.ArrayList)participantService.search(participant);
            request.setAttribute("participants", participants);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setNextState(event.getActionRequest(), "searchResults.jsp");
    }
}
