<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute  name="currentTab" required="true" %>
<%@attribute name="registration" type="edu.duke.cabig.c3pr.domain.StudySubject" required="true"%>
	
<!-- check for all tab numbers 
0 - select epoch and study
1 - Enrollment Detail
2 - Eligibility 
3 - Stratifycommand
4 - Select Arm
5 - Review and Submit
 -->

	<c:set var="eligibiltyRequired" value ="false" ></c:set>
	<c:set var="stratificationRequired" value ="false" ></c:set>
	<c:set var="randomizationRequired" value ="false" ></c:set>
	<c:set var="goToTab" value ="1" scope="request"></c:set>
  	<c:if test="${!empty registration}">
		<c:if test="${fn:length(command.studySubject.scheduledEpoch.subjectEligibilityAnswers) != 0}">
			<c:set var="eligibiltyRequired" value ="true" ></c:set>
		</c:if>				
		<c:if test="${command.studySubject.scheduledEpoch.epoch.stratificationIndicator == 'true' }">
			<c:set var="stratificationRequired" value ="true" ></c:set>
		</c:if>
		<c:if test="${!empty command.studySubject.scheduledEpoch.epoch.arms}">
			<c:set var="randomizationRequired" value ="true" ></c:set>		
		</c:if>
		<c:if test="${(currentTab == 1) && eligibiltyRequired == 'true' && goToTab == 1}">	
			<c:set var="goToTab" value ="2" scope="request"></c:set>	
		</c:if>	
		<c:if test="${(currentTab == 1) && stratificationRequired == 'true' && goToTab == 1}">
			<c:set var="goToTab" value ="3" scope="request"></c:set>	
		</c:if>	
		<c:if test="${(currentTab == 1) && randomizationRequired == 'true' && goToTab == 1}">
			<c:set var="goToTab" value ="4" scope="request"></c:set>	
		</c:if>	
	
		<c:if test="${(currentTab == 2) && stratificationRequired == 'true' && goToTab == 1}">
			<c:set var="goToTab" value ="3" scope="request"></c:set>	
		</c:if>	
		<c:if test="${(currentTab == 2) && randomizationRequired == 'true' && goToTab == 1}">
			<c:set var="goToTab" value ="4" scope="request" ></c:set>	
		</c:if>	
		<c:if test="${(currentTab == 2) && goToTab == 1}">
			<c:set var="goToTab" value ="2" scope="request" ></c:set>	
		</c:if>
		<c:if test="${(currentTab == 3) && randomizationRequired == 'true' && goToTab == 1}">
			<c:set var="goToTab" value ="4" scope="request"></c:set>	
		</c:if>	

		<c:if test="${(currentTab == 3) && goToTab == 1}">
			<c:set var="goToTab" value ="3" scope="request"></c:set>	
		</c:if>
		
		<c:if test="${(currentTab == 4) && goToTab == 1}">
			<c:set var="goToTab" value ="4" scope="request"></c:set>	
		</c:if>	
    </c:if>
