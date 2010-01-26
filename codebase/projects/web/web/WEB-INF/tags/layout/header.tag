<!--BEGIN header.tag-->
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="header">
	<a id="skipnav" href="#skipnav">Skip Navigation</a>
	<a id="logo" href="<c:url value='/pages/dashboard' />">C3PR Dashboard</a>
    <div class="background-R">
            <c:if test="${userObject != null}">
					<div id="welcome-user">Welcome<b> <c:out value="${userObject.firstName} ${userObject.lastName}" /> | ${userRole}</b></div>
					</c:if>
                    <c:if test="${siteName ne ''}">
                      <div id="instName">
                        <img src="${siteName}" height="35px" />
                        <c:out value="${instName}" />
                      </div>
                    </c:if>
        <div id="login-action">
                            <csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="loginAuthorizationCheck">
							<c:url value="https://cabig-kc.nci.nih.gov/CTMS/KC/index.php/C3PR_End_User_Guide" scope="request" var="_c3prHelpURL" />
							<c:choose>
								<c:when test ="${currentSubTask != null}">
										<c:set var="roboHelpKey">ROBOHELP_${currentSubTask.linkName}</c:set>
								</c:when>
								<c:otherwise>
									<c:set var="roboHelpKey">ROBOHELP_${currentTask.linkName}</c:set>
								</c:otherwise>
							</c:choose>
							<spring:message var="roboHelpLink" code="${roboHelpKey}" text="NO_${roboHelpKey}"/>
          					<a href="${_c3prHelpURL}#${roboHelpLink}" target="_blank" id="help">Help</a>  
		  
		  &nbsp;|</a>&nbsp;&nbsp;<a href="<c:url value="/j_acegi_logout"/>">Log out</a></csmauthz:accesscontrol>
                            <csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="logoutAuthorizationCheck"><a href="<c:url value="/public/login"/>">Log in</a></csmauthz:accesscontrol>
                            <br />
                            <div><a href="<c:url value='/pages/dashboard' />">Dashboard</a>
                            &nbsp;<csmauthz:accesscontrol domainObject="/pages/skin" authorizationCheckName="urlAuthorizationCheck"><a>|</a>&nbsp;<a id="changeSkin" style="cursor:pointer;">Change skin</a></csmauthz:accesscontrol></div>
                        </div>


        <ul id="sections" class="tabs">
            <c:forEach items="${sections}" var="section">
                <csmauthz:accesscontrol authorizationCheckName="sectionAuthorizationCheck"
                                        domainObject="${section}">
                    <li class="${section == currentSection ? 'selected' : ''}"><div>
                        <a id="firstlevelnav_${section.mainController}" href="<c:url value="${section.mainUrl}"/>">${section.displayName}</a>
                    </div></li>
                </csmauthz:accesscontrol>
            </c:forEach>
        </ul>
		<br style="clear:both;"/>
        <div id="taskbar">
            <c:if test="${not empty currentSection.tasks}">
               	<span width="80%" style="float:left;">
                <c:forEach items="${currentSection.tasks}" var="task">
                    <csmauthz:accesscontrol domainObject="${task}" authorizationCheckName="taskAuthorizationCheck">
                      <c:set var="lengthOfTask" value="${fn:length(task.displayName)}" />
                        <c:choose>
                            <c:when test="${task == currentTask}">
                                <a class="${lengthOfTask gt 21 ? 'gt18' : ''}" href="<c:url value="${task.url}"/>" id="current"><img class="${lengthOfTask gt 21 ? 'imagegt18' : ''}" src="/c3pr/images/icons/${task.linkName}_icon.png" alt=""/>${task.displayName}</a>
                            </c:when>
                            <c:otherwise>
                                <a class="${lengthOfTask gt 21 ? 'gt18' : ''}" href="<c:url value="${task.url}"/>"><img class="${lengthOfTask gt 21 ? 'imagegt18' : ''}" src="/c3pr/images/icons/${task.linkName}_icon.png" alt=""/>${task.displayName}</a>
                            </c:otherwise>
                        </c:choose>
			
                    </csmauthz:accesscontrol>
                </c:forEach>
                </span>
          </div>
          <c:if test="${not empty currentTask.displayableSubTasks && fn:length(currentTask.displayableSubTasks)>0}">
             <div id="subTasks">
				  <c:forEach items="${currentTask.subTasks}" var="subtask">
                    <csmauthz:accesscontrol domainObject="${subtask}" authorizationCheckName="taskAuthorizationCheck">
	                    <c:set var="i" value="${i + 1}" />
						<c:choose>
	                            <c:when test="${subtask == currentSubTask}">
	                                <a href="<c:url value="${subtask.url}"/>" id="current">${subtask.displayName}</a>
	                            </c:when>
	                            <c:otherwise>
	                                <a href="<c:url value="${subtask.url}"/>">${subtask.displayName}</a>
	                            </c:otherwise>
	                    </c:choose>
                    </csmauthz:accesscontrol>
                  </c:forEach>
              </div>
              <div id="linebelowsubtasks">&nbsp;</div>
            </c:if>
          </c:if>
    </div>
</div>
<csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="loginAuthorizationCheck">
<a name="skipnav"></a>
<script>

    if ($('changeSkin')) {
            Event.observe(window, "load", function() {
                    $('changeSkin').observe('click', function(event) {
                            Lightview.show({
                              href: "<c:url value='/public/skin' />",
                              rel: 'ajax',
                              title: 'Select the Skin',
                              caption: "Cancer Central Clinical Participant Registry",
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
    if ($('rdMOCHA').checked) return ('mocha');
    if ($('rdGREEN').checked) return ('green');
    if ($('rdORANGE').checked) return ('orange');
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
</script>

</csmauthz:accesscontrol>
<!-- END header.tag -->
