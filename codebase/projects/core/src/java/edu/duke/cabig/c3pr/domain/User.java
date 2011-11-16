package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.security.util.StringEncrypter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;

/**
 * This class represents the User domain object associated with the Adverse event report.
 * 
 */

@MappedSuperclass
public abstract class User extends C3PRUser{
		    
    private String salt;

    private String token;

    private Timestamp tokenTime;

    private Timestamp passwordLastSet;

    private int numFailedLogins;

    private List<String> passwordHistory;
    
	protected Date lastLoginAttemptTime;


    public User() {
        passwordHistory = new ArrayList<String>();
    }
    
    /* begin password stuff */

    @Column(name = "salt")
    public String getSalt() {
        return salt == null ? "" : salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void resetToken() {
        this.tokenTime = new Timestamp(0);
    }

    @Column(name = "token_time")
    public Timestamp getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(Timestamp tokenTime) {
        this.tokenTime = tokenTime;
    }

    @Column(name = "password_last_set")
    public Timestamp getPasswordLastSet() {
        return passwordLastSet == null ? new Timestamp(0) : passwordLastSet;
    }

    public void setPasswordLastSet(Timestamp passwordLastSet) {
        this.passwordLastSet = passwordLastSet;
    }

    @Transient
    public long getPasswordAge() {
    	long age = (new Date().getTime() - getPasswordLastSet().getTime())/1000;    
        return age;
    }

    @CollectionOfElements
    @JoinTable(name = "password_history", joinColumns = @JoinColumn(name = "user_id"))
    @IndexColumn(name = "list_index")
    @Column(name = "password")
    public List<String> getPasswordHistory() {
        return passwordHistory;
    }

    public void setPasswordHistory(List<String> passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    public void addPasswordToHistory(String password, int maxHistorySize) {
        passwordHistory.add(password);
        while (passwordHistory.size() > maxHistorySize && maxHistorySize > 0)
            passwordHistory.remove(0);
    }

    @Column(name = "num_failed_logins")
    public int getFailedLoginAttempts() {
        return numFailedLogins;
    }

    public void setFailedLoginAttempts(int numFailedLogins) {
        this.numFailedLogins = numFailedLogins;
    }
    
    
    /**
     * Gets the last failed login attempt time.
     *
     * @return the last failed login attempt time
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "last_login")
	public Date getLastFailedLoginAttemptTime() {
		return lastLoginAttemptTime;
	}

	/**
	 * Sets the last failed login attempt time.
	 *
	 * @param lastLoginAttemptTime the new last failed login attempt time
	 */
	public void setLastFailedLoginAttemptTime(Date lastLoginAttemptTime) {
		this.lastLoginAttemptTime = lastLoginAttemptTime;
	}    
    

    /* end password stuff */


    @Transient
    public String getLastFirst() {
        StringBuilder name = new StringBuilder();
        boolean hasFirstName = getFirstName() != null;
        if (getLastName() != null) {
            name.append(getLastName());
            if (hasFirstName) {
                name.append(", ");
            }
        }
        if (hasFirstName) {
            name.append(getFirstName());
        }
        return name.toString();
    }

    @Transient
    public String getFullName() {
        StringBuilder name = new StringBuilder();
        boolean hasLastName = getLastName() != null;
        if (getFirstName() != null) {
            name.append(getFirstName());
            if (hasLastName) {
                name.append(' ');
            }
        }
        if (hasLastName) {
            name.append(getLastName());
        }
        return name.toString();
    }
    
    public String generateRandomToken(){
    	return encryptString((StringUtils.isEmpty(salt) ? "" : salt ) + tokenTime.toString()
                + "random_string").replaceAll("\\W", "Q");
    }
    
    public String generatePassword(){
    	return encryptString((StringUtils.isEmpty(salt) ? "" : salt ) + "obscurity");
    }
    
    private String encryptString(String string) {
        try {
            return new StringEncrypter().encrypt(string);
        } catch (StringEncrypter.EncryptionException e) {
            throw new RuntimeException(e);
        }
    }
    
	/**
	 * Calculates the time past last failed login attempt
	 * This property is used in determining the account lock out.
	 *
	 * @return seconds past last failed login attempts
	 */
	@Transient
	public long getSecondsPastLastFailedLoginAttempt(){
    	if(getLastFailedLoginAttemptTime()==null) return -1;
    	return (new Date().getTime()-getLastFailedLoginAttemptTime().getTime())/1000;
    }    
}
