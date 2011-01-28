<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <script src="/c3pr/js/timeline/src/webapp/api/timeline-api.js?bundle=false" type="text/javascript"></script>
    <script src="/c3pr/js/timeline/src/ajax/api/simile-ajax-api.js" type="text/javascript"></script>
    <script src="/c3pr/js/timeline/src/ajax/api/simile-ajax-bundle.js" type="text/javascript"></script>
    <script src="/c3pr/js/timeline/src/ajax/api/scripts/signal.js" type="text/javascript"></script>
    <script>
   

    document.observe("dom:loaded", function() {
    	  onLoad();
    	});
        
        // javascript for timeline
        
         window.onresize = function() {
        	 onResize();
    	};
        
        var tl;
        function onLoad() {
        var colors = ['#FF7F50','#F5DEB3','#B8860B','#BDB76B','#FFDEAD','#D2B48C'];
        var numberOfScheduledEpochs = ${fn:length(command.studySubject.scheduledEpochs)};
        for(var i=0;i < numberOfScheduledEpochs; i++){
	        	var divId = 'legend-scheduled_epoch'+ '-' + i;
	        	$(divId).style.backgroundColor  = colors[i];
        }
        var eventSource = new Timeline.DefaultEventSource();
        <c:if test="${not empty command.studySubject.startDate && command.studySubject.regWorkflowStatus != 'PENDING' &&  
        	command.studySubject.regWorkflowStatus != 'RESERVED' && command.studySubject.regWorkflowStatus != 'PENDING_ON_STUDY'}">
	        var evt = new Timeline.DefaultEventSource.Event(
		        		{
		        		textColor: 'black',	
		        		'start':new Date('${command.studySubject.startDateStr}'), //start
		        		'instant':true, //instant
		        		'text':'enrolled', //text
		        		'description':'${command.timeLineDescriptionsOfRegistrationDetails}' //description
		        		}
	        		);
			 eventSource.add(evt);
	 	</c:if>
	 	 <c:if test="${not empty command.studySubject.offStudyDate}">
		        var evt = new Timeline.DefaultEventSource.Event(
			        		{
			        		textColor: 'black',	
			        		'start':new Date('${command.studySubject.offStudyDateStr}'), //start
			        		'instant':true, //instant
			        		'text':'off study', //text
			        		'description':'${command.timeLineDescriptionsOfRegistrationDetails}' //description
			        		}
		        		);
				 eventSource.add(evt);
		 	</c:if>
        <c:forEach items="${command.studySubject.scheduledEpochs}" var="schEpoch" varStatus="schEpochIndex">
	        var evt = new Timeline.DefaultEventSource.Event(
		        		{
		        		'start':new Date('${schEpoch.startDateStr}'), //start
		        		<c:choose>
			        		<c:when test="${not empty schEpoch.offEpochDateStr}">
		        				'end':new Date('${schEpoch.offEpochDateStr}'), //end
		        			</c:when>
			        		<c:otherwise>
			        			'end':new Date(), //end
			        			'isDuration':"true",
			        		</c:otherwise>
		        		</c:choose>
		        		textColor: 'black',	
		        		'color':colors[${schEpochIndex.index}],
		        		'text':'${schEpoch.epoch.name}', //text
		        		'description':'${command.timeLineDescriptionsOfScheduledEpochs[schEpochIndex.index]}'
		        		}
	        		);
			 eventSource.add(evt);
		 </c:forEach>
		 
		 <c:forEach items="${command.studySubject.allSignedConsents}" var="signedConsent" varStatus="signedConsentIndex">
	        var evt = new Timeline.DefaultEventSource.Event(
		        		{
		        		'start':new Date('${signedConsent.informedConsentSignedDateStr}'), //start
		        		textColor: 'black',
		        		'instant':true, //instant
		        		<c:choose>
		        		<c:when test="${signedConsent.consent.studyVersion.originalIndicator}">
		        			'text':'signed ' + '${signedConsent.consent.name}', //text
	        			</c:when>
		        		<c:otherwise>
		        			'text':'signed ' + '${signedConsent.consent.name} (re-consent)', //text
		        		</c:otherwise>
	        			</c:choose>
	        			'description':'${command.timeLineDescriptionsOfSignedConsents[signedConsentIndex.index]}'
		        		}
	        		);
			 eventSource.add(evt);
		 </c:forEach>
		 
		 var zonesTemp = new Array();
			<c:forEach items="${command.studySubject.scheduledEpochs}" var="schEpoch" varStatus="schEpochIndex">
      	 		var hotZone =  
      	 	 		{  	  start :   new Date('${schEpoch.startDateStr}'),
                 		  end:     new Date(new Date('${schEpoch.startDateStr}').getYear(),new Date('${schEpoch.startDateStr}').getMonth(),new Date('${schEpoch.startDateStr}').getDate() +5),
                          magnify:  10,
                	      unit:     Timeline.DateTime.WEEK
            		}
      	 		zonesTemp.push(hotZone);
      		 </c:forEach>
      		 
        
        var theme = Timeline.ClassicTheme.create(); // create the theme
        
        theme.event.bubble.width = 350;   // modify it
        theme.event.bubble.height = 300;
        theme.event.track.height = 15;
        theme.event.tape.height = 8;
        var bandInfos = [
            Timeline.createHotZoneBandInfo({
				zones:zonesTemp,
	        	trackGap:       0.2,
	            eventSource:    eventSource,
	            date:            new Date(new Date().getFullYear(), new Date().getMonth()-8, new Date().getDate()),
	            width:          "80%", 
	            theme:          theme, // Apply the theme
	            intervalUnit:   Timeline.DateTime.MONTH, 
	            intervalPixels: 50
	        }),
	        Timeline.createBandInfo({
	        	trackGap:       0.2,
	            overview:       true,
	            eventSource:    eventSource,
	            date:           new Date(new Date().getFullYear()-2, new Date().getMonth(), new Date().getDate()),
	            width:          "20%", 
	            intervalUnit:   Timeline.DateTime.YEAR, 
	            intervalPixels: 150
	            })
	        ];
	        bandInfos[1].syncWith = 0;
	        bandInfos[1].highlight = true;
        
        for (var i = 0; i < bandInfos.length; i++) {
            bandInfos[i].decorators = new Array();
           	 <c:forEach items="${command.studySubject.scheduledEpochs}" var="schEpoch" varStatus="schEpochIndex">
           	 	var scheduledEpochDecorator =  new Timeline.SpanHighlightDecorator({
           	 	       startDate: new Date('${schEpoch.startDateStr}'),
	           	 	  	 <c:choose>
			        		<c:when test="${not empty schEpoch.offEpochDateStr}">
		        				'endDate':new Date('${schEpoch.offEpochDateStr}'), //end
		        			</c:when>
			        		<c:otherwise>
			        			'endDate':new Date(), //end
			        		</c:otherwise>
		        		</c:choose>
		        		'color':colors[${schEpochIndex.index}]
	        		
                    //   opacity:    50
                     //  theme:      theme
                     //  cssClass: 't-highlight1'
                   });     	 
           	   bandInfos[i].decorators.push(scheduledEpochDecorator);
           	 </c:forEach>
        }
        tl = Timeline.create(document.getElementById("my-timeline"), bandInfos);
        setupFilterHighlightControls(document.getElementById("controls"), tl, [0,1], theme);

        }

        var resizeTimerID = null;
        function onResize() {
            if (resizeTimerID == null) {
                resizeTimerID = window.setTimeout(function() {
                    resizeTimerID = null;
                    tl.layout();
                }, 500);
            }
        }
    </script>
</head>
<body>
<br/>

<chrome:division>
 	<div id="my-timeline" style="height: 300px; border: 1px solid #aaa"></div>
	<noscript>
		This page uses Javascript to show you a Timeline. Please enable Javascript in your browser to see the full page. Thank you.
	</noscript>
	<div class="value" id="my-legend" style="height: 300px; border: 1px solid #aaa">
		<div class="leftpanel">
			<c:forEach items="${command.studySubject.scheduledEpochs}" var="schEpoch" varStatus="schEpochIndex">
				<c:if test="${not empty schEpoch.startDate}">
					<div class="row">
						<div class="label" style="width: 15px; height: 15px;" id="legend-scheduled_epoch-${schEpochIndex.index}">&nbsp;</div>
						<div align="left">&nbsp;&nbsp;${schEpoch.epoch.name} (${schEpoch.epoch.type.displayName})</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
</chrome:division>
</body>
</html>
