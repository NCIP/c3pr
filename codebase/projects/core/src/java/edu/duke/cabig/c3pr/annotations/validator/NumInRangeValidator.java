package edu.duke.cabig.c3pr.annotations.validator;


import edu.duke.cabig.c3pr.annotations.NumInRange;

/**
 */

public class NumInRangeValidator {

    public NumInRangeValidator() {
    }

    public String message(NumInRange range) {
        return "Value is out of range [" + range.min() + ", " + range.max() + "]";
    }

    public void validateAnnotated(NumInRange range, String fieldName, Object fieldValue) {
    }
}

