package com.semanticbits.ctms.portlets.c3pr;

import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletConfig;
import javax.portlet.ActionRequest;
import java.io.IOException;
import java.io.PrintWriter;

import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.web.SearchParticipantCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


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

	public void showResult(ActionFormEvent event) throws PortletException {
        TextFieldBean name = event.getTextFieldBean("name");
		/*
		 * event.getActionResponse().setRenderParameter("helloname", name.getValue());
		 * CheckBoxBean bold = event.getCheckBoxBean("bold"); if (bold.isSelected()) {
		 * TextBean helloname = event.getTextBean("nameTB");
		 * helloname.setStyle(TextBean.MSG_BOLD); }
		 */        
        String[] configFiles = new String[] { "applicationContext-core.xml",
        "applicationContext.xml","applicationContext-configProperties.xml",
        "applicationContext-esb.xml","applicationContext-gridSecurity.xml"};
        BeanFactory factory = new ClassPathXmlApplicationContext(configFiles);

        ActionRequest request=event.getActionRequest();
          //Instantiate an object
    	ParticipantService participantService = (ParticipantService) factory.getBean("participantService");

		Participant participant = new Participant();
		participant.setLastName(name.getValue());
		try{
			List<Participant> participants = participantService.search(participant);
			request.setAttribute("participants", participants);
		}catch(Exception e){
			System.out.println(e);
		}
		setNextState(event.getActionRequest(), "searchResults.jsp");
    }
}
