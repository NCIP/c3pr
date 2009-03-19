class updateIdentifiersAddDefaultSystemAssignedIdentifiersForParticipants extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	   		execute("DELETE from identifiers where system_name='Participant Identifier'");
    }

    void down() {
    	if (databaseMatches('postgres')) {
	   		execute("INSERT INTO identifiers (id,version,dtype,system_name,type,value,retired_indicator,prt_id) (select nextval('identifiers_ID_SEQ'::regclass) as id,1,'SAI','C3PR','Participant Identifier',cast(random()*10000000 as integer),'false',participants.id as ssid from participants)");
 	    }
    	if (databaseMatches('oracle')) {
	   		execute("INSERT INTO identifiers (id,version,dtype,system_name,type,retired_indicator,prt_id,value) select identifiers_ID_SEQ.nextval,1,'SAI','C3PR','Participant Identifier','false',participants.id,dbms_random.string('A', 11) from dual,participants");
 	    }
    }
}
