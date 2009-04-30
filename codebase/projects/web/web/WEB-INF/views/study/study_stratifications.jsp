<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><studyTags:htmlTitle study="${command.study}" /></title>
<style>
.tablecontent .division {
}
</style>
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
			e.style.opacity='0.9';
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
        	$('editStratificationCriteria-${epoch.id}').hide(); 
        	$('addStratificationCriteria-${epoch.id}').show(); 
			clear(epochCountIndex);
			enableQuestionSection(id);
        }
		$('stratificationIndicator-'+epochCountIndex).hide();
     }

    function moveDownThisGroup(e){
    }

    function moveUpThisGroup(e){
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
	<div>
		<input type="hidden" id="_action" name="_action" value=""> 
		<input type="hidden" id="_selectedEpoch" name="_selectedEpoch" value=""> 
		<input	type="hidden" id="_selectedStratification" name="_selectedStratification" value=""> 
		<input type="hidden" id="_selectedAnswer" name="_selectedAnswer" value="">
		<input type="hidden" id="generateGroups" name="generateGroups" value="false"/>
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
                isAdmin: ${isAdmin == 'true'},
                row_index_indicator: "NESTED.PAGE.ROW.INDEX",
                path: "study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers",
            };
            var stratRowInserterProps_${epochCount.index} = {
                nested_row_inserter: stratAnsRowInserterProps_${epochCount.index},
                add_row_division_id: "epoch-${epochCount.index }",
                skeleton_row_division_id: "dummy-strat-${epochCount.index}",
                initialIndex: ${fn:length(command.study.epochs[epochCount.index].stratificationCriteria)},
                softDelete: ${softDelete == 'true'},
                isAdmin: ${isAdmin == 'true'},
                path: "study.epochs[${epochCount.index }].stratificationCriteria",
            };	
            RowManager.addRowInseter(stratRowInserterProps_${epochCount.index});
            RowManager.registerRowInserters();

            function updateName(divID, stringValue) {
        	    if ($(divID)) {
        	        $(divID).innerHTML = stringValue;
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
							<chrome:deletableDivision id="question-${epochCount.index }-${status.index }" divTitle="questionTitle-${epochCount.index }-${status.index }" onclick="RowManager.deleteRow(stratRowInserterProps_${epochCount.index},${status.index},'${strat.id==null?'HC#':'ID#'}${strat.id==null?strat.hashCode:strat.id}');" 
                    		title="Question : ${epoch.stratificationCriteria[status.index].questionText}" >
                    		<table style="border: 0px red dotted;" width="100%">
                    		<tr>	
								<td valign="top">
									<table class=""  width="50%">
										<tr><td><b><fmt:message key="study.question"/></b></td></tr>
										<tr><td>
											<form:textarea path="study.epochs[${epochCount.index }].stratificationCriteria[${status.index}].questionText"
										rows="1" cols="60" cssClass="validate-notEmpty" onkeyup="updateName('questionTitle-${epochCount.index }-${status.index }', 'Question: ' + this.value);"/>
										</td></tr>
									</table>
								</td>
								<td>
									<table class="" id="table1" width="50%">
										<tr>
											<td>
											<tags:button type="button" color="blue" icon="add" value="Add Answer" 
													onclick="RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}));" size="small"/>
											</td>
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
					<tags:button type="button" color="blue" icon="add" value="Add Stratification Criteria" onclick="$('stratumButton-${epoch.id}').show();RowManager.addRow(stratRowInserterProps_${epochCount.index});" size="small"/>
				</span>
				<span id="editStratificationCriteria-${epoch.id}" <c:if test="${fn:length(epoch.stratumGroups) == 0 }"> style = "display:none" </c:if> >
					<tags:button type="button" icon="edit" color="blue" value="Edit Stratification Criteria" onclick="editStratificationCriteria('${epochCount.index}','${isBookRandomized}', '${epoch.id}');" size="small"/>
				</span>
				<span id="stratumButton-${epoch.id}" <c:if test="${fn:length(epoch.stratificationCriteria) == 0}"> style = "display:none" </c:if> >
					<tags:button type="submit" color="blue" value="Generate Stratum Groups" onclick="$('stratificationIndicator-${epochCount.index }').show();preProcessGenerateGroups(${epochCount.index});" size="small"/>
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

	<input type="hidden" name="flowType" value="${flowType}">
	<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}" />
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
								<TEXTAREA name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionText" rows="1" cols="60" class="validate-notEmpty" onkeyup="updateName('questionTitle-${epochCount.index }-PAGE.ROW.INDEX', 'Question: ' + this.value);" ></TEXTAREA>
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
									size="30" class="validate-notEmpty" /></td>
							</tr>
							<tr id="table1-1">
								<td><input type="text"
									name="study.epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[1].permissibleAnswer"
									size="30" class="validate-notEmpty" /></td>
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
				size="30" class="validate-notEmpty" /></td>
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
