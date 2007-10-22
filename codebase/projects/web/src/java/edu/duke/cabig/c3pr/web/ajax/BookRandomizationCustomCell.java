package edu.duke.cabig.c3pr.web.ajax;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;

import org.extremecomponents.table.cell.AbstractCell;

public class BookRandomizationCustomCell extends AbstractCell {

    protected String getCellValue(TableModel tableModel, Column column) {
        if(column.getValue() == null)
        	return "Invalid Entry";
        else {
        	if(column.getValue() instanceof Integer){
        		Integer stratumGroupNumber = (Integer)column.getValue();
        		if(stratumGroupNumber < 0){
        			return "Invalid Entry";
        		}
        	}
        }
    	return column.getValueAsString();
    }
}
