<!--BEGIN header.tag-->
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c3pr" uri="http://edu.duke.cabig.c3pr.web/c3pr" %>
<%@attribute name="ignoreTopRightQuickLinks"%>
<div id="header">
	<a id="skipnav" href="#skipnav">Skip Navigation</a>
	<a id="logo" href="<c:url value='/pages/dashboard' />">C3PR Dashboard</a>
    <div class="background-R">
    	<c:if test="${empty ignoreTopRightQuickLinks || !ignoreTopRightQuickLinks}">
            <c:if test="${userObject != null}">
					<div id="welcome-user">
						<div style="float:left;width:60px">Welcome |</div>
						<div style="float:right;width:220px"><b>
							<div style="padding:0px 2px 0px 2px">
								<c:set var="userNameStr" value="${userObject.firstName} ${userObject.lastName}"/>
								<c:choose>
								<c:when test="${fn:length(userNameStr)>34 }">
									${fn:substring(userNameStr,0,32) }..
								</c:when>
								<c:otherwise>
									${userNameStr }
								</c:otherwise>
								</c:choose>
							</div>
							<div style="padding:0px 2px 0px 2px">
								<c:choose>
									<c:when test="${isSuperUser}">
										Super User
									</c:when>
									<c:when test="${fn:length(userRoles) == 1}">
										${userRoles[0].displayName }
									</c:when>
									<c:otherwise>
										${userRoles[0].displayName }
										<div style="font-size:6pt;"><a style="color:#E4C48F;" href="javascript:Effect.Combo('rolesList')">${fn:length(userRoles)-1 } more roles..</a></div>
										<div id="rolesList" style="display:none;color:white; background-color:rgb(107,107,107);position:absolute;z-index:30;">
											<div style="padding:5px 2px 1px">
											<c:forEach items="${userRoles}" var="role" begin="1">
												${role.displayName }<br>
											</c:forEach>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</b>
						</div>
					</div>
					</c:if>
                    <c:if test="${siteName ne ''}">
                      <div id="instName">
                        <img src="${siteName}" height="35px" />
                        <c:out value="${instName}" />
                      </div>
                    </c:if>
        <div id="login-action">
                            <csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="loginAuthorizationCheck">
						<c:url value="https://cabig-kc.nci.nih.gov/CTMS/KC/index.php/" scope="request" var="_c3prHelpURL" />
							<c:choose>
								<c:when test ="${currentSubTask != null}">
										<c:set var="roboHelpKey">ROBOHELP_${currentSubTask.linkName}</c:set>
								</c:when>
								<c:otherwise>
									<c:set var="roboHelpKey">ROBOHELP_${currentTask.linkName}</c:set>
								</c:otherwise>
							</c:choose>
							<spring:message var="roboHelpLink" code="${roboHelpKey}" text="NO_${roboHelpKey}"/>
          					<a href="${_c3prHelpURL}${roboHelpLink}" target="_blank" id="help">Help</a>  
		  
		  &nbsp;|</a>&nbsp;&nbsp;<a href="<c:url value="/j_acegi_logout"/>">Log out</a></csmauthz:accesscontrol>
                            <csmauthz:accesscontrol domainObject="NOT_NULL_OBJECT" authorizationCheckName="logoutAuthorizationCheck"><a href="<c:url value="/public/login"/>">Log in</a></csmauthz:accesscontrol>
                            <br />
                            <div><a href="<c:url value='/pages/dashboard' />">Dashboard</a>
                            &nbsp;</div>
                        </div>

			</c:if>
        <ul id="sections" class="tabs">
            <c:forEach items="${sections}" var="section">
                    <c:set var="foundDefaultTask" value="false" />
					<c:forEach items="${section.tasks}" var="task">
		                <csmauthz:accesscontrol domainObject="${task}" authorizationCheckName="taskAuthorizationCheck">
							<c:if test="${!foundDefaultTask}">
								<c:set var="defaultTask" value="${task}" />
								<c:set var="foundDefaultTask" value="true" />				                
		                	</c:if>
		                </csmauthz:accesscontrol>
		            </c:forEach>
			        <c:if test="${!empty defaultTask}">
			        <li class="${section == currentSection ? 'selected' : ''}">
                    <div>
                        <a id="firstlevelnav_${section.displayId}" href="<c:url value="${defaultTask.url}"/>">${section.displayName}</a>
                    </div></li>
                    </c:if>
                    <c:set var="defaultTask" value="" />
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
