/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.passwordpolicy;

import javax.persistence.Embeddable;

@Embeddable
public class CombinationPolicy {

    private int minimumRequired;

    private boolean upperCaseAlphabetRequired;

    private boolean lowerCaseAlphabetRequired;

    private boolean nonAlphaNumericRequired;

    private boolean baseTenDigitRequired;

    private int maxSubstringLength;

    public int getMinimumRequired() {
        return minimumRequired;
    }

    public void setMinimumRequired(int minimumRequired) {
        this.minimumRequired = minimumRequired;
    }

    public boolean isUpperCaseAlphabetRequired() {
        return upperCaseAlphabetRequired;
    }

    public void setUpperCaseAlphabetRequired(boolean upperCaseAlphabetRequired) {
        this.upperCaseAlphabetRequired = upperCaseAlphabetRequired;
    }

    public boolean isLowerCaseAlphabetRequired() {
        return lowerCaseAlphabetRequired;
    }

    public void setLowerCaseAlphabetRequired(boolean lowerCaseAlphabetRequired) {
        this.lowerCaseAlphabetRequired = lowerCaseAlphabetRequired;
    }

    public boolean isNonAlphaNumericRequired() {
        return nonAlphaNumericRequired;
    }

    public void setNonAlphaNumericRequired(boolean nonAlphaNumericRequired) {
        this.nonAlphaNumericRequired = nonAlphaNumericRequired;
    }

    public boolean isBaseTenDigitRequired() {
        return baseTenDigitRequired;
    }

    public void setBaseTenDigitRequired(boolean baseTenDigitRequired) {
        this.baseTenDigitRequired = baseTenDigitRequired;
    }

    public int getMaxSubstringLength() {
        return maxSubstringLength;
    }

    public void setMaxSubstringLength(int maxSubstringLength) {
        this.maxSubstringLength = maxSubstringLength;
    }

    public String toString() {
        return "The password must:\n"
                        + (upperCaseAlphabetRequired ? "Contain at least one uppercase letter.\n"
                                        : "")
                        + (lowerCaseAlphabetRequired ? "Contain at least one lowercase letter.\n"
                                        : "")
                        + (nonAlphaNumericRequired ? "Contain at least one non-alphanumeric character.\n"
                                        : "")
                        + (baseTenDigitRequired ? "Contain at least one base ten digit.\n" : "")
                        + "The password may not contain a substring longer than "
                        + maxSubstringLength + " characters from the username.\n";
    }
}
