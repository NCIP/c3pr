<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
    <link rel="stylesheet" type="text/css" href="/c3pr/public/css/tables1" />
    <script type="text/javascript">
    function updateName(divID, stringValue) {
        if ($(divID)) {
            $(divID).innerHTML = stringValue;
        }
    }
    var consentQuestionInserterProps= {
            add_row_division_id: "question",
            skeleton_row_division_id: "dummy-row-consent-question",
            initialIndex: ${fn:length(command.study.consents[consentCount.index].questions)},
            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
            path: "consents[PAGE.ROW.INDEX].questions"
        };
    var consentRowInserterProps = {
    		nested_row_inserter: consentQuestionInserterProps,
    	    add_row_division_id: "mainConsentTable", 	        /* this id belongs to element where the row would be appended to */
    	    skeleton_row_division_id: "dummy-row-consent",
    	    initialIndex: ${fn:length(command.study.consents)},                            /* this is the initial count of the rows when the page is loaded  */
    	    path: "study.consents"                               /* this is the path of the collection that holds the rows  */
    	};

    RowManager.addRowInseter(consentRowInserterProps);
    RowManager.registerRowInserters();
    function addConsent(){
    	if(${fn:length(command.study.consents) == 0}){
    		$('addconsentsMessage').hide();
    	}
    	RowManager.addRow(consentRowInserterProps);
    }

    function addConsentQuestion(consentIndex){
        questionId = "addQuestionMessage-" + consentIndex;
        if($(questionId) !=null){
    		$(questionId).hide();
        }
    	RowManager.addRow(RowManager.getNestedRowInserter(consentRowInserterProps,consentIndex));
    }
    
    function handleMandatoryIndicator(){
    	var consentRequired = $('study.consentRequired').value;
    	if(consentRequired == 'ALL'){
    		$$('form .mandatoryIndicator').each(function(e){
    			e.value=true;
    		});
    	}else if(consentRequired == 'NONE' || consentRequired == 'ONE' ){
    		$$('form .mandatoryIndicator').each(function(e){
    			e.value=false;
    		});
   		}
    }
	
    
	</script>
</head>
<body>

<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" formName="consentForm" displayErrors="false">
	<jsp:attribute name="singleFields">
	<tags:instructions code="study_consents" />
	<tags:errors path="*" />
	<chrome:division>
		<div class="row">
			<div class="label"><tags:requiredIndicator/><fmt:message key="study.consentRequired"/></div>
			<div class="value">
				  <form:select path="study.consentRequired" id= "study.consentRequired" cssClass="required validate-notEmpty" 
				  				onchange="javascript:handleMandatoryIndicator()">
				  	<form:options items="${consentRequired}" itemLabel="desc" itemValue="code" />
				  </form:select>
				  <tags:hoverHint keyProp="study.consentRequired" />
			</div>
		</div>
	</chrome:division>
