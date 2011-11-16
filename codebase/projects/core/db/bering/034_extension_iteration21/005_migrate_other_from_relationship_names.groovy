class MigrateOtherFromRelationshipNames extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("update relationships set name='SIBLING' where name like 'OTHER'");
	}
	void down() {
    }
}
