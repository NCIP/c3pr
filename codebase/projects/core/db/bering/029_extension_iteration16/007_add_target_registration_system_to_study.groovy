class AddTargetRegistrationSystemToStudy extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('studies', 'target_registration_system', 'string');
	}
	void down() {		
    }
}