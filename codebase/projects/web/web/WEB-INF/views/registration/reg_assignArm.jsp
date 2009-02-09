<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script>

	function randomize(){
		var elmt = document.getElementById("scheduledEpoch.scheduledArms[0].arm");
		if(elmt == null || elmt.value != ""){
			<tags:tabMethod method="randomize" divElement="'randomizationMessage'" /> 
		}
		else {
			new Insertion.After(elmt, "<span id='"+elmt.name+"-msg'style='color:#EE3324'>*"+required+"</span>")			
		}			
	}
	
</script>
<style>
	#main {
		top:35px;
	}
</style>
</head>
<body>

	<c:choose>
	 <c:when test="${empty command.studySubject.scheduledEpoch.epoch.arms}">
		<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
			<div align="center"><fmt:message key="REGISTRATION.NO_ARM_ASSIGNMENT_INVOLVED"/></div><br>
		</tags:formPanelBox>
	 </c:when>
	<c:otherwise>
		<c:choose>
		<c:when test="${!command.studySubject.scheduledEpoch.requiresRandomization}">
		  <tags:formPanelBox tab="${tab}" flow="${flow}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
			  <tr>
			    <td class="label" width="80%"><fmt:message key="registration.selectArm"/></td>
				  <td>
					<select name="studySubject.scheduledEpoch.scheduledArms[0].arm">
						<option value="" selected>Please Select</option>
						<c:forEach items="${command.studySubject.scheduledEpoch.epoch.arms}" var="arm">
							<option value="${arm.id }" <c:if test="${!empty command.studySubject.scheduledEpoch.scheduledArms[0].arm && arm.id== command.studySubject.scheduledEpoch.scheduledArms[0].arm.id }">selected</c:if>>${arm.name}</option>
						</c:forEach>
					</select>
				  </td>
			   </tr>
			</table>
		  </tags:formPanelBox>
		</c:when>
		<c:when test="${command.studySubject.studySite.study.blindedIndicator}">
	      <tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
				<div><fmt:message key="REGISTRATION.NO_BLINDED_ARM_ASSIGNMENT"/></div><br>
          </tags:formPanelBox>
		</c:when>
		<c:otherwise>
			<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
				<div><fmt:message key="REGISTRATION.RANDOMIZED_ARM_ASSIGNMENT"/></div><br>
		    </tags:formPanelBox>
		</c:otherwise>
		</c:choose>
	
	</c:otherwise>
	</c:choose>

</body>
</html>
