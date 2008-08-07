<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="header">

   <table width=100% cellspacing="0" cellpadding="0" border="0">
    <tr height="5px">
        <td colspan="3" id="headLane1"></td>
    </tr>
    <tr height="48px" class="headLane2">
        <td align="left"><div id="logo"></div></td>
        <td align="left" width="100%"><div id="headerTitle1"><div id="headerTitle2"><c:out value="${instName}" /></div></div></td>
        <td align="left" width="250px">
            <table border="0" cellpadding="1" cellspacing="1" width="250px">
                <tr>
                    <td align="right" valign="bottom">
                        <c:if test="${userObject != null}">
                            <div id="welcome-user">Welcome<b> <c:out value="${userObject.firstName} ${userObject.lastName}" /></b><!-- [Role name]--></div>
                       </c:if>
                    <td rowspan="2" align="right" width="1px"><img src="${siteName}" height="40px" border="1">
                </tr>
                <tr>
                    <td align="right" valign="top">
                        <div id="login-action">
                            <csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="loginAuthorizationCheck"><a href="http://gforge.nci.nih.gov/frs/download.php/3493/wfu_signoff_on_end_user_guide.doc">Help</a>&nbsp;<csmauthz:accesscontrol domainObject="/pages/skin" authorizationCheckName="urlAuthorizationCheck"><a>|</a>&nbsp;<a id="changeSkin" style="cursor:pointer;">Change skin</a></csmauthz:accesscontrol>&nbsp;<a>|</a>&nbsp;<a href="<c:url value="/j_acegi_logout"/>">Log out</a></csmauthz:accesscontrol>
                            <csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="logoutAuthorizationCheck"><a href="<c:url value="/public/login"/>">Log in</a></csmauthz:accesscontrol>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
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

<%--
    <style>
        <c:set var="i" value="-2" />
        <c:forEach items="${sections}" var="section">
                        <c:set var="i" value="${i + 1}" />
                        <c:out value="#e0_${i}o {background-color: green; ${section.displayName};}" />
                        <c:forEach items="${section.tasks}" var="task">
                                <c:set var="i" value="${i + 1}" />
                                <c:if test="${fn:length(task.subTasks) > 0}">
                                    <c:url var="pic" value="/images/menu_arrow.gif" />
                                    <c:out value="#e0_${i}i {background-color: red;   background-image: url(${pic}); ${task.displayName}; }" />
                                </c:if>
                                <c:forEach items="${task.subTasks}" var="subtask">
                                    <c:set var="i" value="${i + 1}" />
                                    <c:out value="#e0_${i}i {background-color: yellow; ${subtask.displayName};}" />
                                </c:forEach>
                        </c:forEach>
        </c:forEach>
    </style>
--%>


    <script language="JavaScript">
            var MENU_ITEMS = [
                <c:forEach items="${sections}" var="section">
                    <csmauthz:accesscontrol authorizationCheckName="sectionAuthorizationCheck" domainObject="${section}">
                        ['<c:out value="${section.displayName}" />', '<c:url value="${section.mainUrl}"/>', null,
                                <c:forEach items="${section.tasks}" var="task">
                                    <csmauthz:accesscontrol domainObject="${task}" authorizationCheckName="taskAuthorizationCheck">


                            <c:set var="name" value="${task.displayName}" />
                            <c:if test="${fn:length(task.subTasks) > 0}">
                                <c:set var="name" value="${task.displayName} � " />
                            </c:if>

                                       ['<c:out value="${name}" />', '<c:url value="${task.url}"/>', null,
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
       CCTS.appShortName = 'c3pr';
    </script>


   <csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="loginAuthorizationCheck">

    <div id="nav" class="background-R">
        C3PR&nbsp;&nbsp;&raquo;&nbsp;&nbsp;<a href="<c:url value="/pages/dashboard"/>">Home Page</a>
        <c:if test="${currentSection != null}">
            &nbsp;&raquo;&nbsp;<a href="<c:url value="${currentSection.mainUrl}" />"><c:out value="${currentSection.displayName}"/></a>
            <c:if test="${currentTask != null}">
                &nbsp;&raquo;&nbsp;<a href="<c:url value="${currentTask.url}" />"><c:out value="${currentTask.displayName}"/></a>
            </c:if>
        </c:if>
        <c:if test="${tab != null}">
            &nbsp;&raquo;&nbsp;<a href="#"><c:out value="${tab.longTitle}"/></a>
        </c:if>
   </div>
   </csmauthz:accesscontrol>

</div>
<!-- end header -->

<csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="loginAuthorizationCheck">

<script>

    if ($('changeSkin')) {
            Event.observe(window, "load", function() {
                    $('changeSkin').observe('click', function(event) {
                            Lightview.show({
                              href: "<c:url value='/public/skin' />",
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
    }

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

</csmauthz:accesscontrol>    

<style type="text/css">
    div.ssmItems a:link		{color:black; text-decoration:none; font-size:11px;}
    div.ssmItems a:hover	{color:black; text-decoration:none; font-size:11px; padding-left: 3pt;}
    div.ssmItems a:active	{color:black; text-decoration:none; font-size:11px;}
    div.ssmItems a:visited	{color:black; text-decoration:none; font-size:11px;}
</style>

<%--
<script src="<c:url value="/js/sidebar/ssm.js2" />"></script>
<script src="<c:url value="/js/sidebar/ssmItems.js2" />"></script>
--%>
