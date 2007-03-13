<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<!-- TOP NAVIGATION STARTS HERE -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="topNav">
        <tr valign="middle">
            <td class="left">
                <c:forEach items="${sections}" var="section">
                    <c:choose>
                        <c:when test="${section == currentSection}">
                           <span class="current">
                            <a href="<c:url value="${section.mainUrl}"/>">
                            	${section.displayName}</a></span><img src="<tags:imageUrl name="topNavR.gif"/>" 
                            	width="2" height="20" align="absmiddle" class="currentR">
                            </span>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="${section.mainUrl}"/>">${section.displayName}</a>
                            <img src="<tags:imageUrl name="topDivider.gif"/>" width="2" height="20" align="absmiddle" class="divider">
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>

            <td class="right"><a href="<c:url value="/login.jsp"/>">Log out</a></td>
        </tr>
    </table>
    <!-- TOP NAVIGATION ENDS HERE -->
    <!-- SUB NAV STARTS HERE -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="subNav">
        <tr>
            <td width="99%" valign="middle">
                &nbsp;&nbsp;&nbsp;&nbsp;<img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="absmiddle" class="spacer">
                <c:forEach items="${currentSection.tasks}" var="task">
                    <c:choose>
                        <c:when test="${task == currentTask}">
                            <img src="<tags:imageUrl name="arrowRight.gif"/>" width="3" height="5" align="absmiddle"><a href="<c:url value="${task.url}"/>">${task.displayName}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="${task.url}"/>">${task.displayName}</a>
                        </c:otherwise>
                    </c:choose>                                                    
                    <img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="absmiddle" class="spacer">               
                </c:forEach>
            </td>
         </tr>
    </table>
<!-- SUB NAV ENDS HERE -->