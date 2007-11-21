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
