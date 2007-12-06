<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<html>
<head>
<script>		           

	function preProcessGenerateGroups(epochCountIndex){
		var x = document.getElementById('generateGroups');
		x.value=epochCountIndex;
		var y = document.getElementById('_target');
		y.name = 'abc';
	}
	     
	function clear(epochCountIndex){
		<tags:tabMethod method="clearStratumGroups" viewName="/study/asynchronous/strat_combinations" divElement="'sgCombinations_'+epochCountIndex" 
		                javaScriptParam="'epochCountIndex='+epochCountIndex" formName="'tabMethodForm'"/> 
	}

	function postProcessDragDrop(e){  			
		//gettting the epochIndex from the table id.
		var id = e.id;
		var pos = id.indexOf("_");
		var epochCountIndex = id.substring(pos + 1, id.length);
		
		//calling the function that will rearrange the numbers against the stratum groups
		reorderStratumGroupNumbers(epochCountIndex);
		
		var serializedString = escape(Sortable.serialize('sortablelist_'+epochCountIndex));
		<tags:tabMethod method="reorderStratumGroups"  divElement="'dummyDiv'" 
						javaScriptParam="'serializedString='+serializedString" formName="'tabMethodForm'"/>
	}
			
	function reorderStratumGroupNumbers(epochCountIndex){
		var table = document.getElementById('sgCombinationsTable_'+epochCountIndex);
		var length = table.rows.length;
		var newIndex = 0;
		for(var i = 0; i < length; i++){
			if(table.rows[i].id.indexOf('deleted') == -1){
				table.rows[i].cells[0].innerHTML = newIndex;
				newIndex++;
			}
		}
	}
	
	function stratumGroupAlert(epochCountIndex){
		var table = document.getElementById('sgCombinationsTable_'+epochCountIndex);
		var length = 0;
		if(table != null){
			length = table.rows.length;		
		}
		
		if(length > 0){
			confirm("Stratum Groups will be deleted. Do you want to proceed.");
		}
	}
	</script>
</head>

