class IncreaseStudiesTargetAccrualNumberLengthOracle extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
	    if (databaseMatches('oracle')){
	    	execute("alter table studies modify (TARGET_ACCRUAL_NUMBER  Number(6))")
	   	 }
   	 }

    void down() {
   	 }
}