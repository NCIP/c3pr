<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>C3PR V2</title>
<script>
function navRollOver(obj, state) {
  document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
}
function submitPage(s){
//	document.getElementById("searchCategory").value=s;
	document.getElementById(s).submit();
}
</script>
</head>
<body>
<tags:formPanelBox tab="${tab}" flow="${flow}">
Study: <input type="text" name="study" value="Test Study Short Title"/>
<input type="hidden" name="studySite" value="1"/>
<input type="hidden" name="scheduledTreatmentEpochs[0].epoch" value="2"/>
Subject: <input type="text" name="participant" value="1"/>

</tags:formPanelBox>
</body>
</html>
