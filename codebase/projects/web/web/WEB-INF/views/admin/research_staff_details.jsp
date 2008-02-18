<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="edu.duke.cabig.c3pr.domain.C3PRUserGroupType" %>

<html>
<head>
<tags:includeScriptaculous />
<tags:dwrJavascriptLink objects="ResearchStaffAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
	ValidationManager.submitPostProcess= function(formElement, continurSubmission){
		var error = document.getElementById("errorMsg1");
		
		var groups_0 = document.getElementById("groups_0");
		var groups_1 = document.getElementById("groups_1");
		var groups_2 = document.getElementById("groups_2");
		var groups_3 = document.getElementById("groups_3");
		
		if(continurSubmission == true){
			if(groups_0.checked == true || groups_1.checked == true || groups_2.checked == true || groups_3.checked == true){
				return true;
			} else {
			error.style.display = "";
			return false;
		}
		}else {
			error.style.display = "";
			return false;
		}
	}


  var sponsorSiteAutocompleterProps = {
            basename: "healthcareSite",
            populator: function(autocompleter, text) {
                ResearchStaffAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return (obj.name+" ("+obj.nciInstituteCode+")")
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=sponsorSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			 }
        }
         AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);
</script>
</head>
<body>
<div class="tabpane">
    <ul id="workflow-tabs" class="tabs autoclear">
        <li class="tab"><div>
            <a href="../admin/searchResearchStaff">Search Research Staff</a>
        </div></li>
        <li class="tab selected"><div>
            <a href="../admin/createResearchStaff">Create Research Staff</a>
        </div></li>
    </ul>
</div>

<div id="main">
<br/>
<tags:tabForm tab="${tab}" flow="${flow}" title="Research Staff" formName="researchStaffForm">

<jsp:attribute name="singleFields">
<input type="hidden" name="_finish" value="true">
<tags:errors path="*" />

<chrome:division id="site" title="Organization">
    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
               Organization:
            </div>
            <div class="value">
            
             <c:if test="${FLOW == 'EDIT_FLOW'}">
                    <input type="hidden" id="healthcareSite-hidden"
								name="healthcareSite"
								value="${command.healthcareSite.id }" />
								<input id="healthcareSite-input" size="50" type="text"
								name="healthcareSite.name"
								value="${command.healthcareSite.name}" class="autocomplete validate-notEmpty" disabled="true" />
								<tags:hoverHint keyProp="researchStaff.organization"/>
							<tags:indicator id="healthcareSite-indicator" />
							<div id="healthcareSite-choices" class="autocomplete"></div>
                </c:if>
                <c:if test="${FLOW == 'SAVE_FLOW'}">
                 <input type="hidden" id="healthcareSite-hidden"
								name="healthcareSite"
								value="${command.healthcareSite.id }" />
								<input id="healthcareSite-input" size="50" type="text"
								name="healthcareSite.name"
								value="${command.healthcareSite.name}" class="autocomplete validate-notEmpty" />
								<tags:hoverHint keyProp="researchStaff.organization"/>
							<tags:indicator id="healthcareSite-indicator" />
							<div id="healthcareSite-choices" class="autocomplete"></div>
                </c:if>
            </div>
        </div>
    </div>
</chrome:division>

<chrome:division id="staff-details" title="Basic Details">
    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
                First Name:</div>
            <div class="value">
                <form:input size="25" path="firstName"
                            cssClass="validate-notEmpty" />
            </div>
        </div>
        <div class="row">
            <div class="label required-indicator">
                Last Name:</div>
            <div class="value">
                <form:input path="lastName" cssClass="validate-notEmpty" size="25" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                Middle Name:</div>
            <div class="value">
                <form:input path="middleName" size="25" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                Maiden Name:</div>
            <div class="value">
                <form:input path="maidenName" size="25" />
            </div>
        </div>
    </div>

    <div class="rightpanel">
        <div class="row">
            <div class="label required-indicator">
                NCI Identifier:
            </div>
            <div class="value">
                <form:input path="nciIdentifier" size="25" cssClass="validate-notEmpty" />
                <tags:hoverHint keyProp="researchStaff.nciIdentifier"/>
            </div>
        </div>
        <div class="row">
            <div class="label required-indicator">
                    ${command.contactMechanisms[0].type.displayName} (Username):
            </div>
            <div class="value">
                <form:input size="30"
                            path="contactMechanisms[0].value" cssClass="validate-notEmpty&&EMAIL" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[1].type.displayName}:
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[1].value" cssClass="validate-US_PHONE_NO&&MINLENGTH<10>&&MAXLENGTH<10>&&NUMERIC" />

            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[2].type.displayName}:
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[2].value" cssClass="validate-US_PHONE_NO&&MINLENGTH<10>&&MAXLENGTH<10>&&NUMERIC" />
            </div>
        </div>
    </div>
</chrome:division>


<chrome:division id="staff-details" title="* User Role (Minimum One - Check all that apply)">
	<div id="errorMsg1" style="display:none">
		<span id='sid1' style='color:#EE3324'>Please select atleast one role.</span><br/> 	
	</div>
	   
    <div class="leftpanel">
        <c:forEach items="${groups}" var="group" varStatus="status">
            <div class="row">
                <div class="label">
                        ${group.displayName}:
                </div>
                <div class="value">
                    <form:checkbox id="groups_${status.index}" path="groups" value="${group}" />
                </div>
            </div>
        </c:forEach>
    </div>
</chrome:division>
</jsp:attribute>

</tags:tabForm>
</div>
</body>
</html>
