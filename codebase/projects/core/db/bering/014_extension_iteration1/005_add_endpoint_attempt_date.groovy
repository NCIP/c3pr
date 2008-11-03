class AddEndpointAttemptDate extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	 	addColumn("endpoints","attempt_date","date")
	 	if (databaseMatches('postgres')) {
	    	execute("alter table endpoints alter column attempt_date type timestamp without time zone")
	    	execute("alter table errors alter column error_date type timestamp without time zone")
	 	}
    }
  
    void down() {
    	dropColumn('endpoints','attempt_date')
    }
}
