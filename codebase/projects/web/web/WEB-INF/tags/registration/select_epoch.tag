<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>
<tags:dwrJavascriptLink objects="searchStudyController"/>

<script type="text/javascript">
    function navRollOver(obj, state) {
        document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
    }
    function submitPage() {
        document.getElementById("searchForm").submit();
    }
		
</script>

<tags:minimizablePanelBox title="Please Select An Epoch" boxId="Epochbox">
<div id="staticMessage">
Please select a Study First.
</div>
<div id="epochResults" style="display:none;">
<!--In order to ensure that the decorator is not applied to the dynamically (AJAX)inserted jsp
	add the mapping to the excludes section of decorators.xml -->
</div>
</tags:minimizablePanelBox>

<br>


