package edu.duke.cabig.c3pr.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.globus.gsi.GlobusCredential;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.EndPointType;
import edu.duke.cabig.c3pr.utils.StringUtils;

@Entity
@Table(name = "ENDPOINT_PROPS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "ENDPOINT_PROPS_ID_SEQ") })
public class EndPointConnectionProperty extends AbstractMutableDeletableDomainObject {
    private String url;
    
    private Boolean isAuthenticationRequired;
    
    private EndPointType endPointType;

    public EndPointConnectionProperty() {
        super();
    }
    
    @Enumerated(EnumType.STRING)
    public EndPointType getEndPointType() {
        return endPointType;
    }

    public void setEndPointType(EndPointType endPointType) {
        this.endPointType = endPointType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsAuthenticationRequired() {
        return isAuthenticationRequired;
    }

    public EndPointConnectionProperty(EndPointType endPointType) {
        super();
        this.endPointType = endPointType;
    }

    public void setIsAuthenticationRequired(Boolean isAuthenticationRequired) {
        this.isAuthenticationRequired = isAuthenticationRequired;
    }

    @Transient
    public boolean isEndPointDefined(){
        return endPointType!=null && !StringUtils.getBlankIfNull(url).equals("");
    }
    
}
