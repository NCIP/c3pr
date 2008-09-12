<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><registrationTags:htmlTitle registration="${command}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="resources/styles.css" rel="stylesheet" type="text/css">
<link href="resources/search.css" rel="stylesheet" type="text/css">
<script>
		var win;
		function openPopup(studySite, participant, parentRegistrationId){
			win = new Window(
					{title: "Registration", top:35, scrollbar: false, left:100, width:700, height:325 , url: "<c:url value='/pages/registration/testPage?decorator=noheaderDecorator&customButton=true&create_companion=true&participant='/>" + participant  +"&parentRegistrationId=" + parentRegistrationId +"&studySite=" + studySite, 
					  showEffectOptions: {duration:1.5}
					}
				) 
			win.show()
		}

		function closePopup() {
			win.close();
			reloadCompanion();
		}
		function reloadCompanion(){
			$('manageCompanion').submit();
   		}
	</script>
</head>
<body>
<form action="../registration/manageRegistration?registrationId=${command.id }" method="post" id="manageCompanion">
	<input type="hidden" name="_page0" id="_page0" value="0"/>
	<input type="hidden" name="_target2" id="_target2" value="2"/>
	<input type="hidden" name="goToTab" id="goToTab" value="true"/>
</form>
<tags:panelBox>
	<div id="CompanionRegistration">
		<table class="tablecontent" width="100%" title="Companion Registration">
			<tr>
				<th width="7%" scope="col" align="center"><b>Register</b></th>
				<th width="40%" scope="col" align="center"><b>Short Title-(Identifier)</b></th>
				<th width="9%" scope="col" align="center"><b>Mandatory</b></th>
				<th width="20%" scope="col" align="center"><b>Registartion Status</b></th>
				<th />
			</tr>
			<c:forEach items="${companions}" var="companion">
	            <tr>
					<td class="alt" align="center"><input type="checkbox" name="registerFromParentIndicator" value="false" checked="checked" /></td>
	                <td class="alt">${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})</td>
					<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>		                
					<td class="alt">${companion.registrationId == 0?"Not Started":companion.registrationStatus}</td>
	                <td class="alt">
				        <c:choose> 
							<c:when test="${companion.registrationId != 0}"> 
								<input type="button" CompanionStudy" value="Manage" onclick="javascript:document.location='<c:url value='/pages/registration/manageRegistration?registrationId=${ companion.registrationId}' />'"/> 
							</c:when>
							<c:otherwise> 
					        	<input type="button" id="registerCompanionStudy" value="Register" onclick="openPopup('${ companion.studySiteId}','${command.participant.id}','${command.id}');"/> 
							</c:otherwise> 
						</c:choose>
	                </td>
	   	        </tr>	
	        </c:forEach>
		</table>
	</div>
</tags:panelBox>
new Reg = ${newRegistration}
<br> actionButtonLabel="${actionLabel}" <br>
requiresMultiSite="${requiresMultiSite}" 
<c:if test="${registerableWithCompanions &&(actionRequired || hasCompanions)}">
	<tags:panelBox>
		<registrationTags:register registration="${command}" newReg="${newRegistration}" actionButtonLabel="${actionLabel}" requiresMultiSite="${requiresMultiSite}" />
	</tags:panelBox>
</c:if>
</body>
</html>
