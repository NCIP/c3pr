<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<script>
function fireAction(action, selectedEpoch, selectedStratification, selectedAnswer) {
    document.getElementById('command')._target.name = '_noname';
    $('_action').value = action;
    $('_selectedEpoch').value = selectedEpoch;
    $('_selectedStratification').value = selectedStratification;
    $('_selectedAnswer').value = selectedAnswer;
    $('_selectedAnswer').value=selectedEpoch;
    document.form.submit();
}
</script>
</head>

<body>
<form:form method="post" name="form">
<tags:tabFields tab="${tab}"/>
<div>
    <input type="hidden" id="_action" name="_action" value="">
    <input type="hidden" id="_selectedEpoch" name="_selectedEpoch" value="">
    <input type="hidden" id="_selectedStratification" name="_selectedStratification" value="">
    <input type="hidden" id="_selectedAnswer" name="_selectedAnswer" value="">
</div>
<c:forEach items="${command.epochs}" var="epoch" varStatus="epochCount">
	<c:if test="${epoch.class.name=='edu.duke.cabig.c3pr.domain.TreatmentEpoch' }">
		<tags:minimizablePanelBox title="${epoch.name} : ${epoch.descriptionText }" boxId="${epoch.name}">
           <p id="instructions">
               Add Stratatification factor:
               <a href="javascript:fireAction('addStratificationQuestion','${epochCount.index }','0','0');"><img
                       src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>&nbsp;&nbsp
           </p>
           <br>

           <table border="0" cellspacing="0" cellpadding="0" id="mytable">
               <tr>
                   <th scope="col" align="left"></th>
                   <th scope="col" align="left"><b>Stratification Factor <span class="red">*</span></b></th>
                   <th scope="col" align="left"><b>add&nbsp;answers<span class="red">*</span></b></th>
                   <th scope="col" align="left"></th>
               </tr>
               <c:forEach items="${command.epochs[epochCount.index].stratificationCriteria}" var="strat" varStatus="status">
                   <tr>
                       <td class="alt"><a
                               href="javascript:fireAction('removeStratificationQuestion','${epochCount.index }','${status.index}','0');">
                           <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Add"></a>
                       </td>
                       <td class="alt">
                           <form:hidden path="epochs[${epochCount.index }].stratificationCriteria[${status.index}].questionNumber"/>
                           <form:textarea path="epochs[${epochCount.index }].stratificationCriteria[${status.index}].questionText" rows="1"
                                          cols="60" cssClass="validate-notEmpty"/>
                       </td>
                       <td class="alt"><a href="javascript:fireAction('addPermissibleAnswer','${epochCount.index }',${status.index},'0');">
                           <img src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
                       <td>
                           <table border="0" cellspacing="0" cellpadding="0" id="table1" width="50%">
                               <tr>
                                   <td class="alt">Answer</td>
                                   <td class="alt"></td>
                               </tr>
                               <c:forEach var="answer" varStatus="statusAns"
                                          items="${command.epochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers}">
                                   <tr>
                                       <td class="alt">
                                           <form:input
                                                   path="epochs[${epochCount.index }].stratificationCriteria[${status.index}].permissibleAnswers[${statusAns.index}].permissibleAnswer"
                                                   size="30" cssClass="validate-notEmpty"/></td>
                                       <td class="alt"><a
                                               href="javascript:fireAction('removePermissibleAnswer','${epochCount.index }',${status.index},${statusAns.index});">
                                           <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                   </tr>
                               </c:forEach>
                           </table>
                       </td>
                   </tr>
               </c:forEach>
           </table>
         </tags:minimizablePanelBox>
	</c:if>
</c:forEach>
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>
</body>
</html>