<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
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
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
		<c:choose>
		<c:when test="${not empty command.studySubject.scheduledEpoch.epoch.arms}">
			<tr>
				<td> The selected epoch does not involve Assigning Arm.</td>
			</tr>
		</c:when>
		<c:when test="${command.studySubject.studySite.study.randomizedIndicator == 'false'}">
			<c:if test="${fn:length(command.studySubject.scheduledEpoch.epoch.arms) > 0}">
				<tr>
				<td class="label" width="80%"><fmt:message key="registration.selectArm"/></td>
					<td>
						<select name ="scheduledEpoch.scheduledArms[0].arm">
							<option value="" selected>--Please Select--</option>
							<c:forEach items="${command.studySubject.scheduledEpoch.epoch.arms}" var="arm">
								<option value="${arm.id }" <c:if test="${!empty command.studySubject.scheduledEpoch.scheduledArms[0].arm && arm.id== command.studySubject.scheduledEpoch.scheduledArms[0].arm.id }">selected</c:if>>${arm.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(command.studySubject.scheduledEpoch.epoch.arms) == 0}">
				<tr>
					<td> The selected epoch does not involve Assigning Arm.</td>
				</tr>
			</c:if>			
		</c:when>
		<%-- BOOK  --%>		
		<c:when test="${command.studySubject.studySite.study.randomizationType.name == 'BOOK'}">
			This epoch requires Book Randomization. Click <b>Randomize</b> to randomize the subject 
			on the epoch.
		</c:when>
		<%-- BOOK --%>		
		<%-- CALLOUT --%>		
		<c:when test="${command.studySubject.studySite.study.randomizationType.name == 'CALL_OUT'}">
			This epoch requires Call Out Randomization.
		</c:when>
		<%-- CALLOUT --%>		
		<%-- PHONECALL --%>
		<c:when test="${command.studySubject.studySite.study.randomizationType.name == 'PHONE_CALL'}">
			<tr><td>This epoch requires Phone Call Randomization. Call ${command.studySubject.scheduledEpoch.epoch.randomization.phoneNumberString} to get the Arm assignment.</td></tr>
			<tr>
			<td class="label" width="80%"><fmt:message key="registration.stratumGroup"/>: </td>
			<td>${command.studySubject.stratumGroup}</td>
			</tr>
			<tr>
				<td class="label" width="80%"><fmt:message key="registration.selectArm"/></td>
				<td>
					<select name ="scheduledEpoch.scheduledArms[0].arm">
						<option value="" selected>--Please Select--</option>
						<c:forEach items="${command.studySubject.scheduledEpoch.epoch.arms}" var="arm">
							<option value="${arm.id }" <c:if test="${!empty command.studySubject.scheduledEpoch.scheduledArms[0].arm && arm.id== command.studySubject.scheduledEpoch.scheduledArms[0].arm.id }">selected</c:if>>${arm.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</c:when>
		<%-- PHONECALL --%>	
		<c:otherwise>
			<tr>
				<td>Invalid State.</td>
			</tr>
		</c:otherwise>
		</c:choose>
	</table>
	
	<c:if test="${command.studySubject.studySite.study.randomizedIndicator == 'true'}">
		<div id="randomizationMessage">
		</div>
		<br/><input class='ibutton' type='button' onclick="randomize()" value='Randomize' title='Randomize'/>		
	</c:if>
		
</tags:formPanelBox>
</body>
</html>
