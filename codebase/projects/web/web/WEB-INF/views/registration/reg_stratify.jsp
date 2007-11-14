<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function markAsAnswered(id){
	selectBox='scheduledEpoch.subjectStratificationAnswers['+id+'].stratificationCriterionAnswer';
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
<tags:formPanelBox tab="${tab}" flow="${flow}">

	<c:choose>
	<c:when test="${!command.ifTreatmentScheduledEpoch}">
		<b><br><fmt:message key="REGISTRATION.NO_STRATIFICATION"/>
	</c:when>
	<c:otherwise>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		<tr>
			<td>&nbsp;</td>
			<th align="left"><b>Criteria</b></th>
			<th align="left"><b>Answers</b></th>
		</tr>
		<c:forEach var="criteria" varStatus="status" items="${command.scheduledEpoch.subjectStratificationAnswers}">
			<tr>
				<td width="5%">
					<div id="tick-${status.index }" <c:if test="${criteria.stratificationCriterionAnswer==null||criteria.stratificationCriterionAnswer=='' }">style="display:none;"</c:if>>
						<img src="<tags:imageUrl name="checkbox.gif"/>" border="0" alt="answered" height="20" width="20">												
					</div>
				</td>
				<td >
					${criteria.stratificationCriterion.questionText}
				</td>
				<td width="15%">
					<form:select path="scheduledEpoch.subjectStratificationAnswers[${status.index}].stratificationCriterionAnswer" onchange="markAsAnswered('${status.index }')">
					<option value="">Please select</option>
					<c:forEach items="${criteria.stratificationCriterion.permissibleAnswers}" var="option">
						<option value="${option.id }" <c:if test="${option.id== command.scheduledEpoch.subjectStratificationAnswers[status.index].stratificationCriterionAnswer.id}">selected</c:if>>${option.permissibleAnswer }</option>
					</c:forEach>
					</form:select>
				</td>
			</tr>
			
		</c:forEach>
		</table>
	</c:otherwise>
	</c:choose>
</tags:formPanelBox>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
