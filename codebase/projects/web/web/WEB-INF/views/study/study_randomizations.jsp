<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
</head>

<body>
<form:form method="post" name="form">
<tags:tabFields tab="${tab}"/>
<c:if test="${command.randomizationType.name == 'BOOK'}">
	<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
	
	     <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }" class="mytable">         
             <tr>
                <div class="row">
				<div class="label">*Enter Book Randomization Data:</div>
				<div class="value">
					<TEXTAREA NAME="bookRandomizations-${epochCount.index}" COLS=40 ROWS=6 class="validate-notEmpty&&maxlength500"></TEXTAREA>
				</div>
				</div>
             </tr>
	     </table>
	    </tags:minimizablePanelBox>
	</c:forEach>
</c:if>

<c:if test="${command.randomizationType.name == 'CALL_OUT'}">
	<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
		<br/>
	     <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }">         
             <tr>
                <td><b>Enter Call-Out Randomization URL:</b></td>
				<td>
				<!-- <input type="text" 
				id="treatmentEpochs[${epochCountIndex}].stratumGroups[${statusStratumGroup.index}].stratumGroupNumber"
				name="${command.treatmentEpochs[epochCount.index].randomization.calloutUrl}" 
				value="${command.treatmentEpochs[epochCount.index].randomization.calloutUrl}" size="40"/> -->
				<form:input path="treatmentEpochs[${epochCount.index}].randomization.calloutUrl" size="30" cssClass="validate-notEmpty"/>
				</td>				
             </tr>
	     </table>
	     <br/>
	    </tags:minimizablePanelBox>
	</c:forEach>
</c:if>

<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>

</body>
</html>