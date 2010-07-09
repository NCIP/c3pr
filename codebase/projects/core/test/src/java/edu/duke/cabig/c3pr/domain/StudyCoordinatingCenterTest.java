package edu.duke.cabig.c3pr.domain;

import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.EndPointType;
import edu.duke.cabig.c3pr.constants.ServiceName;

/**
 * The Class PhoneCallRandomizationTest.
 */
public class StudyCoordinatingCenterTest extends AbstractTestCase{
	
	/**
	 * Test get possible endpoints.
	 */
	public void testGetPossibleEndpoints(){
		StudyCoordinatingCenter studyCoordinatingCenter = getStudyOrgWithEndPoints();
		 List<APIName> apiList = studyCoordinatingCenter.getPossibleEndpoints();
		 
		 assertTrue(((APIName)apiList.get(0)).getCode().equals(APIName.ENROLL_SUBJECT.getCode()));
	}
	
	/**
	 * Gets the study org with end points.
	 * 
	 * @return the study org with end points
	 */
	private StudyCoordinatingCenter getStudyOrgWithEndPoints() {
		
		StudyCoordinatingCenter studyCoordinatingCenter = new StudyCoordinatingCenter();
		GridEndPoint endPointStudy = new GridEndPoint();
		endPointStudy.setServiceName(ServiceName.REGISTRATION);

		EndPointConnectionProperty endPointConnectionProperty = new EndPointConnectionProperty();
		endPointConnectionProperty.setEndPointType(EndPointType.GRID);
		endPointConnectionProperty.setUrl("https://url.com");
		
		endPointStudy.setEndPointProperty(endPointConnectionProperty);
		endPointStudy.setApiName(APIName.ENROLL_SUBJECT);
		studyCoordinatingCenter.addEndPoint(endPointStudy);
		return studyCoordinatingCenter;
	}
}

