<%@tag%><%@attribute name="action" required="true"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
function submit(){
	document.getElementById("searchForm").submit();
}
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="titleArea">
	<form:form id="searchForm" name="searchForm" method="post" action="${action}">
	<tr>
		<!-- TITLE STARTS HERE -->
		<td width="100%" valign="middle" id="title"></td>
		<!-- TITLE STARTS HERE -->
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td class="labels">&nbsp;</td>
				</tr>
				<tr>
					<td class="searchType">Search by <select name="searchType">
						<c:forEach items="${searchTypeRefData}" var="opt">
							<option value="${opt.code}">${opt.desc}</option>
						</c:forEach></td>
					<td><input type=text name="searchText" size="25" /></td>			
					<td><input name="imageField" type="image" class="button"
					onClick="submit();return false;" src="<tags:imageUrl name="b-go.gif"/>"
					alt="GO" align="middle" width="22" height="10" border="0"></td>
				</tr>
			</table>
			<span class="notation">&nbsp;</span></td>
		</td>
	</tr>
</form:form>
</table>