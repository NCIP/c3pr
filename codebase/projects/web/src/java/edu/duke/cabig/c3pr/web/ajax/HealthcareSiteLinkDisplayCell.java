/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.List;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;

public class HealthcareSiteLinkDisplayCell extends AbstractCell {

    @Override
    protected String getCellValue(final TableModel model, final Column column) {
    	String cellValue = "" ;
    	List<HealthcareSite> hcSites = (List<HealthcareSite>)column.getPropertyValue() ;
    	for(HealthcareSite hcSite : hcSites ){
    		cellValue = cellValue + hcSite.getName() ;
    		if(hcSite instanceof RemoteHealthcareSite){
            	cellValue = cellValue + "<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='17' height='16' border='0' align='middle'/>";
            }
    		cellValue = cellValue + "<br>";
    	}
    	return cellValue ;
    }
}
