<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<fmt:message key="study.disease.noDisease" />
</c:if>
