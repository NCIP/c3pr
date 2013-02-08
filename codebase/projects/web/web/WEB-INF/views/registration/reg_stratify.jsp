<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
	 <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}

</script>
</head>
<body>


	<c:choose>
	<c:when test="${command.studySubject.scheduledEpoch.epoch.stratificationIndicator == 'false' || fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers)==0}">
		<tags:formPanelBox tab="${tab}" flow="${flow}" boxClass="grayed-out">
		<div><fmt:message key="REGISTRATION.NO_STRATIFICATION"/></div><br>
		</tags:formPanelBox>
	</c:when>
	<c:otherwise>
	<tags:formPanelBox tab="${tab}" flow="${flow}">
	<input type="hidden" name="_validate" id="_validate"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<th align="left"><b><fmt:message key="study.criterion"/></b></th>
			<th align="left"><b><fmt:message key="study.answers"/></b></th>
		</tr>
		<c:forEach var="criteria" varStatus="status" items="${command.studySubject.scheduledEpoch.subjectStratificationAnswers}">
			<tr>
				<td><tags:requiredIndicator />
					${criteria.stratificationCriterion.questionText}
				</td>
				<td width="15%">
					<form:select path="studySubject.scheduledEpoch.subjectStratificationAnswers[${status.index}].stratificationCriterionAnswer" cssClass="required validate-notEmpty">
					<option value="">Please select</option>
					<c:forEach items="${criteria.stratificationCriterion.permissibleAnswers}" var="option">
						<option value="${option.id }" <c:if test="${option.id== command.studySubject.scheduledEpoch.subjectStratificationAnswers[status.index].stratificationCriterionAnswer.id}">selected</c:if>>${option.permissibleAnswer }</option>
					</c:forEach>
					</form:select>
				</td>
			</tr>
			
		</c:forEach>
		</table>
		</tags:formPanelBox>
	</c:otherwise>
	</c:choose>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
