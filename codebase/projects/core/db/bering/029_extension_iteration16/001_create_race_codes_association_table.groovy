class CreateRaceCodeAssociationsTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	//creating race_codes table
    	createTable("race_codes") { t ->
            t.addColumn("race_code", "string", nullable: false)
               t.addVersionColumn()
            t.addColumn("grid_id", "string")
         }
         
         // inserting data in race_codes table
         execute("insert into race_codes values (1, 'White')");
         execute("insert into race_codes values (2, 'Asian')");
         execute("insert into race_codes values (3, 'Black_or_African_American')");
         execute("insert into race_codes values (4, 'American_Indian_or_Alaska_Native')");
         execute("insert into race_codes values (5, 'Native_Hawaiian_or_Pacific_Islander')");
         execute("insert into race_codes values (6, 'Not_Reported')");
         execute("insert into race_codes values (7, 'Unknown')");

        if (databaseMatches('oracle')) {
	   		execute("RENAME SEQ_race_codes_id to race_codes_id_SEQ");
	 	}
         
    	// Creating association table
		createTable("race_code_assocn") { t ->
		 	t.addColumn("sub_id", "integer")
            t.addColumn("stu_sub_dmgphcs_id", "integer" )
            t.addColumn("race_code_id", "integer", nullable: false)
         }
         
    	dropColumn('race_code_assocn','id');
	 	
	 	execute('ALTER TABLE race_code_assocn ADD CONSTRAINT FK_RACE_CODE_SUB FOREIGN KEY (sub_id) REFERENCES participants(ID)');
	 	execute('ALTER TABLE race_code_assocn ADD CONSTRAINT FK_RACE_CODE_STU_SUB_DMGPHCS FOREIGN KEY (stu_sub_dmgphcs_id) REFERENCES stu_sub_demographics(ID)');

		// Migrating data from stu_sub_demographics and participants tables
	 	 
	 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  2 from stu_sub_demographics where race_code like '%Asian%')")
	 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  1 from stu_sub_demographics where race_code like '%White%')")
	 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  3 from stu_sub_demographics where race_code like '%Black_or_African_American%')")
	 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  4 from stu_sub_demographics where race_code like '%American_Indian_or_Alaska_Native%')")
	 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  5 from stu_sub_demographics where race_code like '%Native_Hawaiian_or_Pacific_Islander%')")
	 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  6 from stu_sub_demographics where race_code like '%Not_Reported%')")
	 	 execute("insert into race_code_assocn (select null,stu_sub_demographics.id,  7 from stu_sub_demographics where race_code like '%Unknown%')")
	 	 
 		 execute("insert into race_code_assocn (select participants.id, null, 2 from participants where race_code like '%Asian%')")
	 	 execute("insert into race_code_assocn (select participants.id, null, 1 from participants where race_code like '%White%')")
	 	 execute("insert into race_code_assocn (select participants.id, null, 3 from participants where race_code like '%Black_or_African_American%')")
	 	 execute("insert into race_code_assocn (select participants.id, null, 4 from participants where race_code like '%American_Indian_or_Alaska_Native%')")
	 	 execute("insert into race_code_assocn (select participants.id, null, 5 from participants where race_code like '%Native_Hawaiian_or_Pacific_Islander%')")
	 	 execute("insert into race_code_assocn (select participants.id, null, 6 from participants where race_code like '%Not_Reported%')")
	 	 execute("insert into race_code_assocn (select participants.id, null, 7 from participants where race_code like '%Unknown%')")
	 	 
	 	execute("ALTER TABLE race_code_assocn ADD CONSTRAINT UK_RACE_CODE_SUB UNIQUE(race_code_id,sub_id)");
	 	execute("ALTER TABLE race_code_assocn ADD CONSTRAINT UK_RACE_CODE_STU_SUB_DMGPHCS UNIQUE(race_code_id,sub_id)");

	 	// Drop existing columns
	 	dropColumn('participants','race_code');
	 	dropColumn('stu_sub_demographics','race_code');
	 	
	}
	void down() {
		dropTable("race_code_assocn")
    }
}
