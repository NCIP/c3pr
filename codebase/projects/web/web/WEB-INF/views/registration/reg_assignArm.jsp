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
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script>

	function randomize(){
		var elmt = document.getElementById("scheduledEpoch.scheduledArms[0].arm");
		if(elmt == null || elmt.value != ""){
			<tags:tabMethod method="randomize" divElement="'randomizationMessage'" /> 
		}
		else {
			new Insertion.After(elmt, "<span id='"+elmt.name+"-msg'style='color:#EE3324'>*"+required+"</span>")			
		}			
	}
	
</script>
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
		<c:choose>
		<c:when test="${!command.scheduledEpoch.requiresArm}">
			<tr>
				<td><br/><b>The selected epoch does not involve assigning an arm.</b></td>
			</tr>
		</c:when>
		<c:when test="${!command.scheduledEpoch.requiresRandomization}">
			<tr>
			<td class="label" width="80%">Select Arm:</td>
				<td>
					<select name ="scheduledEpoch.scheduledArms[0].arm">
						<option value="" selected>--Please Select--</option>
						<c:forEach items="${command.scheduledEpoch.treatmentEpoch.arms}" var="arm">
							<option value="${arm.id }" <c:if test="${!empty command.scheduledEpoch.scheduledArms[0].arm && arm.id== command.scheduledEpoch.scheduledArms[0].arm.id }">selected</c:if>>${arm.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td>Arm will be assigned after Randomization. Please save the registration record and then randomize</td>
			</tr>
		</c:otherwise>
		</c:choose>
	</table>
</tags:formPanelBox>
</body>
</html>
