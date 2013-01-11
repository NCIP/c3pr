/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;


public class SubjectRaceDisplayCell extends AbstractCell {

	public static final String VIEW_ORG_URL= "editOrganization?nciIdentifier=";
    
    @Override
    protected String getCellValue(final TableModel model, final Column column) {
    	List<String> raceCodes = new ArrayList<String>();
    	for(RaceCodeAssociation raceCodeAssociation : (Collection<RaceCodeAssociation>)column.getPropertyValue()){
    		raceCodes.add(raceCodeAssociation.getRaceCode().getCode());
    	}
    	String raceCodeString= raceCodes.toString();
    	return raceCodeString.substring(1,raceCodeString.length()-1).replaceAll(", ", "<br>");
    }
}
