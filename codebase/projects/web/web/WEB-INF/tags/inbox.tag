<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="recipientScheduledNotification" type="java.util.Collection"  required="true"%>
<%@attribute name="url" required="true"%>
<%@attribute name="htmlContent"%>
<%@attribute name="endValue" required="false"%>
<%@attribute name="canDelete" required="false"%>

<link href="themes/mac_os_x.css" rel="stylesheet" type="text/css"/> 

<c:set var="bgcolorSelected" value="#cccccc" />
<c:set var="bgcolorAlternate" value="#eeeeee" />
<c:set var="bgcolorRead" value="#ffffff" />
<c:set var="bgcolor" value="#fff8aa" />
<script type="text/css">
	.eXtremeTable .toolbar {
		background-color:#F4F4F4;
		border:1px solid silver;
		font-family:verdana,arial,helvetica,sans-serif;
		font-size:9px;
		margin-right:1px;
	}
</script>
<script>
		function showMessageBody(index, rsnId, title, url){
				var id = "messageDetails-" + index;
				var rowId = "row-" + index;
				var textId = "text-" + index;
			
				markAsRead(rowId, rsnId, textId, title, url);
	     		var win = new Window({className: "mac_os_x", width:710, height:350, zIndex: 100, resizable: true, title:"Email Message", 
	        	showEffect:Effect.BlindDown, hideEffect: Effect.BlindUp, draggable:true, wiredDrag: true}); 
	        	win.getContent().innerHTML = $(id).innerHTML;
		     	win.showCenter();
	     }  
	     
	     function markAsRead(rowId, rsnId, textId, title, url){
	     	if(textId != '' && title != ''){
	     		$(textId).innerHTML = title;
	     	}
	     	$(rowId).style.backgroundColor = "#ffffff";
	     	new Ajax.Request(url + '?rsnId='+rsnId, {method:'get', asynchronous:true});
	     } 
	     
	     function deleteRow(rowId, rsnId){
	     	if(confirmationMessage("Are you sure you want to delete this message?")){
	     		//removing from view
	     		var theTableBody = $('rsnTable').tBodies[0];
		     	var x = $(rowId).rowIndex;
		     	theTableBody.deleteRow(x);
		     	//removing from command object
		     	var parameterString = "?rsnId=" + rsnId + "&delete=true";
		     	new Ajax.Request("../admin/viewInbox" + parameterString, {method:'get', asynchronous:true});
	     	}
	     }
	     
	</script>

