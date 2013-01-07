<%--
Copyright Duke Comprehensive Cancer Center and SemanticBits
 
  Distributed under the OSI-approved BSD 3-Clause License.
  See  https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><participanttags:htmlTitle subject="${command.participant}" /></title>
<style type="text/css">
        .labelR { width: 12em; text-align: right; padding: 4px; }
</style>
<style type="text/css">
        .label { width: 12em; text-align: left; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function updateTargetPage(target){
	document.formName._target0.value=s;
	document.formName.submit();
}

</script>
</head>
<body>
<form:form>
<tags:tabFields tab="${tab}"/>
</form:form>
<chrome:box title="Registration Summary">
	<c:choose>
		<c:when test="${fn:length(participantAssignments) == 0}">
			<fmt:message key="PARTICIPANT.NO_REGISTRATIONS"/>
      	</c:when>
      	<c:otherwise>
			<registrationTags:searchResults registrations="${participantAssignments }"/>	      		
     	</c:otherwise>
     </c:choose>
</chrome:box>

</body>
</html>



