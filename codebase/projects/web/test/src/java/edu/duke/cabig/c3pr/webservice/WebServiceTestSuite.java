/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice;

import junit.framework.Test;
import junit.framework.TestSuite;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImplTest;
import edu.duke.cabig.c3pr.webservice.converters.SubjectRegistryJAXBToDomainObjectConverterImplTest;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityImplTest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImplTest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistryImplTest;

public class WebServiceTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for edu.duke.cabig.c3pr.webservice");
		//$JUnit-BEGIN$
		suite.addTestSuite(JAXBToDomainObjectConverterImplTest.class);
		suite.addTestSuite(SubjectManagementImplTest.class);
		suite.addTestSuite(SubjectRegistryJAXBToDomainObjectConverterImplTest.class);
		suite.addTestSuite(SubjectRegistryImplTest.class);
		suite.addTestSuite(StudyUtilityImplTest.class);
		//$JUnit-END$
		return suite;
	}

}
