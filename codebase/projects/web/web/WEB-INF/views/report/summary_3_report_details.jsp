<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="commons" uri="http://bioinformatics.northwestern.edu/taglibs/commons"%>
<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="edu.duke.cabig.c3pr.constants.C3PRUserGroupType" %>

<html>
<head>
    <title>
        Summary 3 Report
    </title>

<tags:dwrJavascriptLink objects="OrganizationAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
  var sourceOrganizationAutocompleterProps = {
            basename: "healthcareSite",
            populator: function(autocompleter, text) {
	  		OrganizationAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
            	if(obj.externalId != null){
            		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
            	} else {
            		image = '';
            	}

            	return (obj.name+" ("+obj.primaryIdentifier+")" + image)
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=sourceOrganizationAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			 }
        }
         AutocompleterManager.addAutocompleter(sourceOrganizationAutocompleterProps);

	  function submitForm(){
			document.getElementById('command').submit();
	}

</script>
</head>
<body>
<form:form name="summary3ReportForm">
	<chrome:box title="Summary 3 Report">
		<tags:tabFields tab="${tab}" />
<tags:errors path="*" />
<tags:instructions code="summary_3_report_details" />
<chrome:division id="site" title="Reporting Organization">
        <div class="row">
            <div class="label"><tags:requiredIndicator />
               <fmt:message key="c3pr.common.organization"/>
            </div>
            <div class="value">
					<c:set var="_codeOrg" value="" />
					<c:set var="_nameOrg" value="" />
					<c:if test="${not empty command.reportingOrganization}">				
							<c:set var="_codeOrg" value="(${command.reportingOrganization.primaryIdentifier})" />
							<c:set var="_nameOrg" value="${command.reportingOrganization.name}" />
							
						</c:if>
               		<input type="hidden" id="healthcareSite-hidden"
						name="reportingOrganization"
						value="${command.reportingOrganization.id }" />
						<input id="healthcareSite-input" size="50" type="text"  value='<c:out value="${_nameOrg} ${_codeOrg}" />'
						class="autocomplete validate-notEmpty" />
						<tags:hoverHint keyProp="summary3Report.organization"/>
					<tags:indicator id="healthcareSite-indicator" />
					<div id="healthcareSite-choices" class="autocomplete" style="display: none;"></div>
            </div>
        </div>
</chrome:division>

<chrome:division id="grantNumber" title="Grant Number">
    <div class="leftpanel">
        <div class="row">
			<div class="label"><tags:requiredIndicator /><b>Grant Number</b></div>
				<div class="value">
					<form:input path="grantNumber" size="15" maxlength="20" id="_grant_number" cssClass="required validate-notEmpty"/>
				</div>
		</div>
	</div>
	<div class="clear"></div>

</chrome:division>

<chrome:division id="reportingPeriod" title="Reporting Period">
    <div class="leftpanel">
        <div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.startDate" /></div>
				<div class="value">
					<tags:dateInput path="startDate" validateDate="true" cssClass='validate-notEmpty'/>
				</div>
		</div>
	</div>

    <div class="rightpanel">
    	<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.endDate" /></div>
				<div class="value">
					<tags:dateInput path="endDate" validateDate="true" cssClass='validate-notEmpty'/> 
				</div>
		</div>
    </div>

	<div class="clear"></div>

</chrome:division>

<tags:tabControls tab="${tab}" flow="${flow}"
	localButtons="${localButtons}" willSave="true">
	<jsp:attribute name="submitButton">
		<table>
			<tr>
				<td>
			    	<tags:button type="submit" color="green" id="flow-update"
					value="Generate Report" onclick="javascript:submitForm();" />
				</td>
			</tr>
		</table>
	</jsp:attribute>
</tags:tabControls></div>

</chrome:box>
</form:form> 

</body>
</html>
