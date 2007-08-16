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
	<!-- minimizable panes for book randomization per epoch-->
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
	<!-- minimizable panes for book randomization per epoch-->
</c:if>

<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>

</body>
</html>