<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
 <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
 </style>
</head>
<body>
<div class="tabpane">
  <ul id="workflow-tabs" class="tabs autoclear">
    <li class="tab"><div>
        <a href="../admin/searchOrganization">Search Organization</a>
    </div></li>
    <li class="tab selected"><div>
        <a href="../admin/createOrganization">Create Organization</a>
    </div></li>
  </ul>
</div>

<div id="main">
<br />

<tags:tabForm tab="${tab}" flow="${flow}" title="Organization" formName="createOrganization">

<jsp:attribute name="singleFields">
<input type="hidden" name="_finish" value="true">
<input type="hidden" name="type1" value="">
<tags:errors path="*"/>

<chrome:division id="organization" title="Details">
<div class="leftpanel">

    <div class="row">
        <div class="label required-indicator">
            Name:
        </div>
        <div class="value">
            <form:input size="48" path="name" cssClass="validate-notEmpty"/>
            <tags:hoverHint keyProp="organization.name"/>
        </div>
    </div>

    <div class="row">
        <div class="label">
            Description:
        </div>
        <div class="value">
            <form:textarea rows="3" cols="45" path="descriptionText"/>
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
    </chrome:division>

    <chrome:division id="address" title="Address">
    <div class="leftpanel">


        <div class="row">
            <div class="label">
                Street Address:
            </div>
            <div class="value">
                <form:input size="46" path="address.streetAddress"/>
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
