<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
 <%@ include file="taglibs.jsp"%>

<html>
<head>
  <title>Change Password</title>
  <style type="text/css">
    .box {
      width: 40em;
      margin: 0 auto;
    }
    .submit {
      float: right;
      margin-top: 1em;
    }
    .label {
      width: 30em;
    }
  </style>
</head>
<body>
  <c:if test="${updated}">
    <c:url value="/public/login" var="login"/>
    <p class="label">Changed password successfully, you should now be able to <a href="${login}">login</a>.</p>
  </c:if>
  <c:if test="${not updated}">
    <chrome:box title="Please enter your credentials" autopad="true">
      <c:url value="/public/user/changePassword" var="action"/>
      <form:form action="${action}">
	<p class="errors">${change_pwd_error.message}</p>
	<div class="row">
	<div class="label"><tags:requiredIndicator /><spring:message code="changepassword.username"/></div>
	<div class="value">
	  <form:input path="userName" cssClass="required validate-notEmpty" autocomplete="off"/>
	</div>
	</div>
	<div class="row">
	  <div class="label"><tags:requiredIndicator /><spring:message code="changepassword.password"/></div>
	  <div class="value">
	    <form:password path="password" cssClass="required validate-notEmpty"autocomplete="off"/><br> <font color="red" style="font-style: italic;"><spring:message code="changepassword.password.requirement"/></font>
	  </div>
	</div>
	<div class="row">
	  <div class="label"><tags:requiredIndicator /><spring:message code="changepassword.password.confirm"/></div>
	  <div class="value">
	    <form:password path="passwordConfirm" cssClass="required validate-notEmpty" autocomplete="off"/>
	  </div>
	</div>
	<div class="row" align="center">
	  <td class="submit">
	    <input type="submit" value="Change Password"/>
	  </td>
	</div>
	<form:hidden path="token"/>
      </form:form>
    </chrome:box>
  </c:if>
</body>
</html>
