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
	selectBox='subjectEligibilityAnswers['+id+'].answerText';
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
<div><tabs:division id="check-eligibility">
<!-- MAIN BODY STARTS HERE -->
<div class="workArea">
<form:form method="post">
<tabs:tabFields tab="${tab}" />
<c:choose>
	<c:when test="${fn:length(command.subjectEligibilityAnswers) == 0}">
			The Selected Study does not have Eligibility Crieterias</td>
	</c:when>
	<c:otherwise>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="table1">
			<tr>
				<td>
					<table id="" width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
						<tr>
							<td>
							<tags:panel id="Inclusion" title="Inclusion Criteria">
								<table width="100%" border="0">
									<tr>
										<td>&nbsp;</td>
										<td align="left"><b>Question<span class="red">*</span></b></td>
										<td align="left"><b>Answers</b></td>
									</tr>
									<c:set var="index" value="0"/>
									<c:forEach var="criteria" varStatus="status" items="${command.studySite.study.incCriterias}">
										<tr>
											<td width="5%">
												<div id="tick-${index }" <c:if test="${command.subjectEligibilityAnswers[index].answerText==null||command.subjectEligibilityAnswers[index].answerText=='' }">style="display:none;"</c:if>>
													<img src="<tags:imageUrl name="checkbox.gif"/>" border="0" alt="answered" height="20" width="20">												
												</div>
											</td>
											<td width="80%">
												${criteria.questionText}
											</td>
											<td width="15%">
												<form:select id="subjectEligibilityAnswers[${index}].answerText" path="subjectEligibilityAnswers[${index}].answerText" onchange="markAsAnswered('${index }')">
													<option value="">--Please Select---</option>
													<form:option value="Yes" />
													<form:option value="No" />
													<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA"/></c:if>
												</form:select>
											</td>
										</tr>
										<c:set var="index" value="${index+1}"/>
									</c:forEach>
								</table>
							</tags:panel>
							</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td>
							<tags:panel id="Exclusion" title="Exclusion Criteria">
								<table width="100%" border="0">
									<tr>
										<td>&nbsp;</td>
										<td align="left"><b>Question<span class="red">*</span></b></td>
										<td align="left"><b>Answers</b></td>
									</tr>
									<c:forEach var="criteria" varStatus="status" items="${command.studySite.study.excCriterias}">
										<tr>
											<td width="5%">
												<div id="tick-${index }" <c:if test="${command.subjectEligibilityAnswers[index].answerText==null||command.subjectEligibilityAnswers[index].answerText=='' }">style="display:none;"</c:if>>
													<img src="<tags:imageUrl name="checkbox.gif"/>" border="0" alt="answered" height="20" width="20">												
												</div>
											</td>
											<td width="80%">
												${criteria.questionText}
											</td>
											<td width="15%">
												<form:select id="subjectEligibilityAnswers[${index}].answerText" path="subjectEligibilityAnswers[${index}].answerText" onchange="markAsAnswered('${index }')">
													<option value="">--Please Select---</option>
													<form:option value="Yes" />
													<form:option value="No" />
													<c:if test="${criteria.notApplicableIndicator}"><form:option value="NA"/></c:if>
												</form:select>
											</td>
										</tr>
										<c:set var="index" value="${index+1}"/>										
									</c:forEach>
								</table>
							</tags:panel>
							</td>
						</tr>
					</table>
					</div>					
				</td>
			</tr>
		</table>
	</c:otherwise>
</c:choose>
</form:form>
</div>
</tabs:division>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
