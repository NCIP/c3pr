<script language = "Javascript">
/**
 * DHTML date validation script.
 */
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   }
   return this
}

function isDate(dtStrVal){

  var dtStr= dtStrVal.value
  
  if (dtStr == "" ){
  return true
  }
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(0,pos1)
	var strDay=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		alert("The date format should be : mm/dd/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false
	}
return true
}

 function ValidateScreeningDate(frm){
  return isDate(frm.month.value+"/"+frm.day.value+"/"+frm.year.value)
 }
 function ValidateBirthDate(frm){
 	str_yr = frm.year.value.length
 	str_month = frm.month.value.length
 	str_day =frm.day.value.length

 	  if(str_yr>0){
 	       if(str_month>0){
 	            if(str_day>0){

 	                return case3(frm)
 	            }else{

 	               return case2(frm)
 	            }
 	       }else{
 	             if(str_day>0){

				  	  return case4(frm)
				 }else{

				  	  return case1(frm)
 	            }
 	       }
 	  }else{

 	     if(str_month>0){
		  	            if(str_day>0){

		  	                return case6(frm)
		  	            }else{

		  	               return case8(frm)
		  	            }
		  	       }else{
		  	             if(str_day>0){

		 				  	  return case5(frm)
		 				 }else{

		 				  	  return case7(frm)
		  	            }
 	       }

 	  }
 }

 function isDay(frm){
     var strDay = frm.day.value
   if(isInteger(strDay)){
        day = parseInt(strDay);
         if (strDay.length<1 || day<1 || day>31){
		 		alert("Please enter a valid day")
		 		return false
	}else{
	     return true
	  }
   }else{
     return false
   }

 }

 function isMonth(frm){
      var strMonth = frm.month.value

    if(isInteger(strMonth)){
         month = parseInt(strMonth);
          if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false
	}else{
 	     return true
 	  }
    }else{
      return false
    }

 }

 function isYear(frm){
       var strYear =frm.year.value

     if(isInteger(strYear)){
          year = parseInt(strYear);
           if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}else{
  	     return true
  	  }
     }else{
       return false
     }

 }
 function case1(frm){
    return isYear(frm)
  }
    // date is null
  function case2(frm){
    return (isYear(frm)&&isMonth(frm))
 }
function case3(frm){

  return isDate(frm.month.value+"/"+frm.day.value+"/"+frm.year.value)
 }

function case4(frm){
   return (isYear(frm)&&isDay(frm))
 }
   //year and month are null
 function case5(frm){
   return (isDay(frm))
 }

 function case6(frm){
    return (isMonth(frm)&&isDay(frm))
  }
  //// everything is null
  function case7(frm){
    return true
 }
 function case8(frm){
    return isMonth(frm)
 }


</script>