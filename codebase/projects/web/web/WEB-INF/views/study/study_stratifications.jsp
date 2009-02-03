<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<script>
	//firstVisit - global var	           
    var firstVisit = true;
	function preProcessGenerateGroups(epochCountIndex){
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
		var isBook = ${isBookRandomized};	
		//gettting the epochIndex from the table id.
		var id = e.id;
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
	</script>
</head>

<body>
<tags:instructions code="study_stratifications" />
<c:choose>
<c:when test="${command.study.stratificationIndicator =='false' }">
			<tags:formPanelBox tab="${tab}" flow="${flow}"><br/><br><div align="center"><fmt:message key="STUDY.NO_STRATIFICATION"/></div><br><br>
			</tags:formPanelBox>
	</c:when>
<c:otherwise>
<form:form method="post" name="form">
	<tags:tabFields tab="${tab}" />
	<div><input type="hidden" id="_action" name="_action" value=""> 
		<input type="hidden" id="_selectedEpoch" name="_selectedEpoch" value=""> 
		<input	type="hidden" id="_selectedStratification" name="_selectedStratification" value=""> 
		
		
		<input type="hidden" id="_selectedAnswer" name="_selectedAnswer" value="">
		<input type="hidden" id="generateGroups" name="generateGroups" value="false"/>
	</div>
	<c:if test="${command.study.stratificationIndicator}">
	<c:forEach items="${command.study.epochs}" var="epoch"
		varStatus="epochCount">
		<c:if test="${epoch.stratificationIndicator == 'true' }">
		<script>
            var startAnsRowInserterProps_${epochCount.index}= {
                add_row_division_id: "table1",
                skeleton_row_division_id: "dummy-strat-ans-${epochCount.index }",
                initialIndex: 2,
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                callRemoveFromCommand:"true",
                row_index_indicator: "NESTED.PAGE.ROW.INDEX",
                path: "study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers",
                epochCountIndex: ${epochCount.index},
                deleteMsgPrefix: ${isBookRandomized == 'true'}? "Stratum Groups and Book Randomization Entries(if any) will be deleted." : "Stratum Groups(if any) will be deleted.",
                onDeleteFromCommandSuccess: function(t){
                	var sgdiv = document.getElementById("sgCombinations_"+${epochCount.index});
                	sgdiv.innerHTML = "Generate Stratum Groups again.";
                },
			    postProcessRowInsertion: function(object){
	                clear(object.epochCountIndex );                	
			    }
            };
            var stratRowInserterProps_${epochCount.index} = {
                nested_row_inserter: startAnsRowInserterProps_${epochCount.index},
                add_row_division_id: "epoch-${epochCount.index }",
                skeleton_row_division_id: "dummy-strat-${epochCount.index}",
                initialIndex: ${fn:length(command.study.epochs[epochCount.index].stratificationCriteria)},
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                callRemoveFromCommand:"true",
                path: "study.epochs[${epochCount.index }].stratificationCriteria",
                epochCountIndex: ${epochCount.index},
                deleteMsgPrefix: ${isBookRandomized == 'true'}? "Stratum Groups and Book Randomization Entries(if any) will be deleted." : "Stratum Groups(if any) will be deleted.",
                onDeleteFromCommandSuccess: function(t){
                	var sgdiv = document.getElementById("sgCombinations_"+${epochCount.index});
                	sgdiv.innerHTML = "Generate Stratum Groups again.";
                },
                postProcessRowInsertion: function(object){
                	clear(object.epochCountIndex);
			    }
            };
            RowManager.addRowInseter(stratRowInserterProps_${epochCount.index});
            RowManager.registerRowInserters();
        </script>
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
		<br/>
		
		<chrome:division title="Questions and Answers">
		<br/>
			<table id="epoch-${epochCount.index}" class="tablecontent">
			<input type="hidden" name="epochCountIndex" value="${epochCount.index}"/>
			<div id="criteriaHeader">
				 <tr id="hInclusionEligibility--${epochCount.index}" <c:if test="${fn:length(epoch.stratificationCriteria) == 0}">style="display:none;"</c:if>>					
					<th><tags:requiredIndicator />Question<tags:hoverHint id="study.treatmentEpochs.stratificationCriteria-${epochCount.index}" keyProp="study.treatmentEpochs.stratificationCriteria"/></th>
					<th><tags:requiredIndicator />Answer<tags:hoverHint id="study.treatmentEpochs.stratificationCriteria.permissibleAnswers-${epochCount.index}" keyProp="study.treatmentEpochs.stratificationCriteria.permissibleAnswers"/></th>
					<th></th>
				</tr>
			</div>
				<c:forEach
					items="${command.study.epochs[epochCount.index].stratificationCriteria}"
					var="strat" varStatus="status">
					<c:if test="${epoch.stratificationIndicator == 'true' }">
					<script>
                        RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}).updateIndex(${fn:length(command.study.epochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers)});
                    </script>
					<tr id="epoch-${epochCount.index }-${status.index }">						
						<td><form:textarea
							path="study.epochs[${epochCount.index }].stratificationCriteria[${status.index}].questionText"
							rows="1" cols="60" cssClass="validate-notEmpty" /></td>

						<td>
							<table class="tablecontent" id="table1" width="50%">
								<tr>
									<th></th>
									<th><input type="button" value="Add Answer"
										onclick="if(stratumGroupAlert('${epochCount.index}', '${isBookRandomized}')){RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}));}" />
										</th>
								</tr>
								<c:forEach var="answer" varStatus="statusAns"
									items="${command.study.epochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers}">
									<c:if test="${epoch.stratificationIndicator == 'true' }">
									<tr id="table1-${statusAns.index }">
										<td class="alt"><form:input
											path="study.epochs[${epochCount.index }].stratificationCriteria[${status.index}].permissibleAnswers[${statusAns.index}].permissibleAnswer"
											size="30" cssClass="validate-notEmpty" /></td>
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
						</td>
						<td class="alt"><a
							href="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},${status.index},'${strat.id==null?'HC#':'ID#'}${strat.id==null?strat.hashCode:strat.id}');">
						<img src="<tags:imageUrl name="checkno.gif"/>" border="0"
							alt="Delete"></a></td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
			<br>
			<div align="right"><input type="button"
				value="Add Stratification Factor"
				onclick="$('hInclusionEligibility--${epochCount.index}').show();if(stratumGroupAlert('${epochCount.index}','${isBookRandomized}')){RowManager.addRow(stratRowInserterProps_${epochCount.index});}" />
			</div>
			</chrome:division>
			<br/>
			<jsp:include page="../study/asynchronous/reordered_strat_combinations.jsp">
				<jsp:param name="epochCountIndex" value="${epochCount.index}" />
			</jsp:include>
			<div id="stratumButton" align="right">
				<input type='submit' onClick="preProcessGenerateGroups(${epochCount.index})" value='Generate Stratum Groups' />
			</div>
		</tags:minimizablePanelBox>
		</c:if>
	</c:forEach>
	</c:if>

	<input type="hidden" name="flowType" value="${flowType}">
	<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}" />
