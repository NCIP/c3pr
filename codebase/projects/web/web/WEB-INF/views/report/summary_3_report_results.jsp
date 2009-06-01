<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="commons"
	uri="http://bioinformatics.northwestern.edu/taglibs/commons"%>
<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="edu.duke.cabig.c3pr.constants.C3PRUserGroupType"%>

<html>
<head>
<style>
		#main {
			top:35px;
		}
	</style>

<script language="JavaScript" type="text/JavaScript">
	  function submitForm(){
		  	document.getElementById('_finish').value='true';
			document.getElementById('command').submit();
	}

</script>
<title>Summary 3 Report</title>

</head>
<body>
<div id="controlPanel">
	<tags:controlPanel>					
				<tags:oneControlPanelItem linkhref="javascript:submitForm();" imgsrc="/c3pr/templates/mocha/images/controlPanel/pdf_icon.png" linktext="Export As PDF" />
	</tags:controlPanel>
</div>
<form:form name="summary3ReportForm">
	<div>
    	<input type="hidden" name="_finish" id="_finish" value="true"/>
	</div>
	<chrome:box title="Summary 3: Reportable Patients/Participation in Therapeutic Protocols"  htmlContent="2P30CA654321-50">
		<tags:tabFields tab="${tab}" />

		<chrome:division>
			<table id="reportTableInfo" align="center" width="80%">
			<tr>
				<td></td>
				<td align="center"><b><font size="3">${command.reportingOrganization.name}</font></b></td> 
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td align="center"><b> Reporting Period&nbsp;${command.startDateStr} - ${command.endDateStr}<b></td> 
				<td></td>
			</tr>

			</table>
			<table id="summaryReportData" class="tablecontent" align="center" width="80%">
				<tr>
					<th width="40%"><b>Disease Site</b></th>
					<th width="10%">Newly Registered Patients</th>
					<th width="18%">Total patients newly enrolled in <b>therapeutic</b> protocols</th>
				</tr>
				<c:forEach items="${command.reportData}"
					var="diseaseSite" varStatus="status">
					<tr id="diseaseSite-${status.index}">
						<td>${diseaseSite.key.name}</td>
						<c:forEach items="${diseaseSite.value}" var="registrationCounts" varStatus="varStatus">
							<td>${registrationCounts.value}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</chrome:division>
		<br>
		<div align="right">
				    	<tags:button type="submit" color="green" id="flow-update"
						value="Export As PDF" onclick="javascript:submitForm();" />
		</div>
	</chrome:box>
</form:form>


</body>
</html>
