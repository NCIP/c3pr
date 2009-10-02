class BackDatedRegistrationSupportFlagInStudy extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('studies', 'back_dated_reg_support', 'boolean');
    	if (databaseMatches('postgresql')) {
    		execute("UPDATE studies SET back_dated_reg_support = 'false'");
    	}else  if (databaseMatches('oracle')) {
    		execute("UPDATE studies SET back_dated_reg_support = 0");
    	}
    } 

	void down() {
		dropColumn('studies', 'back_dated_reg_support');
	}
}