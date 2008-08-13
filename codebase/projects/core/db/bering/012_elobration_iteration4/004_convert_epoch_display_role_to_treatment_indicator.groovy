class convertEpochDisplayRoleToTreatmentIndicator extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       addColumn('epochs','treatment_indicator','boolean');
       if (databaseMatches('postgresql')){
	       execute("update epochs set treatment_indicator='true' where display_role='Treatment' or display_role='TREATMENT' or display_role='Generic'");
	       execute("update epochs set treatment_indicator='false' where display_role='Non-Treatment' or display_role='NonTreatment'");
       }
        if (databaseMatches('oracle')){
	       execute("update epochs set treatment_indicator='yes' where display_role='Treatment' or display_role='TREATMENT' or display_role='Generic'");
	       execute("update epochs set treatment_indicator='no' where display_role='Non-Treatment' or display_role='NonTreatment'");
       }
        dropColumn('epochs','display_role');
    }

    void down() {
       addColumn('epochs','display_role','string');
       if (databaseMatches('postgresql')){
	       execute("update epochs set display_role='TREATMENT' where treatment_indicator='true'");
	       execute("update epochs set display_role='NON-TREATMENT' where treatment_indicator='false'");
       }
       if (databaseMatches('oracle')){
	       execute("update epochs set display_role='TREATMENT' where treatment_indicator='yes'");
	       execute("update epochs set display_role='NON-TREATMENT' where treatment_indicator='no'");
       }
       dropColumn('epochs','treatment_indicator');
       
    }
}