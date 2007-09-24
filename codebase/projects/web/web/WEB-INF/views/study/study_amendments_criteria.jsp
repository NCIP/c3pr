<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
<script>
	var qsParm = new Array();
	function qs() {
		var query = window.location.search.substring(1);
		var parms = query.split('&');
		for (var i=0; i<parms.length; i++) {
			var pos = parms[i].indexOf('=');
			if (pos > 0) {
				var key = parms[i].substring(0,pos);
				var val = parms[i].substring(pos+1);
				qsParm[key] = val;
			}
		}
	}

	function gotoEditFlow(){
		var epochAndArms = document.getElementById('epochAndArms').value;
		var eligibility = document.getElementById('eligibility').value;
		var stratification = document.getElementById('stratification').value;
		var diseases = document.getElementById('diseases').value;
		var consent = document.getElementById('consent').value;
		var principalInvestigator = document.getElementById('principalInvestigator').value;
		qs();
		var studyId = qsParm[0];
		var url = "/pages/study/editStudy?studyId="	+studyId+ "&epochAndArms=" +epochAndArms+ "&eligibility=" +eligibility+
		"&stratification=" +stratification+ "&diseases=" +diseases+ "&consent=" +consent+ "&principalInvestigator=" +principalInvestigator;
		alert(url);
		document.location = url;
	}
</script>
</head>

<body>
<br />


<div id="AmendButton" align="center">
	<input type="button" value="Amend Study" onclick="gotoEditFlow();"/>
</div>
</body>
</html>