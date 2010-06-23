class CSMPGPECorrection extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
    	external("CSM_PG_PE_Correction.sql")
    }

    void down() {
    }
}