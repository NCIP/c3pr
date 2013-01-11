<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="../tags/taglibs.jsp"%>
<link href="themes/mac_os_x.css" rel="stylesheet" type="text/css"/>
<%--
  User: ion
  Date: Jun 11, 2008
  Time: 10:54:45 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>C3PR Dashboard Page</title>

<c:set var="bgcolorSelected" value="#cccccc" />
<c:set var="bgcolorAlternate" value="#eeeeee" />
<c:set var="bgcolor" value="#ffffff" />
</head>
<body>

<table width="100%" border="0">
	<tr>
		<td valign="top" width="33%"
			style="background: url(../../images/chrome/li_item.jpg);"><chrome:box
			title="Frequently Used Shortcuts">
			<table width=100% cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td><c:forEach var="lov" items="${links}"
						varStatus="status">
						<img src="<c:url value="/images/chrome/li_item.jpg" />">&nbsp;&nbsp;<a
							href="<c:url value="/${lov.code}" />">${lov.desc}</a>
						<br />
						<c:if test="${status.count == (fn:length(links)%2==0?fn:length(links):fn:length(links)+1) / 2}">
							</td><td>
						</c:if>
					</c:forEach>
					</td>
				</tr>
			</table>
		</chrome:box> 
		<c3pr:checkprivilege hasPrivileges="INBOX_READ">
		<tags:inbox
			recipientScheduledNotification="${recipientScheduledNotification}"
			endValue="5"
			htmlContent="<a href='../pages/admin/viewInbox'>My Inbox</a>"
			url="../pages/admin/viewInbox" />
		</c3pr:checkprivilege> 
		<chrome:box
			title="C3PR Development Notes">
			
			<div id="c3pr-wiki">
			<img src="<c:url value="/images/chrome/li_item.jpg" />">&nbsp;&nbsp;
			<a
				href='<fmt:message key="DASHBOARD.C3PR.DEVELOPMENT.WIKI"/>'
				target="_blank">C3PR Wiki</a></div>
			<div id="c3pr-userguide">
			<img src="<c:url value="/images/chrome/li_item.jpg" />">&nbsp;&nbsp;
			<a
				href="https://cabig-kc.nci.nih.gov/CTMS/KC/index.php/C3PR_End_User_Guide"
				target="_blank">C3PR User Guide</a></div>
			<%-- <div id="c3pr-deployment-status">
			<img src="<c:url value="/images/chrome/li_item.jpg" />">&nbsp;&nbsp;
			<a
				href="javascript: Effect.Combo('deployment-modules')">Check
			Deployment Status</a>
			<div id="deployment-modules" style="display: none"><c:if
				test="${cctsEnv}">
				<div id="SmokeTestSerive">Authentication : ${authentication }
				</div>
			</c:if>
			</div>
			</div>
			<br />--%>
			<div id="build-name">version: ${buildInfo.buildName}</div>
		</chrome:box>
		</td>
		
		<td valign="top">
		<c3pr:checkprivilege hasPrivileges="UI_STUDYSUBJECT_SEARCH">
		<chrome:box
			title="Incomplete Registrations*">
			<c:choose>
				
			
			<c:when
				test="${uRegistrations != null && fn:length(uRegistrations) > 0}">
				<table width="100%" cellspacing="1" cellpadding="2">
					<tr bgcolor="${bgcolorAlternate}">
						<td width="25%"><b><fmt:message key="participant.subjectName"/></b></td>
						<td width="25%"><b><fmt:message key="c3pr.common.primaryIdentifier"/></b></td>
						<td width="30%"><b><fmt:message key="study.shortTitle"/></b></td>
						<td width="20%"><b><fmt:message key="registration.registrationStatus"/></b></td>
					</tr>
					<c:forEach var="registration" items="${uRegistrations}"
						varStatus="status">
						<script>
							paramString_${status.index }="<tags:identifierParameterString identifier='${registration.systemAssignedIdentifiers[0] }'/>";
						</script>
						<c:set var="reg_url" value="../pages/registration/manageRegistration" />	
						<c:if test="${status.count % 2 == 1}">
							<c:set var="bg" value="${bgcolor}" />
						</c:if>
						<c:if test="${status.count % 2 == 0}">
							<c:set var="bg" value="${bgcolorAlternate}" />
						</c:if>
						<chrome:tr bgcolor="${bg}" bgcolorSelected="${bgcolorSelected}"
							rowNumber="${status.count}" _onclick="document.location='${reg_url}?'+paramString_${status.index };" >
							<chrome:td bgcolor="${bg}">
								<c:out
									value="${registration.participant.firstName} ${registration.participant.lastName}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">
								<c:out value="${registration.participant.MRN.value}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">
								<c:out value="${registration.studySite.study.shortTitleText}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">
								<c:out value="${registration.regWorkflowStatus.displayName}" />
							</chrome:td>
						</chrome:tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<b><fmt:message key="DASHBOARD.INCOMPLETE_REGISTRATIONS.EMPTY"/></b>
			</c:otherwise>
			</c:choose>
		</chrome:box>
		</c3pr:checkprivilege>
		<c3pr:checkprivilege hasPrivileges="UI_STUDY_SEARCH">
		<chrome:box title="Pending Studies*">
			<c:choose>
			
			<c:when test="${pStudies != null && fn:length(pStudies) > 0}">
				<%--FOUND <c:out value="${fn:length(pStudies)}" />--%>
				<table width="100%" cellspacing="1" cellpadding="2">
					<tr bgcolor="${bgcolorAlternate}">
						<td width="25%"><b><fmt:message key="study.shortTitle"/></b></td>
						<td width="25%"><b><fmt:message key="c3pr.common.primaryIdentifier"/></b></td>
						<td width="30%"><b><fmt:message key="dashboard.coordinatingCenter"/></b></td>
						<td width="20%"><b><fmt:message key="study.phase"/></b></td>
					</tr>
					<c:forEach var="study" items="${pStudies}" varStatus="status">

						<c:if test="${status.count % 2 == 1}">
							<c:set var="bg" value="${bgcolor}" />
						</c:if>
						<c:if test="${status.count % 2 == 0}">
							<c:set var="bg" value="${bgcolorAlternate}" />
						</c:if>

						<c:url var="_url"
							value="/pages/study/viewStudy?studyId=${study.id}" />
						<chrome:tr bgcolor="${bg}" bgcolorSelected="${bgcolorSelected}"
							rowNumber="${status.count}" _url="${_url}">
							<chrome:td bgcolor="${bg}">
								<c:out value="${study.shortTitleText}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">
								<c:out
									value="${study.primaryIdentifier}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">
								<c:out
									value="${study.studyCoordinatingCenters[0].healthcareSite.name}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">
								<c:out value="${study.phaseCode}" />
							</chrome:td>
						</chrome:tr>

					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
			<b><fmt:message key="DASHBOARD.PENDING_STUDIES.EMPTY"/></b>
			</c:otherwise>
			</c:choose>
		</chrome:box> <chrome:box title="Open Studies - Highest Enrollment Rate*">
			<c:choose>
			<c:when test="${aStudies != null && fn:length(aStudies) > 0}">
				<%--FOUND <c:out value="${fn:length(pStudies)}" />--%>
				<table width="100%" cellspacing="1" cellpadding="2">
					<tr bgcolor="${bgcolorAlternate}">
						<td width="25%"><b><fmt:message key="study.shortTitle"/></b></td>
						<td width="25%"><b><fmt:message key="c3pr.common.primaryIdentifier"/></b></td>
						<td width="30%"><b><fmt:message key="dashboard.coordinatingCenter"/></b></td>
						<td width="20%"><b><fmt:message key="dashboard.accrualWithInLastWeek"/></b></td>
					</tr>
					<c:forEach var="study" items="${aStudies}" varStatus="status">

						<c:if test="${status.count % 2 == 1}">
							<c:set var="bg" value="${bgcolor}" />
						</c:if>
						<c:if test="${status.count % 2 == 0}">
							<c:set var="bg" value="${bgcolorAlternate}" />
						</c:if>

						<c:url var="_url"
							value="/pages/study/viewStudy?studyId=${study.id}" />
						<chrome:tr bgcolor="${bg}" bgcolorSelected="${bgcolorSelected}"
							rowNumber="${status.count}" _url="${_url}">
							<chrome:td bgcolor="${bg}">
								<c:out value="${study.shortTitleText}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">
								<c:out
									value="${study.primaryIdentifier}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">
								<c:out
									value="${study.studyCoordinatingCenters[0].healthcareSite.name}" />
							</chrome:td>
							<chrome:td bgcolor="${bg}">${study.accrualCount}</chrome:td>
						</chrome:tr>

					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<b><fmt:message key="DASHBOARD.ENROLLMENT.EMPTY"/></b>
			</c:otherwise>
			</c:choose>
		</chrome:box>
		</c3pr:checkprivilege>
		<c3pr:checkprivilege hasPrivileges="UI_STUDY_SEARCH,UI_STUDYSUBJECT_SEARCH">
			<fmt:message key="DASHBOARD.ASTERISK.MESSAGE"/>
		</c3pr:checkprivilege>
		</td>
		
	</tr>
</table>

</body>
</html>
