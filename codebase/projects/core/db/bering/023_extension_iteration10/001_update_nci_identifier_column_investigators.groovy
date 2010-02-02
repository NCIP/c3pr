class UpdateNCIIdentidifierInvestigators extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
    	  execute("update investigators set nci_identifier = (select chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || '-' || ceil(random()*1000) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || chr(mod(CAST(ceil(random()*100) as Integer),26)+65) || '-' || ceil(random()*1000) from investigators inv where inv.id=investigators.id ) where nci_identifier is null");
    	  execute("ALTER TABLE investigators ALTER COLUMN nci_identifier SET NOT NULL");
		}
		if (databaseMatches('oracle')){
    	  execute("update investigators set nci_identifier =  (select dbms_random.string('U', 3) || '-' || cast(dbms_random.value(1,999) as int)  || dbms_random.string('U', 2) || '-' || cast(dbms_random.value(1,999) as int) from investigators inv where inv.id=investigators.id) where nci_identifier is null");
    	  execute("ALTER TABLE investigators MODIFY(nci_identifier not null)");
		}
		execute("alter table investigators add constraint uq_assignedId_inv unique (nci_identifier)");
		execute("ALTER TABLE investigators RENAME COLUMN nci_identifier to assigned_identifier");
	}
	void down() {
    }
}
