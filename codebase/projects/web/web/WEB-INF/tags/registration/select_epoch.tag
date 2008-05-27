<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<tags:dwrJavascriptLink objects="searchStudyController"/>

<script type="text/javascript">
    function navRollOver(obj, state) {
        document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
    }
    function submitPage() {
        document.getElementById("searchForm").submit();
    }
	epochId="";
	epochName="";
	epochType="";
	function afterCheckEpochAccrual(){
		$("epochElement").value = epochId;
		var message = "Selected epoch: " +epochName+ " (" +epochType+ ") ";
		minimizeEpochBox();
		displayEpochMessage(message, true);
	}
	function postProcessEpochSelection(id, name, type){
		epochId=id;
		epochName=name;
		epochType=type;
		displayEpochMessage("<img align='absmiddle' src='/c3pr/images/indicator.white.gif'/>Processing..", false);
		<tags:tabMethod method="checkEpochAccrualCeiling" viewName="/registration/asynchronous/checkCeiling" divElement="'epochAccrualCeilingResponse'" javaScriptParam="'epochId='+id"  formName="'accrualForm'"/>
	}
	function minimizeEpochBox(){
		PanelCombo('Epochbox');
	}
	function displayEpochMessage(message,pulsateFlag){
		localClassString="#Epoch .header h2";
		element=$$(localClassString)[0];
		new Element.update(element,message);
		pulsateFlag?(!is_ie?new Effect.Pulsate(element):null):null;
	}
</script>

<tags:minimizablePanelBox title="Select an epoch" boxId="Epochbox">
<div id="epochResults">
<!--In order to ensure that the decorator is not applied to the dynamically (AJAX)inserted jsp
	add the mapping to the excludes section of decorators.xml -->
</div>
</tags:minimizablePanelBox>
<div id="epochAccrualCeilingResponse" style="display:none;"></div>
<form:form id="accrualForm">
	<tags:tabFields tab="${tab}"/>
</form:form>
<br>


