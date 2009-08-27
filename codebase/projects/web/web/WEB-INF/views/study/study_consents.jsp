<%@ include file="taglibs.jsp"%>
<html>
<head>
	<style>
		#main {
			top:33px;
		}
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


    var consentVersionInserterProps= {
            add_row_division_id: "consentVersion",
            skeleton_row_division_id: "dummy-consentVersion",
            initialIndex: ${fn:length(command.study.consents[consentCount.index].consentVersions)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
            path: "study.consents[PAGE.ROW.INDEX].consentVersions",
          	postProcessRowInsertion: function(object){
            	inputDateElementLocal="study.consents[" + object.parent_row_index + "].consentVersions[" + object.localIndex + "].effectiveDate";
            	inputDateElementLink="study.consents[" + object.parent_row_index + "].consentVersions[" + object.localIndex + "].effectiveDate-calbutton";
                Calendar.setup( {
                    inputField  : inputDateElementLocal,         // ID of the input field
                    ifFormat    : "%m/%d/%Y",    // the date format
                    button      : inputDateElementLink       // ID of the button
                } );
      		}
        };

     var genericConsentRowInserterProps= {
          	nested_row_inserter: consentVersionInserterProps,
            add_row_division_id: "consent",
            skeleton_row_division_id: "dummy-genericConsent",
            initialIndex: ${fn:length(command.study.consents)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            path: "study.consents",
            postProcessRowInsertion: function(object){
      								var inputName="study.consents["+object.localIndex+"].name";
      								setTimeout("enableFocus(\'"+inputName+"\')",10);
      							},
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
				  	<form:option value="">Please Select...</form:option>
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
                RowManager.getNestedRowInserter(genericConsentRowInserterProps,${consentCount.index}).updateIndex(${fn:length(command.study.consents[consentCount.index].consentVersions)});
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
<tr bgcolor="eeffee">
  <td colspan="3" align="left">
      <!--CONSENT VERSION TABLE-->
      <table id="consentVersion" class="tablecontent" border="0">
      <tags:errors path="study.consents[${consentCount.index}].consentVersions" />
      <tr id="h-${consentCount.index}" >
          <th>
          	<tags:requiredIndicator /><fmt:message key="study.consent.consentVersion.name"/>
          	<tags:hoverHint id="study.consent.consentVersion.name-${consentCount.index}" keyProp="study.consent.consentVersion.name" />
          </th>
          <th>
          	<fmt:message key="study.consent.consentVersion.date"/>
          	<tags:hoverHint id="study.consent.consentVersion.date-${consentCount.index}" keyProp="study.consent.consentVersion.date" />
          </th>
          <th></th>
      </tr>
      <c:choose>
      	<c:when test="${fn:length(consent.consentVersions) == 0}">
      		<tr>
      			<td align="left" id="addConsentVersionMessage-${consentCount.index}"><fmt:message key="study.consent.addConsentVersion"/></td>
      		</tr>
      	</c:when>
      	<c:otherwise>
      		<c:forEach items="${consent.consentVersions}" var="version" varStatus="versionStatus">
	            <tr id="consentVersion-${versionStatus.index}">
	                <td valign="top">
	                	<form:input path="study.consents[${consentCount.index}].consentVersions[${versionStatus.index}].name" size="25" cssClass="validate-notEmpty" />
	                </td>
	                <td valign="top">
	                	<tags:dateInput path="study.consents[${consentCount.index}].consentVersions[${versionStatus.index}].effectiveDate" cssClass="validate validate-DATE&&notEmpty" />
	                </td>
	                <td valign="top" align="left">
	                    <a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(genericConsentRowInserterProps,${consentCount.index}),${versionStatus.index },'${version.id==null?'HC#':'ID#'}${version.id==null?version.hashCode:version.id}');">
	                    	<img src="<tags:imageUrl name="checkno.gif"/>" border="0">
	                    </a>
	                </td>
	            </tr>
            </c:forEach>
      	</c:otherwise>
      </c:choose>    
      </table>
      <br>
      <tags:button id="addVersion-${consentCount.index}" type="button" color="blue" icon="add" value="Add Consent Version"
					onclick="$('addConsentVersionMessage-${consentCount.index}') != null ? $('addConsentVersionMessage-${consentCount.index}').hide():''; javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericConsentRowInserterProps,${consentCount.index}));" size="small"/>
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
 <hr noshade size="1" width="100%" style="border-top: 1px black dotted;" align="left">
	<div align="left">
	<tags:button type="button" color="blue" icon="add" value="Add Consent"
	onclick="$('dummy-genericConsent').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(genericConsentRowInserterProps)" />
    <br></div>
	</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />
</form:form>

<!-- DUMMY SECIION START -->

<div id="dummy-genericConsent" style="display: none"></div>

<div id="dummy-consentVersion" style="display: none">
<table id="consentVersion" class="tablecontent" width="50%">
	<tr>
		<td valign="top">
			<input type="text" size="25" name="study.consents[PAGE.ROW.INDEX].consentVersions[NESTED.PAGE.ROW.INDEX].name" class="validate-notEmpty" />
		</td>
		<td valign="top">
		 	<input type="text" id="study.consents[PAGE.ROW.INDEX].consentVersions[NESTED.PAGE.ROW.INDEX].effectiveDate" name="study.consents[PAGE.ROW.INDEX].consentVersions[NESTED.PAGE.ROW.INDEX].effectiveDate" class="date validate-DATE&&notEmpty" size="18"/>
		 	    <a href="#" id="study.consents[PAGE.ROW.INDEX].consentVersions[NESTED.PAGE.ROW.INDEX].effectiveDate-calbutton">
                    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
                </a>
			
		</td>
		<td valign="top" align="left"><a
			href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(genericConsentRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>

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
				<tr>
					<td colspan="3" align="left">
					<table id="consentVersion" class="tablecontent">
						<tr id="h-PAGE.ROW.INDEX">
							<th><span class=""><tags:requiredIndicator /><fmt:message key="study.consent.consentVersion.name"/></span>
								<tags:hoverHint id="study.consent.consentVersion.name-PAGE.ROW.INDEX" keyProp="study.consent.consentVersion.name" /></th>
							<th>
								<fmt:message key="study.consent.consentVersion.date"/>
								<tags:hoverHint id="study.consent.consentVersion.date-PAGE.ROW.INDEX" keyProp="study.consent.consentVersion.date" />
							</th>
							<th></th>
						</tr>
						<tr>
			      			<td  colspan="3" align="left" id="addConsentVersionMessage-PAGE.ROW.INDEX"><fmt:message key="study.consent.addConsentVersion"/></td>
			      		</tr>
					</table>
					<br>
					<tags:button id="addConsentVersion-PAGE.ROW.INDEX" type="button" color="blue" icon="add" value="Add Consent Version"
					onclick="$('addConsentVersionMessage-PAGE.ROW.INDEX').hide();javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericConsentRowInserterProps,PAGE.ROW.INDEX));" size="small"/>
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