<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<!--[if lte IE 7]>
<style>
/* IE hasLayout hack for dissapearing content on this page */
		*{zoom:1}
		#taskbar {
			margin-left:-230px;
		}
		#workflow-tabs {
			top:-25px;
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
<script>
	//firstVisit - global var	           
    var firstVisit = true;
	function preProcessGenerateGroups(epochCountIndex, epochId){
		$('epochId').value=epochId ;
		var x = document.getElementById('generateGroups');
		x.value=epochCountIndex;
		var y = document.getElementById('_target');
		y.name = 'abc';
	}
	     
	function clear(epochCountIndex){
		<tags:tabMethod method="clearBookEntriesAndStratumGroups" viewName="/study/asynchronous/strat_combinations" divElement="'sgCombinations_'+epochCountIndex" 
		                javaScriptParam="'epochCountIndex=' + epochCountIndex" formName="'tabMethodForm'"/> 
	}

	function postProcessDragDrop(e){  
		//gettting the epochIndex from the table id.
		var id = e.id;
		updateStartumGroups(id);
	}

	function updateStartumGroups(id){
		var isBook = ${isBookRandomized};
		var pos = id.indexOf("_");
		var epochCountIndex = id.substring(pos + 1, id.length);
		var answer;
		if(firstVisit && isBook){
		  //only show the reorder confirm pop-up the first time.
		  answer = confirm("Book Randomization Entries(if any) will be deleted. Do you want to proceed?");
		  new Element.show('reorderGroupsInd-'+epochCountIndex);
		  firstVisit = false;
		} else{
		  answer = true;
		}
		
		if(answer){
			var serializedString = escape(Sortable.serialize('sortablelist_'+epochCountIndex));
			<tags:tabMethod method="reorderStratumGroups" viewName="/study/asynchronous/reordered_strat_combinations" divElement="'sgCombinations_'+epochCountIndex" 
							javaScriptParam="'serializedString='+serializedString+ '&epochCountIndex=' + epochCountIndex" formName="'tabMethodForm'"/>
		} else {
		    //calling the reorderStratumGroups mthod without the serializedString so that the groups are re-loaded with orignal values
		    //doing this as the revert option in the Sortable.create does not work
		    var serializedString = '';
			<tags:tabMethod method="reorderStratumGroups" viewName="/study/asynchronous/reordered_strat_combinations" divElement="'sgCombinations_'+epochCountIndex" 
							javaScriptParam="'serializedString='+serializedString+ '&epochCountIndex=' + epochCountIndex" formName="'tabMethodForm'"/>
		}
	}
	
	function deleteStratumGroup(epochId, stratumGroupId){
		alertMessage="Are you sure you want to delete this stratum group?";
		if(${isBookRandomized}){
			alertMessage= "Book Randomization Entries(if any) will be deleted. " + alertMessage;
		}
		${isBookRandomized == 'true'}? "Book Randomization Entries(if any) will be deleted." : " "
		Dialog.confirm(alertMessage, {className: "alphacube", width:400, cancel:function(win) {win.close()}, ok:function(win) {
																											$('stratumGroupId').value= stratumGroupId;
																											$('epochId').value=epochId;
																											$('_target').name="abc";
																											$('command').submit();
																									  	}
																				}); 
	}

	function stratumGroupAlert(epochCountIndex, isBookRandomized){
		var table = document.getElementById('sgCombinationsTable_'+epochCountIndex);
		var length = 0;
		if(table != null){
			length = table.rows.length;		
		}
		if(length > 0){
			if(isBookRandomized == 'true'){
				return confirm("Stratum Groups and Book Randomization Entries(if any) will be deleted. Do you want to proceed?");			
			} else{
				return confirm("Stratum Groups will be deleted. Do you want to proceed?");
			}
		} else {
			return true;
		}
	}
    
    function disableQuestionSection(id){
		el = $('stratificationQuestions-'+id);
		toggleDisabled(el);
 	}

    function enableQuestionSection(id){
		e1 = $('stratificationQuestions-'+id);
		toggleEnabled(e1);
 	}
    
    function toggleDisabled(el) {
    	el.descendants().each(function(e){
			e.disabled="true";
			e.style.opacity='0.95';
			if(e.tagName == 'A'){
				e.__href=e.href;
				e.href="#";
			}
			}
		);
    }

    function toggleEnabled(el) {
    	el.descendants().each(function(e){
    		e.removeAttribute('disabled');
    		e.style.opacity='1';
    		if(e.tagName == 'A'){
    			e.href=e.__href;
    			
        	}
			}
		);
    }

    function editStratificationCriteria(epochCountIndex, isBook, id){
    	$('stratificationIndicator-'+epochCountIndex).show();
        if(stratumGroupAlert(epochCountIndex, isBook)){
        	$('editStratificationCriteria-'+id).hide(); 
        	$('addStratificationCriteria-'+id).show(); 
			clear(epochCountIndex);
			enableQuestionSection(id);
        }
		$('stratificationIndicator-'+epochCountIndex).hide();
     }


	function moveElementUpforList(list, key) {
		var sequence=Sortable.sequence(list);
		var newsequence=[];
		var reordered=false;
		if (sequence.length>1) for (var j=0; j<sequence.length; j++) {
			if (j>0 && sequence[j].length>0 && sequence[j]==key) {
				var temp=newsequence[j-1];
				newsequence[j-1]=key;
				newsequence[j]=temp;
				reordered=true;
			}
			else {
				newsequence[j]=sequence[j];
			}
		}

		if (reordered){ 
			Sortable.setSequence(list,newsequence);
		}

		return reordered;
	}

	function moveElementDownforList(list, key) {
		var sequence=Sortable.sequence(list);
		var newsequence=[];
		var reordered=false;
		if (sequence.length>1) for (var j=0; j<sequence.length; j++) {
			if (j<(sequence.length-1) && sequence[j].length>0 && sequence[j]==key) {
				newsequence[j+1]=key;
				newsequence[j]=sequence[j+1];
				reordered=true;
				j++;
			}
			else {
				newsequence[j]=sequence[j];
			}
		}

		if (reordered){
			 Sortable.setSequence(list,newsequence);
		}
		return reordered;
	}

	</script>
