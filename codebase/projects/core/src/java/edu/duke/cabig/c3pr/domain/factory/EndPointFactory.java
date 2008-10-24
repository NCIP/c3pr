package edu.duke.cabig.c3pr.domain.factory;

import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.EndPointType;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.esb.DelegatedCredentialProvider;

public class EndPointFactory {

    private DelegatedCredentialProvider delegatedCredentialProvider;
    
    public EndPoint newInstance(ServiceName multisiteServiceName, APIName multisiteAPIName, EndPointConnectionProperty endPointProperty){
        EndPoint endPoint=null;
        if(endPointProperty.getEndPointType()==EndPointType.GRID){
            endPoint=new GridEndPoint(endPointProperty, multisiteServiceName, multisiteAPIName, getCredential());
        }
        
        return endPoint;
    }

    private GlobusCredential getCredential(){
        if(delegatedCredentialProvider==null)
            return null;
        return delegatedCredentialProvider.provideDelegatedCredentials().getCredential();
    }

    public void setDelegatedCredentialProvider(DelegatedCredentialProvider delegatedCredentialProvider) {
        this.delegatedCredentialProvider = delegatedCredentialProvider;
    }
}
