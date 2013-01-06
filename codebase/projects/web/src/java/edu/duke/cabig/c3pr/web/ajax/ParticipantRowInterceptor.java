package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.RowInterceptor;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

public class ParticipantRowInterceptor implements RowInterceptor {

	public static final String VIEW_PARTICIPANT_URL= "viewParticipant?";
	
	public void addRowAttributes(TableModel tableModel, Row row) {
		// TODO Auto-generated method stub

	}

	public void modifyRowAttributes(TableModel model, Row row) {
		Participant participant= (Participant) model.getCurrentRowBean();
		String url = "document.location='" + VIEW_PARTICIPANT_URL + ControllerTools.createParameterString(participant.getPrimaryIdentifier()) + "'";
    	row.setOnclick(url);
    	row.setStyle("cursor:pointer");  
	}

}
