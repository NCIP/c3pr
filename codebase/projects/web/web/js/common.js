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
