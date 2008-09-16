<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
	<head>
	  <title>Reset Password</title>
	  <style type="text/css">
	    .box {
	     width: 40em;
	     margin: 0 auto;
	    }
	    .submit {
	     float: right;
	     margin-top: 1em;
	    }
	  </style>
	</head>
	<body>
	  <chrome:box title="Please enter your username" autopad="true">
	  <c:url value="/public/user/resetPassword" var="action"/>
	    <form:form action="${action}">
			<p class="errors">${reset_pwd_error}</p>
	      <div class="row">
				<div class="label required-indicator">Username &nbsp;&nbsp;</div> 
				<td class="label"><form:input cssClass="validate-notEmpty" path="userName"/></td>
		  </div>
		  <div class="row"  align="center">
			<td class="submit">
		  		<input type="submit" value="Reset Password"/>
			</td>
	      </div>

	    </form:form>
	  </chrome:box>
	</body>
</html>