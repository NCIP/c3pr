/**
 *    Test Registration Consumer. Just logs the incoming message
 */
package gov.nih.nci.cabig.c3pr.grid;

import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.ScheduledTreatmentEpochType;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;

import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.springframework.context.MessageSource;

public class CoOrdinatingSiteRegistrationConsumer implements RegistrationConsumer {

    private C3PRExceptionHelper exceptionHelper;

    private MessageSource multsiteErrorMessageSource;


    private StudySubjectService studySubjectService;
    
    private XmlMarshaller marshaller;

    public void setMarshaller(XmlMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    public CoOrdinatingSiteRegistrationConsumer() {

    }

    /*
      * (non-Javadoc)
      *
      * @see gov.nih.nci.cabig.ctms.common.RegistrationConsumer#createRegistration(gov.nih.nci.cabig.ctms.grid.RegistrationType)
      */

    public Registration register(Registration registration)	throws InvalidRegistrationException,RegistrationConsumptionException, RemoteException {
        InvalidRegistrationException invalidRegistrationException = new InvalidRegistrationException();
        System.out.println("Registration received with Grid Id "
                + registration.getGridId());
        StringWriter sw=new StringWriter();
        String registrationXml="";
        try {
			Utils.serializeObject(registration, new QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain","registration"), sw);
			registrationXml=sw.toString();
			System.out.println(registrationXml);
			StudySubject studySubject=studySubjectService.buildStudySubject((StudySubject)marshaller.fromXML(new StringReader(registrationXml)));
			studySubject.setRegDataEntryStatus(studySubjectService.evaluateRegistrationDataEntryStatus(studySubject));
			studySubject.getScheduledEpoch().setScEpochDataEntryStatus(studySubjectService.evaluateScheduledEpochDataEntryStatus(studySubject));
			this.studySubjectService.processAffliateSiteRegistrationRequest(studySubject);
		}catch (InvalidRegistrationException e){
            throw e;
        }catch (C3PRCodedException e) {
            e.printStackTrace();
            invalidRegistrationException.setFaultCode(e.getExceptionCode()+"");
            invalidRegistrationException.setFaultDetailString(e.getMessage());
            invalidRegistrationException.setStackTrace(e.getStackTrace());
            throw invalidRegistrationException;
        }catch (Exception e) {
            e.printStackTrace();
            invalidRegistrationException.setFaultCode("-1");
            invalidRegistrationException.setFaultDetailString(e.getMessage());
            invalidRegistrationException.setStackTrace(e.getStackTrace());
            throw invalidRegistrationException;
        }
        return registration;
    }


    public void rollback(Registration registration) throws RemoteException, InvalidRegistrationException {
        throw new UnsupportedOperationException("Transaction support not implemented");
    }

    public void commit(Registration registration) throws RemoteException, InvalidRegistrationException {
        throw new UnsupportedOperationException("Transaction support not implemented");
    }

    public ServiceSecurityMetadata getServiceSecurityMetadata()
            throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    private int getCode(String errortypeString){
        return Integer.parseInt(this.multsiteErrorMessageSource.getMessage(errortypeString, null, null));
    }

    public void setMultsiteErrorMessageSource(
            MessageSource multsiteErrorMessageSource) {
        this.multsiteErrorMessageSource = multsiteErrorMessageSource;
    }

}