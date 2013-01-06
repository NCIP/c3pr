<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%--<div id="build-name">Build Number: ${buildInfo.buildName}</div>--%>
<div id="footer"></div>
<tags:debugInfo/>
<%-- Run the validation on page load to accomodate default values for required fields--%>
<script>
if(!C3PR.disableCheckRequiredFieldOnLoad){
	for(i=0 ; i<document.forms.length ; i++){
			Form.getElements(document.forms[i]).findAll(function(field){
								return field.hasClassName("required")
							}).each(ValidationManager.doFieldValidationOnLoad)
	}
}
</script>
