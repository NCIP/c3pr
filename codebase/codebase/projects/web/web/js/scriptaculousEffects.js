/// AJAX, Scriptaculous effects - Utility functions

Effect.OpenUp = function(element) {
     element = $(element);
     new Effect.BlindDown(element, arguments[1] || {});
//     new Effect.Grow(element, arguments[1] || {});
 }

 Effect.CloseDown = function(element) {

     element = $(element);
     new Effect.BlindUp(element, arguments[1] || {});
 }

 Effect.ComboCheck = function(element) {
     element = $(element);
     if(element.style.display == "none") {
          new Effect.OpenUp(element, arguments[1] || {});
      }
 }

 Effect.Combo = function(element,imageStr,title) {
      element = $(element);
     if(element.style.display == "none") {
          new Effect.OpenUp(element, arguments[1] || {});
          document.getElementById(imageStr).src="<tags:imageUrl name="b-minus.gif"/>";
         // new Effect.Grow(document.getElementById(title));
     }else {
          new Effect.CloseDown(element, arguments[1] || {});
           document.getElementById(imageStr).src="<tags:imageUrl name="b-plus.gif"/>";
     }
 }
function hideTextArea(a,b){
	if(document.getElementById(a).checked==true){
		new Effect.OpenUp(document.getElementById(b));
	}else{
		new Effect.CloseDown(document.getElementById(b));
	}
}