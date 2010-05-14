<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="command" required="true" type="edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper"%>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="jwr" uri="http://jawr.net/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>


<tags:controlPanel>
		<csmauthz:accesscontrol domainObject="${command.studySubject}" hasPrivileges="UPDATE" authorizationCheckName="domainObjectAuthorizationCheck">
			<c:if test="${isAdmin}">
				<tags:oneControlPanelItem linkhref="javascript:invalidateRegistrationRecord();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_closeStudy.jpg" linktext="Invalidate Record" />
			</c:if>
			<c:choose>
				<c:when test="${isCompleteRegistration && isAdmin}">
					
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${not empty command.studySubject.parentStudySubject}">
							<c:set var="editRegURL"><c:url value='/pages/registration/editCompanionRegistration'/></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="editRegURL"><c:url value='/pages/registration/editRegistration'/></c:set>
						</c:otherwise>
					</c:choose>
					<c:set var="editRegURL">
						${editRegURL }?<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>
					</c:set>
					<tags:oneControlPanelItem linkhref="${editRegURL}" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Resume Registration" />
				</c:otherwise>
			</c:choose>
			<c:if test="${canChangeEpoch}">
				<tags:oneControlPanelItem linkhref="javascript:changeEpochPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_changeEpoch.png" linktext="Change Epoch" />
			</c:if>
	    	<c:if test="${takeSubjectOffStudy}">
				<%--<tags:oneControlPanelItem linkhref="javascript:takeSubjectOffStudyPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_takesubjoff.png" linktext="Take subject off study" />--%>
				<tags:oneControlPanelItem linkhref="changeEpoch?offStudySection=defReasonTable" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_takesubjoff.png" linktext="Take subject off study" />
			</c:if>
			<c:if test="${canBroadcast}">
				<tags:oneControlPanelItem linkhref="javascript:confirmBroadcastRegistration();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_broadcast.png" linktext="Broadcast Registration" />
			</c:if>
    	</csmauthz:accesscontrol>
    	<c:if test="${command.canAllowEligibilityWaiver && isStudyCoordinator}">
    		<tags:oneControlPanelItem linkhref="javascript:allowEligibilityWaiverPopup();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_waiveEligibility.png" linktext="Waive Eligibility" />
    	</c:if>
		<tags:oneControlPanelItem linkhref="javascript:C3PR.disableAjaxLoadingIndicator=true;$('exportForm')._target.name='xxxx';$('exportForm').submit();C3PR.disableAjaxLoadingIndicator=false;" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_xml.png" linktext="Export XML" />
		<tags:oneControlPanelItem linkhref="javascript:launchPrint()" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
	</tags:controlPanel>