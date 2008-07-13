
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/c3pr/public/css/tables1" />
    <script type="text/javascript">
    
    
    function onAddTreatmentEpoch(){
	    $('dummy-genericEpoch').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(genericEpochRowInserterProps);
	 //   alert('accrualCeiling-'+0);
	     Effect.Fold($('genericEpoch-'+0));
    }
     
     function manageAccrualIndicatorSelectBox(box,index) {
            if (box.value == 'true') {
                //		document.getElementById('accrualCeiling-index').style.display='show';
                Effect.OpenUp('accrualCeiling-'+index);
            }
            if (box.value == 'false') {
                	//	document.getElementById('accrualCeiling-index').style.display='none';
                Effect.CloseDown('accrualCeiling-'+index);
            }
        }
        
         function manageEnrollingIndicatorSelectBox(box,index) {
            if (box.value == 'false') {
                		//document.getElementById('reservationIndicator').style.display='show';
           //   Form.reset('reservationIndicator-'+index);
           		alert('reservationIndicator-'+index);
                Effect.OpenUp('reservationIndicator-'+index);
                Effect.OpenUp('reservationIndicatorLabel-'+index);
            }
            if (box.value == 'true') {
             	//document.getElementById('reservationIndicator').style.display='none';
                Effect.CloseDown('reservationIndicator-'+index);
                Effect.CloseDown('reservationIndicatorLabel-'+index);
           //  $('epochs['+index+'].reservationIndicator').value=false;
            }
        }
        
        var armInserterProps= {
            add_row_division_id: "arm",
            skeleton_row_division_id: "dummy-arm",
            initialIndex: ${fn:length(command.epochs[treatmentEpochCount.index].arms)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            row_index_indicator: "NESTED.PAGE.ROW.INDEX",
            deleteMsgPrefix: "Book Randomization Entries(if any) for this Epoch will be deleted.",
            path: "epochs[PAGE.ROW.INDEX].arms"
        };
          var genericEpochRowInserterProps= {
          	nested_row_inserter: armInserterProps,
            add_row_division_id: "genericEpoch",
            skeleton_row_division_id: "dummy-genericEpoch",
            initialIndex: ${fn:length(command.epochs)},
            softDelete: ${softDelete == 'true'},
            isAdmin: ${isAdmin == 'true'},
            path: "epochs"
            };
 		RowManager.addRowInseter(genericEpochRowInserterProps);
        RowManager.registerRowInserters();
    </script>

</head>

<body>
<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}"
	formName="studyDesignForm" displayErrors="false">
	<jsp:attribute name="singleFields">
	
	<tags:errors path="epochs"/> 

<div><input type="hidden" name="_action" value=""> <input
        type="hidden" name="_selectedEpoch" value=""> <input type="hidden"
                                                             name="_selectedArm" value=""></div>


<table id="genericEpoch" width="100%">
    <c:forEach items="${command.epochs}" var="treatmentEpoch"
               varStatus="treatmentEpochCount">
		
        <tr id="genericEpoch-${treatmentEpochCount.index}">
            <script>
                RowManager.getNestedRowInserter(genericEpochRowInserterProps,${treatmentEpochCount.index}).updateIndex(${fn:length(command.epochs[treatmentEpochCount.index].arms)});
            </script>
            
            <td>
      <chrome:deletableDivision divTitle="genericTitle" id="genericEpochBox-${treatmentEpochCount.index}" title="${command.epochs[treatmentEpochCount.index].displayRole}: ${command.epochs[treatmentEpochCount.index].name}" onclick="RowManager.deleteRow(genericEpochRowInserterProps,${treatmentEpochCount.index},'${treatmentEpoch.id==null?'HC#':'ID#'}${treatmentEpoch.id==null?treatmentEpoch.hashCode:treatmentEpoch.id}')">
<!-- GENERIC START-->

<script language="JavaScript1.2">
  function updateName(string) {
      if ($("genericTitle")) {
          $("genericTitle").innerHTML = string;
      }
  }
</script>

