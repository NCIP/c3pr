/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import java.util.ArrayList;
import java.util.List;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class RaceCodeFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
//    	RaceCodeAssociation raceCodeAssociation = (RaceCodeAssociation) object;
//        try {
//            return raceCodeAssociation.getRaceCode().getName().toString();
//        }
//        catch (Exception e) {
//            return "";
//        }
    	
    	if (object instanceof StudySubjectDemographics) {
 			StudySubjectDemographics studySubjectDemographics = (StudySubjectDemographics) object;
 			List<String> raceCodes = new ArrayList<String>();
 			for(RaceCodeAssociation raceCodeAssociation : studySubjectDemographics.getRaceCodeAssociations()){
 				raceCodes.add(raceCodeAssociation.getRaceCode().getName());
 			}
 			return raceCodes;
 		}else if (object instanceof Participant) {
 			Participant participant = (Participant) object;
 			List<String> raceCodes = new ArrayList<String>();
 			for(RaceCodeAssociation raceCodeAssociation : participant.getRaceCodeAssociations()){
 				raceCodes.add(raceCodeAssociation.getRaceCode().getName());
 			}
 			return raceCodes;
 		}
         throw new C3PRBaseRuntimeException("Illegal object instance.");
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null) return;
    	
    	if (object instanceof StudySubjectDemographics) {
 			StudySubjectDemographics studySubjectDemographics = (StudySubjectDemographics) object;
			RaceCodeAssociation raceCodeAssociation = new RaceCodeAssociation();
	    	raceCodeAssociation.setRaceCode(RaceCodeEnum.valueOf((String) value));
	    	studySubjectDemographics.addRaceCodeAssociation(raceCodeAssociation);
 		}else if (object instanceof Participant) {
 			Participant participant = (Participant) object;
			RaceCodeAssociation raceCodeAssociation = new RaceCodeAssociation();
	    	raceCodeAssociation.setRaceCode(RaceCodeEnum.valueOf((String) value));
	    	participant.addRaceCodeAssociation(raceCodeAssociation);
 		}
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    	RaceCodeAssociation raceCodeAssociation = (RaceCodeAssociation) object;
    	raceCodeAssociation.setRaceCode(null);
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
