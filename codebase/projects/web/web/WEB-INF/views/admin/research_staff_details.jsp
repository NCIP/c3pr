<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>
        <c:choose>
            <c:when test="${command.id > 0}"><c:out value="Research Staff: ${command.firstName} ${command.lastName} - ${command.nciIdentifier}@${command.healthcareSite.name}" /></c:when>
            <c:otherwise>Create Research Staff</c:otherwise>
        </c:choose>
    </title>

<tags:dwrJavascriptLink objects="ResearchStaffAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
	ValidationManager.submitPostProcess= function(formElement, continueSubmission){
		var error = document.getElementById("errorMsg1");
		
		var groups_0 = document.getElementById("groups_0");
		var groups_1 = document.getElementById("groups_1");
		var groups_2 = document.getElementById("groups_2");
		var groups_3 = document.getElementById("groups_3");
		
		if(continueSubmission == true){
			if(groups_0.checked == true || groups_1.checked == true || groups_2.checked == true || groups_3.checked == true){
				return true;
			} else {
				error.style.display = "";
				return false;
			}
		}else {
			if(groups_0.checked == true || groups_1.checked == true || groups_2.checked == true || groups_3.checked == true){
				error.style.display = "none";
			}else{
				error.style.display = "";
			} 
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

<div id="main">
<tags:tabForm tab="${tab}" flow="${flow}" title="Research Staff" formName="researchStaffForm">

<jsp:attribute name="singleFields">
<input type="hidden" name="_finish" value="true">

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
                            path="contactMechanisms[1].value" cssClass="validate-US_PHONE_NO" /> e.g. 7035600296 or 703-560-0296

            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[2].type.displayName}:
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[2].value" cssClass="validate-US_PHONE_NO" /> e.g. 7035600296 or 703-560-0296
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
