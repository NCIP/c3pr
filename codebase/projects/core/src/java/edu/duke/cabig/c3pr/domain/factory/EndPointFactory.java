package edu.duke.cabig.c3pr.domain.factory;

import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.EndPointType;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
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
    
    public EndPoint getEndPoint(ServiceName multisiteServiceName, APIName multisiteAPIName, StudyOrganization studyOrganization){
        EndPointConnectionProperty endPointProperty=multisiteServiceName==ServiceName.STUDY?studyOrganization.getHealthcareSite().getStudyEndPointProperty():studyOrganization.getHealthcareSite().getRegistrationEndPointProperty();
        EndPoint endPoint=studyOrganization.getEndPoint(multisiteServiceName, multisiteAPIName);
        if(endPoint==null){
            endPoint=this.newInstance(multisiteServiceName, multisiteAPIName, endPointProperty);
            studyOrganization.addEndPoint(endPoint);
            endPoint.setStudyOrganization(studyOrganization);
        }else{
            if(endPoint instanceof GridEndPoint){
                ((GridEndPoint)endPoint).setGlobusCredential(getCredential());
            }
        }
        return endPoint;
    }

    private GlobusCredential getCredential(){
        if(delegatedCredentialProvider==null){
        	System.out.println("delegatedCredentialProvider is null, return null credential");
            return null;
        }
        GlobusCredential credential=delegatedCredentialProvider.provideDelegatedCredentials().getCredential();
        if(credential==null){
        	System.out.println("GlobusCredential is null, return null credential");
        }else{
        	System.out.println("found delegatedCredentialProvider, returning credential");
        }
        return credential;
    }

    public void setDelegatedCredentialProvider(DelegatedCredentialProvider delegatedCredentialProvider) {
        this.delegatedCredentialProvider = delegatedCredentialProvider;
    }
}
