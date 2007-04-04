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
		<div><input type="hidden" name="_page" id="_page" value="0"> <input
			type="hidden" name="_action" id="_action" value=""> <input
			type="hidden" name="_selected" id="_selected" value=""> <input
			type="hidden" name="_updateaction" id="_updateaction" value=""></div>
		<table border="0" id="table1" cellspacing="0" width="100%">

			<table border="0" id="table1" cellspacing="0" width="100%">
				<tr>
					<td valign="top" width="30%"><participantTags:participantSummary />
					</td>
					<td width="40%" valign="top"><tabs:levelTwoTabs tab="${tab}"
						flow="${flow}" /> <tabs:division id="Editing">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="details">
							<tr>
								<td align="left" width="40%" border="0" valign="top"
									class="contentAreaL">
								<form name="form2" method="post" action="" id="form1">
								<table width="670" border="0" cellspacing="0" cellpadding="0"
									id="details">
									<tr>
										<td width="50%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td align="right"><span class="red">*</span><em></em> <b>First
												Name:</b>&nbsp;</td>
												<td align="left"><form:input path="firstName" /><em></em></td>
											</tr>
											<tr>
												<td align="right"><span class="red">*</span><em></em> <b>Last
												Name:</b>&nbsp;</td>
												<td align="left"><form:input path="lastName" /><em></em></td>
											</tr>
											<tr>
												<td align="right"><span class="red">*</span> <em></em> <b>Gender:</b>&nbsp;</td>
												<td align="left"><form:select
													path="administrativeGenderCode">
													<form:options items="${administrativeGenderCode}"
														itemLabel="desc" itemValue="code" />
												</form:select></td>
											</tr>
										</table>
										</td>
										<td width="50%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0"
											id="table1">
											<tr>
												<td align="right"><span class="red">&nbsp;&nbsp;&nbsp;*</span><em></em><b>Birth
												Date:</b> &nbsp;</td>
												<td align="left"><form:input path="birthDate" />&nbsp;<a
													href="#"
													onClick="cal1.select(document.getElementById('birthDate'),'anchor1','MM/dd/yyyy');return false;"
													name="anchor1" id="anchor1"><img
													src="<tags:imageUrl name="b-calendar.gif"/>" alt="Calendar"
													width="17" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;&nbsp;<span
													class="red"><form:errors path="birthDate" /><em></em></span></td>
											</tr>
											<tr>
												<td align="right"><em></em><b>Ethnicity:</b>&nbsp;</td>
												<td align="left"><form:select path="ethnicGroupCode">
													<form:options items="${ethnicGroupCode}" itemLabel="desc"
														itemValue="code" />
												</form:select></td>
											</tr>
											<tr>
												<td align="right"><em></em><b>Race(s):</b>&nbsp;</td>
												<td align="left"><form:select path="raceCode">
													<form:options items="${raceCode}" itemLabel="desc"
														itemValue="code" />
												</form:select></td>
											</tr>

										</table>
										</td>
									</tr>
								</table>
								</form>
							</tr>
							<c:forEach begin="1" end="13">
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