<chrome:box title="Notifications" htmlContent='${htmlContent}'>
	<c:choose>
		<c:when	test="${empty recipientScheduledNotification || fn:length(recipientScheduledNotification) == 0}">
			<br /><b>You don't have any notifications.</b>
          </c:when>
		<c:otherwise>
			<c:set var="numOfMessagesOnPage" value="5" />
			<c:if test="${empty endValue}">
				<c:set var="startValue" value="1" />
				<c:set var="endValue" value="5" />
			</c:if>
			
			<c:if test="${empty canDelete}">
				<c:set var="canDelete" value="false" />
			</c:if>
			
			<c:set var="prevStartValue" value="${startValue - numOfMessagesOnPage}" />
			<c:set var="prevEndValue" value="${startValue - 1}" />
			<c:set var="nextStartValue" value="${endValue + 1}" />
			<c:set var="nextEndValue" value="${endValue + numOfMessagesOnPage>fn:length(recipientScheduledNotification)?fn:length(recipientScheduledNotification):endValue + numOfMessagesOnPage}" />
			
			<%-- ${startValue} and ${endValue} |	${prevStartValue} and ${prevEndValue} |	${nextStartValue} and ${nextEndValue} --%>
			
			<table id="rsnTable" width="100%" cellspacing="1" cellpadding="2">
				<c:if test="${canDelete != 'false'}">
				<tr>
				<td colspan="1" align="left" valign="bottom">
					<b>${startValue} - ${endValue}</b> of <b>${fn:length(recipientScheduledNotification)}</b>
				</td>
				<td colspan="2" align="right">
				<div class="eXtremeTable">
					<table cellspacing="1" cellpadding="0" border="0" class="toolbar">
						<tbody><tr>
							<td>
								<c:choose>
									<c:when test="${(startValue - numOfMessagesOnPage) > 0}">
										<a href='viewInbox?startValue=1&endValue=${numOfMessagesOnPage}'>
											<img alt="First" style="border: 0pt none ;" src="/c3pr/images/table/firstPage.gif"/>
										</a>
									</c:when>
									<c:otherwise>
										<img alt="First" style="border: 0pt none ;" src="/c3pr/images/table/firstPageDisabled.gif"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${(startValue - numOfMessagesOnPage) > 0}">
										<a href='viewInbox?startValue=${prevStartValue}&endValue=${prevEndValue}'>
											<img alt="Prev" style="border: 0pt none ;" src="/c3pr/images/table/prevPage.gif"/>
										</a>
									</c:when>
									<c:otherwise>
										<img alt="Prev" style="border: 0pt none ;" src="/c3pr/images/table/prevPageDisabled.gif"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${endValue < fn:length(recipientScheduledNotification)}">
										<a href='viewInbox?startValue=${nextStartValue}&endValue=${nextEndValue}'>
											<img alt="Next" style="border: 0pt none ;" src="/c3pr/images/table/nextPage.gif"/>
										</a>
									</c:when>
									<c:otherwise>
										<img alt="Next" style="border: 0pt none ;" src="/c3pr/images/table/nextPageDisabled.gif"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${endValue < fn:length(recipientScheduledNotification)}">
										<c:choose>
										<c:when test="${fn:length(recipientScheduledNotification) - fn:length(recipientScheduledNotification) % numOfMessagesOnPage + 1 > fn:length(recipientScheduledNotification)}">
											<a href='viewInbox?startValue=${fn:length(recipientScheduledNotification) - numOfMessagesOnPage + 1}&endValue=${fn:length(recipientScheduledNotification)}'>
												<img alt="Last" style="border: 0pt none ;" src="/c3pr/images/table/lastPage.gif"/>
											</a>
										</c:when>
										<c:otherwise>
											<a href='viewInbox?startValue=${fn:length(recipientScheduledNotification) - fn:length(recipientScheduledNotification) % numOfMessagesOnPage + 1}&endValue=${fn:length(recipientScheduledNotification)}'>
												<img alt="Last" style="border: 0pt none ;" src="/c3pr/images/table/lastPage.gif"/>
											</a>
										</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<img alt="Last" style="border: 0pt none ;" src="/c3pr/images/table/lastPageDisabled.gif"/>
									</c:otherwise>
								</c:choose>
							</td>
							</tr>
						</tbody>
					</table>
				</div>
				</td>
				</tr>
				</c:if>
				<tr bgcolor="${bgcolorAlternate}">
					<td><b>Title</b></td>
					<td width="20%"><b>Date</b></td>
					<c:if test="${canDelete != 'false'}">
						<td width="10%"></td>
					</c:if>
				</tr>
				<c:forEach var="rsn" items="${recipientScheduledNotification}" begin="${startValue}" end="${endValue}"
					varStatus="rsnStatus">
						<!-- Unread emails -->
						<c:if test="${!rsn.isRead}">
							<tr id="row-${rsnStatus.index}" bgcolor="${bgcolor}">
								<td><a id="text-${rsnStatus.index}"
									href="javascript:showMessageBody('${rsnStatus.index}','${rsn.id}','${rsn.scheduledNotification.title}','${url}')">
								<b><c:out value="${rsn.scheduledNotification.title}" /></b> </a></td>
								<td><fmt:formatDate
									value="${rsn.scheduledNotification.dateSent}"
									pattern="MM/dd/yyyy" /></td>
								<c:if test="${canDelete != 'false'}">
									<td align="center"><a href="javascript:deleteRow('row-${rsnStatus.index}', '${rsn.id}');">
										<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
									</td>
								</c:if>
							</tr>
						</c:if>
						<!-- Unread emails -->
						
						<!-- emails that have been viewed -->
						<c:if test="${rsn.isRead}">
							<tr id="row-${rsnStatus.index}" bgcolor="${bgcolorRead}">
								<td><a
									href="javascript:showMessageBody('${rsnStatus.index}','${rsn.id}','','${url}')">
								<c:out value="${rsn.scheduledNotification.title}" /></a></td>
								<td><fmt:formatDate
									value="${rsn.scheduledNotification.dateSent}"
									pattern="MM/dd/yyyy" /></td>
								<c:if test="${canDelete != 'false'}">
									<td align="center"><a href="javascript:deleteRow('row-${rsnStatus.index}', '${rsn.id}');">
										<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
									</td>
								</c:if>
							</tr>
						</c:if>
						<!-- emails that have been viewed -->
						
						<tr style="display:none;">
							<td colspan="3">
							<div id="messageDetails-${rsnStatus.index}">
								<div>
									<table width="80%">
										<tr>
											<td><img src="<tags:imageUrl name="spacer.gif"/>"
												width="1" height="20" align="middle" class="spacer"></td>
										</tr>
										<tr>
											<td align="right"><input type="button" value="Print"
												onClick="javascript:C3PR.printElement('printable');" /></td>
										</tr>
									</table>
								</div>
								<div id="printable">
									<table width="80%">
										<tr>
											<td width="20%" align="right" style="font-size: 11px;"><b>Subject :</b></td>
											<td style="font-size:11px; outline-color:black; outline-width:thin; outline-style:solid;"">
											<!-- <input type="text" name="title" value="${rsn.scheduledNotification.title}" size="50" class="width:96%;" 
														onfocus="lastElement = this;" />  -->
											&nbsp;${rsn.scheduledNotification.title}</td>
										</tr>
		
										<tr>
											<td colspan="2"><img
												src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10"
												align="middle" class="spacer"></td>
										</tr>
										<tr>
											<td valign="top" align="right" style="font-size: 11px;"><b>Message :</b></td>
											<td valign="top" height="100" style="font-size: 11px; outline-color: black; outline-width: thin; outline-style: solid;">
											<div>&nbsp;${rsn.scheduledNotification.htmlMessage}</div>
											</td>
										</tr>
										<tr>
											<td colspan="2"><img
												src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20"
												align="middle" class="spacer"></td>
										</tr>
									</table>
								</div>
							</div>
							</td>
						</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</chrome:box>
