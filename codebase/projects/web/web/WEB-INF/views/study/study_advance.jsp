<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp" %>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}"/></title>
</head>
<body>
<tags:panelBox>
<form:form>
    <div class="leftpanel">
        <div class="row">
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.hostedMode"/>
            </div>
            <div class="value"><form:checkbox id="hostedMode" path="study.hostedMode"/>
                <tags:hoverHint keyProp="study.hostedMode"/>
            </div>
        </div>
    </div>
    <script>
        <c:if test="${!command.study.multiInstitutionIndicator}">$('hostedMode').disabled = true;
        </c:if>
    </script>
    <div class="content buttons autoclear">
        <div class="flow-buttons">
            <span class="next">
				<input type="submit" value="Save"/>
 			</span>
        </div>
    </div>
</form:form>
</tags:panelBox>

</body>
</html>
