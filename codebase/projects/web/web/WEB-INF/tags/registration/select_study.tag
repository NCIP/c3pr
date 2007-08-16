<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<script type="text/javascript" src="<c:url value="/js/extremecomponents.js"/>"></script>

<script type="text/javascript">

	function minimizeStudyBox(msg){
		PanelCombo('Studybox');
		localClassString="#Study .header h2";
		element=$$(localClassString)[0];
		htmlStr=msg;
		new Element.update(element,htmlStr);
		new Effect.Pulsate(element);
	}
	
	function minimizeEpochBox(msg){
		PanelCombo('Epochbox');
		localClassString="#Epoch .header h2";
		element=$$(localClassString)[0];
		htmlStr=msg;
		new Element.update(element,htmlStr);
		new Effect.Pulsate(element);
	}
	
	
    function navRollOver(obj, state) {
        document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
    }		
    
    function callbackStudyFail(t ){
		alert("Search Failed");
	}
	
	function callbackStudy(t){
		var resultDiv = document.getElementById("studySearchResults");
		resultDiv.innerHTML = t.responseText;
		new Effect.SlideDown(resultDiv);;
	}
	
	function postProcessStudySelection(id, siteName, studyName, identifier){
		document.getElementById("studySite").value = id;
		var url = "../registration/searchEpoch?studySiteId="+id;
		new Ajax.Updater('epochResults',url, {onSuccess:callbackEpoch, onFailure:callbackEpochFail});
		var message = "Selected Study: " +studyName+ " (" +identifier+ ") "  + " at " +siteName;
		minimizeStudyBox(message);

	}
	
	 function callbackEpochFail(t ){
		alert("epoch Search Failed");
	}
	
	function callbackEpoch(t){
		document.getElementById("Epoch").style.display="";
		var resultDiv = document.getElementById("epochResults");
		resultDiv.innerHTML = t.responseText;
		new Effect.SlideDown(resultDiv);		
	}
	
	function postProcessEpochSelection(id, name, type){
		$("epochElement").value = id;
		var message = "Selected Epoch: " +name+ " (" +type+ ") ";
		minimizeEpochBox(message);
	}
</script>

<!--tags:minimizablePanelBox title="${epoch.name} : ${epoch.descriptionText }"	boxId="${epoch.name}"-->
<tags:minimizablePanelBox	title="Select A Study" boxId="Studybox">
        <form id="searchstudyForm" action="" method="post">
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value=""> 
            <input type="hidden" name="async" id="async" value="async"> 
        <div class="content">
            <div class="row">
                <div class="label">
                    Search Studies By:
                </div>
                <div class="value">
                	<select id="searchType" name="searchType">
							<option value="">--Please Select--</option>
						<c:forEach items="${searchTypeRefDataStudy}" var="searchTypeRefDataStudy">
						<c:if test="${!empty searchTypeRefDataStudy.desc}">
							<option value="${searchTypeRefDataStudy.code}">${searchTypeRefDataStudy.desc}</option>
						</c:if>									
						</c:forEach>
					</select>
                </div>		
            </div>
            <div class="row">
                <div class="label">
                    Search Criteria:
                </div>
                <div class="value">
                	<input id="searchText" name="searchText" type="text" value="" size="25"/>
                	<input type="button" value="Search" onclick="new Ajax.Updater('studySearchResults','../registration/searchStudy', {method:'post', postBody:Form.serialize('searchstudyForm'), onSuccess:callbackStudy, onFailure:callbackStudyFail});"/>
                </div>		
            </div>
        </div>
		</form>
    	<hr align="left" width="95%" />
    	
    	<div id="studySearchResults" style="display:none;">
		<!--In order to ensure that the decorator is not applied to the dynamically (AJAX)inserted jsp
    		add the mapping to the excludes section of decorators.xml -->
    	</div>
</tags:minimizablePanelBox>



