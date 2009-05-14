package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.List;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.constants.RaceCode;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

public class ParticipantLinkDisplayCell extends AbstractCell {

public static final String VIEW_PARTICIPANT_URL= "viewParticipant?";
    
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
        Participant participant = null;
        List<RaceCode> races = new ArrayList<RaceCode>();
    	if(model.getCurrentRowBean() instanceof Participant){
    		participant = (Participant)model.getCurrentRowBean();
    		races = participant.getRaceCodes();
    	} 
    	
    	String cellValue = "";
        
    	for(RaceCode raceCode : races){
    		cellValue += raceCode.getDisplayName() + " " ;
    	}
    	
        setRowOnClick(model, participant);
        return cellValue;
    }
    
    public void setRowOnClick(TableModel tableModel, Participant participant ){    	
    	String url = "document.location='" + VIEW_PARTICIPANT_URL + ControllerTools.createParameterString(participant.getOrganizationAssignedIdentifiers().get(0)) + "'";
    	//+ VIEW_PARTICIPANT_URL + participant.getNciInstituteCode().toString() + "'";
    	tableModel.getRowHandler().getRow().setOnclick(url);
    	tableModel.getRowHandler().getRow().setStyle("cursor:pointer");        
    }


}
