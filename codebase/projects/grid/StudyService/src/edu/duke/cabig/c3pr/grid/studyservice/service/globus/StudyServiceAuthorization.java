package edu.duke.cabig.c3pr.grid.studyservice.service.globus;


import java.rmi.RemoteException;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.MessageContext;

import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.impl.security.authorization.exceptions.CloseException;
import org.globus.wsrf.impl.security.authorization.exceptions.InitializeException;
import org.globus.wsrf.impl.security.authorization.exceptions.InvalidPolicyException;
import org.globus.wsrf.security.authorization.PDP;
import org.globus.wsrf.security.authorization.PDPConfig;
import org.w3c.dom.Node;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This is a PDP for use with the globus authorization callout.
 * This class will have a authorize<methodName> method for each method on this grid service.
 * The method is responsibe for making any authorization callouts required to satisfy the 
 * authorization requirements placed on each method call.  Each method will either return
 * apon a successful authorization or will throw an exception apon a failed authorization.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class StudyServiceAuthorization implements PDP {

	public static final String SERVICE_NAMESPACE = "http://studyservice.grid.c3pr.cabig.duke.edu/StudyService";
	
	
	public StudyServiceAuthorization() {
	}
	
	protected String getServiceNamespace(){
		return SERVICE_NAMESPACE;
	}
	
	public static String getCallerIdentity() {
		String caller = org.globus.wsrf.security.SecurityManager.getManager().getCaller();
		if ((caller == null) || (caller.equals("<anonymous>"))) {
			return null;
		} else {
			return caller;
		}
	}
					
	public static void authorizeGetMultipleResourceProperties() throws RemoteException {
		
		
	}
					
	public static void authorizeGetResourceProperty() throws RemoteException {
		
		
	}
					
	public static void authorizeQueryResourceProperties() throws RemoteException {
		
		
	}
					
	public static void authorizeGetServiceSecurityMetadata() throws RemoteException {
		
		
	}
					
	public static void authorizeActivateStudySite() throws RemoteException {
		
		
	}
					
	public static void authorizeAmendStudy() throws RemoteException {
		
		
	}
					
	public static void authorizeCloseStudyToAccrual() throws RemoteException {
		
		
	}
					
	public static void authorizeCloseStudySiteToAccrual() throws RemoteException {
		
		
	}
					
	public static void authorizeCreateStudyDefinition() throws RemoteException {
		
		
	}
					
	public static void authorizeOpenStudy() throws RemoteException {
		
		
	}
					
	public static void authorizeUpdateStudySiteProtocolVersion() throws RemoteException {
		
		
	}
					
	public static void authorizeUpdateStudy() throws RemoteException {
		
		
	}
					
	public static void authorizeGetStudy() throws RemoteException {
		
		
	}
					
	public static void authorizeCloseStudyToAccrualAndTreatment() throws RemoteException {
		
		
	}
					
	public static void authorizeTemporarilyCloseStudyToAccrual() throws RemoteException {
		
		
	}
					
	public static void authorizeTemporarilyCloseStudyToAccrualAndTreatment() throws RemoteException {
		
		
	}
					
	public static void authorizeCloseStudySiteToAccrualAndTreatment() throws RemoteException {
		
		
	}
					
	public static void authorizeTemporarilyCloseStudySiteToAccrualAndTreatment() throws RemoteException {
		
		
	}
					
	public static void authorizeTemporarilyCloseStudySiteToAccrual() throws RemoteException {
		
		
	}
	
	
	public boolean isPermitted(Subject peerSubject, MessageContext context, QName operation)
		throws AuthorizationException {
		
		if(!operation.getNamespaceURI().equals(getServiceNamespace())){
		  return false;
		}
		if(operation.getLocalPart().equals("getMultipleResourceProperties")){
			try{
				authorizeGetMultipleResourceProperties();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("getResourceProperty")){
			try{
				authorizeGetResourceProperty();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("queryResourceProperties")){
			try{
				authorizeQueryResourceProperties();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("getServiceSecurityMetadata")){
			try{
				authorizeGetServiceSecurityMetadata();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("activateStudySite")){
			try{
				authorizeActivateStudySite();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("amendStudy")){
			try{
				authorizeAmendStudy();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("closeStudyToAccrual")){
			try{
				authorizeCloseStudyToAccrual();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("closeStudySiteToAccrual")){
			try{
				authorizeCloseStudySiteToAccrual();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("createStudyDefinition")){
			try{
				authorizeCreateStudyDefinition();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("openStudy")){
			try{
				authorizeOpenStudy();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("updateStudySiteProtocolVersion")){
			try{
				authorizeUpdateStudySiteProtocolVersion();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("updateStudy")){
			try{
				authorizeUpdateStudy();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("getStudy")){
			try{
				authorizeGetStudy();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("closeStudyToAccrualAndTreatment")){
			try{
				authorizeCloseStudyToAccrualAndTreatment();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("temporarilyCloseStudyToAccrual")){
			try{
				authorizeTemporarilyCloseStudyToAccrual();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("temporarilyCloseStudyToAccrualAndTreatment")){
			try{
				authorizeTemporarilyCloseStudyToAccrualAndTreatment();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("closeStudySiteToAccrualAndTreatment")){
			try{
				authorizeCloseStudySiteToAccrualAndTreatment();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("temporarilyCloseStudySiteToAccrualAndTreatment")){
			try{
				authorizeTemporarilyCloseStudySiteToAccrualAndTreatment();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("temporarilyCloseStudySiteToAccrual")){
			try{
				authorizeTemporarilyCloseStudySiteToAccrual();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} 		
		return false;
	}
	

	public Node getPolicy(Node query) throws InvalidPolicyException {
		return null;
	}


	public String[] getPolicyNames() {
		return null;
	}


	public Node setPolicy(Node policy) throws InvalidPolicyException {
		return null;
	}


	public void close() throws CloseException {


	}


	public void initialize(PDPConfig config, String name, String id) throws InitializeException {

	}
	
	
}