</head>

<body>
<c:choose>
	<c:when test="${!command.study.stratificationIndicator}">
		<!--  No instruction necessary -->
	</c:when>
		<c:when test="${fn:length(command.study.epochs) == 0}">
		<tags:instructions code="study_no_epoch_stratification" />
	</c:when>
	<c:when test="${command.study.hasStratifiedEpoch}">
		<tags:instructions code="study_stratifications" />
	</c:when>
	<c:otherwise>
		<tags:instructions code="study_no_stratified_epoch" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${command.study.stratificationIndicator =='false' }">
		<form:form method="post" name="studyDetails" cssClass="standard" >
			<tags:tabFields tab="${tab}" />
			<chrome:box title="${tab.shortTitle}">
					<br/><br><div align="center"><fmt:message key="STUDY.NO_STRATIFICATION"/></div><br><br>
			</chrome:box>
			<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}">
				<jsp:attribute name="localButtons">
					<c:if test="${!empty param.parentStudyFlow}">
					<tags:button type="button" color="blue" icon="back" value="Return to parent" onclick="returnToParentUsingButton('${param.parentStudyFlow}', '${command.study.parentStudyAssociations[0].parentStudy.id}')" />
					</c:if>
				</jsp:attribute>
			</tags:tabControls>
		</form:form>
	</c:when>
	<c:when test="${!command.hasStratifiedEpoch}">
		<form:form method="post" name="studyDetails" cssClass="standard" >
			<tags:tabFields tab="${tab}" />
				<chrome:box title="${tab.shortTitle}">
				<br/><br><div align="center">
				<div id="flash-message" class="error"><img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align: top;" /> 
					<fmt:message key="STUDY.NO_STRATIFICATION_FOR_EPOCH" />
				</div>
				<br><br></div>
				</chrome:box>
			<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}">
				<jsp:attribute name="localButtons">
					<c:if test="${!empty param.parentStudyFlow}">
					<tags:button type="button" color="blue" icon="back" value="Return to parent" onclick="returnToParentUsingButton('${param.parentStudyFlow}', '${command.study.parentStudyAssociations[0].parentStudy.id}')" />
					</c:if>
				</jsp:attribute>
			</tags:tabControls>
		</form:form>
	</c:when>
	<c:otherwise>
