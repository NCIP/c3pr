class AddConfigurationDatabaseMigrationNeededFlag extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('EPOCHS','TYPE','string');
    	if (databaseMatches('postgres')){
       		execute("update epochs set type='RESERVING' where reservation_indicator='true'");
       		execute("update epochs set type='TREATMENT' where treatment_indicator='true'");
       	}
     	if (databaseMatches('oracle')){
       		execute("update epochs set type='RESERVING' where reservation_indicator='1'");
       		execute("update epochs set type='TREATMENT' where treatment_indicator='1'");
       	}
    	dropColumn("epochs","treatment_indicator");
    	dropColumn("epochs","reservation_indicator");
    }

    void down() {
    }
}