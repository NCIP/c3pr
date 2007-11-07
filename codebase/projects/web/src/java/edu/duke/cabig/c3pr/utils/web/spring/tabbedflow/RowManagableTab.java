package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;

/**
 * @author Rhett Sutphin
*/
public abstract class RowManagableTab<C> extends ReflexiveAjaxableTab<C>{

	protected String getSoftDeleteParamName(){
		return "softDelete";
	}
	
	protected String getDeleteIndexParamName(){
		return "deleteIndex";
	}

	protected String getDeleteHashCodeParamName(){
		return "deleteHashCode";
	}
	
	protected String getCollectionParamName(){
		return "collection";
	}
		//override this method for soft deletes
	protected boolean shouldDelete(HttpServletRequest request, Object command, Errors error) {
		return !WebUtils.hasSubmitParameter(request, this.getSoftDeleteParamName());
	}

	public RowManagableTab(){
	}	
	
	public RowManagableTab(String longTitle, String shortTitle, String viewName) {
		super(longTitle, shortTitle, viewName, new Class[]{HttpServletRequest.class,Object.class, Errors.class});
    }
    
	public RowManagableTab(String longTitle, String shortTitle, String viewName, Class[] params) {
		super(longTitle, shortTitle, viewName);
		super.paramTypes=params;
    }

	public ModelAndView deleteRow(HttpServletRequest request, Object command, Errors error)throws Exception{
		String listPath=request.getParameter(getCollectionParamName());
		List col= (List) new DefaultObjectPropertyReader(command, listPath).getPropertyValueFromPath();
		int hashCode=Integer.parseInt(request.getParameter(getDeleteHashCodeParamName()));
		Integer index=null;
		for(int i=0 ; i<col.size() ; i++){
			if(col.get(i).hashCode()==hashCode){
				index=i;
				break;
			}
		}
		if(this.shouldDelete(request, command, error)){
	      	col.remove(index.intValue());
		}else{
			//Enabling the retitred_indicator
	      	AbstractMutableDeletableDomainObject obj=(AbstractMutableDeletableDomainObject)col.get(index);
	      	obj.setRetiredIndicatorAsTrue();
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put(getFreeTextModelName(), "deletedIndex="+request.getParameter(getDeleteIndexParamName())+"||hashCode="+request.getParameter(getDeleteHashCodeParamName())+"||");
		return new ModelAndView("", map);
	}
}