<table width="100%">
<tr>
  <td valign="top" width="50%">

      <table width="100%" border="0" cellspacing="5">
      <tr>
          <td align="right"><b>Name:</b></td>
          <td align="left" valign="top"><form:input path="epochs[${treatmentEpochCount.index}].name" size="43" cssClass="validate-notEmpty" />
			                                        <tags:hoverHint id="study.treatmentEpoch.name-	" keyProp="study.treatmentEpoch.name"/></td>
      </tr>

      <tr>
          <td align="right"> <span class="required-indicator"><b>Order:</b></span></td>
          <td><form:input path="epochs[${treatmentEpochCount.index}].epochOrder" size="5" maxlength="1"
			                                               cssClass="validate-notEmpty&&numeric" />
			                                        <tags:hoverHint id="study.treatmentEpoch.epochorder-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.epochOrder"/></td>
      </tr>

  <c:if test="${command.randomizedIndicator== true && command.epochs[treatmentEpochCount.index].displayRole != 'NonTreatment'}">
      <tr>
              <td align="right"><span class="required-indicator"><b>Randomized:</b></span></td>
              <td><form:select
				                                        path="epochs[${treatmentEpochCount.index}].randomizedIndicator"
				                                        cssClass="validate-notEmpty">
				                                   <option value="">Please Select</option>
				                                     <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
				                                </form:select><tags:hoverHint id="study.treatmentEpoch.randomizedIndicator-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.randomizedIndicator"/></td>
      </tr>
  </c:if>
  
  
  <c:if test="${command.epochs[treatmentEpochCount.index].displayRole!='Treatment'}">
      <tr>
          <td align="right"><b>Enrolling:</b></td>
          <td align="left">
              <form:select  id="epochs[${treatmentEpochCount.index}].enrollmentIndicator"
	                                        path="epochs[${treatmentEpochCount.index}].enrollmentIndicator"
	                                        onchange="manageEnrollingIndicatorSelectBox(this,${treatmentEpochCount.index});"
	                                        cssClass="validate-notEmpty">
	                                    <option value="">Please Select</option>
	                                    <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
	                                	</form:select>
		                               		 <tags:hoverHint id="study.nonTreatmentEpoch.enrollmentIndicator-${treatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.enrollmentIndicator"/>
          </td>
      </tr>
    </c:if>

      </table>

  </td>


    <style>
        .descTextarea {
            width:380px;
        }
    </style>

  <!--[if IE]>
    <style>
        .descTextarea {
            width:384px;
        }
    </style>

  <![endif]-->

<%--
DELETED TD
--%>

  <td valign="top">
      <table width="100%" border="0">
      <tr>
          <td align="right"><b>Description:</b></td>
          <td align="left" colspan="3"><form:textarea
                                            path="epochs[${treatmentEpochCount.index}].descriptionText"
                                            rows="5" cssClass="descTextarea" />
                                        <tags:hoverHint id="study.treatmentEpoch.description-${treatmentEpochCount.index}" keyProp="study.treatmentEpoch.description"/></td>
      </tr>
      
      <c:if test="${command.epochs[treatmentEpochCount.index].displayRole!='Treatment'}">
      <tr>
          <td align="right"><b>Accrual Ceiling:</b> </td>
          <td align="left">
              <form:input path="epochs[${treatmentEpochCount.index}].accrualCeiling" size="12"
	                                                          maxlength="5" cssClass="validate-numeric&&nonzero_numeric" />
	                                		<tags:hoverHint id="study.nonTreatmentEpoch.accrualCeiling-${treatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.accrualCeiling"/>
          </td>
      <!--</tr>-->
<!---->
        <c:if test="${!command.epochs[treatmentEpochCount.index].enrollmentIndicator}">          
        <!--<tr>-->
                        <td align="right"><div id="reservationIndicatorLabel-${treatmentEpochCount.index} <c:if test="${command.epochs[treatmentEpochCount.index].enrollmentIndicator==true}">style="display:none;"</c:if>">
                        <b>Reserving:</b>
                        </div></td>
                        <td align="left">
                           <div id ="reservationIndicator-${treatmentEpochCount.index}" >
                                                                 <form:select
                                                                  path="epochs[${treatmentEpochCount.index}].reservationIndicator"
                                                                  cssClass="validate-notEmpty">
                                                                  <option value="">Please Select</option>
                                                                   <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                                                              </form:select>
                                                                 <tags:hoverHint id="study.nonTreatmentEpoch.reservationIndicator-${treatmentEpochCount.index}" keyProp="study.nonTreatmentEpoch.reservationIndicator"/>
                                                             </div>
                        </td>
        </tr>
        </c:if>
<!---->
      </c:if>
      </table>
  </td>
