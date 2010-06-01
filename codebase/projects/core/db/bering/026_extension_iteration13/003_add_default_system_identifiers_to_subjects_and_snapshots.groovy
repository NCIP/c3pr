class AddDefaultSystemIdentifiersToSubjectsAndSnapshots extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	if (databaseMatches('postgres')) {
			execute("insert into identifiers (id,version,dtype,system_name,type,value,prt_id,retired_indicator) (select nextVal('identifiers_ID_SEQ'),0,'SAI','C3PR','SUBJECT_IDENTIFIER',chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || '-' || ceil(random()*1000) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || '-' || ceil(random()*1000),id,'false' from participants)");
			execute("insert into identifiers (id,version,dtype,system_name,type,retired_indicator,value,stu_sub_dmgphcs_id) (select nextVal('identifiers_ID_SEQ'),0,'SAI','C3PR','SUBJECT_IDENTIFIER','false',identifiers.value,stu_sub_demographics.id from identifiers join stu_sub_demographics on identifiers.prt_id = stu_sub_demographics.prt_id where identifiers.prt_id is not null and identifiers.type like 'SUBJECT_IDENTIFIER' and identifiers.system_name like 'C3PR')");
		 }
	 
	 if (databaseMatches('oracle')) {
	 		execute("insert into identifiers (id,version,dtype,system_name,type,value,prt_id,retired_indicator) (select identifiers_ID_SEQ.NEXTVAL,0,'SAI','C3PR','SUBJECT_IDENTIFIER',dbms_random.string('U', 3) || '-' || cast(dbms_random.value(1,999) as int)  || dbms_random.string('U', 2) || '-' || cast(dbms_random.value(1,999) as int),id,'false' from participants)");
			execute("insert into identifiers (id,version,dtype,system_name,type,retired_indicator,value,stu_sub_dmgphcs_id) (select identifiers_ID_SEQ.NEXTVAL,0,'SAI','C3PR','SUBJECT_IDENTIFIER','false',identifiers.value,stu_sub_demographics.id from identifiers join stu_sub_demographics on identifiers.prt_id = stu_sub_demographics.prt_id where identifiers.prt_id is not null and identifiers.type like 'SUBJECT_IDENTIFIER' and identifiers.system_name like 'C3PR')");
	 	}
	}

	void down(){

	}
}