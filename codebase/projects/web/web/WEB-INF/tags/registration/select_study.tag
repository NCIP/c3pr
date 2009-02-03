<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>

<script type="text/javascript">

	function minimizeStudyBox(msg){
		PanelCombo('Studybox');
		displayStudyMessage(msg,true);
	}
	
	function displayStudyMessage(message,pulsateFlag){
		localClassString="#Study .header h2";
		element=$$(localClassString)[0];
		new Element.update(element,message);
		pulsateFlag?(!is_ie?new Effect.Pulsate(element):null):null;
	}
	
    function navRollOver(obj, state) {
        document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
    }		
    
    function callbackStudyFail(t ){
		alert("Search Failed");
	}
	
	function callbackStudy(t){
		new Element.hide('searchStudyInd');
		var resultDiv = document.getElementById("studySearchResults");
		resultDiv.innerHTML = t.responseText;
		new Effect.SlideDown(resultDiv);;
	}
	
	function postProcessStudySelection(isActive, id, siteName, studyName, identifier){
		if(!isActive){
			alert("Study is not active at "+siteName);
			return;
		}
		document.getElementById("studySubject.studySite").value = id;
		var url = "../registration/searchEpoch?studySiteId="+id;
		new Ajax.Updater('epochResults',url, {onSuccess:callbackEpoch, onFailure:callbackEpochFail});
		var message = "Selected study: " +studyName+ " (" +identifier+ ") "  + " at " +siteName;
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

    function searchStudy() {
        new Element.show('searchStudyInd');
        new Ajax.Updater('studySearchResults','../registration/searchStudy?fromRegistration=true&decorator=nullDecorator',
        {
            method:'post',
            postBody:Form.serialize('searchstudyForm'), 
            onSuccess:callbackStudy,
            onFailure:callbackStudyFail
        });
    }
    
</script>

<!--tags:minimizablePanelBox title="${epoch.name} : ${epoch.descriptionText }"	boxId="${epoch.name}"-->
<tags:minimizablePanelBox	title="Select a study" boxId="Studybox">
<tags:instructions code="select_study" />
        <form id="searchstudyForm" action="" method="post">
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value=""> 
            <input type="hidden" name="async" id="async" value="async"> 
        <div class="content">
            <div class="row">
                <div class="label">
                    Search studies by
                </div>
                <div class="value">
                	<select id="searchType" name="searchType">
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
                    Search criteria
                </div>
                <div class="value">
                	<input type="hidden" id="activeOnly" name="activeOnly" value="true"/>
                	<input type="hidden" id="standaloneOnly" name="standaloneOnly" value="true"/>
                	<input id="searchText" name="searchText" type="text" value="" size="25"/>
                	<input type="button" value="Search" onclick="searchStudy();"/>
                	<img id="searchStudyInd" src="<tags:imageUrl name="indicator.white.gif"/>"
								alt="Indicator" align="absmiddle">
					<script>new Element.hide('searchStudyInd');</script>
                </div>		
            </div>
        </div>
		</form>
    	
    	<div id="studySearchResults" style="display:none;">
		<!--In order to ensure that the decorator is not applied to the dynamically (AJAX)inserted jsp
    		add the mapping to the excludes section of decorators.xml -->
    	</div>
</tags:minimizablePanelBox>