<br>
<!-- CONSENT TABLE START -->
<table id="mainConsentTable" width="100%" border="0">
<tr></tr>
	<c:choose>
		<c:when test="${fn:length(command.study.consents) == 0}">
			<tr>
				<td id="addconsentsMessage"><fmt:message key="study.noConsents" /></td>
			</tr>
		</c:when>
		<c:otherwise>
		    <c:forEach items="${command.study.consents}" var="consent"  varStatus="consentCount">
		    	 <script type="text/javascript">
                	RowManager.getNestedRowInserter(consentRowInserterProps,${consentCount.index}).updateIndex(${fn:length(command.study.consents[consentCount.index].questions)});
            	</script>
		    	 <tr id="genericConsent-${consentCount.index}">
		            <td>
		    	<chrome:deletableDivision divTitle="genericTitle-${consentCount.index}" id="mainConsentTable-${consentCount.index}"
						title="Consent: ${command.study.consents[consentCount.index].name}" minimize="true" divIdToBeMinimized="consent-${consentCount.index}"
							onclick="RowManager.deleteRow(consentRowInserterProps,${consentCount.index},'${consent.id==null?'HC#':'ID#'}${consent.id==null?consent.hashCode:consent.id}')">
							 <!-- SINGLE CONSENT TABLE START-->
			    		
			    		<div id="consent-${consentCount.index}" style="display: none">
			    		<table width="100%" border="0">
						<tr>
	 					<td valign="top">
			    		<table id="consent" width="52%"cellspacing="4" cellpadding="2">
			    			<tr>
								<td align="right"><tags:requiredIndicator/><b><fmt:message key="study.consentName"/></b>
									<tags:hoverHint id="study.consent.name-${consentCount.index}" keyProp="study.consent.name" /></td>
								<td align="left"><form:input path="study.consents[${consentCount.index}].name" size="45" cssClass="required validate-notEmpty"
												onkeyup="updateName('genericTitle-${consentCount.index}', 'Consent: ' + this.value);" />
								</td>
							</tr>
			    		
							<tr>
								<td align="right"><b><fmt:message key="study.consentVersion"/></b>
									<tags:hoverHint id="study.consent.versionId-${consentCount.index}" keyProp="study.consent.versionId" /></td>
								<td align="left"><form:input path="study.consents[${consentCount.index}].versionId" size="30" />
				  				</td>
							</tr>
							
					    	<tr>
					    		<td align="right"><tags:requiredIndicator/><b><fmt:message key="study.consentMandatoryIndicator"/></b>
									<tags:hoverHint id="study.consent.mandatoryIndicator-${consentCount.index}" keyProp="study.consent.mandatoryIndicator" />
								</td>
								<td align="left"><form:select path="study.consents[${consentCount.index}].mandatoryIndicator"  
										cssClass="required validate-notEmpty mandatoryIndicator">
									<option value="">Please Select</option>
				          			<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
									</form:select>
								</td>
							</tr>
							<tr>
					    		<td align="right"><tags:requiredIndicator/><b><fmt:message key="study.consentingMethods"/></b>
									<tags:hoverHint id="study.consentingMethods-${consentCount.index}" keyProp="study.consentingMethods" />
								</td>
								<td><table width="70%"><tr>
									<td align="left"><input type="checkbox" id="consentingMethod-${consentCount.index}_written" 
										name="study.consents[${consentCount.index}].consentingMethods" 
										value="WRITTEN" <c:if test="${c3pr:containsConsentingMethod(command.study.consents[consentCount.index].consentingMethods, 'Written')}"> checked </c:if> /> Written 
									</td>
									<td align="left"><input type="checkbox" id="consentingMethod-${consentCount.index}_verbal" 
										 name="study.consents[${consentCount.index}].consentingMethods" 
										value="VERBAL" <c:if test="${c3pr:containsConsentingMethod(command.study.consents[consentCount.index].consentingMethods, 'Verbal')}"> checked </c:if> /> Verbal
									</td>
								</tr></table></td>
							</tr>
							
							 <tr bgcolor="eeffee">
									  <td align="left" colspan="2">
									      <table id="question" class="tablecontent" border="0">
										      <tr id="h-${consentCount.index}" >
										      	   <th>
										          	<fmt:message key="study.consent.question.code"/>
										          	<tags:hoverHint id="study.consent.question.code-${consentCount.index}" keyProp="study.consent.question.code" />
										          </th>
										          <th>
										          	<fmt:message key="study.consent.question.text"/>
										          	<tags:hoverHint id="study.consent.question.text-${consentCount.index}" keyProp="study.consent.question.text" />
										          </th>
										          <th></th>
										      </tr>
										      <c:choose>
											      	<c:when test="${fn:length(consent.questions) == 0}">
											      		<tr>
											      			<td align="left" colspan="2" width="100%" id="addQuestionMessage-${consentCount.index}"><fmt:message key="study.consent.addQuestion"/></td>
											      		</tr>
											      	</c:when>
											      	<c:otherwise>
											      		<c:forEach items="${consent.questions}" var="question" varStatus="statusQuestions">
												            <tr id="question-${statusQuestions.index}">
												            	<td valign="top">
												                	<form:input path="study.consents[${consentCount.index}].questions[${statusQuestions.index}].code" size="10" 
												                	 cssClass="required validate-notEmpty" />
												                </td>
												                <td valign="top">
												                	<form:input path="study.consents[${consentCount.index}].questions[${statusQuestions.index}].text" size="110" 
												                	 cssClass="required validate-notEmpty" />
												                </td>
												                <td valign="top" align="left">
												                    <a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(consentRowInserterProps,${consentCount.index}),${statusQuestions.index },'${question.id==null?'HC#':'ID#'}${question.id==null?question.hashCode:question.id}');">
												                    	<img src="<tags:imageUrl name="checkno.gif"/>" border="0">
												                    </a>
												                </td>
												            </tr>
										            	</c:forEach>
										      		</c:otherwise>
									      		</c:choose>    
									      </table>
									      <div align="right">
									      	<tags:button id="addQuestion-${consentCount.index}" type="button" color="blue" icon="add" value="Add Question"
									      		onclick="addConsentQuestion(${consentCount.index});" size="small"/>
									 	 </div>
									  </td>
								</tr>    
					 	  </table>
					 	  </td></tr></table>
			 	    <!-- SINGLE CONSENT TABLE END-->
			 	    </div>
				</chrome:deletableDivision>
				</td></tr>
		    </c:forEach>
		</c:otherwise>
	</c:choose>
	</table>

<br>
<div align="right">
	<tags:button type="button" color="blue" icon="add" value="Add Consent" onclick="addConsent();" size="small"/>
    <br>
</div>
</jsp:attribute>
<jsp:attribute name="localButtons">
			<c:if test="${!empty param.parentStudyFlow}">
			<tags:button type="button" color="blue" icon="back" value="Return to parent" onclick="returnToParentUsingButton('${param.parentStudyFlow}', '${command.study.parentStudyAssociations[0].parentStudy.id}')" />
			</c:if>
</jsp:attribute>

</tags:tabForm>

