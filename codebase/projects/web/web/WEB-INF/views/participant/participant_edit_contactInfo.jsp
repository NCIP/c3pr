<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="participantTags" tagdir="/WEB-INF/tags/participant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function getPage(s){
	parent.window.location="reg_patient_search.htm";
}
function updatePage(s){
	document.getElementById("_page").name=s;
	document.getElementById("_page").value="next";
	document.getElementById("form").submit();
}
function updateAction(action){
		document.getElementById("_updateaction").value=action;
		document.getElementById("form1").submit();
}
function clearField(field){
field.value="";
}
</script>
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script language="JavaScript" id="js1">
	var cal1 = new CalendarPopup();
</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->

<tags:search action="searchParticipant" />

<tabs:body
	title="Edit Subject : ${command.firstName}  ${command.lastName}">
	<form:form method="post" name="form" id="form">
		<table border="0" id="table1" cellspacing="10" width="100%">

			<table border="0" id="table1" cellspacing="10" width="100%">
				<tr>
					<td valign="top" width="30%"><participantTags:participantSummary />
					</td>
					<td width="40%" valign="top"><tabs:levelTwoTabs tab="${tab}"
						flow="${flow}" showNumber="false" /> <tabs:division id="Editing">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="details">
							<tr>
								<td align="left" width="50%" border="0" valign="top"
									class="contentAreaL">
								<form name="form2" method="post" action="" id="form1">
								<div><input type="hidden" name="_page" id="_page" value="3"> <input
									type="hidden" name="_action" id="_action" value=""> <input
									type="hidden" name="_selected" id="_selected" value=""> <input
									type="hidden" name="_updateaction" id="_updateaction" value=""></div>
								<table width="670" border="0" cellspacing="0" cellpadding="0"
									id="table1">
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="1" class="heightControl"></td>
										<td width="65%"><img src="<tags:imageUrl name="spacer.gif"/>"
											width="1" height="1" class="heightControl"></td>
									</tr>

									<tr>
										<td><b>${command.firstName} ${command.lastName} has no Contact
										Information </b></td>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="1" class="heightControl  style1"></td>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="1" class="heightControl"></td>
									</tr>
									<tr>
										<td><img src="<tags:imageUrl name="spacer.gif"/>" width="1"
											height="2" class="heightControl"></td>
										<td width="65%"><img src="<tags:imageUrl name="spacer.gif"/>"
											width="1" height="1" class="heightControl"></td>
									</tr>
								</table>

								</form>
							</tr>
							<tr>
								<td align="center" colspan="3"><!-- action buttons begins -->
								<table cellpadding="4" cellspacing="0" border="0">
									<tr>
										<td colspan=2 valign="top"><br>
										<br>
										<a href="javascript:updateAction('update');"><img
											src="<tags:imageUrl name="b-saveChanges.gif"/>" border="0"
											alt="Save the Changes"></a>
									</tr>
								</table>
								</td>
							</tr>
							<c:forEach begin="1" end="14">
								<tr>
									<td><br>
									</td>
								</tr>
							</c:forEach>
						</table>

					</tabs:division></td>

					<td valign="top" width="30%"><participantTags:registrationSummary />
					</td>
				</tr>
				<tr>
					<participantTags:registrationHistory />
				</tr>
			</table>
			</form:form>
			</tabs:body>
			<!-- MAIN BODY ENDS HERE -->
</body>
</html>
