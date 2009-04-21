<%@ include file="taglibs.jsp"%>
<html>
<head>
 <title><studyTags:htmlTitle study="${command.study}" /></title>
<style type="text/css">
        .bookContainer { width: 100%;}
</style>
<tags:dwrJavascriptLink objects="BookRandomizationAjaxFacade"/>

<script>
	var globalIndex;
	function uploadBook(form, index, flowType){
		var parameterMap;
		if(index == ""){
			index = globalIndex;
		} else {			
			globalIndex = index;
		}
		var str = "bookRandomizations-" + index;
		var content = "";
		if(form == "command"){
			content = document.getElementById(str).value;
		}
		BookRandomizationAjaxFacade.getTable(parameterMap, content, index, flowType, uploadBookCallback);
	}
	
	function uploadBookCallback(table){
		var str = "bookRandomizationsDisplay-"+ globalIndex; 
		new Element.update(str, table)
	}	
	
	function uploadFile(index){
		<tags:tabMethod method="parseFile" divElement="'bookRandomizationsDisplay-'+index" javaScriptParam="'index='+index"  formName="'epochForm_'+index"/>        
	}
	
	ValidationManager.submitPostProcess= function(formElement, continueSubmission){
		if(formElement.id.indexOf("epochForm_") != -1){
			if(formElement._target != null){
				formElement._target.name = "temp";
			}			
		}
		return continueSubmission;
	}

	function moveToUploadBook(index){
  		$('upload_btn_'+index).className="fifthlevelTab-current";
  		$('insert_btn_'+index).className="fifthlevelTab";
  	
  		$('uploadBook_'+index).style.display="";
  		$('insertBook_'+index).style.display="none";
  	}
  	
  	function moveToInsertBook(index){
  		$('insert_btn_'+index).className="fifthlevelTab-current";
  		$('upload_btn_'+index).className="fifthlevelTab";
  	
  		$('insertBook_'+index).style.display="";
  		$('uploadBook_'+index).style.display="none";
  	}
  		
</script>
</head>
<body>
<tags:instructions code="study_randomizations" />
<c:choose>
<c:when test="${command.study.randomizedIndicator =='false' }">
	<tags:formPanelBox tab="${tab}" flow="${flow}"><br/><br><div align="center"><fmt:message key="STUDY.NO_RANDOMIZATION"/></div><br><br>
	</tags:formPanelBox>
</c:when>
<c:otherwise>

<!--BOOK RANDOMIZATION SECTION-->
<c:if test="${command.study.randomizationType.name == 'BOOK'}">	
	<c:forEach items="${command.study.epochs}" var="epoch" varStatus="epochCount">
		<c:if test="${epoch.randomizedIndicator}">
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
		<div id="book_${epochCount.index}" class="leftpanel" style="border-width:thin; border-style:solid; border-color:grey ; padding:5px; width:48%" >
			<a href="javascript:moveToUploadBook('${epochCount.index}');" id="upload_btn_${epochCount.index}" class="fifthlevelTab-current">
    			<span>Upload Book</span>
    		</a>
    		<a href="javascript:moveToInsertBook('${epochCount.index}');" id="insert_btn_${epochCount.index}" class="fifthlevelTab">
    			<span id="InsertBookSpan">Insert Book</span>
    		</a> 
    		<br>
			<div id="insertBook_${epochCount.index}" style="display: none">   
	     		<input type="hidden" name="index" value="${epochCount.index}"/>       
				<div class="row">
		            <c:choose>
		                <c:when test="${epoch.stratificationIndicator}">
			               	<div class="label" style="margin-top: 6em">
		                		<fmt:message key="study.randomizationBook"/>
		                		<tags:hoverHint keyProp="study.bookRandomizations.text"/>
			               	</div>
		                </c:when>
		                <c:otherwise>
			               	<div class="label" style="margin-top: 6em">
			               		<fmt:message key="study.randomizationBook"/>
			               		<tags:hoverHint keyProp="study.bookRandomizationsWithoutStratification.text"/>
			               	</div>
		                </c:otherwise>
	                </c:choose>
					<div class="value">
						<textarea name="study.bookRandomizations-${epochCount.index}" id="bookRandomizations-${epochCount.index}" cols=25 rows=12 class="validate-notEmpty&&maxlength500"></textarea>
					</div>
				</div>				
	     		<div id="bookButton" align="center">
	     			<tags:button type="button" color="blue" value="Upload Randomization Book" onclick="uploadBook('command', '${epochCount.index}', '${flowType}')" size="small"/>    
		 		</div>
		 	</div>
			<div id="uploadBook_${epochCount.index}">
			 	<form:form method="post" id="epochForm_${epochCount.index}" enctype="multipart/form-data">
			    	<input type="hidden" name="index" value="${epochCount.index}"/>
				        <div class="content">
				            <div class="row">
				                <div class="label"><fmt:message key="study.selectFileToImport"/></div>
				                <div class="value">
				                 <c:choose>
		                			<c:when test="${epoch.stratificationIndicator}">
				                    	<div class="fileinputs"><input type="file" name="file" /><tags:hoverHint keyProp="study.bookRandomizations.file"/></div>
				                 	</c:when>
		                			<c:otherwise>
		                				<div class="fileinputs"><input type="file" name="file" /><tags:hoverHint keyProp="study.bookRandomizationsWithoutStratification.file"/></div>
		                			</c:otherwise>
		                		 </c:choose>
				                </div>
				            </div>
				        </div>
				        <div id="bookButton" align="center">    
				        	<tags:button type="submit" color="blue" value="Upload Randomization File" size="small"/>
						</div> 		        
			    </form:form>
	    	</div>
	    </div>
		<!--  Right hand section for book result section begins -->
		<div id="book_results_${epochCount.index}" class="rightpanel" style="border-width:thin; border-style:solid; border-color:grey; padding:5px; width:48%">
		     <div id="bookRandomizationsDisplay-${epochCount.index}">	
		     	<c:out value="${bookRandomizationEntries[epochCount.index]}" escapeXml="false"/>	    	
		    </div>
		</div>
		<div class="division"></div>		
<%--	will call this to get display onload in edit mode. but currently gives a hibernate exception
		<script>uploadBook("", "${epochCount.index}");</script>		--%>		
		</tags:minimizablePanelBox>	
		</c:if>	
	</c:forEach>	
	
</c:if>
<!--BOOK RANDOMIZATION SECTION-->

<form:form method="post">
<input type="hidden" id="_action" name="_action" value="">
<tags:tabFields tab="${tab}"/>
<!--PHONECALL RANDOMIZATION SECTION-->
<c:if test="${command.study.randomizationType.name == 'PHONE_CALL'}">
	<c:forEach items="${command.study.epochs}" var="epoch" varStatus="epochCount">
	<c:if test="${epoch.randomizedIndicator}">
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
	     <div class="row">
		     <div class="label"><fmt:message key="registration.phoneNumber"/></div>
		     <div class="value">
		     	<form:input path="study.epochs[${epochCount.index}].randomization.phoneNumber" size="20" cssClass="validate-US_PHONE_NO"/>
		     	<tags:hoverHint keyProp="study.randomization.phone"/>
		     </div>
	     </div>
	    </tags:minimizablePanelBox>
	</c:if>
	</c:forEach>
</c:if>
<!--PHONECALL RANDOMIZATION SECTION-->
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>
</c:otherwise>
</c:choose>
	     
</body>
</html>