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
    <c:url value="/pages/admin/password_policy_configure" var="action" />
    <form:form action="${action}">

      <chrome:division title="Login Policy">
	<div class="nested_section">
	  <div class="row">
	    <div class="required_label">Maximum password age (seconds):</div>
	    <div class="required_value">
	      <form:input path="loginPolicy.maxPasswordAge" size="8"/>
	    </div>
	  </div>
	</div>
	<div class="nested_section">
	  <div class="row">
	    <div class="required_label">Number of allowed failed login attempts:</div>
	    <div class="required_value">
	      <form:input path="loginPolicy.allowedFailedLoginAttempts" size="3"/>
	    </div>
	  </div>
	</div>

	<div class="nested_section">
	  <div class="row">
	    <div class="required_label">Lockout duration (seconds):</div>
	    <div class="required_value">
	      <form:input path="loginPolicy.lockOutDuration" size="8"/>
	    </div>
	  </div>
	</div>
      </chrome:division>
		
      <chrome:division title="Password Creation Policy">
	<div class="nested_section">
	  <div class="row">
	    <div class="required_label">Minimum password age (seconds):</div>
	    <div class="required_value">
	      <form:input path="passwordCreationPolicy.minPasswordAge" size="8"/>
	    </div>
	  </div>
	</div>

	<div class="nested_section">
	  <div class="row">
	    <div class="required_label">Password history size:</div>
	    <div class="required_value">
	      <form:input path="passwordCreationPolicy.passwordHistorySize" size="3"/>
	    </div>
	  </div>
	</div>

	<div class="nested_section">
	  <div class="row">
	    <div class="required_label">Minimum password length:</div>
	    <div class="required_value">	
	      <form:input path="passwordCreationPolicy.minPasswordLength" size="3"/>
	    </div>
	  </div>
	</div>  
	</chrome:division>
	<chrome:division title="Complexity Requirement">	  
	  
	  <div class="nested_section">
	    <div class="row">
	      <div class="required_label">Meets the following complexity requirements:</div>
	    </div>
	  </div>

	  <div class="nested_section">
	    <div class="nested_section">
	      <div class="row">
		<div class="required_value">
		  <form:checkbox path="passwordCreationPolicy.combinationPolicy.upperCaseAlphabetRequired"/>
		</div>
		<div class="required_label">At least one uppercase letter</div>
	      </div>
	    </div>
	    
	    <div class="nested_section">
	      <div class="row">
		<div class="required_value">
		  <form:checkbox path="passwordCreationPolicy.combinationPolicy.lowerCaseAlphabetRequired"/>
		</div>
		<div class="required_label">At least one lowercase letter</div>
	      </div>
	    </div>		
	    
	    <div class="nested_section">
	      <div class="row">
		<div class="required_value">
		  <form:checkbox path="passwordCreationPolicy.combinationPolicy.nonAlphaNumericRequired"/>
		</div>
		<div class="required_label">At least one non-alphanumeric character</div>
	      </div>
	    </div>
	    
	    <div class="nested_section">
	      <div class="row">
		<div class="required_value">
		  <form:checkbox path="passwordCreationPolicy.combinationPolicy.baseTenDigitRequired"/>
		</div>
		<div class="required_label">At least one digit</div>
	      </div>
	    </div>
	  </div>

	  <div class="nested_section">
	    <div class="row">
	      <div class="required_label">Largest substring of username allowed:</div>
	      <div class="required_value">
		<form:input path="passwordCreationPolicy.combinationPolicy.maxSubstringLength" size="3"/>
	      </div>
	    </div>
	  </div>
	</chrome:division>

      <div class="row submit" align="right">
	<input type="submit" value="Save"/>
      </div>

      <c:if test="${updated}">
	<p class="updated">Settings saved</p>
      </c:if>
    </form:form>
  </chrome:box>
</body>
</html>