<body>
<form:form method="post" name="form">
	<tags:tabFields tab="${tab}" />
	<div><input type="hidden" id="_action" name="_action" value=""> 
		<input type="hidden" id="_selectedEpoch" name="_selectedEpoch" value=""> 
		<input	type="hidden" id="_selectedStratification" name="_selectedStratification" value=""> 
		<input type="hidden" id="_selectedAnswer" name="_selectedAnswer" value="">
		<input type="hidden" id="generateGroups" name="generateGroups" value="false"/>
	</div>
	<c:forEach items="${command.treatmentEpochs}" var="epoch"
		varStatus="epochCount">
		<script>
            var startAnsRowInserterProps_${epochCount.index}= {
                add_row_division_id: "table1",
                skeleton_row_division_id: "dummy-strat-ans-${epochCount.index }",
                initialIndex: 2,
                softDelete: ${softDelete == 'true'},
                callRemoveFromCommand:"true",
                row_index_indicator: "NESTED.PAGE.ROW.INDEX",
                path: "treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers",
                epochCountIndex: ${epochCount.index},
                deleteMsgPrefix: "Stratum Groups will be deleted.",
                onDeleteFromCommandSuccess: function(t){
	                clear(${epochCount.index});                	
			    },
			    postProcessRowInsertion: function(object){
	                clear(object.epochCountIndex);                	
			    }
            };
            var stratRowInserterProps_${epochCount.index} = {
                nested_row_inserter: startAnsRowInserterProps_${epochCount.index},
                add_row_division_id: "epoch-${epochCount.index }",
                skeleton_row_division_id: "dummy-strat-${epochCount.index}",
                initialIndex: ${fn:length(command.treatmentEpochs[epochCount.index].stratificationCriteria)},
                softDelete: ${softDelete == 'true'},
                callRemoveFromCommand:"true",
                path: "treatmentEpochs[${epochCount.index }].stratificationCriteria",
                epochCountIndex: ${epochCount.index},
                deleteMsgPrefix: "Stratum Groups will be deleted.",
                onDeleteFromCommandSuccess: function(t){
	                clear(${epochCount.index});                	
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
			<table id="epoch-${epochCount.index }" class="tablecontent">
			<input type="hidden" name="epochCountIndex" value="${epochCount.index}"/>
			<div id="criteriaHeader" 
			style=<c:if test="${fn:length(command.treatmentEpochs[epochCount.index].stratificationCriteria) == 0}">"display:none"</c:if>>
				<tr>					
					<th><span class="required-indicator">Question</span></th>
					<th><span class="required-indicator">Answer</span></th>
					<th></th>
				</tr>
			</div>
				<c:forEach
					items="${command.treatmentEpochs[epochCount.index].stratificationCriteria}"
					var="strat" varStatus="status">
					<script>
                        RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}).updateIndex(${fn:length(command.treatmentEpochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers)});
                    </script>
					<tr id="epoch-${epochCount.index }-${status.index }">						
						<td><form:textarea
							path="treatmentEpochs[${epochCount.index }].stratificationCriteria[${status.index}].questionText"
							rows="1" cols="60" cssClass="validate-notEmpty" /></td>

						<td>
							<table class="tablecontent" id="table1" width="50%">
								<tr>
									<th></th>
									<th><input type="button" value="Add Answer"
										onclick="stratumGroupAlert('${epochCount.index}');RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}));" />
										</th>
								</tr>
								<c:forEach var="answer" varStatus="statusAns"
									items="${command.treatmentEpochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers}">
									<tr id="table1-${statusAns.index }">
										<td class="alt"><form:input
											path="treatmentEpochs[${epochCount.index }].stratificationCriteria[${status.index}].permissibleAnswers[${statusAns.index}].permissibleAnswer"
											size="30" cssClass="validate-notEmpty" /></td>
										<c:choose>
											<c:when test="${statusAns.index < 2}">
											</c:when>
											<c:otherwise>
												<td class="alt"><a
													href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}),${statusAns.index },${answer.hashCode});">
												<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td class="alt"><a
							href="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},${status.index },${strat.hashCode});">
						<img src="<tags:imageUrl name="checkno.gif"/>" border="0"
							alt="Delete"></a></td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<div align="right"><input type="button"
				value="Add Stratification Factor"
				onclick="stratumGroupAlert('${epochCount.index}');RowManager.addRow(stratRowInserterProps_${epochCount.index});" />
			</div>
			<br/>
			<!--stratum groups combinations display section-->
			<chrome:division title="Stratum Groups">
			<script>
			var stratumGroupRowInserter_${epochCount.index} = {
			    add_row_division_id: "stratumGroupTable1_${epochCount.index}", 	        
			    skeleton_row_division_id: "dummy-strat-strGrp-${epochCount.index}",
			    initialIndex: -1,
			    postProcessRowDeletion: function(t){
	                reorderStratumGroupNumbers(${epochCount.index});                	
			    },
			    path: "treatmentEpochs[${epochCount.index}].stratumGroups"
			};
			</script>
			
			<br />
			<div id="sgCombinations_${epochCount.index}"><!--This part is loaded onload and is updated with new content when generate str grps is clicked-->
			<c:if test="${fn:length(epoch.stratificationCriteria) > 0}">
				<script>
					stratumGroupRowInserter_${epochCount.index}.initialIndex= ${fn:length(command.treatmentEpochs[epochCount.index].stratumGroups)};
					RowManager.registerRowInserter(stratumGroupRowInserter_${epochCount.index});
				</script>				
				<table border="1" class="tablecontent"  width="50%">
				<tr>
					<th width="20%">Number</th>					
					<th width="75%">Stratum Group</th>
					<th width="5%"></th>
				</tr>
				</table>
				<table id="sgCombinationsTable_${epochCount.index}" border="1" class="tablecontent"  width="50%">
					<tbody id="sortablelist_${epochCount.index}">
						
						<c:forEach var="stratumGroup" varStatus="statusStratumGroup"
							items="${command.treatmentEpochs[epochCount.index].stratumGroups}">	
							<tr id="stratumGroupTable1_${epochCount.index}-${statusStratumGroup.index}">
								<td width="20%">${statusStratumGroup.index}</td>					
								<td width="75%">${command.treatmentEpochs[epochCount.index].stratumGroups[statusStratumGroup.index].answerCombinations}</td>
								<td width="5%"><a href="javascript:RowManager.deleteRow(stratumGroupRowInserter_${epochCount.index},${statusStratumGroup.index},${stratumGroup.hashCode});">
									<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>					
							</tr>
						</c:forEach>					
					</tbody>
				</table>

			</c:if> <!--This part is loaded onload and is updated with new content when generate str grps is clicked-->
			</div>
			<br>
			<div id="stratumButton" align="right">
				<input type='submit' onclick="preProcessGenerateGroups(${epochCount.index})" value='Generate Stratum Groups' />
			</div>
			<script type="text/javascript" language="javascript">
		  		Sortable.create('sortablelist_${epochCount.index}',{tag:'TR',constraint:false,onUpdate:postProcessDragDrop});
			</script>
			</chrome:division>
		</tags:minimizablePanelBox>
	</c:forEach>
	<div id="dummyDiv" style="display:none">
	</div>

	<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}" />
