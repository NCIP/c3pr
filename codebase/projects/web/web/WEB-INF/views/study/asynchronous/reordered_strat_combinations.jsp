<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:set var="epochCountIndex" value="${param.epochCountIndex}" />

	<div id="sgCombinations_${epochCountIndex}"><!--This part is loaded onload and is updated with new content when generate str grps is clicked-->
	<chrome:division title="Stratum Groups (drag/drop the groups to re-order)">
	<!--stratum groups combinations display section-->
	<script>
	var stratumGroupRowInserter_${epochCountIndex} = {
	    add_row_division_id: "stratumGroupTable1_${epochCountIndex}", 	        
	    skeleton_row_division_id: "dummy-strat-strGrp-${epochCountIndex}",
	    initialIndex: -1,
	    callRemoveFromCommand:"true",
	    deleteMsgPrefix:  ${isBookRandomized == 'true'}? "Book Randomization Entries(if any) will be deleted." : " ",
	    postProcessRowDeletion: function(t){
			// fix this issue, not deleting book entry
            reorderStratumGroupNumbers(${epochCountIndex});                	
	    },
	    path: "study.epochs[${epochCountIndex}].stratumGroups"
	};
	</script>
	
	<br />			
		<c:if test="${fn:length(command.study.epochs[epochCountIndex].stratificationCriteria) > 0}">
		<script>
			stratumGroupRowInserter_${epochCountIndex}.initialIndex= ${fn:length(command.study.epochs[epochCountIndex].stratumGroups)};
			RowManager.registerRowInserter(stratumGroupRowInserter_${epochCountIndex});
		</script>				
		
		<table width="75%">
		<tr><td width="80%">
			<table border="1" class="tablecontent" width="100%">
			<tr>
				<th width="30%">Group Number&nbsp;<tags:hoverHint id="study.stratumGroup.stratumGroupNumber-${epochCountIndex}" keyProp="study.stratumGroup.stratumGroupNumber"/></th>					
				<th width="65%">Answer Combination&nbsp;<tags:hoverHint id="study.stratumGroup.answerCombinations-${epochCountIndex}" keyProp="study.stratumGroup.answerCombinations"/></th>
				<th width="5%"></th>
			</tr>
			</table>
			<table id="sgCombinationsTable_${epochCountIndex}" border="1" class="tablecontent"  width="100%">
				<tbody id="sortablelist_${epochCountIndex}">
					<c:forEach var="stratumGroup" varStatus="statusStratumGroup"
						items="${command.study.epochs[epochCountIndex].stratumGroups}">	
						
						<c:if test="${command.study.epochs[epochCountIndex].stratificationIndicator == 'true' }">					
						<tr id="stratumGroupTable1_${epochCountIndex}-${statusStratumGroup.index}" style="cursor:move">
							<td>
								<c:if test="${statusStratumGroup.index != 0}">
									<a href="javascript:moveElementUpforList('sortablelist_${epochCountIndex}', '${epochCountIndex}-${statusStratumGroup.index}');updateStartumGroups('sortablelist_${epochCountIndex}');" >
									<img src="<tags:imageUrl name="b-arrowUp.gif"/>" border="0"></a>
								</c:if>
							</td>
							<td>
								<c:if test="${fn:length(command.study.epochs[epochCountIndex].stratumGroups) != statusStratumGroup.index + 1}">
									<a href="javascript:moveElementDownforList('sortablelist_${epochCountIndex}','${epochCountIndex}-${statusStratumGroup.index}');updateStartumGroups('sortablelist_${epochCountIndex}');" >
									<img src="<tags:imageUrl name="b-arrowDown.gif"/>" border="0"></a>
								</c:if>
							</td>
							<td width="30%">
								${stratumGroup.stratumGroupNumber}
							</td>					
							<td width="59%">${stratumGroup.answerCombinations}</td>
							<td width="5%">
							<a href="javascript:RowManager.deleteRow(stratumGroupRowInserter_${epochCountIndex},${statusStratumGroup.index},'${stratumGroup.id==null?'HC#':'ID#'}${stratumGroup.id==null?stratumGroup.hashCode:stratumGroup.id}');">
								<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
							</td>					
						</tr>
						</c:if>
					</c:forEach>					
				</tbody>
			</table>
		</td>
		<td align="left" width="20%"><img id="reorderGroupsInd-${epochCountIndex}" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" style="display:none">
		</td></tr>
		</table>
		
		</c:if><!--This part is loaded onload and is updated with new content when generate str grps is clicked-->
		<script type="text/javascript" language="javascript">
	  		Sortable.create('sortablelist_${epochCountIndex}',{tag:'TR', constraint:false, onUpdate:postProcessDragDrop});
		</script>
		<br>
	</chrome:division>
	</div>
	


