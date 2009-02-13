<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
			<td valign="top" width="35%" align="right"><b><fmt:message key="study.shortTitle"/>
			:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySubject.studySite.study.trimmedShortTitleText}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="c3pr.common.primaryIdentifier"/>
			:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySubject.studySite.study.primaryIdentifier}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="study.phase"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySubject.studySite.study.phaseCode}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="c3pr.common.status"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySubject.studySite.study.status}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="study.sponsor"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySubject.studySite.study.identifiers[0].value}</td>
		</tr>
		<tr>
			<td valign="top" width="35%" align="right"><b><fmt:message key="c3pr.common.type"/>:&nbsp;</b></td>
			<td valign="bottom" align="left">${command.studySubject.studySite.study.type}</td>
		</tr>
		<c:forEach begin="1" end="10">
			<tr>
				<td><br>
				</td>
			</tr>
		</c:forEach>
		
		
	</table>
</tabs:division>
