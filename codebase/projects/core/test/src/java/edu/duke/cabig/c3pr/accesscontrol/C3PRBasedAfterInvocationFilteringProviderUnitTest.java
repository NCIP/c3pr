package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Study;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.security.acegi.csm.authorization.AuthorizationSwitch;

public class C3PRBasedAfterInvocationFilteringProviderUnitTest extends AbstractTestCase{

	private AuthorizationSwitch authorizationSwitch;
	
	private String processConfigAttribute;
	
	private LinkedHashMap<Class<? extends AbstractMutableDomainObject> , DomainObjectSecurityFilterer> domainObjectC3PRAuhthorizationCheckProvidersMap= new LinkedHashMap<Class<? extends AbstractMutableDomainObject> , DomainObjectSecurityFilterer>();
	
	private C3PRBasedAfterInvocationFilteringProvider c3AfterInvocationCollectionFilteringProvider;
	
	private DomainObjectSecurityFilterer domainObjectSecurityFilterer;
	
	private Authentication authentication;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		c3AfterInvocationCollectionFilteringProvider = new C3PRBasedAfterInvocationFilteringProvider();
		processConfigAttribute = "C3PR_AUTH_CHECK";
		domainObjectSecurityFilterer = registerMockFor(DomainObjectSecurityFilterer.class);
		domainObjectC3PRAuhthorizationCheckProvidersMap.put(Study.class, domainObjectSecurityFilterer);
		authorizationSwitch = registerMockFor(AuthorizationSwitch.class);
		c3AfterInvocationCollectionFilteringProvider.setAuthorizationSwitch(authorizationSwitch);
		c3AfterInvocationCollectionFilteringProvider.setDomainObjectC3PRAuhthorizationCheckProvidersMap(domainObjectC3PRAuhthorizationCheckProvidersMap);
		c3AfterInvocationCollectionFilteringProvider.setProcessConfigAttribute(processConfigAttribute);
		authentication = registerMockFor(Authentication.class);
	}
	
	public void testDecideNull(){
		assertNull(c3AfterInvocationCollectionFilteringProvider.decide(authentication, null, null, null));
	}
	
	public void testDecideAuthorizationOFF(){
		List objectList= Arrays.asList(new Object[]{});
		EasyMock.expect(authorizationSwitch.isOn()).andReturn(false);
		replayMocks();
		assertEquals(objectList , c3AfterInvocationCollectionFilteringProvider.decide(authentication, null, null, objectList));
		verifyMocks();
	}
	
	public void testDecideAuthorizationEmptyCollection(){
		List objectList= Arrays.asList(new Object[]{});
		EasyMock.expect(authorizationSwitch.isOn()).andReturn(true);
		replayMocks();
		assertEquals(objectList , c3AfterInvocationCollectionFilteringProvider.decide(authentication, null, null, objectList));
		verifyMocks();
	}
	
	public void testDecideCollectionDomainObjectMappingAbsent(){
		List objectList= Arrays.asList(new Object[]{new LocalHealthcareSite()});
		EasyMock.expect(authorizationSwitch.isOn()).andReturn(true);
		replayMocks();
		assertEquals(objectList , c3AfterInvocationCollectionFilteringProvider.decide(authentication, null, null, objectList));
		verifyMocks();
	}
	
	public void testDecideCollectionNotAuthorized(){
		List objectList= Arrays.asList(new Object[]{new LocalStudy()});
		EasyMock.expect(authorizationSwitch.isOn()).andReturn(true);
		EasyMock.expect(domainObjectSecurityFilterer.filter(EasyMock.isA(Authentication.class), EasyMock.isA(String.class), EasyMock.isA(CollectionFilterer.class))).andReturn(new ArrayList());
		replayMocks();
		assertEquals(0 , ((Collection)c3AfterInvocationCollectionFilteringProvider.decide(authentication, null, null, objectList)).size());
		verifyMocks();
	}
	
	public void testDecideCollectionAuthorized(){
		List objectList= Arrays.asList(new Object[]{new LocalStudy()});
		EasyMock.expect(authorizationSwitch.isOn()).andReturn(true);
		EasyMock.expect(domainObjectSecurityFilterer.filter(EasyMock.isA(Authentication.class), EasyMock.isA(String.class), EasyMock.isA(CollectionFilterer.class))).andReturn(objectList);
		replayMocks();
		assertEquals(objectList , c3AfterInvocationCollectionFilteringProvider.decide(authentication, null, null, objectList));
		verifyMocks();
	}
	
	public void testDecideDomainObjectNotAuthorized(){
		Study study = new LocalStudy();
		EasyMock.expect(authorizationSwitch.isOn()).andReturn(true);
		EasyMock.expect(domainObjectSecurityFilterer.filter(EasyMock.isA(Authentication.class), EasyMock.isA(String.class), EasyMock.isA(AbstractMutableDomainObjectFilterer.class))).andThrow(new AccessDeniedException(""));
		replayMocks();
		try{
			c3AfterInvocationCollectionFilteringProvider.decide(authentication, null, null, study);
			fail("Should have thrown exception");
		}catch(AccessDeniedException ade){
		}catch (Exception e) {
			fail("Wrong exception"+e);
		}finally{
			verifyMocks();
		}
	}
	
	public void testDecideDomainObjectAuthorized(){
		Study study = new LocalStudy();
		EasyMock.expect(authorizationSwitch.isOn()).andReturn(true);
		EasyMock.expect(domainObjectSecurityFilterer.filter(EasyMock.isA(Authentication.class), EasyMock.isA(String.class), EasyMock.isA(AbstractMutableDomainObjectFilterer.class))).andReturn(study);
		replayMocks();
		try{
			assertNotNull(c3AfterInvocationCollectionFilteringProvider.decide(authentication, null, null, study));
		}catch (Exception e) {
			fail("Shouldn't have thrown exception"+e);
		}finally{
			verifyMocks();
		}
	}
	
}
