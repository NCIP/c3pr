<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><registrationTags:htmlTitle registration="${command}"></title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
<input type="hidden" name="epoch" value="2"/>
Subject: <input type="text" name="participant" value="2"/>

</tags:formPanelBox>
</body>
</html>
