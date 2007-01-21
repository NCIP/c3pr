package edu.duke.cabig.c3pr.utils;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import edu.duke.cabig.c3pr.domain.DomainObjectWithGridId;

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
        if (entity instanceof DomainObjectWithGridId) {	         
	     	int gridIdIdx = findGridId(propertyNames);
	     	if (gridIdIdx < 0) throw new IllegalStateException("GridIdentifierInterceptor : " +
	     		"Class doesn't have gridId property: " +entity.getClass().getName());  
	        if (state[gridIdIdx] == null) {
	        	String leng =
	            gridIdentifierCreator.getGridIdentifier(entity.getClass().toString()+
	            	UUID.randomUUID().toString());
	             state[gridIdIdx] = leng;
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

    /**
     * Generates a Unique Identifier for this instance across systems
     * @param entity
     * @param state
     * @param propertyNames
     * @return
     */
    private String getIdentifier(Object entity, Object[] state, String[] propertyNames) {    	   
    String identifier = Base64.encode((entity.getClass().toString().getBytes())) + "/";
	    for (int count = 0; count < state.length; count++) {
	      Object attributeValue = state[count];
	      if (attributeValue != null) {
	        identifier = identifier
	            + Base64.encode(propertyNames[count].getBytes()) + ":"
	            + Base64.encode(attributeValue.toString().getBytes()) + ";";
	      }
	    }
	    System.out.println("***************************** "+identifier.length());
	    return identifier;
    }
    
	public GridIdentifierCreator getGridIdentifierCreator() {
		return gridIdentifierCreator;
	}

	public void setGridIdentifierCreator(GridIdentifierCreator gridIdentifierCreator) {
		this.gridIdentifierCreator = gridIdentifierCreator;
	}
}
