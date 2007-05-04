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

////// SSO

Event.observe(window, "load", function() {
    $$("a.sso").each(function(a) {
        Event.observe(a, "click", function(e) {
            Event.stop(e)
            var ssoForm = $('sso-form')
            ssoForm.action = a.href
            ssoForm.submit()
        })
    })
})
Effect.OpenUp = function(element) {
     element = $(element);
     new Effect.BlindDown(element, arguments[1] || {});
}

Effect.CloseDown = function(element) {
	element = $(element);
	new Effect.BlindUp(element, arguments[1] || {});
}

Effect.Combo = function(element) {
    element = $(element);
    if (element.style.display == 'none') {
        new Effect.OpenUp(element, arguments[1] || {});
    } else {
        new Effect.CloseDown(element, arguments[1] || {});
    }
}
function PanelCombo(element) {
    panelDiv = $(element);
    imageId= element+'-image';
    imageSource=document.getElementById(imageId).src;
    if (panelDiv.style.display == 'none') {
        new Effect.OpenUp(panelDiv, arguments[1] || {});
        document.getElementById(imageId).src=imageSource.replace('minimize','maximize');
    } else {
        new Effect.CloseDown(panelDiv, arguments[1] || {});
        document.getElementById(imageId).src=imageSource.replace('maximize','minimize');
    }
}
function displayDiv(id,flag){
	if(flag=='true'){
		document.getElementById(id).style.display='block';
	}else
		document.getElementById(id).style.display='none';	
}
