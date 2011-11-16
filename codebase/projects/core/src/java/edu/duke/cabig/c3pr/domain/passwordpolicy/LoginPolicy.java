package edu.duke.cabig.c3pr.domain.passwordpolicy;

import javax.persistence.Embeddable;

import edu.duke.cabig.c3pr.annotations.NumInRange;

@Embeddable
public class LoginPolicy {

    private int allowedFailedLoginAttempts;

    private int lockOutDuration;

    private int maxPasswordAge;
    
    private int allowedLoginTime;

    @NumInRange(min = 0)
    public int getAllowedFailedLoginAttempts() {
        return allowedFailedLoginAttempts;
    }

    public void setAllowedFailedLoginAttempts(int allowedFailedLoginAttempts) {
        this.allowedFailedLoginAttempts = allowedFailedLoginAttempts;
    }

    @NumInRange(min = 0)
    public int getLockOutDuration() {
        return lockOutDuration;
    }

    public void setLockOutDuration(int lockOutDuration) {
        this.lockOutDuration = lockOutDuration;
    }

    /* hard-coded to min at a week for now */
    @NumInRange(min = 604800)
    public int getMaxPasswordAge() {
        return maxPasswordAge;
    }

    public void setMaxPasswordAge(int maxPasswordAge) {
        this.maxPasswordAge = maxPasswordAge;
    }
    
	/**
	 * Gets the allowed login time.
	 *
	 * @return the allowed login time
	 */
	public int getAllowedLoginTime() {
		return allowedLoginTime;
	}

	/**
	 * Sets the allowed login time.
	 *
	 * @param allowedLoginTime the new allowed login time
	 */
	public void setAllowedLoginTime(int allowedLoginTime) {
		this.allowedLoginTime = allowedLoginTime;
	}

    public String toString() {
        return "Maximum password age is " + maxPasswordAge + " (seconds).\n"
                        + "Number of allowed login attempts is " + allowedFailedLoginAttempts
                        + ".\n" + "The lockout duration is " + lockOutDuration + " (seconds).\n";
    }
}
