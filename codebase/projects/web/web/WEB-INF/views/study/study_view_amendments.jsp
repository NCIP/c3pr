<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<jwr:script src="/js/tabbedflow.js" />
<script>
var confirmWin ;

function confirmationPopup(message){
	confirmWin = new Window({className :"mac_os_x", title: "Confirm", 
		hideEffect:Element.hide, 
		zIndex:100, width:400, height:120 , minimizable:false, maximizable:false,
		showEffect:Element.show 
		}); 
	confirmWin.setContent($(message)) ;
	confirmWin.showCenter(true);
}

function apply(){
	<tags:tabMethod method="viewAmendment" divElement="'studyAmendmentDiv'" formName="'tabMethodForm'"  viewName="/study/asynchronous/study_amendment_section" onComplete="closePopup"/>
}

function viewAmendment(versionId){   
	confirmWin = new Window(
			{title: "Amendment Detail", top:35, left:35, width:850, height:400, zIndex:100,
			url: "<c:url value='/pages/study/viewAmendment?decorator=noheaderDecorator&studyVersionId='/>"+versionId, showEffectOptions: {duration:1.5}}
			) 
	confirmWin.showCenter(true);
}

function closePopup(){
	confirmWin.close();
}

function viewAmendmentSummary(studyVersionId){
	var arr= $$("#versionSummary-"+studyVersionId);
	win = new Window({className :"mac_os_x", title: "Amendment Summary",
							hideEffect:Element.hide,
							zIndex:100, width:450, height:250 , minimizable:false, maximizable:false,
							showEffect:Element.show
							})
	win.setContent(arr[0]) ;
	win.showCenter(true);
}

</script>
</head>
<body>
<input type="hidden" name="versionId" id="versionId"/>
<chrome:box title="Amendments">
<div id="applyAmendmentMessage" style="padding: 15px;display:none;">
	<img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="STUDY.APPLY_AMENDMENT.WARNING"/>
	<div id="actionButtons">
		<div class="flow-buttons">
	   	<span class="next">
	   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
			<tags:button type="button" color="green" icon="save" onclick="javascript:apply();" value="Apply amendment" />
		</span>
		</div>
	</div>
</div>
<c:choose>
	<c:when test="${fn:length(command.study.studyAmendments) > 0}">
	<c:choose>
    	<c:when test="${amendment.study.companionIndicator=='true'}">
        	<c:set  var="resumeAmendmentUrl" value="javascript:document.location='../study/amendCompanionStudy?studyId=${command.study.id}'" />
        </c:when>
        <c:otherwise>
        	<c:set  var="resumeAmendmentUrl" value="javascript:document.location='../study/amendStudy?studyId=${command.study.id}'" />
        </c:otherwise>
    </c:choose>
	<div id="studyAmendmentDiv">
	<table id="studyAmendmets" class="tablecontent">
		<tr>
			<th width="20%">
				<fmt:message key="study.versionNameNumber" />
				<tags:hoverHint keyProp="study.version.name" />
			</th>
			<th width="17%">
				<fmt:message key="study.version.date" />
				<tags:hoverHint keyProp="study.version.date" />
			</th>
			<th width="10%">
				<fmt:message key="c3pr.common.status" />
				<tags:hoverHint keyProp="study.version.status" />
			</th>
			<th width="35%">
			</th>
		</tr>
		<c:forEach items="${command.study.studyAmendments}" var="amendment" varStatus="status">
			<tr>
				<td>${amendment.name}</td>
				<td>${amendment.versionDateStr}</td>
				<td>${amendment.versionStatus.code}</td>
				<td>
					<c:choose>
						<c:when test="${amendment.versionStatus == 'IN'}">
							<tags:button color="blue" icon="amend" onclick="${resumeAmendmentUrl}" size="small" value="Resume Amendment"></tags:button>
							<c:if test="${applyAmendment}">
								<tags:button color="blue" icon="apply" onclick="javascript:confirmationPopup('applyAmendmentMessage');;" size="small" value="Apply Amendment"></tags:button>
							</c:if>
							<tags:button color="blue" icon="page" onclick="viewAmendmentSummary('${amendment.id}');" size="small" value="Summary"></tags:button>
						</c:when>
						<c:otherwise>
							<tags:button color="blue" icon="search" onclick="viewAmendment('${amendment.id}');" size="small" value="View"></tags:button>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<div id="versionSummary-${amendment.id}" style="display:none;">
				<studyTags:study_amendment_summary studyVersion="${amendment}"></studyTags:study_amendment_summary>
			</div>
		</c:forEach>
	</table>
	</div>
	</c:when>
	<c:otherwise>
		<fmt:message key="study.version.noAmendment"></fmt:message>
	</c:otherwise>
</c:choose>
</chrome:box>
</body>
</html>
