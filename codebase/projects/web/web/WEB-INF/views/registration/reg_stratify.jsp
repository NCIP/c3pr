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
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	id="table1">
	<c:choose>
	<c:when test="${fn:length(command.scheduledEpoch.subjectStratificationAnswers) == 0}">
		<tr>
			<td class="label" align=left>The Selected Study does not have Stratification Factors</td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr>
			<td>&nbsp;</td>
			<td align="left"><b>Criteria<span class="red">*</span></b></td>
			<td align="left"><b>Answers</b></td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
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
					<option value="">--Please Select---</option>
					<c:forEach items="${criteria.stratificationCriterion.permissibleAnswers}" var="option">
						<option value="${option.id }" <c:if test="${option.id== command.scheduledEpoch.subjectStratificationAnswers[status.index].stratificationCriterionAnswer.id}">selected</c:if>>${option.permissibleAnswer }</option>
					</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr><td colspan="3"><hr align="left" width="100%"></td></tr>
			
		</c:forEach>
	</c:otherwise>
	</c:choose>
</table>
</tags:formPanelBox>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
