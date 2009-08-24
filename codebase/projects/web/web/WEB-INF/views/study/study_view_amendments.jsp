<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<jwr:script src="/js/tabbedflow.js" />
<style>
#main {
	top: 33px;
}
</style>
<script>
function viewStudyAmendment(){
	Dialog.confirm("Are you sure you want to see this amendment version of the study?",
	        {width:300, height:85, okLabel: "Ok", ok:function(win) {

			if(companionIndicator == 'true' ){
				document.location='../study/amendCompanionStudy?studyId='+id
			}else{
				document.location='../study/amendStudy?studyId='+id
			}

				<tags:tabMethod method="viewAmendmentVersion" divElement="'dummyDiv'" formName="'tabMethodForm'"/>
    		}
	  });
}

function applyAmendment(index){
	Dialog.confirm("Are you sure you want to apply current amendment to the study?",
	        {width:300, height:85, okLabel: "Ok", ok:function(win) {
		  		<tags:tabMethod method="applyAmendment" divElement="'study-amendment-'+index" formName="'tabMethodForm'" javaScriptParam="'statusIndex='+index" viewName="/study/asynchronous/study_amendment_section"/>
    		}
	  });
}

</script>
</head>
<body>
<div id="dummyDiv"></div>
<chrome:box title="Amendments">
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
	<table id="studyAmendmets" class="tablecontent">
		<tr>
			<th width="25%">
				<fmt:message key="study.versionNameNumber" />
				<tags:hoverHint keyProp="study.version.name" />
			</th>
			<th width="17%">
				<fmt:message key="study.version.date" />
				<tags:hoverHint keyProp="study.version.date" />
			</th>
			<th width="13%">
				<fmt:message key="study.version.required" />
				<tags:hoverHint keyProp="study.version.required" />
			</th>
			<th width="17%">
				<fmt:message key="study.version.status" />
				<tags:hoverHint keyProp="study.version.status" />
			</th>
			<th width="25%">
			</th>
		</tr>
		<c:forEach items="${command.study.studyAmendments}" var="amendment" varStatus="status">
			<div id="amendment-${status.index}"></div>
			<div id="study-amendment-${status.index}">
			<tr>
				<td>${amendment.name}</td>
				<td>${amendment.versionDateStr}</td>
				<td>${amendment.mandatoryIndicator ? 'Yes' : 'No'}</td>
				<td>${amendment.versionStatus.code}</td>
				<td>
					<c:choose>
						<c:when test="${amendment.versionStatus == 'IN'}">
							<tags:button color="blue" icon="amend" onclick="${resumeAmendmentUrl}" size="small" value="Resume Amendment"></tags:button>
							<c:if test="${applyAmendment}">
								<tags:button color="blue" icon="apply" onclick="applyAmendment('${status.index}');" size="small" value="Apply Amendment"></tags:button>
							</c:if>
						</c:when>
						<c:otherwise>
							<tags:button color="blue" icon="search" onclick="viewStudyAmendment();" size="small" value="View"></tags:button>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</div>
		</c:forEach>
	</table>
	</c:when>
	<c:otherwise>
		<fmt:message key="study.version.noAmendment"></fmt:message>
	</c:otherwise>
</c:choose>
</chrome:box>
</body>
</html>
