<%@ include file="taglibs.jsp" %>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}"/></title>
    <script type="text/javascript">
        function saveStudy() {
            document.getElementById("_activate").value = "false";
            document.getElementById("_action").value = "create";
            document.getElementById("viewDetails").submit();
        }
    </script>
</head>
<body>
<tags:basicFormPanelBox flow="${flow}" tab="${tab}">
    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
                Hosted Mode:
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
				<input type="button" value="Save" id="saveAdvanceConfig"
                       onclick="saveStudy();"/>
 			</span>
        </div>
    </div>
</tags:basicFormPanelBox>

</body>
</html>