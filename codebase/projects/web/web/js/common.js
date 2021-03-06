// Namespace for C3PR-specific shared functions and classes
var C3PR = { }

C3PR.INDICATOR_REF_COUNTS = { };

// this stuff should technically be synchronized.  Let see if it causes a problem.
C3PR.showIndicator = function(id) {
    if (!C3PR.INDICATOR_REF_COUNTS[id]) C3PR.INDICATOR_REF_COUNTS[id] = 0;
    C3PR.INDICATOR_REF_COUNTS[id] += 1
    C3PR.updateIndicatorVisibility(id)
}

C3PR.hideIndicator = function(id) {
    if (!C3PR.INDICATOR_REF_COUNTS[id]) C3PR.INDICATOR_REF_COUNTS[id] = 0;
    C3PR.INDICATOR_REF_COUNTS[id] -= 1;
    if (C3PR.INDICATOR_REF_COUNTS[id] < 0) C3PR.INDICATOR_REF_COUNTS[id] = 0;
    C3PR.updateIndicatorVisibility(id)
}

C3PR.updateIndicatorVisibility = function(id) {
    if (C3PR.INDICATOR_REF_COUNTS[id] > 0) {
        $(id).reveal();
    } else {
        $(id).conceal();
    }
}

////// PROTOTYPE EXTENSIONS
// TODO: This code is shared with PSC.

Element.addMethods( {
    // Like prototype's hide(), but uses the visibility CSS prop instead of display
    conceal: function() {
        for (var i = 0; i < arguments.length; i++) {
          var element = $(arguments[i]);
          element.style.visibility = 'hidden';
        }
    },

    // Like prototype's show(), but uses the visibility CSS prop instead of display
    reveal: function() {
        for (var i = 0; i < arguments.length; i++) {
          var element = $(arguments[i]);
          element.style.visibility = 'visible';
        }
    },
    
    getOffsetParent: function(element) {
	    if (element.offsetParent) return $(element.offsetParent);
	    if (element == document.body) return $(element);
	    
	    if(Prototype.Browser.IE){
	    	while ((element = element.parentNode) && element != document.body && element != document)
	  	      if (Element.getStyle(element, 'position') != 'static')
	  	        return $(element);
	    }else{
	    	while ((element = element.parentNode) && element != document.body )
	  	      if (Element.getStyle(element, 'position') != 'static')
	  	        return $(element);
	    }
	
	    return $(document.body);
  	}
} );

////// CALENDAR POPUP HANDLERS

Element.observe(window, "load", function() {
    $$("input.date").each(function(input) {
        var anchorId = input.id + "-calbutton"
        Calendar.setup(
            {
                inputField  : input.id,
                button      : anchorId,
                ifFormat    : "%m/%d/%Y", // TODO: get this from the configuration
                weekNumbers : false
            }
        );
    })
});

C3PR.printElement=function(id){
	str=$(id).innerHTML
	newwin=window.open('','printwin','left=100,top=100,width=400,height=400')
	newwin.document.write('<HTML>\n<HEAD>\n')
	newwin.document.write('<TITLE>Print Page</TITLE>\n')
	newwin.document.write('<script>\n')
	newwin.document.write('function chkstate(){\n')
	newwin.document.write('if(document.readyState=="complete"){\n')
	newwin.document.write('window.close()\n')
	newwin.document.write('}\n')
	newwin.document.write('else{\n')
	newwin.document.write('setTimeout("chkstate()",2000)\n')
	newwin.document.write('}\n')
	newwin.document.write('}\n')
	newwin.document.write('function print_win(){\n')
	newwin.document.write('window.print();\n')
	newwin.document.write('chkstate();\n')
	newwin.document.write('}\n')
	newwin.document.write('<\/script>\n')
	newwin.document.write('</HEAD>\n')
	newwin.document.write('<BODY onload="print_win()">\n')
	newwin.document.write(str)
	newwin.document.write('</BODY>\n')
	newwin.document.write('</HTML>\n')
	newwin.document.close()
}

