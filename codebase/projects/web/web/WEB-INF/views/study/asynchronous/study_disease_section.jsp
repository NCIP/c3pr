<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                   <a href="javascript:deleteStudyDisease(${studyDisease.diseaseTerm.id});">
                   	<img src="<tags:imageUrl name="checkno.gif"/>" border="0">
                   </a>
               </td>
		</tr>
	</c:forEach>
</table>