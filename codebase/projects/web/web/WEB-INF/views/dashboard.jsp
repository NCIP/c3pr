<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

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

	<script>
		function showMessageBody(id){
	     		var win = new Window({className: "dialog", width:710, height:350, zIndex: 100, resizable: true, title:"Email Message", 
	        	showEffect:Effect.BlindDown, hideEffect: Effect.BlindUp, draggable:true, wiredDrag: true}); 
	        	win.getContent().innerHTML = $(id).innerHTML;
		     	win.showCenter();
	     }   
	</script>
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
          <c:choose> 
           <c:when test="${(empty recipientScheduledNotification || fn:length(recipientScheduledNotification) == 0)
            			 && (empty scheduledNotifications || fn:length(scheduledNotifications) == 0)} ">
           		You don't have any notifications.
           </c:when>
           <c:otherwise>
           		<table width="100%" cellspacing="1" cellpadding="2">
                    <tr bgcolor="${bgcolorAlternate}">
                        <td width="65%"><b>Title </b></td>
                        <td width="35%"><b>Date</b></td>
                    </tr>
                    <c:forEach var="rsn" items="${recipientScheduledNotification}" varStatus="rsnStatus" end="5">
                    	<c:if test="${rsnStatus.count % 2 == 1}"><c:set var="bg" value="${bgcolor}"/></c:if>
                        <c:if test="${rsnStatus.count % 2 == 0}"><c:set var="bg" value="${bgcolorAlternate}"/></c:if>
                        <chrome:tr bgcolor="${bg}" bgcolorSelected="${bgcolorSelected}" rowNumber="${rsnStatus.count}">
                            <chrome:td bgcolor="${bg}"><a href="javascript:showMessageBody('messageDetails-${rsnStatus.index}')">
                            		<c:out value="${rsn.scheduledNotification.title}" /></a>
                            </chrome:td>
                            <chrome:td bgcolor="${bg}"><fmt:formatDate value="${rsn.scheduledNotification.dateSent}" pattern="MM/dd/yyyy"/></chrome:td>
                        </chrome:tr>
                        <tr style="display:none;"><td>
                        	<div id="messageDetails-${rsnStatus.index}">
                            		<table>
										<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
										<tr><td width="10%" align="right" style="font-size: 11px;">Subject Line:</td> 								 
										<td><input type="text" name="title" value="${rsn.scheduledNotification.title}" size="50" class="width:96%;" onfocus="lastElement = this;" />
										</td></tr>
											
										<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
										<tr><td valign="top" align="right" style="font-size: 11px;">Message:</td>
											<td>
												<textarea rows="10" cols="57" id="message">${rsn.scheduledNotification.message}</textarea>
											</td>
										</tr>
									</table>
                            	</div>
                        </td></tr>
                    </c:forEach>
                    <c:forEach var="sn" items="${scheduledNotifications}" varStatus="snStatus" end="5">
                    	<c:if test="${snStatus.count % 2 == 1}"><c:set var="bg" value="${bgcolor}"/></c:if>
                        <c:if test="${snStatus.count % 2 == 0}"><c:set var="bg" value="${bgcolorAlternate}"/></c:if>
                        <chrome:tr bgcolor="${bg}" bgcolorSelected="${bgcolorSelected}" rowNumber="${snStatus.count}">
                            <chrome:td bgcolor="${bg}"><a href="javascript:showMessageBody('messageDetails-${snStatus.index}')">
                            		<c:out value="${sn.title}" /></a>
                            		</chrome:td>
                            <chrome:td bgcolor="${bg}"><fmt:formatDate value="${sn.dateSent}" pattern="MM/dd/yyyy"/></chrome:td>
                        </chrome:tr>
                        <tr style="display:none;"><td>
                        	<div id="messageDetails-${snStatus.index}">
                           		<table>
									<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
									<tr><td width="10%" align="right" style="font-size: 11px;">Subject Line:</td> 								 
									<td><input type="text" name="title" value="${sn.title}" size="100" class="width:96%;" onfocus="lastElement = this;" />
									</td></tr>
										
									<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
									<tr><td valign="top" align="right" style="font-size: 11px;">Message:</td>
										<td>
											<textarea rows="20" cols="97" id="message">${sn.message}</textarea>
										</td>
									</tr>
								</table>
                           	</div>
	                       </td></tr>
                    </c:forEach>
                </table>
           </c:otherwise>
         </c:choose>
        </chrome:box>
        <chrome:box title="C3PR Development Notes">
			<br>			
			<div id="c3pr-wiki"><a href="https://wiki.nci.nih.gov/display/CTMS/Cancer+Central+Clinical+Participant+Registry+%28C3PR%29" target="_blank"><b>C3PR Wiki</b></a></div>
			<div id="c3pr-userguide"><a href="https://cabig-kc.nci.nih.gov/CTMS/KC/index.php/C3PR_End_User_Guide" target="_blank"><b>C3PR User Guide</b></a></div>
			<div id="c3pr-deployment-status"><a href="javascript: new Effect.Combo('depoyment-modules	')" target="_blank"><b>Check Deployment Status</b></a>
				<div id="depoyment-modules" style="display: none">
					<!--<c:if test="${cctsEnv}">-->
					<div id ="SmokeTestSerive"/>
						Authentication : ${authentication }
					</div>
					<!--</c:if>--
				</div>
					<div id ="SmokeTestSerive"/>
						SMTP : Not Implemented
					</div>
				</div>
			</div>
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
