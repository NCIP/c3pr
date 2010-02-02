class UpdateNCIIdentidifierResearshStaff extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
    	  execute("update research_staff set nci_identifier = (select chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || '-' || ceil(random()*1000) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || '-' || ceil(random()*1000) from research_staff rs where rs.id=research_staff.id ) where nci_identifier is null or nci_identifier=''");
    	  execute("ALTER TABLE research_staff ALTER COLUMN nci_identifier SET NOT NULL");
		}
		if (databaseMatches('oracle')){
    	  execute("update research_staff set nci_identifier =  (select dbms_random.string('U', 3) || '-' || cast(dbms_random.value(1,999) as int)  || dbms_random.string('U', 2) || '-' || cast(dbms_random.value(1,999) as int) from research_staff rs where rs.id=research_staff.id) where nci_identifier is null or nci_identifier=''");
    	  execute("ALTER TABLE research_staff MODIFY(nci_identifier not null)");
		}
		execute("alter table research_staff add constraint uq_assignedId_rs unique (nci_identifier)");
		execute("ALTER TABLE research_staff RENAME COLUMN nci_identifier to assigned_identifier");
	}
	void down() {
    }
}
