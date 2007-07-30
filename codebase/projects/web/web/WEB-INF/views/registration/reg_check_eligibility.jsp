<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function markAsAnswered(id){
	selectBox='scheduledEpoch.subjectEligibilityAnswers['+id+'].answerText';
	tick='tick-'+id;
	if(document.getElementById(selectBox).value==''){
		document.getElementById(tick).style.display='none';
	}else{
		document.getElementById(tick).style.display='block';
	}
}
</script>
</head>
<body>
<form:form method="post">
<tags:tabFields tab="${tab}"/>
<c:choose>
	<c:when test="${!command.ifTreatmentScheduledEpoch || fn:length(command.scheduledEpoch.subjectEligibilityAnswers) == 0}">
			<tags:panelBox>The Selected Study does not have Eligibility Crieterias</tags:panelBox>
	</c:when>
	<c:otherwise>
		<table id="" width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
			<tr>
				<td>
				<tags:minimizablePanelBox boxId="Inclusion" title="Inclusion Criteria">
					<table width="100%" border="0">
						<tr>
							<td>&nbsp;</td>
							<td align="left"><b>Question<span class="red">*</span></b></td>
							<td align="left"><b>Answers</b></td>
						</tr>
						<c:set var="index" value="0"/>
						<c:forEach var="criteria" varStatus="status" items="${command.scheduledEpoch.treatmentEpoch.inclusionEligibilityCriteria}">
							<tr>
								<td width="5%">
									<div id="tick-${index }" <c:if test="${command.scheduledEpoch.subjectEligibilityAnswers[index].answerText==null||command.scheduledEpoch.subjectEligibilityAnswers[index].answerText=='' }">style="display:none;"</c:if>>
										<img src="<tags:imageUrl name="checkbox.gif"/>" border="0" alt="answered" height="20" width="20">												
									</div>
								</td>
								<td width="80%">
									${criteria.questionText}
								</td>
								<td width="15%">
									<form:select id="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" path="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" onchange="markAsAnswered('${index }')">
										<option value="">--Please Select---</option>
										<form:option value="Yes" />
										<form:option value="No" />
										<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
									</form:select>
								</td>
							</tr>
							<c:set var="index" value="${index+1}"/>
						</c:forEach>
					</table>
				</tags:minimizablePanelBox>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
				<tags:minimizablePanelBox boxId="Exclusion" title="Exclusion Criteria">
					<table width="100%" border="0">
						<tr>
							<td>&nbsp;</td>
							<td align="left"><b>Question<span class="red">*</span></b></td>
							<td align="left"><b>Answers</b></td>
						</tr>
						<c:forEach var="criteria" varStatus="status" items="${command.scheduledEpoch.treatmentEpoch.exclusionEligibilityCriteria}">
							<tr>
								<td width="5%">
									<div id="tick-${index }" <c:if test="${command.scheduledEpoch.subjectEligibilityAnswers[index].answerText==null||command.scheduledEpoch.subjectEligibilityAnswers[index].answerText=='' }">style="display:none;"</c:if>>
										<img src="<tags:imageUrl name="checkbox.gif"/>" border="0" alt="answered" height="20" width="20">												
									</div>
								</td>
								<td width="80%">
									${criteria.questionText}
								</td>
								<td width="15%">
									<form:select id="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" path="scheduledEpoch.subjectEligibilityAnswers[${index}].answerText" onchange="markAsAnswered('${index }')">
										<option value="">--Please Select---</option>
										<form:option value="Yes" />
										<form:option value="No" />
										<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA" label="Not Applicable"/></c:if>
									</form:select>
								</td>
							</tr>
							<c:set var="index" value="${index+1}"/>										
						</c:forEach>
					</table>
				</tags:minimizablePanelBox>
				</td>
			</tr>
		</table>
	</c:otherwise>
</c:choose>
<!-- MAIN BODY ENDS HERE -->
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>
	</body>
</html>