<!-- DUMMY SECIION START -->
<div id="dummy-row-consent" style="display:none;">
	<table width="52%">
		<tr>
			<td>
				<chrome:deletableDivision divTitle="divConsentBox-PAGE.ROW.INDEX" id="consentBox-PAGE.ROW.INDEX" 
					title="Consent: "onclick="RowManager.deleteRow(consentRowInserterProps,PAGE.ROW.INDEX,-1)">
					<table>
							<tr>
								<td align="right"><tags:requiredIndicator/><b><fmt:message key="study.consentName"/></b>
									<tags:hoverHint id="study.consent.name-PAGE.ROW.INDEX" keyProp="study.consent.name" /></td>
									
								<td align="left"><input id="study.consents[PAGE.ROW.INDEX].name" name="study.consents[PAGE.ROW.INDEX].name" type="text" size="45" 
									onkeyup="updateName('divConsentBox-PAGE.ROW.INDEX', 'Consent: ' + this.value);" class="required validate-notEmpty" /></td>
							</tr>
							<tr>
								<td align="right"><b><fmt:message key="study.consentVersion"/></b>
									<tags:hoverHint id="study.consent.versionId-PAGE.ROW.INDEX" keyProp="study.consent.versionId" /></td>
								<td align="left"><input id="study.consents[PAGE.ROW.INDEX].versionId" name="study.consents[PAGE.ROW.INDEX].versionId" type="text" size="30" /></td>
							</tr>
							
							<tr>
								<td align="right"><tags:requiredIndicator/><b><fmt:message key="study.consentMandatoryIndicator"/></b>
									<tags:hoverHint id="study.consent.mandatoryIndicator-PAGE.ROW.INDEX" keyProp="study.consent.mandatoryIndicator" /></td>
								<td align="left">
									<select id="study.consents[PAGE.ROW.INDEX].mandatoryIndicator" name="study.consents[PAGE.ROW.INDEX].mandatoryIndicator" class="required validate-notEmpty mandatoryIndicator">
							                    <option value="">Please Select</option>
							                    <c:forEach items="${yesNo}" var="status">
							                        <option value="${status.code}">${status.desc}</option>
							                    </c:forEach>
							        </select>
							     </td>
							</tr>
							
							<tr>
					    		<td align="right"><tags:requiredIndicator/><b><fmt:message key="study.consentingMethods"/></b>
									<tags:hoverHint id="study.consentingMethods-PAGE.ROW.INDEX" keyProp="study.consentingMethods" />
								</td>
								<td><table width="50%"><tr>
									<td align="left"><input type="checkbox" id="consentingMethod-PAGE.ROW.INDEX_written" 
										name="study.consents[PAGE.ROW.INDEX].consentingMethods" value="WRITTEN" /> Written 
									</td>
									<td align="left"><input type="checkbox" id="consentingMethod-PAGE.ROW.INDEX_verbal" 
										 name="study.consents[PAGE.ROW.INDEX].consentingMethods" value="VERBAL" /> Verbal
									</td>
								</tr></table></td>
							</tr>
							  
							<tr>
								<td colspan="2" align="left">
									<table id="question" class="tablecontent">
										<tr id="h-PAGE.ROW.INDEX">
											<th> <fmt:message key="study.consent.question.code"/>
										          <tags:hoverHint id="study.consent.question.code-PAGE.ROW.INDEX" keyProp="study.consent.question.code" />
										    </th>
											<th><fmt:message key="study.consent.question.text"/><tags:hoverHint
												id="study.consent.question.text-PAGE.ROW.INDEX" keyProp="study.consent.question.text" /></th>
											<th></th>
										</tr>
										<tr>
							      			<td  align="left" width="100%" id="addQuestionMessage-PAGE.ROW.INDEX"><fmt:message key="study.consent.addQuestion"/></td>
							      		</tr>
									</table>
								</td>
							</tr>  
							<tr>
								<td colspan="2" align="right">
									<tags:button id="addQuestion-PAGE.ROW.INDEX" type="button" color="blue" icon="add" value="Add Question"
									      				onclick="addConsentQuestion(PAGE.ROW.INDEX);" size="small"/>
								</td>
							</tr>
						</table>
					</chrome:deletableDivision>
				</td>
			</tr>
		</table>
</div>
<div id="dummy-row-consent-question" style="display:none;">
<table id="question" class="tablecontent" width="50%">
	<tr>
		<td><input id="study.consents[PAGE.ROW.INDEX].questions[NESTED.PAGE.ROW.INDEX]" name="study.consents[PAGE.ROW.INDEX].questions[NESTED.PAGE.ROW.INDEX].code" type="text" size="10" class="required validate-notEmpty" /></td>
		<td><input id="study.consents[PAGE.ROW.INDEX].questions[NESTED.PAGE.ROW.INDEX]" name="study.consents[PAGE.ROW.INDEX].questions[NESTED.PAGE.ROW.INDEX].text" type="text" size="110" class="required validate-notEmpty" /></td>
		<td valign="top" align="left"><a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(consentRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>
<!-- DUMMY SECIION END -->
</body>
</html>
