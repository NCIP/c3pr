package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.List;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;

public class SubjectRaceDisplayCell extends AbstractCell {

	public static final String VIEW_ORG_URL= "editOrganization?nciIdentifier=";
    
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
    	List<String> raceCodes = new ArrayList<String>();
    	for(RaceCodeEnum raceCode : (List<RaceCodeEnum>)column.getPropertyValue()){
    		raceCodes.add(raceCode.getCode());
    	}
    	String raceCodeString= raceCodes.toString();
    	return raceCodeString.substring(1,raceCodeString.length()-1).replaceAll(", ", "<br>");
    }
}
