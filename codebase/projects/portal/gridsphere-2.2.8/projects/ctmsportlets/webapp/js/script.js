function changeMenuStyle(obj, new_style) { 
  obj.className = new_style; 
}

function showCursor(){
	document.body.style.cursor='hand'
}

function hideCursor(){
	document.body.style.cursor='default'
}

function confirmDelete(){
  if (confirm('Are you sure you want to delete?')){
    return true;
    }else{
    return false;
  }
}

function goTo(page) { 
  document.location = page; 
}

function clear(page) { 
  document.location = page; 
}

function confirmation(message, page) { 
  if(confirm(message)){
  	document.location = page;
  }  
}

function popupWindow(url, width, height){
	window.open(url, 'generic', 
	  'toolbar=0,location=0,status=0,menubar=0,' + 
	  'scrollbars=1,resizable=1,' + 
	  'width=' + width + ',height=' + height);
}