<form:form method="post" name="form">
	<tags:tabFields tab="${tab}" />
	
	<div>
		<input type="hidden" id="_action" name="_action" value=""> 
		<input type="hidden" id="_selectedEpoch" name="_selectedEpoch" value=""> 
		<input type="hidden" id="_selectedStratification" name="_selectedStratification" value=""> 
		<input type="hidden" id="_selectedAnswer" name="_selectedAnswer" value="">
		<input type="hidden" id="generateGroups" name="generateGroups" value="false"/>
		<input type="hidden" id="epochId" name="epochId"/>
		<input type="hidden" id="stratumGroupId" name="stratumGroupId"/>
	</div>
	
	<c:if test="${command.study.stratificationIndicator}">
	
	<c:forEach items="${command.study.epochs}" var="epoch" varStatus="epochCount">
		<c:if test="${epoch.stratificationIndicator == 'true' }">
		
		<script>
            var stratAnsRowInserterProps_${epochCount.index}= {
                add_row_division_id: "table1",
                skeleton_row_division_id: "dummy-strat-ans-${epochCount.index }",
                initialIndex: 2,
                softDelete: ${softDelete == 'true'},
                row_index_indicator: "NESTED.PAGE.ROW.INDEX",
                path: "study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers"
            };
            var stratRowInserterProps_${epochCount.index} = {
                nested_row_inserter: stratAnsRowInserterProps_${epochCount.index},
                add_row_division_id: "epoch-${epochCount.index }",
                skeleton_row_division_id: "dummy-strat-${epochCount.index}",
                initialIndex: ${fn:length(command.study.epochs[epochCount.index].stratificationCriteria)},
                softDelete: ${softDelete == 'true'},
                path: "study.epochs[${epochCount.index }].stratificationCriteria"
            };	
            RowManager.addRowInseter(stratRowInserterProps_${epochCount.index});
            RowManager.registerRowInserters();

            function updateName(divID, stringValue) {
        	    if ($(divID)) {
            	    if(stringValue.length > 55){
						newvalue = stringValue.substring(0,55) + "..........";
            	    	$(divID).innerHTML = newvalue;
                	}else{
        	        	$(divID).innerHTML = stringValue;
                	}
        	    }
        	}
        </script>
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
			<div id="stratificationQuestions-${epoch.id}">
			<table id="epoch-${epochCount.index}" class="">
			<input type="hidden" name="epochCountIndex" value="${epochCount.index}"/>
			<tr></tr>
			<c:forEach items="${command.study.epochs[epochCount.index].stratificationCriteria}" var="strat" varStatus="status">
					<c:if test="${epoch.stratificationIndicator == 'true' }">
					<script>
                        RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}).updateIndex(${fn:length(command.study.epochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers)});
                    </script>
					<tr id="epoch-${epochCount.index }-${status.index}">
						<td>
							<c:choose>
								<c:when test="${fn:length(epoch.stratificationCriteria[status.index].questionText) > 55}">
									<c:set var="questionText" value="${fn:substring(epoch.stratificationCriteria[status.index].questionText,0,55)}.........."></c:set>
								</c:when>
								<c:otherwise>
										<c:set var="questionText" value="${epoch.stratificationCriteria[status.index].questionText}"></c:set>
								</c:otherwise>
							</c:choose>
							<chrome:deletableDivision id="question-${epochCount.index }-${status.index }" divTitle="questionTitle-${epochCount.index }-${status.index }" onclick="RowManager.deleteRow(stratRowInserterProps_${epochCount.index},${status.index},'${strat.id==null?'HC#':'ID#'}${strat.id==null?strat.hashCode:strat.id}');" 
                    		title="Question : ${questionText}" >
                    		<table style="border: 0px red dotted;" width="100%">
                    		<tr>	
								<td valign="top">
									<table class=""  width="50%">
										<tr><td><b><fmt:message key="study.question"/></b></td></tr>
										<tr><td>
											<form:textarea path="study.epochs[${epochCount.index }].stratificationCriteria[${status.index}].questionText"
										rows="2" cols="60" cssClass="required validate-notEmpty$$MAXLENGTH200" onkeyup="updateName('questionTitle-${epochCount.index }-${status.index }', 'Question: ' + this.value);"/>
										</td></tr>
									</table>
								</td>
								<td>
									<table class="" id="table1" width="50%">
										
										<c:forEach var="answer" varStatus="statusAns"
											items="${command.study.epochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers}">
											<c:if test="${epoch.stratificationIndicator == 'true' }">
											<tr id="table1-${statusAns.index }">
												<td class="alt"><form:input
													path="study.epochs[${epochCount.index }].stratificationCriteria[${status.index}].permissibleAnswers[${statusAns.index}].permissibleAnswer"
													size="30" cssClass="required validate-notEmpty$$MAXLENGTH200" /></td>
												<c:choose>
													<c:when test="${statusAns.index < 2}">
													</c:when>
													<c:otherwise>
														<td class="alt"><a
															href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}),${statusAns.index },'${answer.id==null?'HC#':'ID#'}${answer.id==null?answer.hashCode:answer.id}');">
														<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
													</c:otherwise>
												</c:choose>
											</tr>
											</c:if>
										</c:forEach>
										
											
									</table>
									<span style="margin-left:2px">
									<tags:button type="button" color="blue" icon="add" value="Add Answer" 
													onclick="RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}));" size="small"/>
											</span>
								</td>
							</tr>
                    		</table>											
							</chrome:deletableDivision>
							<hr noshade size="1" width="100%" style="border-top: 1px black dotted;" align="left">
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
			</div>
			<br>
			<div align="left">
				<span id="addStratificationCriteria-${epoch.id}" <c:if test="${fn:length(epoch.stratumGroups) > 0 }"> style = "display:none" </c:if> >
					<tags:button type="button" color="blue" icon="add" value="Add Stratification Criterion" onclick="$('stratumButton-${epoch.id}').show();RowManager.addRow(stratRowInserterProps_${epochCount.index});" size="small"/>
				</span>
				<span id="editStratificationCriteria-${epoch.id}" <c:if test="${fn:length(epoch.stratumGroups) == 0 }"> style = "display:none" </c:if> >
					<tags:button type="button" icon="edit" color="blue" value="Edit Stratification Criteria" onclick="editStratificationCriteria('${epochCount.index}','${isBookRandomized}', '${epoch.id}');" size="small"/>
				</span>
				<span id="stratumButton-${epoch.id}" <c:if test="${fn:length(epoch.stratificationCriteria) == 0}"> style = "display:none" </c:if> >
					<tags:button type="submit" color="blue" icon="check" value="Generate Stratum Groups" onclick="$('stratificationIndicator-${epochCount.index }').show();preProcessGenerateGroups(${epochCount.index}, ${epoch.id});" size="small"/>
				</span>
				<img id="stratificationIndicator-${epochCount.index }" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
			</div>
			<div id="stratificationGroup-${epoch.id}" <c:if test="${fn:length(epoch.stratumGroups) == 0 }"> style = "display:none" </c:if>>
			<jsp:include page="../study/asynchronous/reordered_strat_combinations.jsp">
				<jsp:param name="epochCountIndex" value="${epochCount.index}" />
			</jsp:include>
			</div>
			
		</tags:minimizablePanelBox>
		<c:if test="${fn:length(epoch.stratumGroups) > 0 }">
			<script>
				disableQuestionSection('${epoch.id}');
			</script>
		</c:if>
		</c:if>
	</c:forEach>
	</c:if>
    
	<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}">
			<jsp:attribute name="localButtons">
			<c:if test="${!empty param.parentStudyFlow}">
			<tags:button type="button" color="blue" icon="back" value="Return to parent" onclick="returnToParentUsingButton('${param.parentStudyFlow}', '${command.study.parentStudyAssociations[0].parentStudy.id}')" />
			</c:if>
		</jsp:attribute>
