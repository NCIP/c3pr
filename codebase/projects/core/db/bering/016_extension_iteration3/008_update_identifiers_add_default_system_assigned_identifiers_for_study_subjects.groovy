class updateIdentifiersAddDefaultSystemAssignedIdentifiersForStudySubjects extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')) {
	   		execute("INSERT INTO identifiers (id,version,dtype,system_name,type,value,retired_indicator,spa_id) (select nextval('identifiers_ID_SEQ'::regclass) as id,1,'SAI','C3PR','Study Subject Identifier',cast(random()*10000000 as integer),'false',study_subjects.id as ssid from study_subjects)");
 	    }
    	if (databaseMatches('oracle')) {
	   		execute("INSERT INTO identifiers (id,version,dtype,system_name,type,retired_indicator,spa_id,value) select identifiers_ID_SEQ.nextval,1,'SAI','C3PR','Study Subject Identifier','false',study_subjects.id,dbms_random.string('A', 11) from dual,study_subjects");
 	    }
    }

    void down() {
    }
}
