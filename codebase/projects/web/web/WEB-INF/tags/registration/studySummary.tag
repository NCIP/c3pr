<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<tabs:division id="Summary" title="Study Summary">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		id="table1">
		<tr>
			<td valign="top" width="35%" align="right"><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
			<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
				height="1" class="heightControl"></td>
		</tr>
		
		<tr>
			<td valign="top" width="35%" align="right"><b>Short
			Title:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySite.study.trimmedShortTitleText}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Primary
			Identifier:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySite.study.primaryIdentifier}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Phase:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySite.study.phaseCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Status:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySite.study.status}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Sponsor:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySite.study.sponsorCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b>Type:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySite.study.type}</td>
		</tr>
		<c:forEach begin="1" end="6">
			<tr>
				<td><br>
				</td>
			</tr>
		</c:forEach>
		
		
	</table>
</tabs:division>
