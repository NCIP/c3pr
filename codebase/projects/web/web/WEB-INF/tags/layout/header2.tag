<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="header">

   <table width=100% cellspacing="0" cellpadding="0" border="0">
    <tr height="5px">
        <td colspan="3" id="headLane1"></td>
    </tr>
    <tr height="48px" class="headLane2">
        <td align="left"><div id="logo"></div></td>
        <td align="left" width="100%"><div id="headerTitle1"><div id="headerTitle2">Duke University</div></div></td>
        <td align="left" width="150px" nowrap=""><div id="login-action"><a href="http://gforge.nci.nih.gov/frs/download.php/3493/wfu_signoff_on_end_user_guide.doc">Help</a>&nbsp;<a>|</a>&nbsp;<a href="<c:url value="/j_acegi_logout"/>">Log out</a><a>&nbsp;|</a>&nbsp;<a href="<c:url value='/public/skin' />" id="changeSkin">Change skin</a></div></td>
    </tr>
    </table>
    <div id="menuLaneCenter"><div id="menuLaneRight"><div id="menuLaneLeft"></div></div></div>



<%--
        <ul id="sections" class="tabs">
            <c:forEach items="${sections}" var="section">
                <csmauthz:accesscontrol authorizationCheckName="sectionAuthorizationCheck" domainObject="${section}">
                    <c:set var="selected" value="${section == currentSection ? 'selected' : ''}" />
                    <li class="${selected}"><div><a href="<c:url value="${section.mainUrl}"/>" class="${selected}">${section.displayName}</a></div></li>
                    <li class="divider" />
                </csmauthz:accesscontrol>
            </c:forEach>
        </ul>
--%>


    <script language="JavaScript">
            var MENU_ITEMS = [
                <c:forEach items="${sections}" var="section">
                    <csmauthz:accesscontrol authorizationCheckName="sectionAuthorizationCheck" domainObject="${section}">
                        ['<c:out value="${section.displayName}" />', '<c:url value="${section.mainUrl}"/>', null,
                                <c:forEach items="${section.tasks}" var="task">
                                    <csmauthz:accesscontrol domainObject="${task}" authorizationCheckName="taskAuthorizationCheck">
                                       ['<c:out value="${task.displayName}" />', '<c:url value="${task.url}"/>', null,
                                           <c:forEach items="${task.subTasks}" var="subtask">
                                                ['<c:out value="${subtask.displayName}" />', '<c:url value="${subtask.url}"/>'],
                                           </c:forEach>
                                       ],
                                    </csmauthz:accesscontrol>
                                </c:forEach>
                        ],
                    </csmauthz:accesscontrol>
                </c:forEach>
            ];
    </script>

    <script language="JavaScript">
       new menu (MENU_ITEMS, MENU_TPL);
    </script>

    <div class="background-R">

        <%--
        <div id="taskbar">
            <c:if test="${not empty currentSection.tasks}">
               	<span width="80%" style="float:left;">
                Tasks:
                <c:forEach items="${currentSection.tasks}" var="task">
                    <csmauthz:accesscontrol domainObject="${task}" authorizationCheckName="taskAuthorizationCheck">
                        <c:choose>
                            <c:when test="${task == currentTask}">
                                <a href="<c:url value="${task.url}"/>" id="current">${task.displayName}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="${task.url}"/>">${task.displayName}</a>
                            </c:otherwise>
                        </c:choose>
                    </csmauthz:accesscontrol>
                </c:forEach>
                </span>
            </c:if>
        </div>
        --%>
    </div>
</div>
<!-- end header -->
<script>

    Event.observe(window, "load", function() {
    $('changeSkin').observe('click', function(event) {
//          alert('Not implemented yet !!!');
    });
})
////////////////////////////////////////////////////////////////////////////////////////////////////////////
</script>

<%--<c:out value="skinName" />--%>
