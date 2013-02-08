/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.factory;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.extensions.DomainAbstractSubClass;
import edu.duke.cabig.c3pr.utils.extensions.DomainPrivateSubClass;
import edu.duke.cabig.c3pr.utils.extensions.DomainSubClass;
import edu.duke.cabig.c3pr.utils.extensions.StudySiteSubClass;
import gov.nih.nci.cabig.ctms.domain.DomainObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ParameterizedInstantiateFactoryTest.
 */
public class ParameterizedBiDirectionalInstantiateFactoryTest extends AbstractTestCase {

	
	/**
	 * Test get class to instantiate.
	 */
	public void testGetClassToInstantiate(){
		StudySiteSubClass studySiteSubClass= new StudySiteSubClass();
		studySiteSubClass.setId(2);
		ParameterizedBiDirectionalInstantiateFactory<DomainAbstractSubClass> parameterizedInstantiateFactory= new ParameterizedBiDirectionalInstantiateFactory<DomainAbstractSubClass>(DomainAbstractSubClass.class,studySiteSubClass);
		assertEquals(DomainAbstractSubClass.class, parameterizedInstantiateFactory.getClassToInstantiate());
	}
	
	/**
	 * Test set class to instantiate.
	 */
	public void testSetClassToInstantiate(){
		StudySiteSubClass studySiteSubClass= new StudySiteSubClass();
		studySiteSubClass.setId(2);
		ParameterizedBiDirectionalInstantiateFactory<DomainAbstractSubClass> parameterizedInstantiateFactory= new ParameterizedBiDirectionalInstantiateFactory<DomainAbstractSubClass>(DomainAbstractSubClass.class,studySiteSubClass);
		parameterizedInstantiateFactory.setClassToInstantiate(AbstractMutableDeletableDomainObject.class);
		assertEquals(AbstractMutableDeletableDomainObject.class, parameterizedInstantiateFactory.getClassToInstantiate());
	}
	
	/**
	 * Test create.
	 */
	public void testCreate(){
		StudySiteSubClass studySiteSubClass= new StudySiteSubClass();
		studySiteSubClass.setId(2);
		ParameterizedBiDirectionalInstantiateFactory<DomainSubClass> parameterizedInstantiateFactory= new ParameterizedBiDirectionalInstantiateFactory<DomainSubClass>(DomainSubClass.class, studySiteSubClass);
		DomainSubClass domainSubClass= parameterizedInstantiateFactory.create();
		assertNotNull(domainSubClass);
		assertNotNull(domainSubClass.getStudySiteSubClass());
		assertEquals(2, domainSubClass.getStudySiteSubClass().getId().intValue());
	}
	
	/**
	 * Test create illegal exception.
	 */
	public void testCreateIllegalException(){
		StudySiteSubClass studySiteSubClass= new StudySiteSubClass();
		studySiteSubClass.setId(2);
		try {
			new ParameterizedBiDirectionalInstantiateFactory<DomainPrivateSubClass>(DomainPrivateSubClass.class,studySiteSubClass);
			fail("Should have failed");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test create null object.
	 */
	public void testCreateInstantiationException(){
		StudySiteSubClass studySiteSubClass= new StudySiteSubClass();
		studySiteSubClass.setId(2);
		ParameterizedBiDirectionalInstantiateFactory<DomainAbstractSubClass> parameterizedInstantiateFactory= new ParameterizedBiDirectionalInstantiateFactory<DomainAbstractSubClass>(DomainAbstractSubClass.class,studySiteSubClass);
		DomainObject domainObject=parameterizedInstantiateFactory.create();
		assertNull(domainObject);
	}
}

