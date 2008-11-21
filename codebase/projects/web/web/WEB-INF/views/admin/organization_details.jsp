<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Organization: ${command.name}:${command.nciInstituteCode}</title>
    <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
    </style>
    <script>
    ValidationManager.submitPostProcess=function(formElement, flag){
    	if(formElement.id=='command'){
    		complete=true;
    		if($('advance').checked)
    			if($('studyServiceURL').value==''){
    				ValidationManager.removeError($('studyServiceURL'));
    				ValidationManager.showError($('studyServiceURL'),'required');
    				complete=false;
    			}else if($('registrationServiceURL').value==''){
    				ValidationManager.removeError($('registrationServiceURL'));
    				ValidationManager.showError($('registrationServiceURL'),'required');
    				complete=false;
    			}
    		return complete;
    	}
    }
    </script>
</head>
<body>

<div id="main">

<tags:tabForm tab="${tab}" flow="${flow}" title="Organization" formName="createOrganization">

<jsp:attribute name="singleFields">
<input type="hidden" name="_finish" value="true">
<input type="hidden" name="type1" value="">
<tags:instructions code="organization_details" />
<tags:errors path="*"/>

<chrome:division id="organization" title="Details">
<div class="leftpanel">

    <div class="row">
        <div class="label required-indicator">
            Name:
        </div>
        <div class="value">
            <form:input size="37" path="name" cssClass="validate-notEmpty"/>
            <tags:hoverHint keyProp="organization.name"/>
        </div>
    </div>

    <div class="row">
        <div class="label">
            Description:
        </div>
        <div class="value">
            <form:textarea rows="3" cols="35" path="descriptionText"/>
        </div>
    </div>

    <div class="row">
        <div class="label required-indicator">
            NCI Institute Code:
        </div>
        <div class="value">
	        <c:if test="${FLOW == 'EDIT_FLOW'}">
	            <form:input path="nciInstituteCode" size="20" cssClass="validate-notEmpty" disabled="true"/>
	            <tags:hoverHint keyProp="organization.nciInstituteCode"/>
	        </c:if>
	        <c:if test="${FLOW == 'SAVE_FLOW'}">
                <form:input path="nciInstituteCode" size="20" cssClass="validate-notEmpty"/>
                <tags:hoverHint keyProp="organization.nciInstituteCode"/>
	        </c:if>
        </div>
    </div>
    
    <div class="row">
        <div class="label">
            Advanced Property:
        </div>
        <div class="value">
	        <input type="checkbox" id="advance" name="setAdvancedProperty" onChange="new Effect.Combo('multisite-config');">
        </div>
    </div>
    
    </chrome:division>

    <chrome:division id="multisite-config" title="Multisite configuration">
    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
                Study service url:
            </div>
            <div class="value">
                <input type="text" size="60" id="studyServiceURL" name="studyServiceURL" value="${command.hasEndpointProperty?command.studyEndPointProperty.url:''}" class="validate-notEmpty"/>
            </div>
        </div>

        <div class="row">
            <div class="label required-indicator"">
                Registration service url:
            </div>
            <div class="value">
                <input type="text" size="60" id="registrationServiceURL" name="registrationServiceURL" value="${command.hasEndpointProperty?command.registrationEndPointProperty.url:''}" class="validate-notEmpty"/>
            </div>
        </div>

        <div class="row">
            <div class="label">
                Authentication required:
            </div>
            <div class="value">
                <input type="checkbox" id="authenticationRequired" name="authenticationRequired"/>
            </div>
        </div>
    </div>
    </chrome:division>
    <script>
    <c:choose>
	<c:when test="${command.hasEndpointProperty}">
       $('advance').checked=true;
       <c:if test="${command.endPointAuthenticationRequired}">$('authenticationRequired').checked=true;</c:if>
    </c:when>
    <c:otherwise>
    	new Element.hide('multisite-config');
    </c:otherwise>
    </c:choose>
    </script>
    <chrome:division id="address" title="Address">
    <div class="leftpanel">


        <div class="row">
            <div class="label">
                Street Address:
            </div>
            <div class="value">
                <form:input size="40" path="address.streetAddress"/>
            </div>
        </div>

        <div class="row">
            <div class="label">
                City:
            </div>
            <div class="value">
                <form:input path="address.city" size="20"/>
            </div>
        </div>

        <div class="row">
            <div class="label">
                State:
            </div>
            <div class="value">
                <form:input path="address.stateCode" size="20"/>
            </div>
        </div>

        <div class="row">
            <div class="label">
                Zip:
            </div>
            <div class="value">
                <form:input path="address.postalCode" size="20"/>
            </div>
        </div>

        <div class="row">
            <div class="label">
                Country:
            </div>
            <div class="value">
                <form:input path="address.countryCode" size="20"/>
            </div>
        </div>


    </div>

    </chrome:division>


    </jsp:attribute>
    </tags:tabForm>
    </div>
    </body>
</html>
