class CreateRaceCodeAssociationsTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	// Creating association table
		createTable("race_code_assocn") { t ->
		 	t.addColumn("sub_id", "integer" )
            t.addColumn("stu_sub_dmgphcs_id", "integer")
            t.addColumn("race_code", "string")
         }
         
    	dropColumn('race_code_assocn','id');
	 	
	 	execute('ALTER TABLE race_code_assocn ADD CONSTRAINT FK_RACE_CODE_SUB FOREIGN KEY (sub_id) REFERENCES participants(ID)');
	 	execute('ALTER TABLE race_code_assocn ADD CONSTRAINT FK_RACE_CODE_STU_SUB_DMGPHCS FOREIGN KEY (stu_sub_dmgphcs_id) REFERENCES stu_sub_demographics(ID)');

		// Migrating data from stu_sub_demographics and participants tables
	 	
	 	 if (databaseMatches('postgresql')) {
         	execute("insert into race_code_assocn (select participants.id, null, split_part(participants.race_code,':', 1) from participants)");
			execute("insert into race_code_assocn (select participants.id, null, split_part(participants.race_code,':', 2) from participants)");
			execute("insert into race_code_assocn (select participants.id, null, split_part(participants.race_code,':', 3) from participants)");
			execute("insert into race_code_assocn (select participants.id, null, split_part(participants.race_code,':', 4) from participants)");
			execute("insert into race_code_assocn (select participants.id, null, split_part(participants.race_code,':', 5) from participants)");
			execute("insert into race_code_assocn (select participants.id, null, split_part(participants.race_code,':', 6) from participants)");
			execute("insert into race_code_assocn (select participants.id, null, split_part(participants.race_code,':', 7) from participants)");
			
			execute("insert into race_code_assocn  (select null,stu_sub_demographics.id, split_part(stu_sub_demographics.race_code,':', 1) from stu_sub_demographics)");
			execute("insert into race_code_assocn  (select null,stu_sub_demographics.id, split_part(stu_sub_demographics.race_code,':', 2) from stu_sub_demographics)");
			execute("insert into race_code_assocn  (select null,stu_sub_demographics.id, split_part(stu_sub_demographics.race_code,':', 3) from stu_sub_demographics)");
			execute("insert into race_code_assocn  (select null,stu_sub_demographics.id, split_part(stu_sub_demographics.race_code,':', 4) from stu_sub_demographics)");
			execute("insert into race_code_assocn  (select null,stu_sub_demographics.id, split_part(stu_sub_demographics.race_code,':', 5) from stu_sub_demographics)");
			execute("insert into race_code_assocn  (select null,stu_sub_demographics.id, split_part(stu_sub_demographics.race_code,':', 6) from stu_sub_demographics)");
			execute("insert into race_code_assocn  (select null,stu_sub_demographics.id, split_part(stu_sub_demographics.race_code,':', 7) from stu_sub_demographics)");
			
			execute("delete from race_code_assocn where race_code like ''")
	 	}
	 	
	 	 if (databaseMatches('oracle')) {
		 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  'Asian' from stu_sub_demographics where race_code like '%Asian%')")
		 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  'White' from stu_sub_demographics where race_code like '%White%')")
		 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  'Black_or_African_American' from stu_sub_demographics where race_code like '%Black_or_African_American%')")
		 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  'American_Indian_or_Alaska_Native' from stu_sub_demographics where race_code like '%American_Indian_or_Alaska_Native%')")
		 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  'Native_Hawaiian_or_Pacific_Islander' from stu_sub_demographics where race_code like '%Native_Hawaiian_or_Pacific_Islander%')")
		 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  'Not_Reported' from stu_sub_demographics where race_code like '%Not_Reported%')")
		 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  'Unknown' from stu_sub_demographics where race_code like '%Unknown%')")
		 	 
 	 		 execute("insert into race_code_assocn (select participants.id, null, 'Asian' from participants where race_code like '%Asian%')")
		 	 execute("insert into race_code_assocn (select participants.id, null, 'White' from participants where race_code like '%White%')")
		 	 execute("insert into race_code_assocn (select participants.id, null, 'Black_or_African_American' from participants where race_code like '%Black_or_African_American%')")
		 	 execute("insert into race_code_assocn (select participants.id, null, 'American_Indian_or_Alaska_Native' from participants where race_code like '%American_Indian_or_Alaska_Native%')")
		 	 execute("insert into race_code_assocn (select participants.id, null, 'Native_Hawaiian_or_Pacific_Islander' from participants where race_code like '%Native_Hawaiian_or_Pacific_Islander%')")
		 	 execute("insert into race_code_assocn (select participants.id, null, 'Not_Reported' from participants where race_code like '%Not_Reported%')")
		 	 execute("insert into race_code_assocn (select participants.id, null, 'Unknown' from participants where race_code like '%Unknown%')")
	 	 }
	 	 
	 	execute("ALTER TABLE race_code_assocn ADD CONSTRAINT UK_RACE_CODE_SUB UNIQUE(race_code,sub_id)");
	 	execute("ALTER TABLE race_code_assocn ADD CONSTRAINT UK_RACE_CODE_STU_SUB_DMGPHCS UNIQUE(race_code,sub_id)");
	 	
	 	// Drop existing columns
	 	dropColumn('participants','race_code');
	 	dropColumn('stu_sub_demographics','race_code');
	 	
	}
	void down() {
		dropTable("race_code_assocn")
    }
}
