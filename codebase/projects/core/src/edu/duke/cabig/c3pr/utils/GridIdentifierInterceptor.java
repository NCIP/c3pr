package edu.duke.cabig.c3pr.utils;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import edu.duke.cabig.c3pr.domain.GridIdentifiable;

/**
 * Wrapper interceptor to add Grid Identifiers to objects which 
 * support them. Uses GridIdentifierCreator to delegate the creation
 * of Grid Identifiers
 * 
 * @author Priyatam
 */
public class GridIdentifierInterceptor extends EmptyInterceptor {
	
    private GridIdentifierCreator gridIdentifierCreator;
  
    @Override
    /* Intercepting behaviour before Saving this entity
     * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
     */
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        boolean localMod = false;
        if (entity instanceof GridIdentifiable) {	         
	     	int gridIdIdx = findGridId(propertyNames);
	     	if (gridIdIdx < 0) throw new IllegalStateException("GridIdentifierInterceptor : " +
	     		"Class doesn't have gridId property: " +entity.getClass().getName());  
	        if (state[gridIdIdx] == null) {	        	
	        	 state[gridIdIdx] = gridIdentifierCreator.getGridIdentifier(entity.getClass().toString()+
	            	UUID.randomUUID().toString());	         
	            localMod = true;
	        }
        }
     
        return localMod;
    }
    
    private int findGridId(String[] propertyNames) {
        for (int i = 0; i < propertyNames.length; i++) {
            if ("gridId".equals(propertyNames[i])) return i;
        }
        return -1; // defer throwing exception so we can report class
    }
  
	public GridIdentifierCreator getGridIdentifierCreator() {
		return gridIdentifierCreator;
	}

	public void setGridIdentifierCreator(GridIdentifierCreator gridIdentifierCreator) {
		this.gridIdentifierCreator = gridIdentifierCreator;
	}
}
