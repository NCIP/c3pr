<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp" %>
<html>
    <head>
        <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
		<%--<jwr:style src="/css/subtabbedflow.css" />--%>
        <script>
        	C3PR.disableCheckRequiredFieldOnLoad=true;

        	/* handlers for searchSubject flwo */
    	  	
        	function postProcessSubjectSelection(id, name, identifier){
        		$('studySubject.participant').value = id;
        	//	${command.studySubject.participant} = id;
        		minimizeSubjectBox("Selected Subject: " +name+ " (" + identifier + ")");	
        	}  	
        	function minimizeSubjectBox(msg){
        		PanelCombo('SubjectBox');
        		displaySubjectMessage(msg,true);
        	}
        	
        	function displaySubjectMessage(message,pulsateFlag){
        		element=$$("#Subject .header h2")[0];
        		new Element.update(element,message);
        		pulsateFlag?(!is_ie?new Effect.Pulsate(element):null):null;
        	}

            function navRollOver(obj, state){
                document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
            }
            
            function getPage(s){
                parent.window.location = "reg_patient_search.htm";
            }
            
            //-----------------------------------------------------------------------------------------------------
            function catchKey(e){
            	var evt=(e)?e:(window.event)?window.event:null;
            	if(evt){  
                	if (evt.keyCode == 13) {
                		var idElement ;
                		if(is_ie){
							idElement = evt.srcElement.id 
                    	}else{
                    		idElement = evt.target.id
                    	}
	                    if ( idElement == "searchSubjectText") {
	                        searchParticipant();
	                    }
	                    
	                    if (idElement == "searchText") {
	                        searchStudy();
	                    }
	                    
	                    return false;
                    
                	}
            	}
              }
            
            //-----------------------------------------------------------------------------------------------------
            document.onkeypress = catchKey;
        </script>
        <style type="text/css">
            .labelR {
                text-align: right;
                padding: 4px;
                font-weight: bold;
            } .labelL {
                text-align: left;
                padding: 4px;
                font-weight: bold;
            } .labelC {
                text-align: center;
                padding: 4px;
                font-weight: bold;
            } .race input {
					margin-right:5px;
					margin-bottom:5px;
				}
				
		</style>
    </head>
    <body>
    	<div id="topLevelErrorDisplay" class="errors" style="display: none;">
		</div>
        <div id="Study">
            <registrationTags:select_study/>
        </div>
        <div id="Epoch" style="display:none">
            <registrationTags:select_epoch/>
        </div>
        <div id="Subject">
            <registrationTags:select_subject/>
        </div>
        <form:form method="post">
		<tags:errors path="*"/>
            <tags:tabFields tab="${tab}"/>
             <input type="hidden" name="studySiteStudyVersionId" id="studySiteStudyVersionId" value="${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.id }"/>
            <div style="display:none">
                <form:input path="studySubject.participant" cssClass="required validate-notEmpty" /><input type="text" id="epochElement" name="epoch" value="${!empty command.studySubject.scheduledEpoch?command.studySubject.scheduledEpoch.epoch.id:''}" class="required validate-notEmpty"/>
            </div>
            <tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
        </form:form>
        <c:if test="${command.studySubject.studySite.id!=null && command.studySubject.participant.id!=null}">
            <script>
                                		<c:choose> 
                                			<c:when test="${!empty command.studySubject.studySubjectStudyVersions[0].scheduledEpochs[0]}"> 
                                				new Element.show('Epoch'); 
                                				minimizeEpochBox(); 
                                				displayEpochMessage("Selected Epoch: ${command.studySubject.scheduledEpoch.epoch.name}",true); 
                                			</c:when> 
                                			<c:otherwise> 
                                				var url1 = "../registration/searchEpoch?studySiteStudyVersionId="+${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.id}; 
                                				new Ajax.Updater('epochResults',url1, {onSuccess:callbackEpoch, onFailure:callbackEpochFail}); 
                                			</c:otherwise> 
                                		</c:choose> 
                                		minimizeStudyBox("Selected Study: ${command.studySubject.studySite.study.shortTitleText} (${command.studySubject.studySite.study.coordinatingCenterAssignedIdentifier.value}) at ${command.studySubject.studySite.healthcareSite.name}"); 
                                		minimizeSubjectBox("Selected Subject: ${command.studySubject.participant.fullName} ");
                                	
                            
            </script>
        </c:if>
    </body>
</html>
