<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>${tab.longTitle}</title>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function validatePage(){
	return true;
}
function fireAction(action, selected){
	if(validatePage()){
		document.getElementsByName('_target5')[0].name='_target4';
		document.studySiteForm._action.value=action;
		document.studySiteForm._selected.value=selected;
		document.studySiteForm.submit();
	}
}
function clearField(field){
field.value="";
}

function chooseSites(){
		//alert("inside choose sites");
		document.getElementsByName('_target5')[0].name='_target4';
		document.studySiteForm._action.value="siteChange";
		document.studySiteForm._selected.value=document.getElementById('site').value;
		document.studySiteForm.submit();
}

function chooseSitesfromSummary(selected){
		//alert("inside chooseSitesfromSummary");
		//alert(selected);
		document.getElementsByName('_target5')[0].name='_target4';
		document.studySiteForm._action.value="siteChange";
		document.studySiteForm._selected.value=selected;
		document.studySiteForm.submit();
}

function fireAction1(action, selected, studysiteindex){

	//alert("inside fireAction1");
	//alert(selected);
	//alert(studysiteindex);
	if(validatePage()){
		document.getElementsByName('_target5')[0].name='_target4';
		document.studySiteForm._action.value=action;
		document.studySiteForm._selected.value=selected;
		document.studySiteForm._studysiteindex.value=studysiteindex;
		document.studySiteForm.submit();
	}
}
</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->

<tabs:body title="${flow.name}: ${tab.longTitle}">
				<form:form method="post" name="studySiteForm">

				<table border="0" id="table1" cellspacing="10" width="100%">
					<tr>

					<td width="75%" valign="top">
				<tabs:division id="study-details">
				<tabs:tabFields tab="${tab}"/>
				<div>
					<input type="hidden" name="_action" value="">
					<input type="hidden" name="_selected" value="">
					<input type="hidden" name="_studysiteindex" value="">
				</div>
				<p id="instructions">
					Please choose a study site and link Research staff to that study site
				</p>
					<c:set var="index1" value="0"/>
					<c:if test="${!empty site_id_for_per}">
						<c:set var="index1" value="${site_id_for_per}"/>
					</c:if>

					<table border="0" id="table1" cellspacing="10" width="30%">

	                   <tr>
	                   		<td align="right"> <b> <span class="red">*</span><em></em>Site:</b> </td>
	                   		<td align="left">
								<select id="site" name="site" onchange="javascript:chooseSites();">
									<c:forEach var="studySite" varStatus="status" items="${command.studySites}">
										<c:if test="${index1 == status.index }">
											<option selected="true" value=${status.index}>${studySite.site.name}</option>
										</c:if>
										<c:if test="${index1 != status.index }">
											<option value=${status.index}>${studySite.site.name}</option>
										</c:if>
									</c:forEach>
				    			</select>
	                   		</td>
	                   </tr>
					</table>

					<c:set var="index" value="0"/>
					<c:if test="${!empty site_id_for_per}">
						<c:set var="index" value="${site_id_for_per}"/>
					</c:if>

					<table border="0" id="table1" cellspacing="10" width="100%">
						<tr>
							<td align="center"> <b> <span class="red">*</span><em></em>Research Staff:</b> </td>
							<td align="center"> <b> <span class="red">*</span><em></em>Role:</b> </td>
							<td align="center"> <b> <span class="red">*</span><em></em>Status:</b> </td>
							<td align="center">
								<b><a href="javascript:fireAction1('addInv','0', ${index});"><img
									src="<tags:imageUrl name="b-addLine.gif"/>" border="0" alt="Add"></a></b>
							</td>
						</tr>


					    <c:forEach varStatus="status" items="${command.studySites[index].studyPersonnels}">
							<tr>
								<td align="center" width="15%">
								    <form:select path="studySites[${index}].studyPersonnels[${status.index}].researchStaff">
									 	<form:options items="${researchStaffRefData}" itemLabel="firstName"
												itemValue="id" />
									</form:select>
								</td>

								<td align="center" width="15%">
								    <select id="x1" name="x2">
										<option value=" ">role1</option>
									    <option value=" ">role2</option>
				    				</select>
								</td>

								<td align="center" width="15%">
								    <select id="x1" name="x2">
										<option value=" ">active</option>
									    <option value=" ">closed</option>
				    				</select>
								</td>

    	    				    <td align="center" width="10%">
									<a href="javascript:fireAction1('removeInv',${status.index}, ${index});"><img
											src="/caaers/images/checkno.gif" border="0" alt="delete"></a>
								</td>
							</tr>
						</c:forEach>

					</table>
				</tabs:division>
				</td>
				<td valign="top" width="25%">
					<tabs:division id="Summary" title="Summary">
					<font size="2"><b> Study Sites </b> </font>
					<br><br>
					<table border="0" id="table1" cellspacing="0" cellpadding="0" width="100%">
					<c:forEach var="studySite" varStatus="status" items="${command.studySites}">
						<tr>
							<td>
								<a onclick="javascript:chooseSitesfromSummary(${status.index});" title="click here to edit person assigned to a study"> <font size="2"> <b> ${studySite.site.name} </b> </font> </a>
							</td>
						</tr>
						<tr>
							<td>
								Personnels Assigned: <b> ${fn:length(studySite.studyPersonnels)} </b>
							</td>
						</tr>
						<tr>
							<td>
								<br>
							</td>
						</tr>
					</c:forEach>
					<c:forEach begin="1" end="15">

					<tr>
						<td>
							<br>
						</td>
					</tr>
					</c:forEach>
					</table>
					</tabs:division>
					</td>
				</tr>
				</table>
				</form:form>
<!-- MAIN BODY ENDS HERE -->
</tabs:body>
</body>
</html>
