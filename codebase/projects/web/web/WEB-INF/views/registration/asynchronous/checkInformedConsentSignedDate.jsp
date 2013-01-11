<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="../taglibs.jsp"%>
<c:if test="${cannotEnroll}">
	<c:choose>
	<c:when test="${empty studyVersion}">
		<div style="border:1px solid #f00; height:100px; padding:9px; margin-bottom:10px;">
			<img src="<tags:imageUrl name="stop_sign.png" />" alt="Stop!" style="float:left; margin-right:30px; margin-left:80px;" />
			<div style="font-size:20px; margin-bottom:5px;">Invalid</div>
			<div>
				<fmt:message key="REGISTRATION.STUDYVERSION.ERROR" />
			</div>
		</div>
		&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="REGISTRATION.STUDYVERSION.ERROR.CONTINUE" />
		<div align="right" style="padding-top: 10px">
			<tags:button type="button" color="red" icon="Back" value="Back" onclick="closePopup();" />
		</div>
	</c:when>
	<c:otherwise>
		<div style="padding-top: 20px">
			<img src="<tags:imageUrl name="error.png" />" alt="Alert!" style="float:left; margin-right:30px; margin-left:30px;" />
			<fmt:message key="REGISTRATION.STUDYVERSION.ERROR.FOUND.VALID" />
			<ul style="padding-left:150px;">
				<li><fmt:message key="study.versionNameNumber" /> : ${studyVersion.name}</li>
				<li><fmt:message key="study.version.date" /> : ${studyVersion.versionDateStr}</li>
			</ul>
		</div>
		<div align="right" style="padding-top: 10px">
			<tags:button type="button" color="red" icon="Back" value="Cancel" onclick="closePopup();" />
			<tags:button type="button" color="green" icon="Save &amp; Continue" value="Continue" onclick="changeStudyVersion()" />
		</div>
	</c:otherwise>
	</c:choose>
</c:if>
