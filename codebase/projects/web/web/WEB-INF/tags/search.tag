<%@tag%><%@attribute name="action" required="true"%>
<%@attribute name="category"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<script>
function submit(s){
	document.getElementById("searchCategory").value=s;	
	document.getElementById("searchForm").submit();
}
</script>
<!-- STUDY SEARCH START HERE -->
<form:form id="searchForm" name="searchForm" method="post" action="${action}" >		
<table width="100%" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td>		
		<tabs:division id="study-search">
		<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="5" >
			<tr>
				<td align="right" width="90%"/>
				Search by <select name="searchType">
					<c:forEach items="${searchTypeRefData}" var="opt">
						<option value="${opt.code}">${opt.desc}</option>
					</c:forEach>
				</td>
				<td><input type=text name="searchText" size="25" /></td>			
					<td><input name="imageField" type="image" class="button"
					onClick="submit(${category});return false;" src="<tags:imageUrl name="b-go.gif"/>"
					alt="GO" align="middle" width="22" height="10" border="0">
				</td>
			</tr>			
		</table>
		</tabs:division>		
	</td>
	</tr>
</table>
</form:form>
<!-- STUDY SEARCH END HERE -->