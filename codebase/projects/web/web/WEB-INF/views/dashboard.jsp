<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>

<%--
  User: ion
  Date: Jun 11, 2008
  Time: 10:54:45 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>C3PR Dashboard Page</title>

    <c:set var="bgcolorSelected" value="#cccccc"/>
    <c:set var="bgcolorAlternate" value="#eeeeee"/>
    <c:set var="bgcolor" value="#ffffff"/>

</head>
<body>

<table width="100%" border="0">
<tr>
    <td valign="top" width="33%" style="background:url(../../images/chrome/li_item.jpg);">
        <chrome:box title="Frequently Used Shortcuts">

                <table width=100% cellspacing="0" cellpadding="0" border="0">
                <tr><td>
                    <c:forEach var="k" items="${links.keys}" varStatus="status">
                        <img src="<c:url value="/images/chrome/li_item.jpg" />">&nbsp;&nbsp;<a href="<c:url value="/${k}" />">${links.p[k]}</a><br/>
                        <c:if test="${status.count == fn:length(links.keys) / 2}"><td></c:if>
                    </c:forEach>
                </tr>
                </table>

        </chrome:box>
        <chrome:box title="C3PR Notifications">
            _CONTENT_
        </chrome:box>
        <chrome:box title="C3PR Development Notes">
			<br>			
			<div id="c3pr-wiki"><a href="https://wiki.nci.nih.gov/display/CTMS/Cancer+Central+Clinical+Participant+Registry+%28C3PR%29" target="_blank"><b>C3PR Wiki</b></a></div>
			<div id="c3pr-userguide"><a href="https://cabig-kc.nci.nih.gov/CTMS/KC/index.php/C3PR_End_User_Guide" target="_blank"><b>C3PR User Guide</b></a></div>
       		<div id="build-name">Build Number: ${buildInfo.buildName}</div>
        </chrome:box>
    </td>
    <td valign="top">
        <chrome:box title="Incomplete Registrations - Most Recent">
            <c:if test="${uRegistrations != null && fn:length(uRegistrations) > 0}">
                <table width="100%" cellspacing="1" cellpadding="2">
                    <tr bgcolor="${bgcolorAlternate}">
                        <td width="25%"><b>Subject Name</b></td>
						<td width="25%"><b>Subject Medical Record #</b></td>
                        <td width="30%"><b>Study Short Title</b></td>
						<td width="20%"><b>Registration Status</b></td>
                    </tr>
                    <c:forEach var="registration" items="${uRegistrations}" varStatus="status">

                        <c:if test="${status.count % 2 == 1}"><c:set var="bg" value="${bgcolor}"/></c:if>
                        <c:if test="${status.count % 2 == 0}"><c:set var="bg" value="${bgcolorAlternate}"/></c:if>
							
						<c:url var="_url" value="/pages/registration/manageRegistration?registrationId=${registration.id}" />
                        <chrome:tr bgcolor="${bg}" bgcolorSelected="${bgcolorSelected}" rowNumber="${status.count}" _url="${_url}">
                            <chrome:td bgcolor="${bg}"><c:out value="${registration.participant.firstName} ${registration.participant.lastName}" /></chrome:td>
                            <chrome:td bgcolor="${bg}"><c:out value="${registration.participant.MRN.value}" /></chrome:td>
                        	<chrome:td bgcolor="${bg}"><c:out value="${registration.studySite.study.shortTitleText}" /></chrome:td>
							<chrome:td bgcolor="${bg}"><c:out value="${registration.regWorkflowStatus.displayName}" /></chrome:td>
                        </chrome:tr>

                    </c:forEach>
                </table>
            </c:if>
        </chrome:box>
        <chrome:box title="Pending Studies - Most Recent">
            <c:if test="${pStudies != null && fn:length(pStudies) > 0}">
            <%--FOUND <c:out value="${fn:length(pStudies)}" />--%>
                <table width="100%" cellspacing="1" cellpadding="2">
                    <tr bgcolor="${bgcolorAlternate}">
                        <td width="25%"><b>Short Title</b></td>
						<td width="25%"><b>Primary Identifier</b></td>
                        <td width="30%"><b>Coordinating Center</b></td>
						<td width="20%"><b>Phase</b></td>
                    </tr>
                    <c:forEach var="study" items="${pStudies}" varStatus="status">

                        <c:if test="${status.count % 2 == 1}"><c:set var="bg" value="${bgcolor}"/></c:if>
                        <c:if test="${status.count % 2 == 0}"><c:set var="bg" value="${bgcolorAlternate}"/></c:if>

						<c:url var="_url" value="/pages/study/viewStudy?studyId=${study.id}" />
                        <chrome:tr bgcolor="${bg}" bgcolorSelected="${bgcolorSelected}" rowNumber="${status.count}" _url="${_url}">
                            <chrome:td bgcolor="${bg}"><c:out value="${study.shortTitleText}" /></chrome:td>
                            <chrome:td bgcolor="${bg}"><c:out value="${study.coordinatingCenterAssignedIdentifier.value}" /></chrome:td>
                            <chrome:td bgcolor="${bg}"><c:out value="${study.studyCoordinatingCenters[0].healthcareSite.name}" /></chrome:td>
							<chrome:td bgcolor="${bg}"><c:out value="${study.phaseCode}" /></chrome:td>
                        </chrome:tr>

                    </c:forEach>
                </table>
            </c:if>
        </chrome:box>
        <chrome:box title="Most Active Studies">
            <c:if test="${aStudies != null && fn:length(aStudies) > 0}">
            <%--FOUND <c:out value="${fn:length(pStudies)}" />--%>
                <table width="100%" cellspacing="1" cellpadding="2">
                    <tr bgcolor="${bgcolorAlternate}">
                        <td width="25%"><b>Short Title</b></td>
						<td width="25%"><b>Primary Identifier</b></td>                        
						<td width="30%"><b>Coordinating Center</b></td>
                        <td width="20%"><b>Accrual w/in Last Week</b></td>
                    </tr>
                    <c:forEach var="study" items="${aStudies}" varStatus="status">

                        <c:if test="${status.count % 2 == 1}"><c:set var="bg" value="${bgcolor}"/></c:if>
                        <c:if test="${status.count % 2 == 0}"><c:set var="bg" value="${bgcolorAlternate}"/></c:if>
						
						<c:url var="_url" value="/pages/study/viewStudy?studyId=${study.id}" />
                        <chrome:tr bgcolor="${bg}" bgcolorSelected="${bgcolorSelected}" rowNumber="${status.count}" _url="${_url}">
                            <chrome:td bgcolor="${bg}"><c:out value="${study.shortTitleText}" /></chrome:td>
                            <chrome:td bgcolor="${bg}"><c:out value="${study.coordinatingCenterAssignedIdentifier.value}" /></chrome:td>
                            <chrome:td bgcolor="${bg}"><c:out value="${study.studyCoordinatingCenters[0].healthcareSite.name}" /></chrome:td>
                            <chrome:td bgcolor="${bg}">${study.acrrualsWithinLastWeek}</chrome:td>
                        </chrome:tr>

                    </c:forEach>
                </table>
            </c:if>
        </chrome:box>
    </td>
</tr>
</table>

</body>
</html>
