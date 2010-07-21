class UpdateDuplicateParticipantIdentifier extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("update identifiers set value = value||'-dup-'||id where id in (select i1.id from identifiers i1 where i1.prt_id is not null and 1 < (select count(*) from identifiers i2 where i1.value =i2.value and i1.type = i2.type and i1.dtype = i2.dtype and i1.hcs_id = i2.hcs_id and i2.prt_id is not null))");
	}
	void down() {
    }
}
