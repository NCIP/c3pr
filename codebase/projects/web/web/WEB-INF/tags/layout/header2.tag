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
        <td align="left" width="150px" nowrap=""><div id="login-action"><a href="http://gforge.nci.nih.gov/frs/download.php/3493/wfu_signoff_on_end_user_guide.doc">Help</a>&nbsp;<a>|</a>&nbsp;<a <%-- href="<c:url value='/public/skin' />"--%> id="changeSkin" style="cursor:pointer;">Change skin</a>&nbsp;<a>|</a>&nbsp;<a href="<c:url value="/j_acegi_logout"/>">Log out</a>&nbsp;</div></td>
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

    <style>
    </style>

    <div id="nav" class="background-R">
        C3PR&nbsp;&nbsp;&raquo;&nbsp;&nbsp;</a><a href="<c:url value="/pages/dashboard"/>">Home Page</a>
        <c:if test="${currentSection != null}">
            &nbsp;&raquo;&nbsp;<a href="<c:url value="${currentSection.mainUrl}" />"><c:out value="${currentSection.displayName}"/></a>
            &nbsp;&raquo;&nbsp;<a href="<c:url value="${currentTask.url}" />"><c:out value="${currentTask.displayName}"/>
        </c:if>
    </div>
</div>
<!-- end header -->
<script>

    Event.observe(window, "load", function() {

    $('changeSkin').observe('click', function(event) {
            Lightview.show({
              href: "/c3pr/public/skin",
              rel: 'ajax',
              title: ':: C3PR Project::',
              caption: "Pick up the skin please...",
              options: {
              autosize: false,
              width: 850,
              height:600,
              ajax: {
                    onComplete: function() {
                        // alert('QQQ');
                        $('submitAJAXForm').observe('click', postSubmitSkinForm);
                    }
                }
              }
            });
    });
})
////////////////////////////////////////////////////////////////////////////////////////////////////////////

function postSubmitSkinForm() {
    var action = document.forms[0].action;
    var value = getSelectedValue();

    var url = action + "?conf['skinPath'].value=" + value;
//    alert(url);
//    return;
    
    new Ajax.Request(url, {
      method: 'post',
      onSuccess: function(transport) {
          Lightview.hide();
          setTimeout('reloadPage()', 500);
      }
    });
}

function reloadPage() {
    location.reload(true);
}
    
function getSelectedValue() {
    if ($('rdBLUE').checked) return ('blue');
    if ($('rdGREEN').checked) return ('green');
    if ($('rdORANGE').checked) return ('orange');
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
</script>

<%--<c:out value="skinName" />--%>
