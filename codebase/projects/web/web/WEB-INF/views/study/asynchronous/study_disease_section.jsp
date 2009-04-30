<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<table class="tablecontent" width="90%">
	<tr>
		<th width="32%">Disease Category</th>
		<th width="32%">Disease Sub Category</th>
		<th width="32%">Disease</th>
		<th width="4%"></th>
	</tr>
	<c:forEach items="${command.study.studyDiseases}" var="studyDisease" varStatus="status">
		<tr>
			<td>${studyDisease.diseaseTerm.category.parentCategory.name}</td>
			<td>${studyDisease.diseaseTerm.category.name}</td>
			<td>${studyDisease.diseaseTerm.ctepTerm}</td>
			<td valign="top" align="left">
                   <a href="javascript:deleteStudyDiseases(${studyDisease.diseaseTerm.id});">
                   	<img src="<tags:imageUrl name="checkno.gif"/>" border="0">
                   </a>
               </td>
		</tr>
	</c:forEach>
</table>
<br>
<c:if test="${fn:length(command.study.studyDiseases) == 0}">
	Select Disease term from autocompleter and click 'Add Disease' to add disease to study or to  add multiple disease click 'Add Multiple Diseases'.
</c:if>