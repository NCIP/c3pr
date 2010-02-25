class UpdateNCIIdentidifierInvestigators extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('studies', 'summary_3_reportable', 'boolean');
    	if (databaseMatches('oracle')) {
        	execute("ALTER TABLE STUDIES MODIFY summary_3_reportable DEFAULT 0")
        	execute("update studies set summary_3_reportable=0")
        	execute("update studies set summary_3_reportable=1 where type='Genetic Therapeutic'")
        	execute("alter table studies modify summary_3_reportable not null");
        } else if (databaseMatches('postgres')){
        	execute("ALTER TABLE STUDIES ALTER COLUMN summary_3_reportable SET DEFAULT false")
        	execute("update studies set summary_3_reportable=false")
        	execute("update studies set summary_3_reportable=true where type='Genetic Therapeutic'")
        	execute("alter table studies alter column summary_3_reportable set not null");
        }
	}
	void down() {
    }
}
