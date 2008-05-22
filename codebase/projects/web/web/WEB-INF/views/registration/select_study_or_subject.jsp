<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>

<html>
<head>
    <jwr:style src="/css/subtabbedflow.css" />
<script>						    
	function navRollOver(obj, state) {
	  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
	}
	function getPage(s){
		parent.window.location="reg_patient_search.htm";
	}

//-----------------------------------------------------------------------------------------------------
    function catchKey(evt) {

        if (evt.keyCode == 13) {

            if (evt.target.id == "searchSubjectText") {
                searchParticipant();
            }

            if (evt.target.id == "searchText") {
                searchStudy();
            }

            return false;
            
        };

    }

//-----------------------------------------------------------------------------------------------------
    document.onkeypress = catchKey;

</script>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<style type="text/css">
        .labelL { text-align: left; padding: 4px; font-weight: bold;}
</style>
<style type="text/css">
        .labelC { text-align: center; padding: 4px; font-weight: bold;}
</style>
</head>

<body>
<div id="main">
<div id="Subject">
	<registrationTags:select_subject />
</div>
<div id="Study">
	<registrationTags:select_study />
</div>
<div id="Epoch" style="display:none">
	<registrationTags:select_epoch />
</div>
<form:form method="post">
<tags:tabFields tab="${tab}"/>
	<div style="display:none">
		<form:input path="studySite" cssClass="validate-notEmpty"/>
		<form:input path="participant" cssClass="validate-notEmpty" />
		<input type="text" id="epochElement" name="epoch" value="${!empty command.scheduledEpoch?command.scheduledEpoch.epoch.id:''}" class="validate-notEmpty"/>
	</div>	
	<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>
</div>
<c:if test="${command.studySite.id!=null && command.participant.id!=null}">
	<script>
		new Element.show('Epoch');
		minimizeEpochBox();
		displayEpochMessage("Selected epoch: ${command.scheduledEpoch.epoch.name}",true);
		minimizeStudyBox("Selected study: ${command.studySite.study.shortTitleText} (${command.studySite.study.coordinatingCenterAssignedIdentifier.value}) at ${command.studySite.healthcareSite.name}");
		minimizeSubjectBox("Selected subject: ${command.participant.fullName} ");
	</script>
</c:if>
</body>
</html>