</tr>
<c:if test="${command.epochs[treatmentEpochCount.index].displayRole!='NonTreatment'}">
<tr>
  <td colspan="3" align="left">
      <hr noshade size="1" width="100%">

      <table id="arm" class="tablecontent" border="0">
      <tr>
          <th><span class="required-indicator">Arm</span><tags:hoverHint id="study.arm.name-${treatmentEpochCount.index}" keyProp="study.arm.name"/></th>
          <th>Description<tags:hoverHint id="study.arm.description-${treatmentEpochCount.index}" keyProp="study.arm.description"/></th>
          <th>Accrual Ceiling<tags:hoverHint id="study.arm.targetAccrualNumber-${treatmentEpochCount.index}" keyProp="study.arm.targetAccrualNumber"/></th>
          <th><input id="addArm" type="button" value="Add Arm" onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,${treatmentEpochCount.index}));" /></th>
      </tr>
      
       <c:forEach items="${treatmentEpoch.arms}" var="arm" varStatus="statusArms">
        <tr id="arm-${statusArms.index}"><td valign="top"><form:input path="epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].name"  size="43" />
                                                <tr id="arm-${statusArms.index}">
                                                    <td valign="top"><form:input
                                                            path="epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].name"  size="43"
                    cssClass="validate-notEmpty" /></td>
            <td valign="top"><form:textarea path="epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].descriptionText" rows="5" cols="40" /></td>
            <td valign="top" align="center"><form:input path="epochs[${treatmentEpochCount.index}].arms[${statusArms.index}].targetAccrualNumber" size="6" maxlength="6" cssClass="validate-numeric&&nonzero_numeric" /></td>
            <td valign="top" align="center">
            	<a href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,${treatmentEpochCount.index}),${statusArms.index },'${arm.id==null?'HC#':'ID#'}${arm.id==null?arm.hashCode:arm.id}');">
            	<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
            </td>
        </tr>
	 </c:forEach>
      </table>

  </td>
</tr>
</c:if>

</table>


<!-- GENERIC END-->
      </chrome:deletableDivision>
  </td>
        </tr>
    </c:forEach>
</table>
<div align="right">
	
	<input id="addEpoch" type="button" value="Add Generic Epoch"
           onclick="$('dummy-genericEpoch').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(genericEpochRowInserterProps)" />

   <input id="addEpoch" type="button" value="Add Treatment Epoch"
   		onclick="$('dummy-genericEpoch').innerHTML=$('treatmentHtml').innerHTML;RowManager.addRow(genericEpochRowInserterProps)" />
    
    <input id="addEpoch" type="button" value="Add Non-Treatment Epoch"
           onclick="$('dummy-genericEpoch').innerHTML=$('non-treatmentHtml').innerHTML;RowManager.addRow(genericEpochRowInserterProps)" />
    <br>
    
    </div>

</jsp:attribute>
</tags:tabForm>

<div id="dummy-genericEpoch" style="display:none"></div>

<div id="treatmentHtml" style="display:none">
<table width="100%">
<tr valign="top">
  <td>
      <chrome:deletableDivision divTitle="genericTitle" id="genericEpochBox-PAGE.ROW.INDEX" title="Treatment: " onclick="RowManager.deleteRow(genericEpochRowInserterProps,PAGE.ROW.INDEX,-1)">
<!-- GENERIC START-->

<script language="JavaScript1.2">
  function updateName(string) {
      if ($("treatmentTitle")) {
          $("treatmentTitle").innerHTML = string;
      }
  }
</script>

<table style="border: 0px red dotted;" width="100%">
<tr>
  <td valign="top">

      <table width="100%" border="0" cellspacing="2">
      <tr>
          <td align="right"><b>Name:</b></td>
          <td align="left"><input type="text" name="epochs[PAGE.ROW.INDEX].name" size="43" class="validate-notEmpty" onkeyup="updateName(this.value);" /><tags:hoverHint id="study.treatmentEpoch.name-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.name"/></td>
      </tr>

      <tr>
          <td align="right"> <span class="required-indicator"><b>Order:</b></span></td>
          <td align="left"><input type="text" name="epochs[PAGE.ROW.INDEX].epochOrder" size="5" maxlength="1" class="validate-notEmpty&&numeric" /><tags:hoverHint id="study.treatmentEpoch.epochOrder-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.epochOrder"/></td>
      </tr>

  <c:if test="${command.randomizedIndicator == true}">
      <tr>
              <td align="right"><span class="required-indicator"><b>Randomized:</b></span></td>
              <td align="left">
                  <select name="epochs[PAGE.ROW.INDEX].randomizedIndicator" class="validate-notEmpty">
                      <option value="">Please Select</option>
                      <option value="true" selected="selected">Yes</option>
                      <option value="false">No</option>
                  </select>
                  <tags:hoverHint id="study.treatmentEpoch.randomizedIndicator-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.randomizedIndicator"/>
              </td>
      </tr>
  </c:if>
      </table>

  </td>

  <td valign="top">
      <table width="100%" border="0">
      <tr>
          <td align="right"><b>Description:</b></td>
          <td align="left"><textarea name="epochs[PAGE.ROW.INDEX].descriptionText" rows="5" cols="40"></textarea><tags:hoverHint id="study.treatmentEpoch.description-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.description"/></td>
      </tr>
      </table>
  </td>
