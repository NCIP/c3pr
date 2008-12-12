<%@ include file="taglibs.jsp"%>
<script type="text/javascript">
     function copyToParent(){
     	parent.createCompanion($('_shortTitle').value);
     }

     function blindedRandomization(index){
    	var bIndicator=$('companionStudy'+index+'-blindedIndicator');
      	var rIndicator=$('companionStudy'+index+'-randomizedIndicator');
      	var rType=$('companionStudy'+index+'-randomizationType');
      	var rTypeDiv=$('randomizationTypeDiv');

      	if(bIndicator.value == 'true'){
      		rIndicator.value = 'true';
      		rIndicator.disabled = true;
      		rTypeDiv.style.display = "";
      		rType.value="PHONE_CALL";
      		rType.disabled = true;
      	} else {
      		rIndicator.value = 'false';
      		rIndicator.disabled = false;
      		rTypeDiv.style.display = "none";
      		rType.value="BOOK";   
      		rType.disabled = false;
      	}
     }

     function manageRandomizedIndicatorSelectBox(box, index) {
    	 var rType=$('companionStudy'+index+'-randomizationType');
    	 var rTypeDiv=$('randomizationTypeDiv');
         if (box.value == 'true') {
        	 rTypeDiv.style.display = "";
             Effect.OpenUp('randomizationTypeDiv');
             rType.className="validate-notEmpty";
         }
         if (box.value == 'false') {
             Effect.CloseDown('randomizationTypeDiv');
             rType.className="";
         }
     }
     
     function manageRandomizationTypeSelectBox(box) {
         var rIndicator = $('companionStudy'+index+'-randomizedIndicator') ;
         var rType=$('companionStudy'+index+'-randomizationType');
         if (box.value == '' && rIndicator.value =='true') {
        	 rType.className="validate-notEmpty";
         }
     }

