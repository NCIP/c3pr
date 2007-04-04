<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			<td valign="top" width="35%" align="right"><b>First Name:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.firstName}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Last Name:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.lastName}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Gender:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.administrativeGenderCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Ethnicity:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.ethnicGroupCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Races(s):&nbsp;</b></td>
			<td valign="bottom" align="left">${command.raceCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Primary
			Identifier:&nbsp;</b></td>
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
			<b>Adverse Event Reporting</b></a></td>
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
			<b>Study Calendar</b></a></td>
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
			<b>Clinical Database</b></a></td>
		</tr>
		<c:forEach begin="1" end="7">
			<tr>
				<td><br>
				</td>
			</tr>
		</c:forEach>

	</table>

	</form>

</tabs:division>
