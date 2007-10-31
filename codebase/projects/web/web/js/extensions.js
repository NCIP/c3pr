Ajax.InPlaceCollectionEditor.prototype.__createEditField = Ajax.InPlaceCollectionEditor.prototype.createEditField;
Ajax.InPlaceCollectionEditor.prototype.__onSubmit = Ajax.InPlaceCollectionEditor.prototype.onSubmit;
Ajax.InPlaceCollectionEditor.prototype = Object.extend(Ajax.InPlaceCollectionEditor.prototype, {
    createEditField: function() {
        if (this.options.callback) { var callbackSet = this.options.callback };
        this.__createEditField();
        if (callbackSet) { this.options.callback = callbackSet;    };
    },
    onSubmit: function(){
		if(this.editField.value=="" && this.options.requiredIndicator!=null && this.options.requiredIndicator==true){
			alert("Its a required field. Please enter a value.");
			if (arguments.length > 1) {
		      Event.stop(arguments[0]);
		    }
		    return false;
		}else{
        	this.__onSubmit();
        }
    },
    
});

//InPlaceEditor extension that adds a 'click to edit' text when the field is 
//empty.
Ajax.InPlaceEditor.prototype.__onSubmit = Ajax.InPlaceEditor.prototype.onSubmit;
Ajax.InPlaceEditor.prototype = Object.extend(Ajax.InPlaceEditor.prototype, {
    onSubmit: function(){
		if(this.editField.value=="" && this.options.requiredIndicator!=null && this.options.requiredIndicator==true){
			alert("Its a required field. Please enter a value.");
			if (arguments.length > 1) {
		      Event.stop(arguments[0]);
		    }
		    return false;
		}else{
        	this.__onSubmit();
        }
    },
});

Autocompleter.Base.prototype.__onBlur = Autocompleter.Base.prototype.onBlur;
Autocompleter.Base.prototype = Object.extend(Autocompleter.Base.prototype, {
	onBlur: function(){
		//getting the id of the elmt...replacing"-input" with "-hidden" to get the hidden var
		//and re-setting the entered text with "" if the hidden var contains no value.
		//the hidden value will contain a val only if something is selected
		//from the auto-completer drop-down.
		//so when the user enters junk literals in the auto-completer
		//and tries to submit or clicks elsewhere the onblur clears the textbox.
		if(!this.options.isFreeTextAllowed){
			var hiddenElmtId = this.element.id.substring(0, this.element.id.lastIndexOf('-input')) + "-hidden";	
			if($(hiddenElmtId) != null ){
				if($(hiddenElmtId).value == null || $(hiddenElmtId).value == '')
					$(this.element.id).value="";
			}
		}
				
		this.__onBlur();
	},
});

Autocompleter.Base.prototype.__onKeyPress = Autocompleter.Base.prototype.onKeyPress;
Autocompleter.Base.prototype = Object.extend(Autocompleter.Base.prototype, {
	onKeyPress: function(event) {
	    if(this.active)
	      switch(event.keyCode) {
	       case Event.KEY_TAB:
	       case Event.KEY_RETURN:
	         this.selectEntry();
	         Event.stop(event);
	       case Event.KEY_ESC:
	         this.hide();
	         this.active = false;
	         Event.stop(event);
	         return;
	       case Event.KEY_LEFT:
	       case Event.KEY_RIGHT:
	         return;
	       case Event.KEY_UP:
	         this.markPrevious();
	         this.render();
	         if(navigator.appVersion.indexOf('AppleWebKit')>0) Event.stop(event);
	         return;
	       case Event.KEY_DOWN:
	         this.markNext();
	         this.render();
	         if(navigator.appVersion.indexOf('AppleWebKit')>0) Event.stop(event);
	         return;
	      }
	     else 
	       if(event.keyCode==Event.KEY_TAB || event.keyCode==Event.KEY_RETURN || 
	         (navigator.appVersion.indexOf('AppleWebKit') > 0 && event.keyCode == 0)) return;
	
	    this.changed = true;
	    this.hasFocus = true;
	    
	    //getting the id of the elmt...replacing"-input" with "-hidden" to get the hidden var
	    //and re-setting it to "" so that the user cannot select something from the autocompleter 
	    //drop down and then modify it and submit the changed text with the 
	    //previously selected id. 
		if(this.options.isFreeTextAllowed != null && !this.options.isFreeTextAllowed){
			var hiddenElmtId = this.element.id.substring(0, this.element.id.lastIndexOf('-input')) + "-hidden";			
			if($(hiddenElmtId) != null ){
				$(hiddenElmtId).value="";
	    	}
	    }
	    
	    if(this.observer) clearTimeout(this.observer);
	      this.observer = 
	        setTimeout(this.onObserverEvent.bind(this), this.options.frequency*1000);
	  },
});




