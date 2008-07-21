<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
</head>
<body>
<div id="main">
	<div class="row">
	 <div class="label"><label for="subject">Subject Line</label></div>
	 <div class="value">
	 	<%--<form:input path="emailNotifications[${index}].subjectLine" size="100" cssStyle="width:96%;" onfocus="lastElement = this;" />	--%>
	 	<input id="notifications[1].emailBasedRecipient[0].emailAddress" name="notifications[1].emailBasedRecipient[0].emailAddress" 
	 			class="validate-notEmpty" type="text" value="Study Status has Changed" size="30"/>
	 </div>
	</div>
	
	
	<div class="row">
	 <div class="label"><label for="subject">Substitution Variables</label></div>
	 <div class="value">
	 	<select id="subVar" name="subVar">
			<option value="">Please Select</option>
			<option value="SS" selected="selected">CoordinatingCenter Study Status</option>
			<option value="ID">Study Id</option>
			<option value="ST">Study Short Title</option>
			<option value="IN">Registration status</option>
		</select>				
	 </div>
	</div>
	
	
	<div class="row">
	 <div class="label"><label for="message">Message</label></div>
	 <div class="value">
	 	<%-- <form:textarea cssStyle="width:96%; height:300px" path="emailNotifications[${index}].notificationBodyContent.body" onfocus="lastElement = this;"/> --%>
	 	<textarea id="emailBody" rows="20" cols="70" ></textarea>
	 </div>
	</div>
</div>
</body>
</html>