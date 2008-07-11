package edu.duke.cabig.c3pr.domain.validator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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

    public ArmValidator getArmValidator() {
        return armValidator;
    }

    public void setArmValidator(ArmValidator armValidator) {
        this.armValidator = armValidator;
    }
}
