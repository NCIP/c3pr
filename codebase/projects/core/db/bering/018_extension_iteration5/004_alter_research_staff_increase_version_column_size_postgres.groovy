class alterResearchStaffIncreaseVersionColumnSizePostgres extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	   		if (databaseMatches('postgres')) {
	   			execute("alter table research_staff alter column version TYPE bigint");
 	    	}
    }

    void down() {
     
    }
}
