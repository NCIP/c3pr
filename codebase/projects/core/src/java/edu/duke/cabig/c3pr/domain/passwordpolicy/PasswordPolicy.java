package edu.duke.cabig.c3pr.domain.passwordpolicy;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.annotations.Validatable;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

@Entity
@Table(name = "password_policy")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "seq_password_policy_id") })
@Validatable
public class PasswordPolicy extends AbstractMutableDomainObject {

    private static final long TOKEN_TIMEOUT_MS = 48 * 60 * 60 * 1000;

    private PasswordCreationPolicy passwordCreationPolicy;

    private LoginPolicy loginPolicy;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "minPasswordAge", column = @Column(name = "cn_min_age")),
            @AttributeOverride(name = "passwordHistorySize", column = @Column(name = "cn_history_size")),
            @AttributeOverride(name = "minPasswordLength", column = @Column(name = "cn_min_length")),
            @AttributeOverride(name = "combinationPolicy.minimumRequired", column = @Column(name = "cn_cb_min_required")),
            @AttributeOverride(name = "combinationPolicy.upperCaseAlphabetRequired", column = @Column(name = "cn_cb_is_upper_case_alphabet")),
            @AttributeOverride(name = "combinationPolicy.lowerCaseAlphabetRequired", column = @Column(name = "cn_cb_is_lower_case_alphabet")),
            @AttributeOverride(name = "combinationPolicy.nonAlphaNumericRequired", column = @Column(name = "cn_cb_is_non_alpha_numeric")),
            @AttributeOverride(name = "combinationPolicy.baseTenDigitRequired", column = @Column(name = "cn_cb_is_base_ten_digit")),
            @AttributeOverride(name = "combinationPolicy.maxSubstringLength", column = @Column(name = "cn_cb_max_substring_length")) })
    public PasswordCreationPolicy getPasswordCreationPolicy() {
        return passwordCreationPolicy;
    }

    public void setPasswordCreationPolicy(PasswordCreationPolicy passwordCreationPolicy) {
        this.passwordCreationPolicy = passwordCreationPolicy;
    }

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "allowedFailedLoginAttempts", column = @Column(name = "ln_allowed_attempts")),
            @AttributeOverride(name = "lockOutDuration", column = @Column(name = "ln_lockout_duration")),
            @AttributeOverride(name = "maxPasswordAge", column = @Column(name = "ln_max_age")) })
    public LoginPolicy getLoginPolicy() {
        return loginPolicy;
    }

    public void setLoginPolicy(LoginPolicy loginPolicy) {
        this.loginPolicy = loginPolicy;
    }

    @Transient
    public long getTokenTimeout() {
        return TOKEN_TIMEOUT_MS;
    }

    public String toString() {
        return loginPolicy.toString() + "\n" + passwordCreationPolicy.toString();
    }
//    private static final long TOKEN_TIMEOUT_MS = 48 * 60 * 60 * 1000;
//
//    private PasswordCreationPolicy passwordCreationPolicy;
//
//    private LoginPolicy loginPolicy;
//    
//    private int minAge;
//    private int  historySize;
//    private int minLength;
//    private int minRequired;
//    private boolean isUpperCaseAlphabet;
//    private boolean isLowerCaseAlphabet;
//    private boolean isNonAlphaNumeric;
//    private boolean isBaseTenDigit;
//    private int maxSubstringLength;
//    private int allowedAttempts;
//    private int lockoutDuration;
//    private int maxAge;
//
//     public LoginPolicy getLoginPolicy() {
//        return loginPolicy;
//    }
//
//    public void setLoginPolicy(LoginPolicy loginPolicy) {
//        this.loginPolicy = loginPolicy;
//    }
//
//    @Transient
//    public long getTokenTimeout() {
//        return TOKEN_TIMEOUT_MS;
//    }
//
//    public String toString() {
//        return loginPolicy.toString() + "\n" + passwordCreationPolicy.toString();
//    }
//    
//    public PasswordCreationPolicy getPasswordCreationPolicy() {
//        return passwordCreationPolicy;
//    }
//
//    public void setPasswordCreationPolicy(PasswordCreationPolicy passwordCreationPolicy) {
//        this.passwordCreationPolicy = passwordCreationPolicy;
//    }
//    
//    @Column(name="cn_min_age")
//	public int getMinAge() {
//		return minAge;
//	}
//
//	public void setMinAge(int minAge) {
//		this.minAge = minAge;
//	}
//	
//	 @Column(name="cn_history_size")
//	public int getHistorySize() {
//		return historySize;
//	}
//
//	public void setHistorySize(int historySize) {
//		this.historySize = historySize;
//	}
//
//	 @Column(name="cn_min_length")
//	public int getMinLength() {
//		return minLength;
//	}
//
//	public void setMinLength(int minLength) {
//		this.minLength = minLength;
//	}
//
//	 @Column(name="cn_cb_min_required")
//	public int getMinRequired() {
//		return minRequired;
//	}
//
//	public void setMinRequired(int minRequired) {
//		this.minRequired = minRequired;
//	}
//
//	 @Column(name="cn_cb_is_upper_case_alphabet")
//	public boolean isUpperCaseAlphabet() {
//		return isUpperCaseAlphabet;
//	}
//
//	public void setUpperCaseAlphabet(boolean isUpperCaseAlphabet) {
//		this.isUpperCaseAlphabet = isUpperCaseAlphabet;
//	}
//
//	 @Column(name="cn_cb_is_lower_case_alphabet")
//	public boolean isLowerCaseAlphabet() {
//		return isLowerCaseAlphabet;
//	}
//
//	public void setLowerCaseAlphabet(boolean isLowerCaseAlphabet) {
//		this.isLowerCaseAlphabet = isLowerCaseAlphabet;
//	}
//
//	 @Column(name="cn_cb_is_non_alpha_numeric")
//	public boolean isNonAlphaNumeric() {
//		return isNonAlphaNumeric;
//	}
//
//	public void setNonAlphaNumeric(boolean isNonAlphaNumeric) {
//		this.isNonAlphaNumeric = isNonAlphaNumeric;
//	}
//
//	 @Column(name="cn_cb_is_base_ten_digit")
//	public boolean isBaseTenDigit() {
//		return isBaseTenDigit;
//	}
//
//	public void setBaseTenDigit(boolean isBaseTenDigit) {
//		this.isBaseTenDigit = isBaseTenDigit;
//	}
//
//	 @Column(name="cn_cb_is_base_ten_digit")
//	public int getMaxSubstringLength() {
//		return maxSubstringLength;
//	}
//
//	public void setMaxSubstringLength(int maxSubstringLength) {
//		this.maxSubstringLength = maxSubstringLength;
//	}
//
//	 @Column(name="ln_allowed_attempts")
//	public int getAllowedAttempts() {
//		return allowedAttempts;
//	}
//
//	public void setAllowedAttempts(int allowedAttempts) {
//		this.allowedAttempts = allowedAttempts;
//	}
//
//	 @Column(name="ln_lockout_duration")
//	public int getLockoutDuration() {
//		return lockoutDuration;
//	}
//
//	public void setLockoutDuration(int lockoutDuration) {
//		this.lockoutDuration = lockoutDuration;
//	}
//
//	 @Column(name="ln_max_age")	
//	public int getMaxAge() {
//		return maxAge;
//	}
//
//	public void setMaxAge(int maxAge) {
//		this.maxAge = maxAge;
//	}
}
