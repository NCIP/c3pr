class AddPhonecallRandomization extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('randomizations', "phone_number", "string")
   	 }

    void down() {
    	dropColumn('randomizations','phone_number')    		
	}
	
}