C3PR.printText=function(str){
	newwin=window.open('','printwin','left=100,top=100,width=400,height=400')
	newwin.document.write('<HTML>\n<HEAD>\n')
	newwin.document.write('<TITLE>Print Page</TITLE>\n')
	newwin.document.write('<script>\n')
	newwin.document.write('function chkstate(){\n')
	newwin.document.write('if(document.readyState=="complete"){\n')
	newwin.document.write('window.close()\n')
	newwin.document.write('}\n')
	newwin.document.write('else{\n')
	newwin.document.write('setTimeout("chkstate()",2000)\n')
	newwin.document.write('}\n')
	newwin.document.write('}\n')
	newwin.document.write('function print_win(){\n')
	newwin.document.write('window.print();\n')
	newwin.document.write('chkstate();\n')
	newwin.document.write('}\n')
	newwin.document.write('<\/script>\n')
	newwin.document.write('</HEAD>\n')
	newwin.document.write('<BODY onload="print_win()">\n')
	newwin.document.write(str)
	newwin.document.write('</BODY>\n')
	newwin.document.write('</HTML>\n')
	newwin.document.close()
}

C3PR.showCCTSError=function(){
	$('built-cctsErrorMessage').innerHTML=C3PR.buildCCTSErrorHtml($('cctsErrorMessage').innerHTML)
	//Lightview.show({ href: 'built-cctsErrorMessage', rel: 'inline', options: { width: 800, height: 500 }})
	var win = new Window({className: "dialog", width:350, height:400, zIndex: 100, resizable: true, title: "CCTS Error Messages", showEffect:Effect.BlindDown, hideEffect: Effect.SwitchOff, draggable:true, wiredDrag: true});
	win.getContent().innerHTML= $('built-cctsErrorMessage').innerHTML;
	win.setStatusBar("Error"); 
	win.showCenter();
}

C3PR.showAsMessage=function(title, divId){
	var win = new Window({className: "dialog", width:350, height:400, zIndex: 100, resizable: true, title: title, showEffect:Effect.BlindDown, hideEffect: Effect.SwitchOff, draggable:true, wiredDrag: true});
	win.getContent().innerHTML= $(divId).innerHTML;
	win.setStatusBar("Message"); 
	win.showCenter();
}

