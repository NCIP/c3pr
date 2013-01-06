class AddSummary4FieldsToStudy extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('studies','sponsor_type','string');  
		addColumn('studies','investigator_initiated','boolean'); 
		addColumn('studies','category','string'); 
		addColumn('studies','nci_recognized_program_name','string');  		 	
	}
	void down() {
		dropColumn('studies','sponsor_type'); 
		dropColumn('studies','investigator_initiated'); 
		dropColumn('studies','category'); 
		dropColumn('studies','nci_recognized_program_name'); 
    }
}
