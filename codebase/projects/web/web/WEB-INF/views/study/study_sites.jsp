<%@ include file="taglibs.jsp"%>
<link href="themes/mac_os_x.css" rel="stylesheet" type="text/css"/>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
	<script type="text/javascript">
	var nciCode ;
	var primaryIdentifier;
	
	function updateStudy() {
		$('openSections').value = getOpenSectionsStr();
	    document.getElementById("studySitesForm").submit();
	}

	function getOpenSectionsStr(){
		var strHiddenDiv = '' ;
		$$('.hiddenDiv').each(function(element){
			if(element.style.display != 'none'){
				strHiddenDiv = strHiddenDiv + '|' +element.id.substring(element.id.indexOf("-") + 1, element.id.length) ;
			}
		});
		return strHiddenDiv;
	}

	function deleteStudySite(nciCode){
		<tags:tabMethod method="deleteStudySite" divElement="'studySites'" formName="'tabMethodForm'"  viewName="/study/asynchronous/delete_study_site_section" javaScriptParam="'_doNotSave=true&nciCode='+nciCode+'&openSections='+getOpenSectionsStr()" /> ;
	}

	function addStudySite(){
		primaryIdentifier = $('studysite-hidden').value ;
		var alreadyExist = false ;
		$$('.divisonClass').each(function(element){
			if(element.id.indexOf(primaryIdentifier) !=-1){
				alreadyExist = true ;
			}
		});
		if(alreadyExist){
			Dialog.alert("Study site already exist", {className: "alphacube", width:240, okLabel: "Done" });
			return;
		}
		<tags:tabMethod method="addStudySite" divElement="'newStudySite'" formName="'tabMethodForm'"  viewName="/study/asynchronous/add_study_site_section" javaScriptParam="'primaryIdentifier='+primaryIdentifier" onComplete="refreshStudySiteSection" /> ;
	}

	function refreshStudySiteSection(){
			Element.insert($('studySites'), { bottom: $('newStudySite').innerHTML })
			$('studysite-hidden').value = '' ;
			$('studysite-input').value = '' ;
			$('addStudySite').disabled=true ;
			inputDateElementLocal="irbApprovalDate-"+primaryIdentifier;
	       	inputDateElementLink="irbApprovalDate-"+primaryIdentifier+"-calbutton";
	       	Calendar.setup(
	       	{
	       	    inputField  : inputDateElementLocal,         // ID of the input field
	       	    ifFormat    : "%m/%d/%Y",    // the date format
	       	    button      : inputDateElementLink       // ID of the button
	       	}
	       	);
	       	$('siteIndicator').hide();
	       	$('divison-'+primaryIdentifier).scrollIntoView();
	}

	var multisiteStudySiteAutocompleterProps = {
	    basename: "studysite",
	    populator: function(autocompleter, text) {
	        StudyAjaxFacade.matchHealthcareSites( text,function(values) {
	            autocompleter.setChoices(values)
	        })
	    },
	    valueSelector: function(obj) {
	    	if(obj.externalId != null){
	    		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
	    	} else {
	    		image = '';
	    	}
	    	 return (obj.name+" ("+obj.ctepCode+")" + image)
	    },
	    afterUpdateElement:
		    function(inputElement, selectedElement, selectedChoice) {
	    			hiddenField=inputElement.id.split("-")[0]+"-hidden";
		    		$(hiddenField).value=selectedChoice.ctepCode;
		    		$('addStudySite').disabled=false ;
		}
	}

	function changeCompanionStudySiteStatus(nciCode){
		action=$("companionSiteAction-"+nciCode).value;
		<tags:tabMethod method="changeStatus" formName="'tabMethodForm'" onFailure='failedStatusChange' viewName="/study/asynchronous/companionSites_row" divElement="'dummy-div'" javaScriptParam="'action=' + action+ '&nciCode='+nciCode+ '&studySiteType=companionSite&DO_NOT_SAVE=true'" />
		Element.show('companionSendingMessage-'+nciCode);
	}

	failedStatusChange= function (responseXML){
										Dialog.alert(responseXML.responseText,
							             {width:600, height:600, okLabel: "Close",
							              ok:function(win) {$('reload').submit(); return true;}});
									}

	AutocompleterManager.addAutocompleter(multisiteStudySiteAutocompleterProps);

	var win;
	function selectStudySites(studyId, parentAssociationId, parentIndex){
		win = new Window({title: "Select Study Sites",
				scrollbar: false, zIndex:100, width:900, height:325 ,
				recenterAuto:true, className :"mac_os_x",
				url: "<c:url value='/pages/study/selectStudySites?decorator=noheaderDecorator&parentAssociationId='/>" + parentAssociationId  +"&parentIndex=" + parentIndex  +"&studyId=" + studyId ,
				showEffectOptions: {duration:1.5}
					}
				)
		win.showCenter(true)
	}

	function closePopup() {
		win.close();
	}

	function reloadParentStudySites(studyId , studyAssociationId , nciCodes , parentIndex, irbApprovalSites){
		$('nciCodes').value=nciCodes;
		$('irbApprovalSites').value=irbApprovalSites;
		$('studyAssociationId').value=studyAssociationId;
		<tags:tabMethod method="associateParentStudySites" divElement="'parentStudySiteDiv-'+parentIndex" formName="'parentStudySiteForm'"  viewName="/study/parentStudySiteSection" javaScriptParam="'parentIndex='+parentIndex"/>
	}

	function deleteCompanionStudySiteAssociation(studySiteId, parentIndex){
		$("_doNotSave").name="xyz";
		<tags:tabMethod method="removeCompanionStudyAssociation" divElement="'parentStudySiteDiv-'+parentIndex" formName="'parentStudySiteForm'"  viewName="/study/parentStudySiteSection" javaScriptParam="'studySiteId='+studySiteId+'&parentIndex='+parentIndex"/>
		$("_doNotSave").name="_doNotSave";
	}

	
	</script>
</head>
<body>
	<form:form id="reload">
		<input type="hidden" name="_target${tab.number}" id="_target"/>
		<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
	</form:form>
	<div id="dummy-div" style="display: none;" ></div>
	<tags:panelBox title="Sites">
		<div class="row">
			<div class="name">
				<tags:autocompleter name="axxxxyyy" displayValue="" value="" basename="studysite" size="90"></tags:autocompleter>
				<tags:button color="blue" value="Add study site" icon="add" type="button" size="small" id="addStudySite" onclick="$('siteIndicator').show();addStudySite();" disabled="true"></tags:button>
				<img id="siteIndicator" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
			</div>
		</div>
		<br>
		<form:form id="studySitesForm">
			<input type="hidden" name="submitted" value="true"/>
			<input type="hidden" name="openSections" id="openSections"/>
			<tags:errors path="study.studySites" />
			<div id="studySites">
				<studyTags:studySitesSection commandObj="${command}"></studyTags:studySitesSection>
			</div>
		</form:form>
		<div id="newStudySite" style="display: none" ></div>
		</tags:panelBox>
		<div class="flow-buttons">
		    <span class="next">
				<tags:button color="green" value="Save" icon="save" id="save" type="button" onclick="updateStudy();"></tags:button>
			</span>
		</div>
	</body>
</html>