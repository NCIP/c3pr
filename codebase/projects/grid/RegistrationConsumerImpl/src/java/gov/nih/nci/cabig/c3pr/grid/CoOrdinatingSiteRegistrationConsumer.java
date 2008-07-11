/**
 *    Test Registration Consumer. Just logs the incoming message
 */
package gov.nih.nci.cabig.c3pr.grid;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;
import gov.nih.nci.common.exception.XMLUtilityException;

import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class CoOrdinatingSiteRegistrationConsumer implements RegistrationConsumer {

    private OpenSessionInViewInterceptor openSessionInViewInterceptor;

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

    private WebRequest preProcess() {
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        openSessionInViewInterceptor.preHandle(webRequest);
        return webRequest;
    }

    private void postProcess(WebRequest webRequest) {
        openSessionInViewInterceptor.afterCompletion(webRequest, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cabig.ctms.common.RegistrationConsumer#createRegistration(gov.nih.nci.cabig.ctms.grid.RegistrationType)
     */

    public Registration register(Registration registration) throws InvalidRegistrationException,
                    RegistrationConsumptionException, RemoteException {
        InvalidRegistrationException invalidRegistrationException = new InvalidRegistrationException();
        System.out.println("Registration received with Grid Id " + registration.getGridId());
        StringWriter sw = new StringWriter();
        StudySubject studySubject = null;
        String registrationXml = "";
        Registration returnRegistration = null;
        WebRequest webRequest = null;
        try {
            webRequest = preProcess();
            Utils.serializeObject(registration, new QName(
                            "gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "registration"),
                            sw);
            registrationXml = sw.toString();
            System.out.println(registrationXml);
            studySubjectService.processAffliateSiteRegistrationRequest((StudySubject) marshaller
                            .fromXML(new StringReader(registrationXml)));
            sw = new StringWriter();
            marshaller.toXML(studySubject, sw);
            registration = (Registration) Utils.deserializeObject(new StringReader(sw.toString()),
                            Registration.class);
        }
        catch (InvalidRegistrationException e) {
            throw e;
        }
        catch (C3PRCodedException e) {
            e.printStackTrace();
            invalidRegistrationException.setFaultCode(e.getExceptionCode() + "");
            invalidRegistrationException.setFaultDetailString(e.getMessage());
            invalidRegistrationException.setStackTrace(e.getStackTrace());
            throw invalidRegistrationException;
        }
        catch (XMLUtilityException e) {
            e.printStackTrace();
            invalidRegistrationException.setFaultCode("-1");
            invalidRegistrationException.setFaultDetailString(e.getMessage());
            invalidRegistrationException.setStackTrace(e.getStackTrace());
            throw invalidRegistrationException;
        }
        catch (Exception e) {
            e.printStackTrace();
            invalidRegistrationException.setFaultCode("-1");
            invalidRegistrationException.setFaultDetailString(e.getMessage());
            invalidRegistrationException.setStackTrace(e.getStackTrace());
            throw invalidRegistrationException;
        }
        finally {
            postProcess(webRequest);
        }
        return returnRegistration;
    }

    public void rollback(Registration registration) throws RemoteException,
                    InvalidRegistrationException {
        throw new UnsupportedOperationException("Transaction support not implemented");
    }

    public void commit(Registration registration) throws RemoteException,
                    InvalidRegistrationException {
        throw new UnsupportedOperationException("Transaction support not implemented");
    }

    public ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void setOpenSessionInViewInterceptor(
                    OpenSessionInViewInterceptor openSessionInViewInterceptor) {
        this.openSessionInViewInterceptor = openSessionInViewInterceptor;
    }

}