</tr>

<tr>
  <td colspan="3" align="left">
      <hr noshade size="1" width="100%">
  <table id="arm" class="tablecontent">
      <tr>
          <th><span class="required-indicator">Arm</span><tags:hoverHint id="study.arm.name-PAGE.ROW.INDEX" keyProp="study.arm.name"/></th>
          <th>Description<tags:hoverHint id="study.arm.description-PAGE.ROW.INDEX" keyProp="study.arm.description"/></th>
          <th>Accrual Ceiling<tags:hoverHint id="study.arm.targetAccrualNumber-PAGE.ROW.INDEX" keyProp="study.arm.targetAccrualNumber"/></th>
          <th><input id="addArm" type="button" value="Add Arm" onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,PAGE.ROW.INDEX));" /></th>
      </tr>
  </table>
  </td>
</tr>

</table>

<input type="hidden" name="epochs[PAGE.ROW.INDEX].displayRole" value="Treatment"/>
<input type="hidden" name="epochs[PAGE.ROW.INDEX].enrollmentIndicator" value="True"/>


<!-- GENERIC END-->
      </chrome:deletableDivision>
  </td>
</tr>
</table>
</div>

<div id="dummy-arm" style="display:none">
    <table id="arm" class="tablecontent" width="50%">
        <tr>
            <td valign="top"><input type="text"  size="43"
                       name="epochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].name"
                       class="validate-notEmpty" value="Arm A" /></td>
            <td><textarea
                       name="epochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].descriptionText"
                       rows="5" cols="40"></textarea></td>
            <td valign="top" align="center"><input type="text"
                       name="epochs[PAGE.ROW.INDEX].arms[NESTED.PAGE.ROW.INDEX].targetAccrualNumber"
                       size="6" maxlength="6" class="validate-numeric" /></td>
            <td valign="top" align="center"><a
                    href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX,-1);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
        </tr>
    </table>
</div>

<div id="non-treatmentHtml" style="display:none">
<table width="100%">
<tr valign="top">
  <td>
      <chrome:deletableDivision divTitle="genericTitle" id="genericEpochBox-PAGE.ROW.INDEX" title="NonTreatment: " onclick="RowManager.deleteRow(genericEpochRowInserterProps,PAGE.ROW.INDEX,-1)">
<!-- GENERIC START-->

<script language="JavaScript1.2">
  function updateName(string) {
      if ($("genericTitle")) {
          $("genericTitle").innerHTML = string;
      }
  }
</script>

