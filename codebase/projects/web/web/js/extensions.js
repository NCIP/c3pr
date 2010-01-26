Ajax.InPlaceCollectionEditor.prototype.__createEditField = Ajax.InPlaceCollectionEditor.prototype.createEditField;
Ajax.InPlaceCollectionEditor.prototype.__onSubmit = Ajax.InPlaceCollectionEditor.prototype.onSubmit;
Ajax.InPlaceCollectionEditor.prototype.__buildOptionList = Ajax.InPlaceCollectionEditor.prototype.buildOptionList;
Object.extend(Ajax.InPlaceCollectionEditor.prototype, {
    createEditField: function() {
        if (this.options.callback) { var callbackSet = this.options.callback };
        this.__createEditField();
        if (callbackSet) { this.options.callback = callbackSet;    };
    },
    buildOptionList: function() {
        if(BrowserDetect.browser=='Explorer')
        	Element.extend(this._controls.editor); 
        this.__buildOptionList();
    },
    onSubmit: function(){
		if(this.options.validations!=null && this.options.validations!=''){
			this.editField.className=this.editField.className + " "+this.options.validations;
			ValidationManager.removeError(this.editField);
			var fields=new Array();
			fields.push(this.editField);
			ValidationManager.prepareField(fields[0]);
			if(validateFields(fields)){
				this.__onSubmit();
		    }else{
		    	arguments.length > 1?Event.stop(arguments[0]):null;
		    	return false;
		    }
		}else{
        	this.__onSubmit();
        }
    }
    
});

//InPlaceEditor extension that adds a 'click to edit' text when the field is 
//empty.
Ajax.InPlaceEditor.prototype.__onSubmit = Ajax.InPlaceEditor.prototype.onSubmit;
Ajax.InPlaceEditor.prototype = Object.extend(Ajax.InPlaceEditor.prototype, {
    onSubmit: function(){
		if(this.options.validations!=null && this.options.validations!=''){
			this.editField.className=this.editField.className + " "+this.options.validations;
			ValidationManager.removeError(this.editField);
			var fields=new Array();
			fields.push(this.editField);
			ValidationManager.prepareField(fields[0]);
			if(validateFields(fields)){
				this.__onSubmit();
		    }else{
		    	arguments.length > 1?Event.stop(arguments[0]):null;
		    	return false;
		    }
		}else{
        	this.__onSubmit();
        }
    }
});

/*//InPlaceCollectionEditor extension that adds a cancel button .
Ajax.InPlaceCollectionEditor.prototype.__createForm = Ajax.InPlaceCollectionEditor.prototype.createForm;
Ajax.InPlaceCollectionEditor.prototype = Object.extend(Ajax.InPlaceCollectionEditor.prototype, {
    createForm: function(){
		this.__createForm();
		if (this.options.cancelButton) {
          cancelButton = document.createElement("input");
	      cancelButton.type = "button";
   	      cancelButton.onclick = this.onclickCancel.bind(this);
	      cancelButton.value = this.options.cancelText;
	      cancelButton.className = 'editor_ok_button';
	      this.form.appendChild(cancelButton);
        }
    }
});

//InPlaceEditor extension that adds a cancel button .
Ajax.InPlaceEditor.prototype.__createForm = Ajax.InPlaceEditor.prototype.createForm;
Ajax.InPlaceEditor.prototype = Object.extend(Ajax.InPlaceEditor.prototype, {
    createForm: function(){
		this.__createForm();
		if (this.options.cancelButton) {
          cancelButton = document.createElement("input");
	      cancelButton.type = "button";
   	      cancelButton.onclick = this.onclickCancel.bind(this);
	      cancelButton.value = this.options.cancelText;
	      cancelButton.className = 'editor_ok_button';
	      this.form.appendChild(cancelButton);
        }
    }
});
*/
Autocompleter.Base.prototype.__onBlur = Autocompleter.Base.prototype.onBlur;
Autocompleter.Base.prototype = Object.extend(Autocompleter.Base.prototype, {
	onBlur: function(event){
		//getting the id of the elmt...replacing"-input" with "-hidden" to get the hidden var
		//and re-setting the entered text with "" if the hidden var contains no value.
		//the hidden value will contain a val only if something is selected
		//from the auto-completer drop-down.
		//so when the user enters junk literals in the auto-completer
		//and tries to submit or clicks elsewhere the onblur clears the textbox.
		if(!this.options.isFreeTextAllowed){
			var hiddenElmtId = this.element.id.substring(0, this.element.id.lastIndexOf('-input')) + "-hidden";	
			if($(hiddenElmtId) != null ){
				if($(hiddenElmtId).value == null || $(hiddenElmtId).value == ''){
					$(this.element.id).value="";
					Element.addClassName(this.element, "pending-search");
				}
				else{
					//setting the required field as valid (white background)
					if (this.element.hasClassName("required") || this.element.hasClassName("validField")){
						ValidationManager.setNormalState(this.element);
						ValidationManager.removeError(this.element);
					}
				}
			}
		}
		if(Prototype.Browser.IE){
			if (event.offsetX > 450) {     
			  //good may close    
			} else if (event.offsetY < 0) {       
			  //good - may close     
			} else {         
			  //prevent autocomplete close     
			  event.cancelBubble = true;         
			  return false;    
			}
		 }
		this.__onBlur(event);
	}
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
	       case 35:
	       case 36:
	       case 224:
	       case 17:
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
	       case 35:
	       case 36:
	       case 224:
	       case 17:
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
	       default:
	       	 if(this.options.isFreeTextAllowed != null && !this.options.isFreeTextAllowed){
				var hiddenElmtId = this.element.id.substring(0, this.element.id.lastIndexOf('-input')) + "-hidden";			
				if($(hiddenElmtId) != null ){
					$(hiddenElmtId).value="";
					//Setting the state as invalid if its a required field
					if (this.element.hasClassName("valueOK") || this.element.hasClassName("validField")){
						ValidationManager.setState(this.element, false);
						
					}
		    	}
		    }
	    }
	    if(this.observer) clearTimeout(this.observer);
	      this.observer = 
	        setTimeout(this.onObserverEvent.bind(this), this.options.frequency*1000);
	  }
});
Autocompleter.Base.prototype.__baseInitialize = Autocompleter.Base.prototype.baseInitialize;
Autocompleter.Base.prototype = Object.extend(Autocompleter.Base.prototype, {
	baseInitialize: function(element, update, options) {
		options.frequency    = 2.0;
    	options.minChars     = 3;
		this.__baseInitialize(element, update, options);
	}
});