</form:form>


<c:forEach items="${command.study.epochs}" var="epoch"
	varStatus="epochCount">
	<c:if test="${epoch.stratificationIndicator == 'true' }">
	<div id="dummy-strat-${epochCount.index }" style="display:none">
	<table>
		<tr>
			
			<td><input type="hidden"
				name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionNumber" />
			<textarea
				name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionText"
				rows="1" cols="60" class="validate-notEmpty"></textarea></td>

			<td>
			<table class="tablecontent" id="table1" width="50%">
				<tr>
					<th></th>
					<th><input type="button" value="Add Answer"
						onclick="stratumGroupAlert('${fn:length(command.study.epochs[epochCount.index].stratumGroups)}','${isBookRandomized}');RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX));" />
					</th>
				</tr>
				<tr id="table1-0">
					<td><input type="text"
						name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[0].permissibleAnswer"
						size="30" class="validate-notEmpty" /></td>
				</tr>
				<tr id="table1-1">
					<td><input type="text"
						name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[1].permissibleAnswer"
						size="30" class="validate-notEmpty" /></td>
				</tr>
			</table>
			</td>
			<td><a
				href="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX,-1);"
				onclick="stratumGroupAlert('${fn:length(command.study.epochs[epochCount.index].stratumGroups)}','${isBookRandomized}');">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"
				alt="Delete"></a></td>
		</tr>
	</table>
	</div>
	<div id="dummy-strat-ans-${epochCount.index }" style="display:none">
	<table>
		<tr>
			<td><input type="text"
				name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[NESTED.PAGE.ROW.INDEX].permissibleAnswer"
				size="30" class="validate-notEmpty" /></td>
			<td><a
				href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);"
				onclick="stratumGroupAlert('${fn:length(command.study.epochs[epochCount.index].stratumGroups)}','${isBookRandomized}');">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
	
	<div id="dummy-strat-strGrp-${epochCount.index }" style="display:none">
	<table>
		<tr>
			<td><input type="text"
				name="study.epochs[${epochCount.index}].stratumGroups[PAGE.ROW.INDEX].stratumGroupNumber"
				size="30" class="validate-notEmpty" /></td>
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
</body>
</html>
