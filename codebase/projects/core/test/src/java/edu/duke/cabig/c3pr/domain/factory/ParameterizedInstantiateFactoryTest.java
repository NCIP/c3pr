package edu.duke.cabig.c3pr.domain.factory;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.utils.extensions.DomainAbstractSubClass;
import edu.duke.cabig.c3pr.utils.extensions.DomainPrivateSubClass;
import edu.duke.cabig.c3pr.utils.extensions.DomainSubClass;

// TODO: Auto-generated Javadoc
/**
 * The Class ParameterizedInstantiateFactoryTest.
 */
public class ParameterizedInstantiateFactoryTest extends AbstractTestCase {

	/**
	 * Test create.
	 */
	public void testCreate(){
		ParameterizedInstantiateFactory<DomainSubClass> parameterizedInstantiateFactory= new ParameterizedInstantiateFactory<DomainSubClass>(DomainSubClass.class);
		DomainSubClass domainSubClass= parameterizedInstantiateFactory.create();
		assertNotNull(domainSubClass);
	}
	
	/**
	 * Test create illegal exception.
	 */
	public void testCreateIllegalException(){
		ParameterizedInstantiateFactory<DomainPrivateSubClass> parameterizedInstantiateFactory= new ParameterizedInstantiateFactory<DomainPrivateSubClass>(DomainPrivateSubClass.class);
		try {
			parameterizedInstantiateFactory.create();
			fail("Should have failed");
		} catch (RuntimeException e) {
			e.printStackTrace();
			assertEquals(e.getCause().getClass(), IllegalAccessException.class);
		}
		
	}
	
	/**
	 * Test create instantiation exception.
	 */
	public void testCreateInstantiationException(){
		ParameterizedInstantiateFactory<DomainAbstractSubClass> parameterizedInstantiateFactory= new ParameterizedInstantiateFactory<DomainAbstractSubClass>(DomainAbstractSubClass.class);
		try {
			parameterizedInstantiateFactory.create();
			fail("Should have failed");
		} catch (RuntimeException e) {
			e.printStackTrace();
			assertEquals(e.getCause().getClass(), InstantiationException.class);
		}
		
	}
	
}
