<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study"%>


<html>
<head>
<tags:includeScriptaculous/>
<tags:dwrJavascriptLink objects="createStudy"/>

<title>${tab.longTitle}</title>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}

function fireAction(action, selectedStratification, selectedAnswer){
	document.getElementById('command').targetPage.name='_noname';
	document.form._action.value=action;
	document.form._selectedStratification.value=selectedStratification;
	document.form._selectedAnswer.value=selectedAnswer;
	document.form.submit();
}
</script>
</head>

<!-- MAIN BODY STARTS HERE -->
<body>
<form:form method="post" name="form">
<table border="0" id="table1" cellspacing="10" width="100%">
	<tr>
        <td valign="top" width="60%" >
        <tabs:division id="study-details" title="Stratification Questions">
        <tabs:tabFields tab="${tab}"/>
        <div>
            <input type="hidden" name="_action" value="">
            <input type="hidden" name="_selectedStratification" value="">  
            <input type="hidden" name="_selectedAnswer" value="">
        </div>
        <p id="instructions">
            Please Add a Stratification Criterion and corresponding Permissible Answers.<br>
            Add multiple Criteria by clicking on the add button next to Criterion
        </p>
        
        <c:set var="selected_stratification" value="0" scope="page"/>
         <c:if test="${not empty selectedStratification}">
            <c:set var="selected_stratification" value="${selectedStratification}"/>
        </c:if>
         <c:set var="selected_answer" value="0" scope="page"/>
         <c:if test="${not empty selectedAnswer}">
              <c:set var="selected_answer" value="${selectedAnswer}"/>
         </c:if>
           
         <table border="0" id="table1" cellspacing="5">
           <tr>
                <td align="right" width="20%"> <b> <span class="red">*</span><em></em>Criterion: (${selected_stratification+1})</b></td>
                <td align="left" width="80%">
                    <form:hidden path="stratificationCriteria[${selected_stratification}].questionNumber"/>
                    <form:textarea path="stratificationCriteria[${selected_stratification}].questionText" rows="1" cols="60"/>
                </td>
           </tr>
           <tr>
              <td align="right" width="20%"> <b> <span class="red">*</span><em></em>Permissible Answer: (${selected_answer+1})</b> </td>
              <td align="left" width="80%">
              <table border="0" id="table1" cellspacing="5">
              <tr>
              	<td>              
	                  <form:input path="stratificationCriteria[${selected_stratification}].permissibleAnswers[${selected_answer}].permissibleAnswer"/>
	                   <td align="left" width="10%">
	                     <a href="javascript:fireAction('addPermissibleAnswer',${selected_stratification},'${selected_answer}');"><img
	                    src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a>
		              </td>     
		               <td align="left" width="90%">
		                  <select name="answers" multiple>
		                  	<c:forEach var="answer" varStatus="statusAns" items="${command.stratificationCriteria[selected_stratification].permissibleAnswers}">                   
		                  		<option value="" >${answer.permissibleAnswer}</option>
		                  	</c:forEach>
		                  </select>
		              </td>  
	              </td>
	             </tr>
	           </table>
              </td>                      
           </tr>
        </table>
      </tabs:division>
      </td>
      <td>
      <a href="javascript:fireAction('addStratificationQuestion','${selected_stratification}','${selected_answer}');"><img
                 src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>
      </td>
       <td valign="top" width="40%">
      <c:set var="i" value="0" scope="page"/>
       
        <tabs:division id="Summary" title="Stratifications Summary">
        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="additionalList">
        <c:if test="${fn:length(command.stratificationCriteria) > 0}">
        <tr class="label">
            <td align="left"><b>Criterion</b></td>
            <td align="left"><b>Permissible Answer(s)</b></td>
        </tr>
        </c:if>
         <c:forEach var="strat" varStatus="status" items="${command.stratificationCriteria}">
          <c:if test="${strat.questionText != null and strat.questionText != ''}">                   
            <tr align="center" id="row${i}" class="results" onMouseOver="navRollOver('row${i-1}', 'on')"
				onMouseOut="navRollOver('row${i-1}', 'off')"  onclick="javascript:fireAction('displayStratification','${status.index}','0');">
                <td align="left" width="65%">
                   
                     <a href="javascript:fireAction('removeStratificationQuestion','${selected_stratification}','${selected_answer}');">
                     <img src="<tags:imageUrl name="checkno.gif"/>" height="10" border="0" alt="Add"></a>
                      <a onclick="javascript:fireAction('displayStratification','${status.index}','0');" title="click here to edit stratification Criterion"> 
                       ${strat.trimmedQuestionText}</a>
                </td>
                <td align="left" width="35%">
                   <table border="0" id="table1" cellspacing="0" cellpadding="0" width="100%">
                   <c:forEach var="answer" varStatus="statusAns" items="${strat.permissibleAnswers}">
                   <c:if test="${answer.permissibleAnswer != null and answer.permissibleAnswer != ''}">
                    <tr>
                    	  <td>
                              <a href="javascript:fireAction('removePermissibleAnswer',${selected_stratification},${selected_answer});">
                              <img src="<tags:imageUrl name="checkno.gif"/>" height="10" border="0"></a>
                         	<a onclick="javascript:fireAction('displayAnswer','${status.index}','${statusAnswer.index}');" title="click here to edit the permissible answer">
                         ${answer.trimmedPermissibleAnswer}	 </a>
                        </td>
                     </tr>
                     </c:if>
                    </c:forEach>
                     </table>
                </td>
            </tr>
           </c:if>
        </c:forEach>
        </table>
        </tabs:division>
	</td>
	  </tr>
	</table>
</form:form>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>