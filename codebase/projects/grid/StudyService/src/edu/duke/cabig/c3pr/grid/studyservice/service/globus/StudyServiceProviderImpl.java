/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.studyservice.service.globus;

import edu.duke.cabig.c3pr.grid.studyservice.service.StudyServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the StudyServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class StudyServiceProviderImpl{
	
	StudyServiceImpl impl;
	
	public StudyServiceProviderImpl() throws RemoteException {
		impl = new StudyServiceImpl();
	}
	

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteResponse activateStudySite(edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteResponse();
    impl.activateStudySite(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyResponse amendStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyResponse();
    impl.amendStudy(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualResponse closeStudyToAccrual(edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualResponse();
    impl.closeStudyToAccrual(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualResponse closeStudySiteToAccrual(edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualResponse();
    impl.closeStudySiteToAccrual(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionResponse createStudyDefinition(edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionResponse();
    impl.createStudyDefinition(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyResponse openStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyResponse();
    impl.openStudy(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionResponse updateStudySiteProtocolVersion(edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionResponse();
    impl.updateStudySiteProtocolVersion(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyResponse updateStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyResponse();
    impl.updateStudy(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyResponse getStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyResponse();
    boxedResult.setMessage(impl.getStudy(params.getMessage().getMessage()));
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentResponse closeStudyToAccrualAndTreatment(edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentResponse();
    impl.closeStudyToAccrualAndTreatment(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualResponse temporarilyCloseStudyToAccrual(edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualResponse();
    impl.temporarilyCloseStudyToAccrual(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentResponse temporarilyCloseStudyToAccrualAndTreatment(edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentResponse();
    impl.temporarilyCloseStudyToAccrualAndTreatment(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentResponse closeStudySiteToAccrualAndTreatment(edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentResponse();
    impl.closeStudySiteToAccrualAndTreatment(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentResponse temporarilyCloseStudySiteToAccrualAndTreatment(edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentResponse();
    impl.temporarilyCloseStudySiteToAccrualAndTreatment(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualResponse temporarilyCloseStudySiteToAccrual(edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualResponse();
    impl.temporarilyCloseStudySiteToAccrual(params.getMessage().getMessage());
    return boxedResult;
  }

    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyResponse createAndOpenStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyRequest params) throws RemoteException {
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyResponse boxedResult = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyResponse();
    impl.createAndOpenStudy(params.getMessage().getMessage());
    return boxedResult;
  }

}
