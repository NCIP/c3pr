/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.esb;

import gov.nih.nci.cabig.ctms.domain.CodedEnum;

import java.util.HashMap;
import java.util.Map;

public class ResponseErrors<C extends CodedEnum<String>> {

    private Map<C, Object> errors=new HashMap<C, Object>();
    
    public Object getError(C key){
        return errors.get(key);
    }
    
    public void addError(C key, Object value){
        errors.put(key, value);
    }
    
    public Map<C, Object> getErrorsMap(){
        return errors;
    }
}
