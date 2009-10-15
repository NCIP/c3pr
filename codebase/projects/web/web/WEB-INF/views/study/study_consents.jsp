<%@ include file="taglibs.jsp"%>
<html>
<head>
	<style>
		*{zoom:1}

		div.row div.newlabel {
			font-weight: normal;
			float: left;
			margin-left: 0.5em;
			margin-right: 0.5em;
			text-align: right; /* default width; pages might override */
			width: 19em;
			font-weight:bold;
		}
	</style>
<!--[if lte IE 7]>
	<style>
		#workflow-tabs {
			top:-15px;
		}
		#workflow-tabs li.selected {
			margin-top:-4px;
			padding-top:4px;
		}
		#workflow-tabs li.selected a{
			padding-bottom:1px;
			padding-top:1px;
		}
	</style>
<![endif]-->
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
    <script type="text/javascript">
    function updateName(divID, stringValue) {
        if ($(divID)) {
            $(divID).innerHTML = stringValue;
        }
    }

    ValidationManager.submitPostProcess= function(formElement, continueSubmission){
    	var strHiddenDiv = '' ;
		$$('.hiddenDiv').each(function(element){
			if(element.style.display != 'none'){
				strHiddenDiv = strHiddenDiv + '|' +element.id.substring(element.id.indexOf("-") + 1, element.id.length) ; 
			}
		});
		$('openSections').value = strHiddenDiv ;
    	return continueSubmission;
	} 


    var genericConsentRowInserterProps={
            add_row_division_id: "consent",
            skeleton_row_division_id: "dummy-genericConsent",
            initialIndex: ${fn:length(command.study.consents)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            path: "study.consents",
            postProcessRowInsertion: function(object){
      								var inputName="study.consents["+object.localIndex+"].name";
      								setTimeout("enableFocus(\'"+inputName+"\')",10);
      							}
            };
 		RowManager.addRowInseter(genericConsentRowInserterProps);
        RowManager.registerRowInserters();
        function enableFocus(inputName2){
        	$$("input[name='"+inputName2+"']")[0].focus();
        }
    
	</script>
</head>
<body>
<form:form id="consentForm">
	<input type="hidden" name="openSections" id="openSections"/>
	<chrome:box title="Consent">
	<tags:tabFields tab="${tab}" />
	<tags:instructions code="study_consents" />
	<tags:errors path="study.consents" />
	<chrome:division>
		<div class="row">
			<div class="newlabel"><fmt:message key="study.consentRequired"/></div>
			<div class="value">
				  <form:select path="study.consentRequired" cssClass="validate-notEmpty" >
				  	<form:options items="${consentRequired}" itemLabel="desc" itemValue="code" />
				  </form:select>
				  <tags:hoverHint keyProp="study.consentRequired" />
			</div>
		</div>
	</chrome:division>
<br>

<!-- CONSENT TABLE START -->
<table id="consent" width="100%" border="0">
	<tr></tr>
    <c:forEach items="${command.study.consents}" var="consent"  varStatus="consentCount" >
        <tr id="consent-${consentCount.index}">
            <script type="text/javascript">
            </script>
            <td>
      			<chrome:deletableDivision divTitle="consentTitle-${consentCount.index}" id="consentBox-${consentCount.index}"
						title="Consent: ${command.study.consents[consentCount.index].name}" minimize="${(fn:contains(openSections, consentCount.index) || fn:length(command.study.consents) == 1)? 'false':'true'}" divIdToBeMinimized="consentDiv-${consentCount.index}"
						onclick="RowManager.deleteRow(genericConsentRowInserterProps,${consentCount.index},'${consent.id==null?'HC#':'ID#'}${consent.id==null?consent.hashCode:consent.id}')">
<!-- CONSENT START-->
<div id="consentDiv-${consentCount.index}"  style="${(fn:contains(openSections,consentCount.index) || fn:length(command.study.consents) == 1) ? '':'display:none'}" class="hiddenDiv">
<table width="100%" border="0">
<tr>
  <td valign="top" width="50%">
      <table width="50%" border="0" cellspacing="4" cellpadding="2">
      <tr>
          <td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.name"/></b></td>
          <td align="left" valign="top">
              <form:input path="study.consents[${consentCount.index}].name" cssClass="validate-notEmpty"
											onkeyup="updateName('consentTitle-${consentCount.index}', 'Consent: ' + this.value);" size="35"/>
			  <tags:hoverHint id="study.consent.name-${consentCount.index}" keyProp="study.consent.name" />
          </td>
      </tr>
      </table>
  </td>
</tr>

</table>
<!-- GENERIC END-->
</chrome:deletableDivision>
</td>
</tr>
</c:forEach>
</table>
<!-- BIG TABLE END -->

	<div align="left">
	<tags:button type="button" color="blue" icon="add" value="Add Consent" size="small"
	onclick="$('dummy-genericConsent').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(genericConsentRowInserterProps)" />
    <br></div>
	</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />
</form:form>

<!-- DUMMY SECIION START -->

<div id="dummy-genericConsent" style="display: none"></div>


<div id="genericHtml" style="display: none">
<table width="100%">
	<tr valign="top">
		<td>
			<chrome:deletableDivision divTitle="divConsentBox-PAGE.ROW.INDEX" id="genericConsentBox-PAGE.ROW.INDEX" title="Consent: " onclick="RowManager.deleteRow(genericConsentRowInserterProps,PAGE.ROW.INDEX,-1)">
			<div class="hiddenDiv" id="consentDiv-PAGE.ROW.INDEX">
			<table style="border: 0px red dotted;" width="100%">
				<tr>
					<td valign="top" width="50%">
					<table width="50%" border="0" cellspacing="4" cellpadding="2">
						<tr>
							<td align="right"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.name"/></b></td>
							<td align="left"><input type="text" name="study.consents[PAGE.ROW.INDEX].name" class="validate-notEmpty"
								onkeyup="updateName('divConsentBox-PAGE.ROW.INDEX', 'Consent: ' + this.value);" size="35"/>
								<tags:hoverHint id="study.consent.name-PAGE.ROW.INDEX" keyProp="study.consent.name" /></td>
						</tr>
					</table>
					</td>
				</tr>
				
			</table>
			</div>
			

			<!-- GENERIC END-->
		</chrome:deletableDivision></td>
	</tr>
	
</table>
</div>

<!-- DUMMY SECIION END -->

</body>
</html>