<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<style type="text/css">
        .label { width: 20em; text-align: right; padding: 2px; }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="/c3pr/js/CalendarPopup.js"></script>
<script>
</script>
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}">
	<strong>Please select an arm  </strong><br>
	<table width="60%" border="0" cellspacing="0" cellpadding="0" id="table1">
		<tr><td>&nbsp;</td></tr>
		<tr>
			<c:forEach var="epoch" items="${command.studySite.study.epochs}">
				<c:choose>
					<c:when test="${epoch.name=='Treatment'}">
						<c:set var="flag" value="true" scope="page"></c:set>
						<td class="label" width="80%">Select Arm:</td>
						<td>
							<select name ="scheduledArms[0].arm">
								<option value="" selected>--Please Select--</option>
								<c:forEach items="${epoch.arms}" var="arm">
									<option value="${arm.id }" <c:if test="${arm.id== command.scheduledArms[0].arm.id }">selected</c:if>>${arm.name}</option>
								</c:forEach>
							</select>
						</td>
					</c:when>
				</c:choose>
			</c:forEach>
			<c:if test="${empty flag}">
				<td><span class="red"> This Study has no radomization</span></td>
			</c:if>
		</tr>
	</table>
</tags:formPanelBox>
</body>
</html>
