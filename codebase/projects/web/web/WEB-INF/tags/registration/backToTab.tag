<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute  name="currentTab" required="true" %>
<%@attribute name="registration" type="edu.duke.cabig.c3pr.domain.StudySubject" required="true"%>
	
<!-- check for all tab numbers 
0 - select epoch and study
1 - Enrollment Detail
2 - Eligibility 
3 - Stratify
4 - Select Arm
5 - Review and Submit
 -->

	<c:set var="eligibiltyRequired" value ="false" ></c:set>
	<c:set var="stratificationRequired" value ="false" ></c:set>
	<c:set var="randomizationRequired" value ="false" ></c:set>
	<c:set var="backToTab" value ="4" scope="request"></c:set>
  	<c:if test="${!empty registration}">
		<c:if test="${fn:length(command.scheduledEpoch.subjectEligibilityAnswers) != 0}">
			<c:set var="eligibiltyRequired" value ="true" ></c:set>
		</c:if>				
		<c:if test="${command.scheduledEpoch.epoch.stratificationIndicator == 'true' }">
			<c:set var="stratificationRequired" value ="true" ></c:set>
		</c:if>
		<c:if test="${!empty command.scheduledEpoch.epoch.arms}">
			<c:set var="randomizationRequired" value ="true" ></c:set>		
		</c:if>
		<c:if test="${(currentTab == 4) && stratificationRequired == 'true' && backToTab == 4}">
			<c:set var="backToTab" value ="3" scope="request"></c:set>	
		</c:if>	
		<c:if test="${(currentTab == 4) && eligibiltyRequired == 'true' && backToTab == 4}">	
			<c:set var="backToTab" value ="2" scope="request"></c:set>	
		</c:if>	
		<c:if test="${(currentTab == 4) &&  backToTab == 4}">
			<c:set var="backToTab" value ="1" scope="request"></c:set>	
		</c:if>
		<c:if test="${(currentTab == 3) && eligibiltyRequired == 'true' && backToTab == 4}">	
			<c:set var="backToTab" value ="2" scope="request"></c:set>	
		</c:if>	
		<c:if test="${(currentTab == 3) &&  backToTab == 4}">
			<c:set var="backToTab" value ="1" scope="request"></c:set>	
		</c:if>
		<c:if test="${(currentTab == 2) &&  backToTab == 4}">
			<c:set var="backToTab" value ="1" scope="request"></c:set>	
		</c:if>
		<c:if test="${(currentTab == 1) &&  backToTab == 4}">
			<c:set var="backToTab" value ="0" scope="request"></c:set>	
		</c:if>
    </c:if>
