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
		<div id="book_container_${epochCount.index}" class="bookContainer">
		<chrome:box title="${epoch.name}" id="book_${epochCount.index}" cssClass="paired"> 
		<br/>
	     <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }">  
	     <input type="hidden" name="index" value="${epochCount.index}"/>       
             <tr>
                <td>
	                <c:choose>
	                <c:when test="${epoch.stratificationIndicator}">
	                	<b> <fmt:message key="study.randomizationBook"/></b><tags:hoverHint keyProp="study.bookRandomizations.text"/>
	                	<br/> <fmt:message key="study.egBookRandomizationWithStratification"/></td>
	                </c:when>
	                <c:otherwise>
	                	<b> <fmt:message key="study.randomizationBook"/></b><tags:hoverHint keyProp="study.bookRandomizationsWithoutStratification.text"/>
	                	<br/> <fmt:message key="study.egBookRandomizationWithoutStratification"/></td>
	                </c:otherwise>
	                </c:choose>
				<td>
					<TEXTAREA name="study.bookRandomizations-${epochCount.index}" id="bookRandomizations-${epochCount.index}" cols=25 rows=12 class="validate-notEmpty&&maxlength500"></TEXTAREA>
				</td>				
             </tr>
	     </table>
	     <br/>
	     <div id="bookButton" align="center">
	     <tags:button type="button" color="blue" value="Upload Randomization Book" 
			onclick="uploadBook('command', '${epochCount.index}', '${flowType}')" size="small"/>    
		 </div>
		 <hr />
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
					</div><br/> 		        
		    </form:form>
	    </chrome:box>	    

		<chrome:box title="${epoch.name}" id="book_results_${epochCount.index}" cssClass="paired">
		     <div id="bookRandomizationsDisplay-${epochCount.index}">	
		     	<c:out value="${bookRandomizationEntries[epochCount.index]}" escapeXml="false"/>	    	
		    </div>
		</chrome:box>
		</div>
		
<%--	will call this to get display onload in edit mode. but currently gives a hibernate exception
		<script>uploadBook("", "${epochCount.index}");</script>		--%>		
		<script>$('book_container_${epochCount.index}').style.height=new String((50+$('book_${epochCount.index}').offsetHeight)+"px")</script>	
		</c:if>	
	</c:forEach>	
	
</c:if>
<!--BOOK RANDOMIZATION SECTION-->

<form:form method="post">
<input type="hidden" id="_action" name="_action" value="">
<tags:tabFields tab="${tab}"/>

<!--CALLOUT RANDOMIZATION SECTION-->
<c:if test="${command.study.randomizationType.name == 'CALL_OUT'}">
	<c:forEach items="${command.study.epochs}" var="epoch" varStatus="epochCount">
	<c:if test="${epoch.randomizedIndicator}">
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
		<br/>
	     <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }">         
             <tr>
                <td><b>Call-Out URL:</b></td>
				<td>
				<form:input path="study.epochs[${epochCount.index}].randomization.calloutUrl" size="30" /> e.g. http://www.callout-url.com
				</td>				
             </tr>
	     </table>
	     <br/>
	    </tags:minimizablePanelBox>
	</c:if>
	</c:forEach>
</c:if>
<!--CALLOUT RANDOMIZATION SECTION-->

<!--PHONECALL RANDOMIZATION SECTION-->
<c:if test="${command.study.randomizationType.name == 'PHONE_CALL'}">
	<c:forEach items="${command.study.epochs}" var="epoch" varStatus="epochCount">
	<c:if test="${epoch.randomizedIndicator}">
		<tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
		<br/>
	     <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }">         
             <tr>
                <td><b><fmt:message key="registration.phoneNumber"/></b></td>
				<td><form:input path="study.epochs[${epochCount.index}].randomization.phoneNumber" size="20" cssClass="validate-US_PHONE_NO"/> 7035600296 or 703-560-0296
				</td>				
             </tr>
	     </table>
	     <br/>
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