<table style="border: 0px red dotted;" width="100%">
<tr>
  <td valign="top">

      <table width="100%" border="0" cellspacing="2">
      <tr>
          <td align="right"><b>Name:</b></td>
          <td align="left"><input type="text" name="epochs[PAGE.ROW.INDEX].name" size="43" class="validate-notEmpty" onkeyup="updateName(this.value);" /><tags:hoverHint id="study.treatmentEpoch.name-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.name"/></td>
      </tr>

      <tr>
          <td align="right"> <span class="required-indicator"><b>Order:</b></span></td>
          <td align="left"><input type="text" name="epochs[PAGE.ROW.INDEX].epochOrder" size="5" maxlength="1" class="validate-notEmpty&&numeric" /><tags:hoverHint id="study.treatmentEpoch.epochOrder-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.epochOrder"/></td>
      </tr>

  <c:if test="${command.randomizedIndicator == true}">
      <tr>
              <td align="right"><span class="required-indicator"><b>Randomized:</b></span></td>
              <td align="left">
                  <select name="epochs[PAGE.ROW.INDEX].randomizedIndicator" class="validate-notEmpty">
                      <option value="">Please Select</option>
                      <option value="true" selected="selected">Yes</option>
                      <option value="false">No</option>
                  </select>
                  <tags:hoverHint id="study.treatmentEpoch.randomizedIndicator-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.randomizedIndicator"/>
              </td>
      </tr>
  </c:if>

      <tr>
          <td align="right"><b>Enrolling:</b></td>
          <td align="left">
              <select id="epochs[PAGE.ROW.INDEX].enrollmentIndicator" name="epochs[PAGE.ROW.INDEX].enrollmentIndicator" onchange="manageEnrollingIndicatorSelectBox(this,PAGE.ROW.INDEX);" class="validate-notEmpty">
                  <option value="">Please Select</option>
                  <option value="true" selected="selected">Yes</option>
                  <option value="false">No</option>
              </select>
              <tags:hoverHint id="study.nonTreatmentEpoch.enrollmentIndicator-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.enrollmentIndicator"/>
          </td>
      </tr>

      </table>

  </td>

  <td valign="bottom">
    <c:if test="${command.epochs[PAGE.ROW.INDEX].enrollmentIndicator}">
          <table width="100%" border="0">
          <tr>
              <td align="right"><div id="reservationIndicatorLabel-PAGE.ROW.INDEX"><b>Reserving:</b></div></td>
              <td align="left">
                  <div id ="reservationIndicator-PAGE.ROW.INDEX">
                      <select id="epochs[PAGE.ROW.INDEX].reservationIndicator" name="epochs[PAGE.ROW.INDEX].reservationIndicator" class="validate-notEmpty">
                           <option value="">Please Select</option>
                           <option value="true">Yes</option>
                           <option value="false" selected="selected">No</option>
                       </select>
                      <tags:hoverHint id="study.nonTreatmentEpoch.reservationIndicator-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.reservationIndicator"/>
                  </div>
              </td>
          </tr>
          </table>
    </c:if> 
  </td>

  <td valign="top">
      <table width="100%" border="0">
      <tr>
          <td align="right"><b>Description:</b></td>
          <td align="left"><textarea name="epochs[PAGE.ROW.INDEX].descriptionText" rows="5" cols="40"></textarea><tags:hoverHint id="study.treatmentEpoch.description-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.description"/></td>
      </tr>
      <tr>
          <td align="right"><b>Accrual Ceiling:</b> </td>
          <td align="left">
              <input type="text" id="epochs[PAGE.ROW.INDEX].accrualCeiling" name="epochs[PAGE.ROW.INDEX].accrualCeiling" size="12" maxlength="5" class="validate-numeric&&nonzero_numeric" />
              <tags:hoverHint id="study.nonTreatmentEpoch.accrualCeiling-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.accrualCeiling"/>
          </td>
      </tr>
      </table>
  </td>
</tr>

</table>

<input type="hidden" name="epochs[PAGE.ROW.INDEX].displayRole" value="NonTreatment"/>


<!-- GENERIC END-->
      </chrome:deletableDivision>
  </td>
</tr>
</table>
</div>

<div id="genericHtml" style="display:none">
<table width="100%">
<tr valign="top">
  <td>
      <chrome:deletableDivision divTitle="genericTitle" id="genericEpochBox-PAGE.ROW.INDEX" title="Generic: " onclick="RowManager.deleteRow(genericEpochRowInserterProps,PAGE.ROW.INDEX,-1)">
<!-- GENERIC START-->

<script language="JavaScript1.2">
  function updateName(string) {
      if ($("genericTitle")) {
          $("genericTitle").innerHTML = string;
      }
  }
</script>

