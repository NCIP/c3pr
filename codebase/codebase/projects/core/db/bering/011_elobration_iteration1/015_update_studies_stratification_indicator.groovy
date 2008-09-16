class UpdateStudiesStratificationIndicator extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	
		if (databaseMatches('postgres'))   {
			execute("update studies set stratification_indicator ='True' where stratification_indicator is null");
		}
	
		if (databaseMatches('oracle'))   {
			execute("update studies set stratification_indicator = '1' where stratification_indicator is null");
		}
	
	}

	void down(){
	
	}
}