</script>
<chrome:box title="${tab.shortTitle}">
	<c:set var="statusIndex" value="PAGE.ROW.INDEX"></c:set>
	<chrome:division id="study-details" title="Basic Details">
	    <div class="leftpanel">
	        <div class="row">
	            <div class="label"><tags:requiredIndicator />Short Title:</div>
	            <div class="value">
	            	<input class="validate-notEmpty" type="text" id="_shortTitle" size="35" maxlength="30" 
	            		name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.shortTitleText" value="${dataFromParent.shortTitle}"/>
	            	<tags:hoverHint keyProp="study.shortTitleText"/>
	            </div>
	        </div>
	        <div class="row">
	            <div class="label"><tags:requiredIndicator />Long Title:</div>
	            <div class="value">
	            	<textarea class="validate-notEmpty&&maxlength1024" rows="2" cols="33" id="companionStudyPAGE.ROW.INDEX-longTitleText"
		                       name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.longTitleText" >${dataFromParent.longTitle}</textarea>
		            <tags:hoverHint keyProp="study.longTitleText"/>
	            </div>
	        </div>
	        <div class="row">
	            <div class="label">Description:</div>
	            <div class="value">
		             <textarea class="validate-maxlength2000" rows="2" cols="33" id="companionStudyPAGE.ROW.INDEX-descriptionText"
			                       name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.descriptionText"></textarea>
		            <tags:hoverHint keyProp="study.description"/>
	            </div>
	        </div>
	        <div class="row">
				<div class="label">Precis:</div>
				<div class="value">
					<textarea class="validate-maxlength200" rows="2" cols="33" id="companionStudyPAGE.ROW.INDEX-precisText"
									name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.precisText" ></textarea>
		            <tags:hoverHint keyProp="study.precisText" />
		        </div>
			</div>
		</div>
	
	    <div class="rightpanel">
	        <div class="row">
	            <div class="label"><tags:requiredIndicator />Target Accrual:</div>
	            <div class="value">
	            	<input class="validate-notEmpty&&numeric&&nonzero_numeric" type="text" id="companionStudyPAGE.ROW.INDEX-targetAccrualNumber"  name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.targetAccrualNumber"
	            			size="10" maxlength="6" value="${command.study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.targetAccrualNumber}"/>
	            	<tags:hoverHint keyProp="study.targetAccrualNumber"/>
	            </div>
	        </div>
	        <div class="row">
	            <div class="label"><tags:requiredIndicator />Type:</div>
	            <div class="value">
	            	<select id="companionStudyPAGE.ROW.INDEX-type" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.type" class="validate-notEmpty">
	                    <option value="">Please Select</option>
	                    <c:forEach items="${typeRefData}" var="status">
	                        <option value="${status.desc}">${status.desc}</option>
	                    </c:forEach>
	                </select>
	            	<tags:hoverHint keyProp="study.type"/>
	            </div>
	        </div>
	        <div class="row">
	            <div class="label"><tags:requiredIndicator />Phase:</div>
	            <div class="value">
	            	<select id="companionStudyPAGE.ROW.INDEX-phaseCode" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.phaseCode" 
	            		class="validate-notEmpty">
	                    <option value="">Please Select</option>
	                    <c:forEach items="${phaseCodeRefData}" var="status">
	                        <option value="${status.desc}">${status.desc}</option>
	                    </c:forEach>
	                </select>
	            	<tags:hoverHint keyProp="study.phaseCode"/>
	            </div>
	        </div>
			<div class="row">
	            <div class="label">Blinded:</div>
	            <div class="value">
	            	<select id="companionStudyPAGE.ROW.INDEX-blindedIndicator" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.blindedIndicator" class="validate-notEmpty" onchange="blindedRandomization(PAGE.ROW.INDEX);">
		                 <option value="">Please Select</option>
		                 <c:forEach items="${yesNo}" var="status">
		                     <option value="${status.code}">${status.desc}</option>
		                 </c:forEach>
	            	</select>
	      			<tags:hoverHint keyProp="study.blindedIndicator"/>
	      		</div>
	        </div>
	        <div class="row">
	            <div class="label"><tags:requiredIndicator />Multi-Institutional:</div>
	            <div class="value">
	            	<select id="companionStudyPAGE.ROW.INDEX-multiInstitutionIndicator" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.multiInstitutionIndicator" class="validate-notEmpty">
	                  	<option value="">Please Select</option>
	                 		<c:forEach items="${yesNo}" var="status">
	                 			<option value="${status.code}">${status.desc}</option>
	                 		</c:forEach>
	            	</select>
	             	<tags:hoverHint keyProp="study.multiInstitutionIndicator"/>
	            </div>
	        </div>
	        <div class="row">
             	<div class="label"><tags:requiredIndicator />Consent Version/Date:</div>
             	<div class="value">
             		<input name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.consentVersion" class="validate-notEmpty"/><em> (mm/dd/yyyy)</em>
             		<a href="#" id="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.consentVersion-calbutton">
                    	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle"/>
                	</a>
             		<tags:hoverHint keyProp="study.consentVersion"/>
             	</div>
         	</div>
        	<div class="row" style="display:none;">
	        	<div class="label"><tags:requiredIndicator />Standalone Study:</div>
	        	<div class="value">
	        		<input class="validate-notEmpty" type="hidden" id="companionStudyPAGE.ROW.INDEX-standaloneIndicator"  
	            		name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.standaloneIndicator" value="false"/>
	        		<tags:hoverHint keyProp="study.standaloneIndicator"/>
	        	</div>
	        </div>
	     </div>
	</chrome:division>
	<chrome:division title="Stratification & Randomization">
    	<div class="leftpanel">
    		<div class="row">
         		<div class="label"><tags:requiredIndicator />Stratified:</div>	
         		<div class="value">
         			<select id="companionStudyPAGE.ROW.INDEX-stratificationIndicator" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.stratificationIndicator" class="validate-notEmpty">
		                 <option value="">Please Select</option>
		                 <c:forEach items="${yesNo}" var="status">
		                     <option value="${status.code}">${status.desc}</option>
		                 </c:forEach>
	            	</select>
         			<tags:hoverHint keyProp="study.stratifiedIndicator"/>
         		</div>
         	</div>
	        <div class="row">
	           	<div class="label"><tags:requiredIndicator />Randomized:</div>
		        <div class="value">
		        	<select id="companionStudyPAGE.ROW.INDEX-randomizedIndicator" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.randomizedIndicator" class="validate-notEmpty" onchange="manageRandomizedIndicatorSelectBox(this, PAGE.ROW.INDEX)">
		                 <option value="">Please Select</option>
		                 <c:forEach items="${yesNo}" var="status">
		                     <option value="${status.code}">${status.desc}</option>
		                 </c:forEach>
	            	</select>
		            <tags:hoverHint keyProp="study.randomizedIndicator"/>
	            </div>
            </div>
    	</div>
		<div class="rightpanel">
        	<div id="randomizationTypeDiv">
	            <div class="row">
    	            <div class="label"><tags:requiredIndicator />Type:</div>
        	        <div class="value">
	        	       	<select id="companionStudyPAGE.ROW.INDEX-randomizationType" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.randomizationType" class="validate-notEmpty" onchange="manageRandomizationTypeSelectBox(this);">
		                    <option value="">Please Select</option>
		                    <option value="BOOK">Book</option>
		                    <option value="PHONE_CALL">Phone Call</option>
		               	</select>
                		<tags:hoverHint keyProp="study.randomizationType"/>
                	</div>
            	</div>
        	</div>
	    </div>
	</chrome:division>
	<chrome:division title="Coordinating Center">
		<tags:errors path="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.coordinatingCenterAssignedIdentifier"/> 
         <div id="coordinatingCenter">
           	  <div class="leftpanel">
              	      <div class="row">
                    <div class="label"><tags:requiredIndicator />Name:</div>
			        <div class="value">
						<input type="hidden" id="companionStudyPAGE.ROW.INDEX-coCenter-hidden" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.studyCoordinatingCenters[0].healthcareSite" value="${dataFromParent.coordinatingCenterList[0].healthcareSite.id}"/>
						<input type="hidden" id="companionStudyPAGE.ROW.INDEX-coCenter-hidden1" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.organizationAssignedIdentifiers[0].healthcareSite"  value="${dataFromParent.coordinatingCenterList[0].healthcareSite.id}"/>
						<input id="companionStudyPAGE.ROW.INDEX-coCenter-input" size="38" type="text" disabled="disabled" name="abcxyz" class="autocomplete validate-notEmpty" value="${dataFromParent.coordinatingCenterList[0].healthcareSite.name}" />
						<tags:hoverHint keyProp="study.healthcareSite.name"/>
						<tags:indicator id="coCenter-indicator" />
						<div id="coCenter-choices" class="autocomplete" style="display:none;"></div>
					</div>
			      </div>	
		  	  </div>
		  	   <div class="rightpanel">
                  <div class="row">
                      <div class="label"><tags:requiredIndicator />Study Identifier:</div>
                      <div class="value">
                      	<input type="text" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.organizationAssignedIdentifiers[0].value" size="30" maxlength="30" class="validate-notEmpty" />
						<input type="hidden" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.organizationAssignedIdentifiers[0].type" value="Coordinating Center Identifier"/>
						<input type="hidden" name="study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.organizationAssignedIdentifiers[0].primaryIndicator" value="true"/>
						<tags:hoverHint keyProp="study.coordinatingcenter.identifier"/></div>
		  			  </div>            
         		  </div>
			  </div>
	     	
	</chrome:division>
		<chrome:division title="Funding Sponsor">
		<div id="fundingSponsor">
     		<div class="leftpanel">
        		<div class="row">
            		<div class="label">Name:</div>
		            <div class="value">
		            	<input type="hidden" id="companionStudyPAGE.ROW.INDEX-healthcareSite-hidden1" name="${(fn:length(dataFromParent.fundingSponsorsList) > 0 ) ? study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.organizationAssignedIdentifiers[1].healthcareSite : 'abcdef' }"  value="${(fn:length(dataFromParent.fundingSponsorsList) > 0 ) ? dataFromParent.fundingSponsorsList[0].healthcareSite.id : ''}" />
		            	<input type="text" id="companionStudyPAGE.ROW.INDEX-healthcareSite-input" size="38" name="aaaxxx" class="autocomplete" disabled="disabled"  value="${(fn:length(dataFromParent.fundingSponsorsList) > 0 ) ? dataFromParent.fundingSponsorsList[0].healthcareSite.name : ''}" />
						<input type="hidden" id="companionStudyPAGE.ROW.INDEX-healthcareSite-hidden"  name="${(fn:length(dataFromParent.fundingSponsorsList) > 0 ) ? study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.studyFundingSponsors[0].healthcareSite : 'qwerty' }"  value="${(fn:length(dataFromParent.fundingSponsorsList) > 0 ) ? dataFromParent.fundingSponsorsList[0].healthcareSite.id : ''}"/>            		
						<tags:indicator id="healthcareSite-indicator" />
						<tags:hoverHint keyProp="study.studyFundingSponsor"/>
						<div id="healthcareSite-choices" class="autocomplete" style="display:none;"></div>
					</div>
        		</div>
			</div>
			<div class="rightpanel">
        		<div class="row">
            		<div class="label">Study Identifier:</div>
            		<div class="value">
	            		<input type="text" name="${fn:length(dataFromParent.fundingSponsorsList) > 0 ? study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.organizationAssignedIdentifiers[1].value : 'qwer'}" size="30" maxlength="30" id="companionStudyPAGE.ROW.INDEX-fundingSponsorIdentifier" />
						<input type="hidden" id="companionStudyPAGE.ROW.INDEX-healthcareSite-hidden1" name="${fn:length(dataFromParent.fundingSponsorsList) > 0 ? study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.organizationAssignedIdentifiers[1].healthcareSite : 'poiuy'}"/>
						<input type="hidden" name="${fn:length(dataFromParent.fundingSponsorsList) > 0 ?  study.companionStudyAssociations[PAGE.ROW.INDEX].companionStudy.organizationAssignedIdentifiers[1].type : 'trewq'}" id="companionStudyPAGE.ROW.INDEX-fundingSponIdentifierType" value="${(fn:length(dataFromParent.fundingSponsorsList) > 0 ) ? 'Protocol Authority Identifier' : ''}" />
						<tags:hoverHint keyProp="study.fundingsponsor.identifier"/>
					</div>
        		</div>
     		</div>
   		</div>
	</chrome:division>
	
	<div class="content buttons autoclear">
		<div class="flow-buttons">
			<span class="next"> 
				<input type="button" value="Create Companion Study" onclick="copyToParent();"/>
				<input type="button" value="Close" onClick="parent.closePopup(true);"/>
			</span>
		</div>
	</div>
</chrome:box>
