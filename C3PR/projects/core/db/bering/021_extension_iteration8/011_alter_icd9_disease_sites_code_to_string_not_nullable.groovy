class ChangeICD9DiseaseSitesCodeToStringNotNullable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    if (databaseMatches('oracle')) {
		   execute("alter table icd9_disease_sites modify (code varchar2(10) not null)")
	 	}
	 	if (databaseMatches('postgres')) {
		   	execute("alter table icd9_disease_sites alter column code type text")
	    	execute("alter table icd9_disease_sites alter column code set not null")
	 	}
    }

    void down() {
    }
}