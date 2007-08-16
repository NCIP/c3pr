/*
 *  C3PR-local classes & functions which require scriptaculous.
 */

////// JS STYLES

C3PR.slideAndHide = function(element, options) {
    var e = $(element);
    new Effect.Parallel(
        [
            new Effect.BlindUp(e, {sync:true}),
            new Effect.Fade(e, {sync:true})
        ], $H(options).merge({
            duration: 1.0
        })
    );
}

C3PR.slideAndShow = function(element, options) {
    var e = $(element);
    new Effect.Parallel(
        [
            new Effect.BlindDown(e, {sync:true}),
            new Effect.Appear(e, {sync:true})
        ], $H({
            duration: 1.0
        }).merge(options)
    );
}

C3PR.highlight = function(element, options) {
    var e = $(element)
    new Effect.Highlight(element, Object.extend({
        restorecolor: "#ffffff"
    }, $H(options)));
}

// This is based on the code from https://dwr.dev.java.net/servlets/ReadMsg?list=users&msgNo=2629
// Differences:  this version includes records whose display value does not include the typed string
//    (this allows the server to match on multiple fields at once)
Autocompleter.DWR = Class.create();
Autocompleter.DWR.prototype = Object.extend(new Autocompleter.Base(), {
    initialize: function(element, update, populator, options) {
        this.baseInitialize(element, update, options);
        this.options.array = new Array(0);
        this.populator = populator;
        if (this.options.afterUpdateElement) {
            this.afterUpdateCallback = this.options.afterUpdateElement;
            this.options.afterUpdateElement = this.afterUpdateElement.bind(this);
        }
    },

    // called by the autocompleter on an event.
    getUpdatedChoices: function() {
        this.populator(this, this.getToken());
    },

    afterUpdateElement: function(element, selectedElement) {
        this.afterUpdateCallback(element, selectedElement, this.options.array[this.index]);
    },

    // should be called by the populator (specified in the constructor)
    setChoices: function(array) {
        this.options.array = array;
        this.updateChoices(this.options.selector(this));
    },

    setOptions: function(options) {
        this.options = Object.extend({
            choices: 10,
            selector: function(instance) {
                var items = [];
                var entry = instance.getToken();
                var count = 0;
                var valueSelector = instance.options.valueSelector;

                for (var i = 0; i < instance.options.array.length &&
                                items.length < instance.options.choices; i++) {

                    var value = valueSelector(instance.options.array[i]);
                    var foundPos = value.toLowerCase().indexOf(entry.toLowerCase());

                    if (foundPos != -1) {
                        items.push("<li>" + value.substr(0, foundPos)
                            + "<strong>" + value.substr(foundPos, entry.length) + "</strong>"
                            + value.substr(foundPos + entry.length) + "</li>");
                    } else {
                        items.push("<li>" + value + "</li>")
                    }
                }
                return "<ul>" + items.join('') + "</ul>";
            },
            valueSelector: function(object) {
                return object;
            }
        }, options || {});
    },

    startIndicator: function() {
      if (this.options.indicator) C3PR.showIndicator(this.options.indicator)
    },

    stopIndicator: function() {
      if (this.options.indicator) C3PR.hideIndicator(this.options.indicator)
    }

});
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
