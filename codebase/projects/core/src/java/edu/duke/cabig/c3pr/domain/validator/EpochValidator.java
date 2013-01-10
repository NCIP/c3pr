/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.validator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;

public class EpochValidator implements Validator {
    private ArmValidator armValidator;

    private StudyValidator studyValidator;

    private Logger log = Logger.getLogger(EpochValidator.class);

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

    public boolean supports(Class clazz) {
        return Epoch.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        validateIndicators(target, errors);
        validateArms(target, errors);
    }

    public void validateArms(Object target, Errors errors) {
        Epoch epoch = (Epoch) target;
        List<Arm> allArms = epoch.getArms();

        try {

            for (int armIndex = 0; armIndex < allArms.size(); armIndex++) {
                errors.pushNestedPath("arms[" + armIndex + "]");
                ValidationUtils.invokeValidator(this.armValidator, allArms.get(armIndex), errors);
                errors.popNestedPath();
            }

            Set uniqueArms = new TreeSet<Arm>();
            uniqueArms.addAll(allArms);
            if (allArms.size() > uniqueArms.size()) {
                errors.rejectValue("arms", new Integer(studyValidator
                                .getCode("C3PR.STUDY.DUPLICATE.ARM.ERROR")).toString(),
                                studyValidator.getMessageFromCode(studyValidator
                                                .getCode("C3PR.STUDY.DUPLICATE.ARM.ERROR"), null,
                                                null));
            }

        }
        catch (Exception ex) {
            log.debug("error while validating arms");
        }
    }
    
    public void validateIndicators(Object target,Errors errors){
    	Epoch epoch = (Epoch) target;
    	if(epoch.getType() == EpochType.RESERVING &&(epoch.getEnrollmentIndicator() || epoch.getType() == EpochType.TREATMENT || epoch.getRandomizedIndicator())){
    		errors.rejectValue("reservationIndicator",new Integer(studyValidator
                    .getCode("C3PR.STUDY.EPOCH.INVALID_RESERVATION_INDICATOR.CODE")).toString(),
                    studyValidator.getMessageFromCode(studyValidator
                                    .getCode("C3PR.STUDY.EPOCH.INVALID_RESERVATION_INDICATOR.CODE"), null,
                                    null));
    	}
    	if(epoch.getRandomizedIndicator() && epoch.getType() != EpochType.TREATMENT){
    		errors.rejectValue("treatmentIndicator",new Integer(studyValidator
                    .getCode("C3PR.STUDY.EPOCH.INVALID_TREATMENT_INDICATOR.CODE")).toString(),
                    studyValidator.getMessageFromCode(studyValidator
                                    .getCode("C3PR.STUDY.EPOCH.INVALID_TREATMENT_INDICATOR.CODE"), null,
                                    null));
    	}
    	if(epoch.getType() == EpochType.TREATMENT && !epoch.getEnrollmentIndicator()){
    		errors.rejectValue("enrollmentIndicator",new Integer(studyValidator
                    .getCode("C3PR.STUDY.EPOCH.INVALID_ENROLLMENT_INDICATOR.CODE")).toString(),
                    studyValidator.getMessageFromCode(studyValidator
                                    .getCode("C3PR.STUDY.EPOCH.INVALID_ENROLLMENT_INDICATOR.CODE"), null,
                                    null));
    	}
    }

    public ArmValidator getArmValidator() {
        return armValidator;
    }

    public void setArmValidator(ArmValidator armValidator) {
        this.armValidator = armValidator;
    }
}