<table style="border: 0px red dotted;" width="100%">
<tr>
  <td valign="top">

      <table width="100%" border="0" cellspacing="2">
      <tr>
          <td align="right"><b>Name:</b></td>
          <td align="left"><input type="text" name="epochs[PAGE.ROW.INDEX].name" size="43" class="validate-notEmpty" onkeyup="updateName(this.value);" /><tags:hoverHint id="study.treatmentEpoch.name-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.name"/></td>
      </tr>

      <tr>
          <td align="right"> <span class="required-indicator"><b>Order:</b></span></td>
          <td align="left"><input type="text" name="epochs[PAGE.ROW.INDEX].epochOrder" size="5" maxlength="1" class="validate-notEmpty&&numeric" /><tags:hoverHint id="study.treatmentEpoch.epochOrder-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.epochOrder"/></td>
      </tr>

  <c:if test="${command.randomizedIndicator == true}">
      <tr>
              <td align="right"><span class="required-indicator"><b>Randomized:</b></span></td>
              <td align="left">
                  <select name="epochs[PAGE.ROW.INDEX].randomizedIndicator" class="validate-notEmpty">
                      <option value="">Please Select</option>
                      <option value="true" selected="selected">Yes</option>
                      <option value="false">No</option>
                  </select>
                  <tags:hoverHint id="study.treatmentEpoch.randomizedIndicator-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.randomizedIndicator"/>
              </td>
      </tr>
  </c:if>

      <tr>
          <td align="right"><b>Enrolling:</b></td>
          <td align="left">
              <select id="epochs[PAGE.ROW.INDEX].enrollmentIndicator" name="epochs[PAGE.ROW.INDEX].enrollmentIndicator" onchange="manageEnrollingIndicatorSelectBox(this,PAGE.ROW.INDEX);" class="validate-notEmpty">
                  <option value="">Please Select</option>
                  <option value="true" selected="selected">Yes</option>
                  <option value="false">No</option>
              </select>
              <tags:hoverHint id="study.nonTreatmentEpoch.enrollmentIndicator-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.enrollmentIndicator"/>
          </td>
      </tr>

      </table>

  </td>

  <td valign="bottom">
    <c:if test="${command.epochs[PAGE.ROW.INDEX].enrollmentIndicator}">
          <table width="100%" border="0">
          <tr>
              <td align="right"><div id="reservationIndicatorLabel-PAGE.ROW.INDEX"><b>Reserving:</b></div></td>
              <td align="left">
                  <div id ="reservationIndicator-PAGE.ROW.INDEX">
                      <select id="epochs[PAGE.ROW.INDEX].reservationIndicator" name="epochs[PAGE.ROW.INDEX].reservationIndicator" class="validate-notEmpty">
                           <option value="">Please Select</option>
                           <option value="true">Yes</option>
                           <option value="false" selected="selected">No</option>
                       </select>
                      <tags:hoverHint id="study.nonTreatmentEpoch.reservationIndicator-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.reservationIndicator"/>
                  </div>
              </td>
          </tr>
          </table>
    </c:if> 
  </td>

  <td valign="top">
      <table width="100%" border="0">
      <tr>
          <td align="right"><b>Description:</b></td>
          <td align="left"><textarea name="epochs[PAGE.ROW.INDEX].descriptionText" rows="5" cols="40"></textarea><tags:hoverHint id="study.treatmentEpoch.description-PAGE.ROW.INDEX" keyProp="study.treatmentEpoch.description"/></td>
      </tr>
      <tr>
          <td align="right"><b>Accrual Ceiling:</b> </td>
          <td align="left">
              <input type="text" id="epochs[PAGE.ROW.INDEX].accrualCeiling" name="epochs[PAGE.ROW.INDEX].accrualCeiling" size="12" maxlength="5" class="validate-numeric&&nonzero_numeric" />
              <tags:hoverHint id="study.nonTreatmentEpoch.accrualCeiling-PAGE.ROW.INDEX" keyProp="study.nonTreatmentEpoch.accrualCeiling"/>
          </td>
      </tr>
      </table>
  </td>
</tr>

<tr>
  <td colspan="3" align="left">
      <hr noshade size="1" width="100%">
  <table id="arm" class="tablecontent">
      <tr>
          <th><span class="required-indicator">Arm</span><tags:hoverHint id="study.arm.name-PAGE.ROW.INDEX" keyProp="study.arm.name"/></th>
          <th>Description<tags:hoverHint id="study.arm.description-PAGE.ROW.INDEX" keyProp="study.arm.description"/></th>
          <th>Accrual Ceiling<tags:hoverHint id="study.arm.targetAccrualNumber-PAGE.ROW.INDEX" keyProp="study.arm.targetAccrualNumber"/></th>
          <th><input id="addArm" type="button" value="Add Arm" onclick="javascript:RowManager.addRow(RowManager.getNestedRowInserter(genericEpochRowInserterProps,PAGE.ROW.INDEX));" /></th>
      </tr>
  </table>
  </td>
</tr>

</table>

<input type="hidden" name="epochs[PAGE.ROW.INDEX].displayRole" value="Generic"/>


<!-- GENERIC END-->
      </chrome:deletableDivision>
  </td>
</tr>
</table>
</div>

</body>
</html>
