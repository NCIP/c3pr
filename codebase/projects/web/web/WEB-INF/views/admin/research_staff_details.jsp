<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="edu.duke.cabig.c3pr.domain.C3PRUserGroupType" %>

<html>
<head>
    <script>


        function handleConfirmation(){
            new Effect.SlideDown('createRS');
            new Effect.SlideUp('confirmationMessage');
            new Effect.SlideDown('dispButton');
        }
    </script>
</head>
<body>

<tags:tabForm tab="${tab}" flow="${flow}" title="Research Staff" formName="researchStaffForm">

<jsp:attribute name="singleFields">
<input type="hidden" name="_finish" value="true">
<input type="hidden" name="type1" value="">
<tags:errors path="*" />

<chrome:division id="site" title="Organization">

    <div class="leftpanel">

        <div class="row">
            <div class="label required-indicator">
                Choose an Organization
            </div>
            <div class="value">
                <form:select path="healthcareSite"
                             id="selectedHealthcareSite" cssClass="validate-notEmpty">
                    <option value="">--Please Select--</option>
                    <form:options items="${healthcareSites}" itemLabel="name"
                                  itemValue="id" />
                </form:select>
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
                <form:input path="lastName" size="25" />
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
                <form:input path="nciIdentifier" size="25" />
            </div>
        </div>

        <div class="row">
            <div class="label required-indicator">
                    ${command.contactMechanisms[0].type.displayName} (Username):
            </div>
            <div class="value">
                <form:input size="25"
                            path="contactMechanisms[0].value" />
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


    </div>

</chrome:division>



</jsp:attribute>
</tags:tabForm>
</body>
</html>
