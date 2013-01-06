<%@ include file="../taglibs.jsp"%>
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
					</c:when>
					<c:otherwise>
						<tags:button color="blue" icon="search" onclick="javascript:confirmationPopup('viewAmendmentMessage');" size="small" value="View"></tags:button>
					</c:otherwise>
				</c:choose>
				<tags:button color="blue" icon="page" onclick="viewAmendmentSummary('${amendment.id}');" size="small" value="Summary"></tags:button>
			</td>
		</tr>
		<div id="versionSummary-${amendment.id}" style="display:none;">
			<studyTags:study_amendment_summary studyVersion="${amendment}"></studyTags:study_amendment_summary>
		</div>
	</c:forEach>
</table>
</div>