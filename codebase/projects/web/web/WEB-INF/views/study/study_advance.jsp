<%@ include file="taglibs.jsp"%>
<html>
<head>
</head>
<body>
<tags:formPanelBox flow="${flow}" tab="${tab}">
    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
                Hosted Mode:</div>
            <div class="value"><form:checkbox id="hostedMode" path="study.hostedMode"/>
            <tags:hoverHint keyProp="study.hostedMode"/>
            </div>
        </div>
	</div>
	<script>
		<c:if test="${!command.study.multiInstitutionIndicator}">$(hostedMode').disabled=true;
		</c:if>
	</script>
</tags:formPanelBox>
</body>
</html>