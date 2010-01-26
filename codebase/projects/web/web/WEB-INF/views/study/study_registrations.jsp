<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
<head>
<script type="text/javascript">
function sendLinkToCreateRegistration(){
	var studyId = ${command.study.id}
	createReg(studyId)
}
function createReg(studyId){
	$('createRegistration_studyId').value=studyId;
	$('fromStudyRegistrations').value=true;
	$('create').submit();
}
</script>
</head>
<!-- MAIN BODY STARTS HERE -->
<body>
<form action="../registration/createRegistration" method="get" id="create">  
	<input type="hidden" name="_target0" id="_target" value="0"/>
	<input type="hidden" name="createRegistration_studyId" id="createRegistration_studyId" value=""/>
	<input type="hidden" name="fromStudyRegistrations" id="fromStudyRegistrations" value="false"/>
</form>
<form:form name="form" method="post">
    <tags:tabFields tab="${tab}"/>
</form:form>
    <chrome:box title="${tab.shortTitle}">
    	<c:choose>
    		<c:when test="${fn:length(participantAssignments) != 0}">
    			<registrationTags:searchResults registrations="${participantAssignments }"/>
    		</c:when>
    		<c:otherwise>
				<fmt:message key="study.noRegistration"></fmt:message>    			
    		</c:otherwise>
    	</c:choose>
            
    </chrome:box>
<div align="right">
	<c:if test="${command.study.coordinatingCenterStudyStatus=='OPEN'}">
		<c:if test="${isOnlyStudyCoordinator=='false'}">
			<tags:button type="button" color="blue" icon="add" value="Register another subject" 
			onclick="javascript:sendLinkToCreateRegistration();" size="small"/>
		</c:if>
	</c:if>
</div>
</body>


<!-- MAIN BODY ENDS HERE -->
</html>