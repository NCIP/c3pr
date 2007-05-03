<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="tabNumber"%>
<%@attribute name="isLast"%>
<%@attribute name="isUpdate"%>

<div class="tabcontrols autoclear">

	<c:if test="${isUpdate =='true'}"><a id="flow-update" class="tab${tab.number - 1}">Update</a></c:if>
    <c:if test="${isUpdate !='true'}">
	    <c:if test="${tab.number > 0}"><a id="flow-prev" class="tab${tab.number - 1}">&laquo; Previous</a></c:if>
	    <c:if test="${isLast}"><a id="flow-next">Continue &raquo;</c:if></a>
	    <c:if test="${!isLast}"><a id="flow-next">Save &raquo;</c:if></a>
     </c:if>
</div>