</tags:tabControls>
	
</form:form>
<c:forEach items="${command.study.epochs}" var="epoch" varStatus="epochCount">
	<c:if test="${epoch.stratificationIndicator == 'true' }">
	<div id="dummy-strat-${epochCount.index }" style="display:none">
	<table>
		<tr>
			<td>
			<chrome:deletableDivision id="question-${epochCount.index }-PAGE.ROW.INDEX" divTitle="questionTitle-${epochCount.index }-PAGE.ROW.INDEX" onclick="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX,-1);" 
                    		title="Question : ${epoch.stratificationCriteria[status.index].questionText}" >
            	<input type="hidden" name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionNumber" />
            	<table style="border: 0px red dotted;" width="100%">
            	<tr>	
					<td>
						<table class=""  width="50%">
							<tr><td><b><fmt:message key="study.question"/></b></td></tr>
							<tr><td>
								<TEXTAREA name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionText" rows="2" cols="60" class="required validate-notEmpty$$MAXLENGTH200" onkeyup="updateName('questionTitle-${epochCount.index }-PAGE.ROW.INDEX', 'Question: ' + this.value);" ></TEXTAREA>
							</td></tr>
						</table>
					</td>
          			<td>
						<table class="" id="table1" width="50%">
							<tr>
								<td>
								<tags:button type="button" color="blue" icon="add" value="Add Answer" 
									onclick="RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX));" size="small"/>
								</td>
							</tr>
							<tr id="table1-0">
								<td><input type="text"
									name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[0].permissibleAnswer"
									size="30" class="required validate-notEmpty$$MAXLENGTH200" /></td>
							</tr>
							<tr id="table1-1">
								<td><input type="text"
									name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[1].permissibleAnswer"
									size="30" class="required validate-notEmpty$$MAXLENGTH200" /></td>
							</tr>
						</table>
					</td>
                </tr>
                </table>
            </chrome:deletableDivision>
            <hr noshade size="1" width="100%" style="border-top: 1px black dotted;" align="left">
			</td>
		</tr>
	</table>
	</div>
	<div id="dummy-strat-ans-${epochCount.index }" style="display:none">
	<table>
		<tr>
			<td><input type="text"
				name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[NESTED.PAGE.ROW.INDEX].permissibleAnswer"
				size="30" class="required validate-notEmpty$$MAXLENGTH200" /></td>
			<td><a
				href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
	
	<div id="dummy-strat-strGrp-${epochCount.index }" style="display:none">
	<table>
		<tr>
			<td><input type="text"
				name="study.epochs[${epochCount.index}].stratumGroups[PAGE.ROW.INDEX].stratumGroupNumber"
				size="30" class="required validate-notEmpty" /></td>
				<td></td>
			<td><a
				href="javascript:RowManager.deleteRow(sratumGroupRowInserter_${epochCount.index},PAGE.ROW.INDEX,-1);">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
	</c:if>
</c:forEach>
</c:otherwise>
</c:choose>
<script>
	$('stratumButton-'+${epochId}).scrollIntoView();
</script>
</body>
</html>
