package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class InvestigatorStatusFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
    	StudyInvestigator studyInvestigator= (StudyInvestigator) object;
        return studyInvestigator.getStatusCode()==null?null:studyInvestigator.getStatusCode().toString();
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null) return;
    	StudyInvestigator studyInvestigator = (StudyInvestigator) object;
    	studyInvestigator.setStatusCode(InvestigatorStatusCodeEnum.valueOf((String) value));
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    	StudyInvestigator studyInvestigator = (StudyInvestigator) object;
    	studyInvestigator.setStatusCode(null);
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null; // To change body of implemented methods use File | Settings | File
                        // Templates.
    }
}
