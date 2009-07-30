package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class IdentifiersHolderFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        if (object instanceof StudyVersion) {
			StudyVersion studyVersion = (StudyVersion) object;
			return new IdentifiersHolder(studyVersion.getStudy().getIdentifiers());
		}else if (object instanceof StudySubject) {
			StudySubject studySubject = (StudySubject) object;
			return new IdentifiersHolder(studySubject.getStudySite().getStudy().getIdentifiers());
		}
        throw new C3PRBaseRuntimeException("Illegal obect instance.");
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null) return;
    	IdentifiersHolder identifiersHolder= (IdentifiersHolder) value;
    	Study study= new Study();
    	study.getIdentifiers().addAll(identifiersHolder.getIdentifiers());
    	if (object instanceof StudyVersion) {
			StudyVersion studyVersion = (StudyVersion) object;
			studyVersion.setStudy(study);
		}else if (object instanceof StudySubject) {
			StudySubject studySubject = (StudySubject) object;
			studySubject.getStudySite().setStudy(study);
		}else{
			throw new C3PRBaseRuntimeException("Illegal obect instance.");
		}
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return new IdentifiersHolder();
    }
}
