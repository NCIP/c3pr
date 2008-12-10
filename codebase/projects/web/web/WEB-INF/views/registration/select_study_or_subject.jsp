<%@ include file="taglibs.jsp" %>
<html>
    <head>
        <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
		<%--<jwr:style src="/css/subtabbedflow.css" />--%>
        <script>
            function navRollOver(obj, state){
                document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
            }
            
            function getPage(s){
                parent.window.location = "reg_patient_search.htm";
            }
            
            //-----------------------------------------------------------------------------------------------------
            function catchKey(evt){
            
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
            } .fifthlevelTab {
                padding: 5px;
                margin-left: 5px;
                background-image: url(../../templates/mocha/images/taskbar_bg.png);
                background-position: -17px;
                text-decoration: none;
                color: white;
            } .current {
                background-image: url(../../templates/mocha/images/select_subject_tab_selected.png);
				padding: 5px;
                margin-left: 5px;
                background-position: bottom;
				background-repeat:repeat-x;
				background-color:#202020;
                text-decoration: none;
                color: white;
				}
				.race input {
					margin-right:5px;
					margin-bottom:5px;
				}
		</style>
		
            <!--[if IE]>
			<style>
			.fifthlevelTab img, .current img {
				vertical-align:middle;
			}
			</style>
<![endif]-->

    </head>
    <body>
        <div id="Subject">
            <registrationTags:select_subject/>
        </div>
        <div id="Study">
            <registrationTags:select_study/>
        </div>
        <div id="Epoch" style="display:none">
            <registrationTags:select_epoch/>
        </div>
        <form:form method="post">
            <tags:tabFields tab="${tab}"/>
            <div style="display:none">
                <form:input path="studySubject.studySite" cssClass="validate-notEmpty"/><form:input path="studySubject.participant" cssClass="validate-notEmpty" /><input type="text" id="epochElement" name="epoch" value="${!empty command.studySubject.scheduledEpoch?command.studySubject.scheduledEpoch.epoch.id:''}" class="validate-notEmpty"/>
            </div><registrationTags:goToTab currentTab="0" registration="${command.studySubject}" /><registrationTags:backToTab currentTab="0" registration="${command.studySubject}" />
            <c:set var="custonButton" value ="${param.customButton}">
            </c:set>
            <tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
        </form:form>
        <c:if test="${command.studySubject.studySite.id!=null && command.studySubject.participant.id!=null}">
            <script>
                                		<c:choose> 
                                			<c:when test="${!empty command.studySubject.scheduledEpoch}"> 
                                				new Element.show('Epoch'); 
                                				minimizeEpochBox(); 
                                				displayEpochMessage("Selected epoch: ${command.studySubject.scheduledEpoch.epoch.name}",true); 
                                			</c:when> 
                                			<c:otherwise> 
                                				var url1 = "../registration/searchEpoch?studySiteId="+${command.studySubject.studySite.id}; 
                                				new Ajax.Updater('epochResults',url1, {onSuccess:callbackEpoch, onFailure:callbackEpochFail}); 
                                			</c:otherwise> 
                                		</c:choose> 
                                		minimizeStudyBox("Selected study: ${command.studySubject.studySite.study.shortTitleText} (${command.studySubject.studySite.study.coordinatingCenterAssignedIdentifier.value}) at ${command.studySubject.studySite.healthcareSite.name}"); 
                                		minimizeSubjectBox("Selected subject: ${command.studySubject.participant.fullName} ");
                                	
                            
            </script>
        </c:if>
    </body>
</html>
