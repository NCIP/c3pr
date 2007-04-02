<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">
function doNothing(){
}
function validatePage(){
	if(document.getElementById("longTitleText") != null)
		return true;
	else
		return false;
}
</script>
</head>
<body>
<form:form method="post" cssClass="standard">
<tabs:tabFields tab="${tab}" />
<div>
<tabs:division id="study-details">
<!-- MAIN BODY STARTS HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top" class="additionals2">
			<tr>
				<td id="current">Study Created Succesfully for Short Title :
				${command.trimmedShortTitleText} on Study Site : ${command.studySites[0].site.name}</td>
			</tr>
			<tr>
				<td class="display">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td valign="top" class="additionals2">
						<input type="hidden"
							name="nextView"> <strong>Please <a
							href="javascript:doNothing()">print</a> and save this
						confirmation in the study records </strong><br>
						<table width="50%" border="0" cellspacing="0" cellpadding="0"
							id="details">
							<tr>
								<td width="50%" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td width ="20%"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="1"
											class="heightControl"></td>
									</tr>
									<tr>
										<td width="20%" class="label">Study Created on:</td>
									</tr>
									<tr>
										<td width="20%" class="label">Study Site:</td>
										<td>${command.studySites[0].site.name}</td>
									</tr>

									<tr>
										<td width="20%" class="label">Study Identifiers</td>
										<td ><c:forEach items="${command.identifiers}" var="id">
											${id.value},
										</c:forEach></td>
									</tr>

									<tr>
										<td class="label">Subject MRN Num:</td>
										<td>${command.primaryIdentifier}</td>
									</tr>

								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</td>
	</tr>
</table>
<!-- MAIN BODY ENDS HERE -->
</tabs:division>
</div>
</form:form>
</body>
</html>
