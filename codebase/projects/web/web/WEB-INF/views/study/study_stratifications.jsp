<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script>
function fireAction(action, selectedStratification, selectedAnswer){
	document.getElementById('command').targetPage.name='_noname';
	document.form._action.value=action;
	document.form._selectedStratification.value=selectedStratification;
	document.form._selectedAnswer.value=selectedAnswer;
	// need to disable validations while removing
		
	document.form.submit();
}

</script>
</head>
<body>
<form:form name="form" method="post" cssClass="standard">
<div>
        <input type="hidden" name="_action" value="">
        <input type="hidden" name="_selectedStratification" value="">  
        <input type="hidden" name="_selectedAnswer" value="">
    </div>
<tabs:tabFields tab="${tab}" />
<div><tabs:division id="Strata Factors">
<!-- MAIN BODY STARTS HERE -->
<table border="0" cellspacing="0" cellpadding="0"">
	<tr>
		<td>
		<p id="instructions">
			Strata:
			<a href="javascript:fireAction('addStratificationQuestion','0','0');"><img
		 	      src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>&nbsp;&nbsp
		</p>
		<br>
		
		<table border="0" cellspacing="0" cellpadding="0" id="mytable">
		<tr>
			<th scope="col" align="left"></th>
			<th scope="col" align="left"><b>Strata <span class="red">*</span></b></th>
			<th scope="col" align="left"><b>add&nbsp;answers<span class="red">*</span></b></th>
			<th scope="col" align="left"></th>
		</tr>
		<c:forEach items="${command.stratificationCriteria}" var="strat" varStatus="status">
		<tr>
			<td class="alt"><a href="javascript:fireAction('removeStratificationQuestion','${status.index}','0');">
               <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Add"></a>
			</td>
			<td class="alt">
				<form:hidden path="stratificationCriteria[${status.index}].questionNumber"/>
                <form:textarea path="stratificationCriteria[${status.index}].questionText" rows="1" cols="60" cssClass="validate-notEmpty"/>
             </td>
			<td class="alt"><a href="javascript:fireAction('addPermissibleAnswer',${status.index},'0');">
				<img src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
			<td>
				<table border="0" cellspacing="0" cellpadding="0" id="table1" width="50%">
					<tr>
						<td class="alt">Answer </td>
						<td class="alt"></td>
					</tr>
					<c:forEach var="answer" varStatus="statusAns" items="${command.stratificationCriteria[status.index].permissibleAnswers}">                 
					<tr>
						<td class="alt">
							<form:input path="stratificationCriteria[${status.index}].permissibleAnswers[${statusAns.index}].permissibleAnswer" size="30" cssClass="validate-notEmpty"/></td>
						<td class="alt"><a href="javascript:fireAction('removePermissibleAnswer',${status.index},${statusAns.index});">
                  			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		</c:forEach>
		</table>
	</table>
	</td>
	</tr>
</table>
<!-- MAIN CONTENT ENDS HERE -->
</tabs:division>
</form:form>
</table>
</div>
</body>
</html>