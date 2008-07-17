package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.Study;

public class StudyLinkDisplayCell extends AbstractCell {

	@Override
	protected String getCellValue(final TableModel model, final Column column) {
		Study study = (Study) model.getCurrentRowBean();
		String cellValue = column.getValueAsString();
		if("true".equals(cellValue)){
			return "Yes" ;
		}else{
			return "No" ;
		}
	}

}
