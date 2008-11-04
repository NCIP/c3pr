package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "Errors")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "errors_id_seq") })
public class Error extends AbstractMutableDeletableDomainObject implements Comparable<Error>{

    private String errorSource;
    
    private String errorCode;
    
    private String errorMessage;
    
    private Date errorDate;

	public Error(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

    public Error(String errorSource, String errorCode, String errorMessage) {
        super();
        this.errorSource = errorSource;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDate = new Date();
    }

    public Error() {
        super();
        // TODO Auto-generated constructor stub
    }


    @Temporal(TemporalType.TIMESTAMP)
    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

    public String getErrorSource() {
        return errorSource;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public int compareTo(Error error) {
        return this.errorDate.compareTo(error.errorDate);
    }
}