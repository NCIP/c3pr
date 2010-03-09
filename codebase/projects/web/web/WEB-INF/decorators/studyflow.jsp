<%-- This is the standard tabbedFlow decorator for respective C3PR pages --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>

<html>
<head>
    <title><decorator:title /></title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <link rel="shortcut icon" href="<tags:imageUrl name="favicon.ico"/>" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <tags:include />
    <jwr:script src="/js/tabbedflow.js" />
    <decorator:head/>

    <style type="text/css">
        .label { width: 20em; text-align: right;}
	</style>
	<script type="text/javascript">
	// This function redirect to parent study flow in case of embedded companion study
	function returnToParent(parentStudyFlow, parentStudyId){
		if(parentStudyFlow == 'AMEND_STUDY'){
			$('parentStudyForm').action = "/c3pr/pages/study/amendStudy?studyId="+parentStudyId ;
		}else{
			$('parentStudyForm').action = "/c3pr/pages/study/editStudy?studyId="+parentStudyId ;
		}
		$('parentStudyForm').submit();
	}

	// This function redirect to parent study manage flow
	function viewParent(parentStudyId){
		$('parentStudyViewForm').action = "/c3pr/pages/study/viewStudy?studyId="+parentStudyId ;
		$('parentStudyViewForm').submit();
	}

	function returnToParentUsingButton(parentStudyFlow, parentStudyId){
		if(parentStudyFlow == 'AMEND_STUDY'){
			$('parentStudyFormButton').action = "/c3pr/pages/study/amendStudy?studyId="+parentStudyId ;
		}else{
			$('parentStudyFormButton').action = "/c3pr/pages/study/editStudy?studyId="+parentStudyId ;
		}
		$('parentStudyFormButton').submit();
	}
		
	</script>
</head>

<body>
	<tags:ajaxLoadingIndicator/>
<div id="all">
<layout:header/>

<c:set var="studyCommand" value="${command}" scope="request"/>
<div class="tabpane">
<c:choose>
    <c:when test="${tab.display!='false'}">
    <chrome:workflowTabs tab="${tab}" flow="${flow}"/>

    <chrome:body title="${flow.name}: ${tab.longTitle}">
        <c:set var="hasSummary" value="${not empty summary}"/>
        <c:if test="${hasSummary}">
            <div id="summary-pane" class="pane">
                <chrome:box title="Summary">
                    <c:forEach items="${summary}" var="summaryEntry">
                        <div class="row">
                            <div class="label">${summaryEntry.key}</div>
                            <div class="value">${empty summaryEntry.value ? '<em class="none">None</em>' : summaryEntry.value}</div>
                        </div>
                    </c:forEach>
                </chrome:box>
            </div>
        </c:if>

        <div id="main${hasSummary ? '' : '-no-summary'}-pane" class="pane">
            <c:choose>
        		<c:when test="${!empty tab && tab.showSummary!='false'}">
	                <layout:studySummaryLayout tab='${tab}' flow="${flow}">
	                    <decorator:body/>
	                </layout:studySummaryLayout>
        		</c:when>
        		<c:otherwise>
        			<decorator:body/>
        			
        		</c:otherwise>
           	</c:choose>
        </div>
    </chrome:body>
    </c:when>
    <c:otherwise>
        <decorator:body/>
    </c:otherwise>
</c:choose>


<c:choose>
	<c:when test="${param.parentStudyFlow == 'EDIT_STUDY' || param.parentStudyFlow == 'CREATE_STUDY'}">
		<form id="parentStudyFormButton" action="" method="post">
			<input type="hidden" name="_page" id="_page7" value="7"/>
			<input type="hidden" name="_target7" id="_target7" value="7"/>
			<input type="hidden" name="refreshCommandObject" id="refreshCommandObject" value="true"/>
		</form>
		<form id="parentStudyForm" action="" method="post">
			<input type="hidden" name="_page" id="_page7" value="7"/>
			<input type="hidden" name="_target7" id="_target7" value="7"/>
			<input type="hidden" name="refreshCommandObject" id="refreshCommandObject" value="true"/>
		</form>
	</c:when>
	<c:when test="${param.parentStudyFlow == 'AMEND_STUDY'}">
		<form id="parentStudyFormButton" action="" method="post">
			<input type="hidden" name="_page" id="_page8" value="8"/>
			<input type="hidden" name="_target8" id="_target8" value="8"/>
			<input type="hidden" name="refreshCommandObject" id="refreshCommandObject" value="true"/>
		</form>
		<form id="parentStudyForm" action="" method="post">
			<input type="hidden" name="_page" id="_page8" value="8"/>
			<input type="hidden" name="_target8" id="_target8" value="8"/>
			<input type="hidden" name="refreshCommandObject" id="refreshCommandObject" value="true"/>
		</form>
	</c:when>
</c:choose>
<form id="parentStudyViewForm" action="" method="post"></form>

<form:form id="flowredirect">
    <input type="hidden" name="_target${tab.targetNumber}" id="flowredirect-target"/>
    <input type="hidden" name="_page${tab.number}"/>
</form:form>
</div>
<tags:jsLogs debug="false"/>
<tags:enableRowDeletion/>
<tags:tabMethodForm/>
<layout:footer/>
<c:if test="${!empty disableForm && disableForm}">
<tags:disableForm/>
</c:if>
</div>
</body>
</html>
