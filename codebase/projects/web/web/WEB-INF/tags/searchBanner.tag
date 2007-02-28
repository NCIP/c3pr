<%@attribute name="searchTitle" required="true"%>
<%@attribute name="searchType" required="true"%>
<%@attribute name="submitPage" required="true"%>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="titleArea">
	<form:form id="searchForm" name="searchForm" method="post">
		<form:hidden path="searchCategory" />
		<tr>
			<!-- TITLE STARTS HERE -->
			<td width="99%" height="30" valign="middle" id="title"></td>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td class="searchType">${searchTitle}<form:select path="${searchType}">
						<form:options items="${searchType}" itemLabel="desc"
							itemValue="code" />
					</form:select></td>
				</tr>
			</table>
			<span class="notation">&nbsp;</span></td>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="search">
				<tr>
					<td align="left" class="labels">Search Criteria:</td>
					<td class="labels">&nbsp;</td>
				</tr>
				<tr>
					<td><form:input path="searchTypeText" size="25" /></td>
					<td><input name="imageField" type="image" class="button"
						onClick="submitPage('protocol');return false;"
						src="<tags:imageUrl name="b-go.gif"/>" alt="GO" align="middle" width="22"
						height="10" border="0"></td>
				</tr>
			</table>
			</td>
		</tr>
	</form:form>
</table>