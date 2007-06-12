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
function toggleTextArea(a,b){
	if(document.getElementById(a).checked==true){
		document.getElementById('command').reset();
		new Effect.OpenUp(document.getElementById(b));
		document.getElementById(a).checked=true;
		document.getElementById('criterias').style.display='none';
		document.getElementById('eligibilityIndicator').name='_eligibilityIndicator';
		document.getElementById('eligibilityIndicator').value='1';		
	}else{
		new Effect.CloseDown(document.getElementById(b));
		document.getElementById('criterias').style.display='block';
		document.getElementById('eligibilityIndicator').name='eligibilityIndicator';
		document.getElementById('eligibilityIndicator').value='on';		
	}
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

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
		<form:form method="post">
		<tabs:tabFields tab="${tab}" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="table1">
			<tr>
				<td>
					<table width="50%">
						<tr>
							<td width="30%" align=left><b>Waive Eligibility</td>
							<td align="left">
								<input type="checkbox" id='eligibilityInd' onClick="toggleTextArea('eligibilityInd','WaiveEligibility')" <c:if test="${!command.eligibilityIndicator }">checked="checked"</c:if>/>
								<input type="hidden" value="${command.eligibilityIndicator?'on':'1' }" id="eligibilityIndicator" name="${command.eligibilityIndicator?'eligibilityIndicator':'_eligibilityIndicator' }"/> 
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<div id="WaiveEligibility" <c:if test="${command.eligibilityIndicator }">style="display:none;"</c:if>>
								<form:textarea path="eligibilityWaiverReasonText" rows="5" cols="50" />
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div id="criterias" <c:if test="${!command.eligibilityIndicator }">style="display:none;"</c:if>>
					<table id="" width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
						<tr>
							<td>
							<tags:minimizablePanelBox boxId="Inclusion" title="Inclusion Criterias">
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
												<select id="subjectEligibilityAnswers[${index}].answerText" name="subjectEligibilityAnswers[${index}].answerText" onchange="markAsAnswered('${index }')">
													<option value="">--Please Select---</option>
													<option value="Yes" ${command.subjectEligibilityAnswers[index].answerText=='Yes'?'selected':'' }>Yes</option>
													<option value="No" ${command.subjectEligibilityAnswers[index].answerText=='No'?'selected':'' }>No</option>
													<c:if test="${criteria.notApplicableIndicator}"><option value="NA" ${command.subjectEligibilityAnswers[index].answerText=='NA'?'selected':'' }>NA</option></c:if>
												</select>
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
							<tags:minimizablePanelBox boxId="Exclusion" title="Exclusion Criterias">
								<table width="100%" border="0">
									<tr>
										<td>&nbsp;</td>
										<td align="left"><b>Question<span class="red">*</span></b></td>
										<td align="left"><b>Answers</b></td>
									</tr>
									<c:forEach var="criteria" varStatus="status" items="${command.studySite.study.excCriterias}">
										<tr>
											<td width="5%">
												<div id="tick-${index }" <c:if test="${command.subjectEligibilityAnswers[index].answerText==null }">style="display:none;"</c:if>>
													<img src="<tags:imageUrl name="checkbox.gif"/>" border="0" alt="answered" height="20" width="20">												
												</div>
											</td>
											<td width="80%">
												${criteria.questionText}
											</td>
											<td width="15%">
												<select id="subjectEligibilityAnswers[${index}].answerText" name="subjectEligibilityAnswers[${index}].answerText" onchange="markAsAnswered('${index }')">
													<option value="">--Please Select---</option>
													<option value="Yes" ${command.subjectEligibilityAnswers[index].answerText=='Yes'?'selected':'' }>Yes</option>
													<option value="No" ${command.subjectEligibilityAnswers[index].answerText=='No'?'selected':'' }>No</option>
													<c:if test="${criteria.notApplicableIndicator}"><option value="NA" ${command.subjectEligibilityAnswers[index].answerText=='NA'?'selected':'' }>NA</option></c:if>
												</select>
											</td>
										</tr>
										<c:set var="index" value="${index+1}"/>										
									</c:forEach>
								</table>
							</tags:minimizablePanelBox>
							</td>
						</tr>
					</table>
					</div>					
				</td>
			</tr>
		</table>
		</form:form>
		</td>
	</tr>
</table>
</div>
</tabs:division>
</div>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
