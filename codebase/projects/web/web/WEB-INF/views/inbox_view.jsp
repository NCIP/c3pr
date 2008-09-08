<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- author: Vinay G --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>C3PR Inbox Page</title>

    <c:set var="bgcolorSelected" value="#cccccc"/>
    <c:set var="bgcolorAlternate" value="#eeeeee"/>
    <c:set var="bgcolor" value="#ffffff"/>

	<script>
		function showMessageBody(id, rsnId, rowId, textId, title){
				markAsRead(rowId, rsnId, textId, title);
	     		var win = new Window({className: "dialog", width:710, height:350, zIndex: 100, resizable: true, title:"Email Message", 
	        	showEffect:Effect.BlindDown, hideEffect: Effect.BlindUp, draggable:true, wiredDrag: true}); 
	        	win.getContent().innerHTML = $(id).innerHTML;
		     	win.showCenter();
	     }  
	     
	     function markAsRead(rowId, rsnId, textId, title){
	     	if(textId != '' && title != ''){
	     		$(textId).innerHTML = title;
	     	}
	     	$(rowId).style.backgroundColor = "#eeeeee";
	     	new Ajax.Request('../admin/viewInbox?rsnId='+rsnId, {method:'get', asynchronous:true});
	     } 
	</script>
</head>
<body>   

	  <chrome:box title="C3PR Notifications">
          <c:choose> 
           <c:when test="${empty recipientScheduledNotification || fn:length(recipientScheduledNotification) == 0}">
           		<br />You don't have any notifications.
           </c:when>
           <c:otherwise>
           		<table id="rsnTable" width="100%" cellspacing="1" cellpadding="2">
                    <tr bgcolor="${bgcolorAlternate}">
                        <td width="65%"><b>Title</b></td>
                        <td width="35%"><b>Date</b></td>
                    </tr>
                    <c:forEach var="rsn" items="${recipientScheduledNotification}" varStatus="rsnStatus" >
						<c:if test="${!empty rsn.scheduledNotification.title || !empty rsn.scheduledNotification.message}">
		                    <!-- Unread emails -->    
		                    <c:if test="${!rsn.isRead}">    
		                        <tr id="row-${rsnStatus.index}" bgcolor="${bgcolor}">
		                            <td><a id="text-${rsnStatus.index}" href="javascript:showMessageBody('messageDetails-${rsnStatus.index}','${rsn.id}','row-${rsnStatus.index}','text-${rsnStatus.index}','${rsn.scheduledNotification.title}')">
		                            		<b><c:out value="${rsn.scheduledNotification.title}" /></b>
		                            	</a>
		                            </td>
		                            <td><fmt:formatDate value="${rsn.scheduledNotification.dateSent}" pattern="MM/dd/yyyy"/></td>
		                        </tr>
		                    </c:if>
		                    <!-- Unread emails -->
		                    <!-- emails that have been viewed -->    
		                    <c:if test="${rsn.isRead}">    
		                        <tr id="row-${rsnStatus.index}" bgcolor="${bgcolorAlternate}">
		                            <td><a href="javascript:showMessageBody('messageDetails-${rsnStatus.index}','${rsn.id}','row-${rsnStatus.index}','','')">
		                            		<c:out value="${rsn.scheduledNotification.title}" /></a>
		                            </td>
		                            <td><fmt:formatDate value="${rsn.scheduledNotification.dateSent}" pattern="MM/dd/yyyy"/></td>
		                        </tr>
		                    </c:if>
		                    <!-- emails that have been viewed --> 
	                        <tr style="display:none;"><td>
	                        	<div id="messageDetails-${rsnStatus.index}">
	                        		<div>
										<table width="50%">
										<tr><td><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
											<tr><td align="right" >
												<input type="button" value="Print" onClick="javascript:C3PR.printElement('printable');"/>
											</td></tr>
										</table>
									</div>
	                        		<div id="printable">
	                            		<table>
											<tr><td width="15%" align="right" style="font-size: 11px;"><b>Subject Line:</b></td> 								 
											<td style="font-size: 11px;">
												<!-- <input type="text" name="title" value="${rsn.scheduledNotification.title}" size="50" class="width:96%;" 
													onfocus="lastElement = this;" />  -->
												 ${rsn.scheduledNotification.title}
											</td></tr>
												
											<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="10" align="middle" class="spacer"></td></tr>
											<tr><td valign="top" align="right" style="font-size: 11px;"><b>Message:</b></td>
												<td style="font-size: 11px;">${rsn.scheduledNotification.htmlMessage}
												</td>
											</tr>
											<tr><td colspan="2"><img src="<tags:imageUrl name="spacer.gif"/>" width="1" height="20" align="middle" class="spacer"></td></tr>
										</table>
									</div>
	                             </div></td>
	                        </tr>
                       </c:if>
                    </c:forEach>
                </table>
           </c:otherwise>
         </c:choose>
        </chrome:box>
        
    <div id="dummyDiv" style="display:none">
	</div>
	<%--<chrome:box title="Results">
	    <chrome:division id="single-fields">
	        <div id="tableDiv">
	   			<c:out value="${assembler}" escapeXml="false"/> 
			</div>
		</chrome:division>
	</chrome:box>
	--%>

</body>
</html>
