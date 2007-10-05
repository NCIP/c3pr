<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="edu.duke.cabig.c3pr.domain.C3PRUserGroupType" %>

<html>
<head>
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
                Choose an Organization
            </div>
            <div class="value">
            	<c:if test="${FLOW == 'EDIT_FLOW'}">
		            <form:select path="healthcareSite"
	                             id="selectedHealthcareSite" cssClass="validate-notEmpty" disabled="true">
	                    <option value="">--Please Select--</option>
	                    <form:options items="${healthcareSites}" itemLabel="name"
	                                  itemValue="id" />
	                </form:select> 
		        </c:if>
		        <c:if test="${FLOW == 'SAVE_FLOW'}">
	                <form:select path="healthcareSite"
	                             id="selectedHealthcareSite" cssClass="validate-notEmpty">
	                    <option value="">--Please Select--</option>
	                    <form:options items="${healthcareSites}" itemLabel="name"
	                                  itemValue="id" />
	                </form:select> 
		        </c:if>
            </div>
        </div>
    </div>
</chrome:division>

<chrome:division id="staff-details" title="Research Staff Details">
    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
                First Name</div>
            <div class="value">
                <form:input size="25" path="firstName"
                            cssClass="validate-notEmpty" />
            </div>
        </div>
        <div class="row">
            <div class="label required-indicator">
                Last Name</div>
            <div class="value">
                <form:input path="lastName" cssClass="validate-notEmpty" size="25" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                Middle Name</div>
            <div class="value">
                <form:input path="middleName" size="25" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                Maiden Name</div>
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
            </div>
        </div>
        <div class="row">
            <div class="label required-indicator">
                    ${command.contactMechanisms[0].type.displayName} (Username):
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[0].value" cssClass="validate-EMAIL" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[1].type.displayName}:
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[1].value" />
            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.contactMechanisms[2].type.displayName}:
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[2].value" />
            </div>
        </div>
    </div>
</chrome:division>

<chrome:division id="staff-details" title="User Role (Check all that apply)">
    <div class="leftpanel">
        <div class="row">
            <div class="label">
                    ${command.groups[0].displayName}:
            </div>
            <div class="value">
                <form:checkbox
                            path="groups[0]" value="${command.groups[0].code}"/>
            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.groups[1].displayName}:
            </div>
            <div class="value">
                <form:checkbox
                            path="groups[1]" value="${command.groups[1].code}"/>
            </div>
        </div>
        <div class="row">
            <div class="label">
                    ${command.groups[2].displayName}:
            </div>
            <div class="value">
                <form:checkbox
                            path="groups[2]" value="${command.groups[2].code}"/>
            </div>
        </div>

        <div class="row">
            <div class="label">
                    ${command.groups[3].displayName}:
            </div>
            <div class="value">
                <form:checkbox
                            path="groups[3]" value="${command.groups[3].code}"/>
            </div>
        </div>
    </div>
</chrome:division>
</jsp:attribute>

</tags:tabForm>
</div>
</body>
</html>
