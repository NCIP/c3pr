/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

/**
 * @author Rhett Sutphin
 */
public abstract class RowManagableTab<C> extends ReflexiveAjaxableTab<C> {

    protected String getSoftDeleteParamName() {
        return "softDelete";
    }

//    protected String getDeleteIndexParamName() {
//        return "deleteIndex";
//    }

    protected String getDeleteHashCodeParamName() {
        return "deleteHashCode";
    }

    protected String getCollectionParamName() {
        return "collection";
    }

    // override this method for soft deletes
    protected boolean shouldDelete(HttpServletRequest request, Object command, Errors error) {
        return !WebUtils.hasSubmitParameter(request, this.getSoftDeleteParamName());
    }

    public RowManagableTab() {
        super.paramTypes = new Class[] { HttpServletRequest.class, Object.class, Errors.class };
    }

    public RowManagableTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName, new Class[] { HttpServletRequest.class,
                Object.class, Errors.class });
    }

    public RowManagableTab(String longTitle, String shortTitle, String viewName, Class[] params) {
        super(longTitle, shortTitle, viewName);
        super.paramTypes = params;
    }

    public ModelAndView deleteRow(HttpServletRequest request, Object command, Errors error)
                    throws Exception {
        String listPath = request.getParameter(getCollectionParamName());
//        List col = (List) new DefaultObjectPropertyReader(command, listPath)
//                        .getPropertyValueFromPath();
        List col=(List)new BeanWrapperImpl(command).getPropertyValue(listPath);
        String deletionIdStr = request.getParameter(getDeleteHashCodeParamName());
        Integer index = getIndex(col, deletionIdStr);
        Map<String, String> map = new HashMap<String, String>();
        if (index == null) {
            map.put(AjaxableUtils.getFreeTextModelName(), "Unmatched hashCode/Id");
            return new ModelAndView("", map);
        }
        
        if (this.shouldDelete(request, command, error) || deletionIdStr.startsWith("HC#")) {
            col.remove(index.intValue());
            map.put(AjaxableUtils.getFreeTextModelName(), "deletedIndex="
                            + index + "||hashCode="
                            + deletionIdStr + "||");
        }
        else {
            // Enabling the retitred_indicator
            AbstractMutableDeletableDomainObject obj = (AbstractMutableDeletableDomainObject) col
                            .get(index);
            obj.setRetiredIndicatorAsTrue();
        }

        return new ModelAndView("", map);
    }
    
    private Integer getIndex(List collection, String deletionString){
    	if (deletionString.startsWith("ID#")) {
    		return getIndexBasedOnID(collection, Integer.parseInt(deletionString.substring(3)));
        }
        else if (deletionString.startsWith("HC#")) {
        	return getIndexBasedOnHash(collection, Integer.parseInt(deletionString.substring(3)));
        }
    	return null;
    }
    
    protected Integer getIndexBasedOnID(List collection, int id){
    	Integer index=null;
    	for (int i = 0; i < collection.size(); i++) {
            AbstractMutableDomainObject amdo;
            if (collection.get(i) instanceof AbstractMutableDomainObject) {
                amdo = (AbstractMutableDomainObject) collection.get(i);
                if (amdo.getId() == id) {
                    index= i;
                    break;
                }
            }
        }
    	//check for duplicate. If found return the one which doesn't have id.
    	if(index!=null){
    		Integer hashBasedIndex=getIndexBasedOnHash(collection, collection.get(index).hashCode());
    		if(hashBasedIndex!=null){
    			return hashBasedIndex;
    		}else{
    			return index;
    		}
    	}
    		
    	return null;
    }
    
    protected Integer getIndexBasedOnHash(List collection, int hash){
    	for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).hashCode() == hash) {
            	if(collection.get(i) instanceof AbstractMutableDeletableDomainObject && ((AbstractMutableDeletableDomainObject)collection.get(i)).getId()==null){
                    return i;
            	}
            }
        }
    	return null;
    }
    
}
