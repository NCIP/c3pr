<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
  <title>Configure Password Policy</title>
  <style type="text/css">
    div {
    }

    .nested_section {
     width: 100%;
     margin-left: 20px;
    }

    .required_label {
     text-align: left;
     float: left;
     line-height: 23px;
     margin: 0px 5px;
    }

    .required_value {
     float: left;
     line-height: 20px;
    }
    
    .required_item_heading {
     clear: both;
    }

    .updated {
     border: #494 solid;
     border-width: 1px 0;
     background-color: #8C8;
     padding: 1em 2em;
     text-align: center;
     margin: 1em 30%;
     color: #fff;
     font-weight: bold;
     font-size: 1.1em;
    }
  </style>
</head>
<body> 
  <chrome:box title="Password Policy Configuration" autopad="true">
  <tags:instructions code="password_policy_configuration" />
    <c:url value="/pages/admin/password_policy_configure" var="action" />
    <form:form action="${action}">

	<chrome:division title="Login Policy">
	<div class="nested_section">
	  <div class="row">
	    <div class="required_label"><b><fmt:message key="password.maximumPasswordAge"/></b></div>
	    <div class="required_value">
	      <form:input path="loginPolicy.maxPasswordAge" size="8"/>
	    </div>
	  </div>
	</div>
	<div class="nested_section">
	  <div class="row">
	    <div class="required_label"><b><fmt:message key="password.numberOfAllowedFailedLoginAttempts"/></b></div>
	    <div class="required_value">
	      <form:input path="loginPolicy.allowedFailedLoginAttempts" size="3"/>
	    </div>
	  </div>
	</div>
	<div class="nested_section">
	  <div class="row">
	    <div class="required_label"><b><fmt:message key="password.allowedLoginTime"/></div>
	    <div class="required_value">
	      <form:input path="loginPolicy.allowedLoginTime" size="8"/>
	    </div>
	  </div>
	</div>
	<div class="nested_section">
	  <div class="row">
	    <div class="required_label"><b><fmt:message key="password.lockoutDuration"/></b></div>
	    <div class="required_value">
	      <form:input path="loginPolicy.lockOutDuration" size="8"/>
	    </div>
	  </div>
	</div>
	</chrome:division>
		
      <chrome:division title="Password Creation Policy">
	<div class="nested_section">
	  <div class="row">
	    <div class="required_label"><b><fmt:message key="password.minimumPasswordAge"/></b></div>
	    <div class="required_value">
	      <form:input path="passwordCreationPolicy.minPasswordAge" size="8"/>
	    </div>
	  </div>
	</div>

	<div class="nested_section">
	  <div class="row">
	    <div class="required_label"><b><fmt:message key="password.passwordHistorySize"/></b></div>
	    <div class="required_value">
	      <form:input path="passwordCreationPolicy.passwordHistorySize" size="3"/>
	    </div>
	  </div>
	</div>

	<div class="nested_section">
	  <div class="row">
	    <div class="required_label"><b><fmt:message key="password.minimumPasswordLength"/></b></div>
	    <div class="required_value">	
	      <form:input path="passwordCreationPolicy.minPasswordLength" size="3"/>
	    </div>
	  </div>
	</div>  
	</chrome:division>
	<chrome:division title="Complexity Requirement">	  
	  
	  <div class="nested_section">
	    <div class="row">
	      <div class="required_label"><b>Meets the following complexity requirements:</b></div>
	    </div>
	  </div>

	  <div class="nested_section">
	    <div class="nested_section">
	      <div class="row">
		<div class="required_value">
		  <form:checkbox path="passwordCreationPolicy.combinationPolicy.upperCaseAlphabetRequired"/>
		</div>
		<div class="required_label"><b><fmt:message key="password.atLeastOneUppercaseLetter"/></b></div>
	      </div>
	    </div>
	    
	    <div class="nested_section">
	      <div class="row">
		<div class="required_value">
		  <form:checkbox path="passwordCreationPolicy.combinationPolicy.lowerCaseAlphabetRequired"/>
		</div>
		<div class="required_label"><b><fmt:message key="password.atLeastOneLowercaseLetter"/></b></div>
	      </div>
	    </div>		
	    
	    <div class="nested_section">
	      <div class="row">
		<div class="required_value">
		  <form:checkbox path="passwordCreationPolicy.combinationPolicy.nonAlphaNumericRequired"/>
		</div>
		<div class="required_label"><b><fmt:message key="password.atLeastOneNon-alphanumericCharacter"/></b></div>
	      </div>
	    </div>
	    
	    <div class="nested_section">
	      <div class="row">
		<div class="required_value">
		  <form:checkbox path="passwordCreationPolicy.combinationPolicy.baseTenDigitRequired"/>
		</div>
		<div class="required_label"><b><fmt:message key="password.atLeastOneDigit"/></b></div>
	      </div>
	    </div>
	  </div>

	  <div class="nested_section">
	    <div class="row">
	      <div class="required_label"><b><fmt:message key="password.largestSubstringOfUsernameAllowed"/></b></div>
	      <div class="required_value">
		<form:input path="passwordCreationPolicy.combinationPolicy.maxSubstringLength" size="3"/>
	      </div>
	    </div>
	  </div>
	</chrome:division>

      <div class="row submit" align="right">
	<tags:button type="submit" color="green" value="Save" icon="save"/>
      </div>

      <c:if test="${updated}">
	<p class="updated">Settings saved</p>
      </c:if>
    </form:form>
  </chrome:box>
</body>
</html>
