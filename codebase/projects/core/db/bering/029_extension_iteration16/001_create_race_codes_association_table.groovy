class CreateRaceCodeAssociationsTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	// Creating association table
		createTable("race_code_assocn") { t ->
		 	t.addVersionColumn()
            t.addColumn("grid_id", "string")
		 	t.addColumn("sub_id", "integer" )
            t.addColumn("stu_sub_dmgphcs_id", "integer")
            t.addColumn("race_code", "string")
         }
		
		if (databaseMatches('oracle')) {
	   		execute("RENAME SEQ_race_code_assocn_id to race_code_assocn_id_SEQ");
	 	}
	 		 	
	 	execute('ALTER TABLE race_code_assocn ADD CONSTRAINT FK_RACE_CODE_SUB FOREIGN KEY (sub_id) REFERENCES participants(ID)');
	 	execute('ALTER TABLE race_code_assocn ADD CONSTRAINT FK_RACE_CODE_STU_SUB_DMGPHCS FOREIGN KEY (stu_sub_dmgphcs_id) REFERENCES stu_sub_demographics(ID)');
			
		// Migrating data from stu_sub_demographics and participants tables
		
		if (databaseMatches('postgres')) {
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Asian' from participants where race_code like '%Asian%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'White' from participants where race_code like '%White%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Black_or_African_American' from participants where race_code like '%Black_or_African_American%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'American_Indian_or_Alaska_Native' from participants where race_code like '%American_Indian_or_Alaska_Native%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Native_Hawaiian_or_Pacific_Islander' from participants where race_code like '%Native_Hawaiian_or_Pacific_Islander%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Not_Reported' from participants where race_code like '%Not_Reported%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Unknown' from participants where race_code like '%Unknown%')")
	   		
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Asian' from stu_sub_demographics where race_code like '%Asian%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'White' from stu_sub_demographics where race_code like '%White%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Black_or_African_American' from stu_sub_demographics where race_code like '%Black_or_African_American%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'American_Indian_or_Alaska_Native' from stu_sub_demographics where race_code like '%American_Indian_or_Alaska_Native%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Native_Hawaiian_or_Pacific_Islander' from stu_sub_demographics where race_code like '%Native_Hawaiian_or_Pacific_Islander%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Not_Reported' from stu_sub_demographics where race_code like '%Not_Reported%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Unknown' from stu_sub_demographics where race_code like '%Unknown%')")
 	    }
    	if (databaseMatches('oracle')) {
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Asian' from participants where race_code like '%Asian%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'White' from participants where race_code like '%White%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Black_or_African_American' from participants where race_code like '%Black_or_African_American%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'American_Indian_or_Alaska_Native' from participants where race_code like '%American_Indian_or_Alaska_Native%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Native_Hawaiian_or_Pacific_Islander' from participants where race_code like '%Native_Hawaiian_or_Pacific_Islander%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Not_Reported' from participants where race_code like '%Not_Reported%')")
	   		execute("INSERT INTO race_code_assocn (id,version,sub_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, participants.id,  'Unknown' from participants where race_code like '%Unknown%')")
	   		
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Asian' from stu_sub_demographics where race_code like '%Asian%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'White' from stu_sub_demographics where race_code like '%White%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Black_or_African_American' from stu_sub_demographics where race_code like '%Black_or_African_American%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'American_Indian_or_Alaska_Native' from stu_sub_demographics where race_code like '%American_Indian_or_Alaska_Native%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Native_Hawaiian_or_Pacific_Islander' from stu_sub_demographics where race_code like '%Native_Hawaiian_or_Pacific_Islander%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Not_Reported' from stu_sub_demographics where race_code like '%Not_Reported%')")
	   		execute("INSERT INTO race_code_assocn (id,version,stu_sub_dmgphcs_id,race_code) (select nextval('race_code_assocn_id_SEQ'::regclass) as id,1, stu_sub_demographics.id,  'Unknown' from stu_sub_demographics where race_code like '%Unknown%')")
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