</form:form>

<c:forEach items="${command.treatmentEpochs}" var="epoch"
	varStatus="epochCount">
	<div id="dummy-strat-${epochCount.index }" style="display:none">
	<table>
		<tr>
			
			<td><input type="hidden"
				name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionNumber" />
			<textarea
				name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionText"
				rows="1" cols="60" class="validate-notEmpty"></textarea></td>

			<td>
			<table class="tablecontent" id="table1" width="50%">
				<tr>
					<th></th>
					<th><input type="button" value="Add Answer"
						onclick="stratumGroupAlert('${fn:length(command.treatmentEpochs[epochCount.index].stratumGroups)}');RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX));" />
					</th>
				</tr>
				<tr id="table1-0">
					<td><input type="text"
						name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[0].permissibleAnswer"
						size="30" class="validate-notEmpty" /></td>
				</tr>
				<tr id="table1-1">
					<td><input type="text"
						name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[1].permissibleAnswer"
						size="30" class="validate-notEmpty" /></td>
				</tr>
			</table>
			</td>
			<td><a
				href="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX,-1);"
				onclick="stratumGroupAlert('${fn:length(command.treatmentEpochs[epochCount.index].stratumGroups)}');">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"
				alt="Delete"></a></td>
		</tr>
	</table>
	</div>
	<div id="dummy-strat-ans-${epochCount.index }" style="display:none">
	<table>
		<tr>
			<td><input type="text"
				name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[NESTED.PAGE.ROW.INDEX].permissibleAnswer"
				size="30" class="validate-notEmpty" /></td>
			<td><a
				href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);"
				onclick="stratumGroupAlert('${fn:length(command.treatmentEpochs[epochCount.index].stratumGroups)}');">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
	
	<div id="dummy-strat-strGrp-${epochCount.index }" style="display:none">
	<table>
		<tr>
			<td><input type="text"
				name="treatmentEpochs[${epochCount.index}].stratumGroups[PAGE.ROW.INDEX].stratumGroupNumber"
				size="30" class="validate-notEmpty" /></td>
				<td></td>
			<td><a
				href="javascript:RowManager.deleteRow(sratumGroupRowInserter_${epochCount.index},PAGE.ROW.INDEX,-1);">
			<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
		</tr>
	</table>
	</div>
</c:forEach>
</body>
</html>
