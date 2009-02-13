<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
function accessApp(url,app,targetWindow){
//	alert("in");
	if(url=="")
		document.getElementById("form").action="/"+app;
	else
		document.getElementById("form").action=url+"/"+app;
	document.getElementById("form").target=targetWindow;
	document.getElementById("form").submit();
}
function submitlinksPage(){
	document.getElementById("form").submit();
}
</script>

<tabs:division id="Summary" title="Subject Summary">
	<form name="form" id="form">
	<table width="100%" height="100%" border="0" cellspacing="0"
		cellpadding="0" id="table1">
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="c3pr.common.firstName"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.firstName}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="participant.lastName"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.lastName}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="participant.maidenName"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.maidenName}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="participant.gender"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.administrativeGenderCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="participant.ethnicity"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.ethnicGroupCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="participant.race"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">
				<c:forEach items="${command.raceCodes}" var="raceCode">
		            <div class="row">
		                <div class="left">${raceCode.displayName}</div>
		            </div>
		        </c:forEach>
	        </td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="c3pr.common.primaryIdentifier"/>
			:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.primaryIdentifier }</td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		<tr>
			<td align="left" colspan="2"><a
				href="javascript:accessApp('http://10.10.10.2:8030','caaers/pages/ae/list?assignment=${command.gridId }','_caaers');">
			<b><fmt:message key="c3pr.common.adverseEventReporting"/></b></a></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>

		<tr>
			<td align="left" colspan="2"><a
				href="javascript:accessApp('http://10.10.10.2:8041','studycalendar/pages/schedule?assignment=${command.gridId }','_psc');">
			<b><fmt:message key="c3pr.common.studyCalendar"/></b></a></td>
		</tr>
		<tr>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>

		<tr>
			<td align="left" colspan="2"><a
				href="javascript:accessApp('https://octrials-train.nci.nih.gov','/opa45/rdclaunch.htm','_c3d');">
			<b><fmt:message key="c3pr.common.clinicalDatabase"/></b></a></td>
		</tr>
		<c:forEach begin="1" end="6">
			<tr>
				<td><br>
				</td>
			</tr>
		</c:forEach>

	</table>

	</form>

</tabs:division>
