<%@attribute name="title"%>
<%@attribute name="helpUrl"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:if test="${empty helpUrl}">
    <c:set var="helpUrl" value="http://gforge.nci.nih.gov/frs/download.php/3493/wfu_signoff_on_end_user_guide.doc" />
</c:if>

<c:if test="${not empty helpUrl}"><a href="${helpUrl}" title="Help..."><img src="<tags:imageUrl name="book.gif" />" border="0" /></a></c:if>
