<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<chrome:deletableDivision divTitle="genericTitle-${index}" id="genericEpochBox-${index}" title="Epoch: ${command.study.epochs[index].name}" onclick="RowManager.deleteRow(genericEpochRowInserterProps,${index},'${treatmentEpoch.id==null?'HC#':'ID#'}${treatmentEpoch.id==null?treatmentEpoch.hashCode:treatmentEpoch.id}')"
						 minimize="false" divIdToBeMinimized="epoch-${index}" >
			<div id="epoch-${index}">
			<div class="leftpanel">
				<div class="row">
					<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.name"/></div>
					<div class="value">
						<input type="text" name="study.epochs[${index}].name" class="required validate-notEmpty" onkeyup="updateName('genericTitle-${index}', 'Epoch: ' + this.value);" />
		  				<tags:hoverHint id="study.treatmentEpoch.name-${index}" keyProp="study.treatmentEpoch.name" />
					</div>
				</div>
				<div class="row">
					<div class="label"><tags:requiredIndicator /><fmt:message key="study.epoch.order"/></div>
					<div class="value">
						<input type="text" name="study.epochs[${index}].epochOrder" size="5" maxlength="1" class="required validate-notEmpty&&numeric" />
             			<tags:hoverHint id="study.treatmentEpoch.epochorder-${index}" keyProp="study.treatmentEpoch.epochOrder" />
					</div>
				</div>
				<div class="row">
					<div class="label"><tags:requiredIndicator /><fmt:message key="study.epoch.treating"/></div>
					<div class="value">
						<select name="study.epochs[${index}].treatmentIndicator" class="required validate-notEmpty">
                   			<option value="">Please Select</option>
                   			<option value="true">Yes</option>
                   			<option value="false">No</option>
                 		</select>
                 		<tags:hoverHint id="study.treatmentEpoch.treatmentIndicator-${index}" keyProp="study.epoch.treatmentIndicator" />
					</div>
				</div>
				<div class="row">
					<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.enrolling"/></div>
					<div class="value">
						<select id="study.epochs[${index}].enrollmentIndicator" name="study.epochs[${index}].enrollmentIndicator" onchange="manageEnrollingIndicatorSelectBox(this,${index});"
								class="required validate-notEmpty">
							<option value="">Please Select</option>
                            <option value="true">Yes</option>
                   			<option value="false">No</option>
						</select>
	                    <tags:hoverHint id="study.nonTreatmentEpoch.enrollmentIndicator-${index}" keyProp="study.nonTreatmentEpoch.enrollmentIndicator" />
					</div>
				</div>
				<c:if test="${command.study.randomizedIndicator== true }">
					<div class="row">
						<div class="label"><tags:requiredIndicator /><fmt:message key="study.randomized"/></div>
						<div class="value">
							<select name="study.epochs[${index}].randomizedIndicator" class="required validate-notEmpty">
                   				<option value="">Please Select</option>
                   				<option value="true">Yes</option>
                   				<option value="false">No</option>
                 			</select>
                 			<tags:hoverHint id="study.treatmentEpoch.randomizedIndicator-${index}" keyProp="study.treatmentEpoch.randomizedIndicator" />
						</div>
					</div>
				</c:if>
			</div>
    		<div class="rightpanel">
    			<div class="row">
    				<div class="label"><fmt:message key="c3pr.common.description"/></div>
    				<div class="value">
    					 <textarea name="study.epochs[${index}].descriptionText" rows="2" ></textarea>
                         <tags:hoverHint id="study.treatmentEpoch.description-${index}" keyProp="study.treatmentEpoch.description" />
    				</div>
    			</div>
    			<div class="row">
    				<div class="label"><fmt:message key="study.accrualCeiling"/></div>
    				<div class="value">
    					<input type="text" name="study.epochs[${index}].accrualCeiling" size="14" maxlength="5" class="validate-numeric&&nonzero_numeric" />
						<tags:hoverHint id="study.nonTreatmentEpoch.accrualCeiling-${index}" keyProp="study.nonTreatmentEpoch.accrualCeiling" />
    				</div>
    			</div>
    			<div class="row">
    				<div class="label"><fmt:message key="study.epoch.reserving"/></div>
    				<div class="value" id="reservationIndicator-${index}">
                       <select name="study.epochs[${index}].reservationIndicator"
							onchange="manageReservingIndicatorSelectBox(this,${index});" class="required validate-notEmpty">
							<option value="">Please Select</option>
                	        <option value="true">Yes</option>
               				<option value="false">No</option>
			  			</select>	
                       <tags:hoverHint id="study.nonTreatmentEpoch.reservationIndicator-${index}" keyProp="study.nonTreatmentEpoch.reservationIndicator" />
    				</div>
    			</div>
    			<c:if test="${command.study.stratificationIndicator== true }">
      				<div class="row">
      					<div class="label"><fmt:message key="study.stratified"/></div>
      					<div class="value">
							<select name="study.epochs[${index}].stratificationIndicator" class="required validate-notEmpty">
								<option value="">Please Select</option>
                   				<option value="true">Yes</option>
                   				<option value="false">No</option>
                 			</select>
                 			<tags:hoverHint id="study.treatmentEpoch.stratificationIndicator-${index}" keyProp="study.epoch.stratificationIndicator" />
      					</div>
      				</div>
     			</c:if>
			</div>
			<tags:button id="addArm-${index}" type="button" color="blue" icon="add" value="Add Arm"
				onclick="$('h-${index}').show(); javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,${index}));" size="small"/>
		</div>
</chrome:deletableDivision>