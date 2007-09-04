package edu.duke.cabig.c3pr.domain.validator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;

public class EpochValidator implements Validator {
	ArmValidator armValidator;

	public boolean supports(Class clazz) {
		return Epoch.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required",
				"required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required",
				"required field");
	}

	public void valiateArms(Object target, Errors errors) {
		Epoch epoch = (Epoch) target;
		if (epoch instanceof TreatmentEpoch) {
			List<Arm> allArms = ((TreatmentEpoch) epoch).getArms();

			try {
				errors.pushNestedPath("arms");
				for (Arm arm : allArms) {
					ValidationUtils.invokeValidator(this.armValidator, arm,
							errors);
				}

				Set uniqueArms = new TreeSet<Arm>();
				uniqueArms.addAll(allArms);
				if (allArms.size() > uniqueArms.size()) {
					errors.reject("Arm alredy exists");
				}

			} finally {
				errors.popNestedPath();
			}
		}
	}

	// TODO add arm validator logic

	public ArmValidator getArmValidator() {
		return armValidator;
	}

	public void setArmValidator(ArmValidator armValidator) {
		this.armValidator = armValidator;
	}
}
