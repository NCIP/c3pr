class MigrateStudiesToLocalStudies extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("update studies set dtype='Local' where external_id is null");
	}

	void down() {

    }
}
