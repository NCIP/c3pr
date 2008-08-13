class addStratificationIndicatorToEpoch extends edu.northwestern.bioinformatics.bering.Migration {
    	void up() {
        addColumn('epochs','stratification_indicator','boolean');
       	if (databaseMatches('postgresql')){
        	execute("update epochs set stratification_indicator='false'");
        	execute("update epochs set stratification_indicator='true' from epochs ep inner join studies on ep.stu_id=studies.id where studies.stratification_indicator ='true' and (epochs.display_role='TREATMENT'or epochs.display_role='Treatment'or epochs.display_role='Generic' )");
        }
        if (databaseMatches('oracle')){
        	execute("update epochs set stratification_indicator='no'");
        	execute("update epochs set stratification_indicator='true' from epochs ep inner join studies on ep.stu_id=studies.id where studies.stratification_indicator ='true' and (epochs.display_role='TREATMENT'or epochs.display_role='Treatment'or epochs.display_role='Generic' )");
        }
    }

    void down() {
       dropColumn('epochs','stratification_indicator');
    }
}

