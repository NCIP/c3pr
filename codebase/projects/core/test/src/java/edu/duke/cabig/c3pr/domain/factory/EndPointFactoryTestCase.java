package edu.duke.cabig.c3pr.domain.factory;

import org.easymock.classextension.EasyMock;
import org.globus.gsi.GlobusCredential;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.EndPointType;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.esb.DelegatedCredential;
import edu.duke.cabig.c3pr.esb.DelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class EndPointFactoryTestCase.
 */
public class EndPointFactoryTestCase extends AbstractTestCase {

	/** The end point factory. */
	private EndPointFactory endPointFactory;
	
	/** The delegated credential provider. */
	private DelegatedCredentialProvider delegatedCredentialProvider;
	
	/** The end point property. */
	private EndPointConnectionProperty endPointProperty;
	
	/** The delegated credential. */
	private DelegatedCredential delegatedCredential;
	
	/** The globus credential. */
	private GlobusCredential globusCredential;
	
	/** The study organization. */
	private StudyOrganization studyOrganization;
	
	/** The healthcare site. */
	private HealthcareSite healthcareSite;
	
	/** The grid end point. */
	private GridEndPoint gridEndPoint;
	
	/** The exception helper. */
	private C3PRExceptionHelper exceptionHelper;
	
	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		endPointFactory= new EndPointFactory();
		delegatedCredentialProvider= registerMockFor(DelegatedCredentialProvider.class);
		endPointFactory.setDelegatedCredentialProvider(delegatedCredentialProvider);
		exceptionHelper= registerMockFor(C3PRExceptionHelper.class);
		endPointFactory.setExceptionHelper(exceptionHelper);
		c3prErrorMessages= registerMockFor(MessageSource.class);
		endPointFactory.setC3prErrorMessages(c3prErrorMessages);
		endPointProperty= registerMockFor(EndPointConnectionProperty.class);
		delegatedCredential= registerMockFor(DelegatedCredential.class);
		globusCredential= registerMockFor(GlobusCredential.class);
		studyOrganization= registerMockFor(StudyOrganization.class);
		healthcareSite= registerMockFor(HealthcareSite.class);
		studyOrganization= registerMockFor(StudyOrganization.class);
		gridEndPoint= registerMockFor(GridEndPoint.class);
	}
	
	
	/**
	 * Test new instance.
	 * endpointType: not GRID
	 */
	public void testNewInstanceNullEndpointType(){
		EasyMock.expect(endPointProperty.getEndPointType()).andReturn(EndPointType.JMS);
		replayMocks();
		assertNull(endPointFactory.newInstance(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE, endPointProperty));
		verifyMocks();
	}
	
	/**
	 * Test new instance.
	 * endpointType: GRID
	 */
	public void testNewInstance(){
		EasyMock.expect(delegatedCredentialProvider.provideDelegatedCredentials()).andReturn(delegatedCredential);
		EasyMock.expect(delegatedCredential.getCredential()).andReturn(globusCredential);
		EasyMock.expect(endPointProperty.getEndPointType()).andReturn(EndPointType.GRID);
		replayMocks();
		assertNotNull(endPointFactory.newInstance(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE, endPointProperty));
		verifyMocks();
	}
	
	/**
	 * Test new instance.
	 * endpointType: GRID
	 * delegatedCredentialProvider: null
	 */
	public void testGetCredentialNullCredential1(){
		endPointFactory.setDelegatedCredentialProvider(null);
		EasyMock.expect(endPointProperty.getEndPointType()).andReturn(EndPointType.GRID);
		replayMocks();
		assertNotNull(endPointFactory.newInstance(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE, endPointProperty));
		verifyMocks();
	}
	
	/**
	 * Test get credential.
	 * delegatedCredentialProvider: not null
	 * delegatedCredential: not null
	 * globusCredential: null
	 */
	public void testGetCredentialNullCredential2(){
		EasyMock.expect(delegatedCredentialProvider.provideDelegatedCredentials()).andReturn(delegatedCredential);
		EasyMock.expect(delegatedCredential.getCredential()).andReturn(null);
		EasyMock.expect(endPointProperty.getEndPointType()).andReturn(EndPointType.GRID);
		replayMocks();
		assertNotNull(endPointFactory.newInstance(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE, endPointProperty));
		verifyMocks();
	}
	
	/**
	 * Test get endpoint when an endpoint exists.
	 */
	public void testGetEndpointExistingEndpoint(){
		EasyMock.expect(studyOrganization.getEndPoint(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE)).andReturn(gridEndPoint);
		EasyMock.expect(delegatedCredentialProvider.provideDelegatedCredentials()).andReturn(delegatedCredential);
		EasyMock.expect(delegatedCredential.getCredential()).andReturn(globusCredential);
		gridEndPoint.setGlobusCredential(globusCredential);
		replayMocks();
		assertNotNull(endPointFactory.getEndPoint(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE, studyOrganization));
		verifyMocks();
	}
	
	/**
	 * Test get endpoint.
	 */
	public void testGetEndpointException(){
		C3PRCodedRuntimeException c3CodedRuntimeException= new C3PRCodedRuntimeException(1,"Test");
		EasyMock.expect(studyOrganization.getEndPoint(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE)).andReturn(null);
		EasyMock.expect(studyOrganization.getHealthcareSite()).andReturn(healthcareSite).times(2);
		EasyMock.expect(healthcareSite.getRegistrationEndPointProperty()).andReturn(endPointProperty);
		EasyMock.expect(endPointProperty.getEndPointType()).andReturn(EndPointType.JMS);
		EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.ENDPOINT_INCORRECT_CONFIGURATION.CODE", null, null)).andReturn("1");
		EasyMock.expect(healthcareSite.getName()).andReturn("Test");
		EasyMock.expect(exceptionHelper.getRuntimeException(EasyMock.anyInt(),EasyMock.isA(String[].class))).andReturn(c3CodedRuntimeException);
		replayMocks();
		try {
			endPointFactory.getEndPoint(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE, studyOrganization);
			fail("Should have failed");
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(1, e.getExceptionCode());
		}finally{
			verifyMocks();
		}
		verifyMocks();
	}
	
	/**
	 * Test get endpoint.
	 * serviceName: REGISTRATION
	 */
	public void testGetEndpointRegistrationService(){
		endPointFactory.setDelegatedCredentialProvider(null);
		EasyMock.expect(studyOrganization.getEndPoint(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE)).andReturn(null);
		EasyMock.expect(studyOrganization.getHealthcareSite()).andReturn(healthcareSite);
		EasyMock.expect(healthcareSite.getRegistrationEndPointProperty()).andReturn(endPointProperty);
		EasyMock.expect(endPointProperty.getEndPointType()).andReturn(EndPointType.GRID);
		studyOrganization.addEndPoint(EasyMock.isA(EndPoint.class));
		replayMocks();
		EndPoint endPoint= endPointFactory.getEndPoint(ServiceName.REGISTRATION, APIName.ACTIVATE_STUDY_SITE, studyOrganization);
		assertNotNull(endPoint);
		assertNotNull(endPoint.getStudyOrganization());
		verifyMocks();
	}
	
}
