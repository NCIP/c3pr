<%@ include file="../taglibs.jsp"%>
<c:if test="${cannotEnroll}">
	<c:choose>
	<c:when test="${empty studyVersion}">
		<div style="border:1px solid #f00; height:100px; padding:9px; margin-bottom:10px;">
			<img src="<tags:imageUrl name="stop_sign.png" />" alt="Stop!" style="float:left; margin-right:30px; margin-left:80px;" />
			<div style="font-size:20px; margin-bottom:5px;">Invalid</div>
			<div>
				<fmt:message key="REGISTRATION.STUDYVERION.ERROR" />
			</div>
		</div>
		&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="REGISTRATION.STUDYVERION.ERROR.CONTINUE" />
		<div align="right" style="padding-top: 10px">
			<tags:button type="button" color="red" icon="Back" value="Back" onclick="closePopup();" />
		</div>
	</c:when>
	<c:otherwise>
		<div style="border:1px solid #f00; height:100px; padding:9px; margin-bottom:10px;">
			<img src="<tags:imageUrl name="stop_sign.png" />" alt="Stop!" style="float:left; margin-right:30px; margin-left:80px;" />
			<div style="font-size:20px; margin-bottom:5px;">Invalid</div>
			<div>
				<fmt:message key="REGISTRATION.STUDYVERION.ERROR.FOUND.VALID" />
				<ul style="padding-left:230px;">
					<li><fmt:message key="study.versionNameNumber" /> : ${studyVersion.name}</li>
					<li><fmt:message key="study.version.date" /> : ${studyVersion.versionDateStr}</li>
				</ul>
			</div>
		</div>
		&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="REGISTRATION.STUDYVERION.ERROR.FOUND.VALID.CONTINUE" />
		<div align="right" style="padding-top: 10px">
			<tags:button type="button" color="red" icon="Back" value="Back" onclick="closePopup();" />
			<tags:button type="button" color="green" icon="Save &amp; Continue" value="Apply Study Version" onclick="changeStudyVersion()" cssClass="transferEpochButton" />
		</div>
	</c:otherwise>
	</c:choose>
</c:if>