C3PR.buildCCTSErrorHtml=function(error){
	appFlag=true
	errorHtml="<table>"
	appErrors=error.split("%%%%")
	for (appErrorCount=0 ; appErrorCount<appErrors.length ; appErrorCount++){
		appError=appErrors[appErrorCount]
		if(appError.indexOf("||||")!=-1){
			errorValues=appError.split("||||")
			appName=errorValues[0]
			errorString=errorValues[1]
			errorHtml+="<tr><td width='20%'><b>"+appName+":<b></td><td>"+errorString+"</td></tr>"
			appFlag=false
		}
	}
	if(appFlag)
		errorHtml+="<tr><td><b>There are no error messages recorded from the integration hub.</b></td></tr>"
	errorHtml+="</table>"
	return errorHtml
}
var BrowserDetect = {
	init: function () {
		this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
		this.version = this.searchVersion(navigator.userAgent)
			|| this.searchVersion(navigator.appVersion)
			|| "an unknown version";
		this.OS = this.searchString(this.dataOS) || "an unknown OS";
	},
	searchString: function (data) {
		for (var i=0;i<data.length;i++)	{
			var dataString = data[i].string;
			var dataProp = data[i].prop;
			this.versionSearchString = data[i].versionSearch || data[i].identity;
			if (dataString) {
				if (dataString.indexOf(data[i].subString) != -1)
					return data[i].identity;
			}
			else if (dataProp)
				return data[i].identity;
		}
	},
	searchVersion: function (dataString) {
		var index = dataString.indexOf(this.versionSearchString);
		if (index == -1) return;
		return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
	},
	dataBrowser: [
		{
			string: navigator.userAgent,
			subString: "Chrome",
			identity: "Chrome"
		},
		{ 	string: navigator.userAgent,
			subString: "OmniWeb",
			versionSearch: "OmniWeb/",
			identity: "OmniWeb"
		},
		{
			string: navigator.vendor,
			subString: "Apple",
			identity: "Safari"
		},
		{
			prop: window.opera,
			identity: "Opera"
		},
		{
			string: navigator.vendor,
			subString: "iCab",
			identity: "iCab"
		},
		{
			string: navigator.vendor,
			subString: "KDE",
			identity: "Konqueror"
		},
		{
			string: navigator.userAgent,
			subString: "Firefox",
			identity: "Firefox"
		},
		{
			string: navigator.vendor,
			subString: "Camino",
			identity: "Camino"
		},
		{		// for newer Netscapes (6+)
			string: navigator.userAgent,
			subString: "Netscape",
			identity: "Netscape"
		},
		{
			string: navigator.userAgent,
			subString: "MSIE",
			identity: "Explorer",
			versionSearch: "MSIE"
		},
		{
			string: navigator.userAgent,
			subString: "Gecko",
			identity: "Mozilla",
			versionSearch: "rv"
		},
		{ 		// for older Netscapes (4-)
			string: navigator.userAgent,
			subString: "Mozilla",
			identity: "Netscape",
			versionSearch: "Mozilla"
		}
	],
	dataOS : [
		{
			string: navigator.platform,
			subString: "Win",
			identity: "Windows"
		},
		{
			string: navigator.platform,
			subString: "Mac",
			identity: "Mac"
		},
		{
			string: navigator.platform,
			subString: "Linux",
			identity: "Linux"
		}
	]

};
BrowserDetect.init();
function updateHelpLink(baseUrl, linkName){
	if($('help') != null){
		$('help').href= baseUrl + "#" + linkName;
	}
}
C3PR.handleAjaxError= function(xmlResponse){
									Dialog.alert(xmlResponse.responseText, 
						             {width:600, height:600, okLabel: "close", 
						              ok:function(win) {return true;}});
								}
								
//Event.observe(window, "load", function (){
//	BrowserDetect.browser=='Explorer'?$$('.required-indicator').each(function(element){element.update("<span style='color:#900;'>*</span> "+element.innerHTML)}):null;
//})
function launchPrint(){
			var windowRef = window.open('/c3pr/print_view.jsp', 'Print', 'scrollbars=yes,menubar=yes,width=730,height=600,toolbar=no');
			windowRef.focus();
		}

function confirmationMessage(message){
	Dialog.confirm(message, {className: "alphacube", width: 500,  okLabel: "Ok", cancel:function(win){return false;}, ok:function(win) {return true;}});
}

function openInfoDialog()
{
	if(C3PR.disableAjaxLoadingIndicator) return; 
	var elIndicator =  $('ajax-loading-indictor');
	 if(elIndicator) {
	 	Insertion.Top($$('body')[0],'<div id="overlay_modal" class="overlay_mac_os_x" style="position: absolute; top: 0pt; left: 0pt; z-index: 101; width: 100%; height: 1075px; opacity: 0.6;"/>');
	 	elIndicator.show();
    }
}

Event.observe(window, "load", function (){
	var elIndicator =  $('ajax-loading-indictor');
	 if(elIndicator) {
	 	Element.remove("overlay_modal");
	 	elIndicator.style.display="none";
    }
})

//updates the forms action, by chopping off the query string in action.
var FormQueryStringUtils = Class.create({
	initialize: function(frm) {
		this.form = frm;
	    this.urlString = frm.action;
	    queryIndex = this.urlString.indexOf('?');
	    if(queryIndex > 0){
	        this.actionURL = this.urlString.substring(0, queryIndex);
	    }else{
	    	this.actionURL = this.urlString;
	    }
	},
	stripAllQueryString: function(){
		this.form.action = this.actionURL;
	},
	stripQueryString: function(paramString){
		this.form.action = this.urlString.replace(paramString,"");
	}
});
