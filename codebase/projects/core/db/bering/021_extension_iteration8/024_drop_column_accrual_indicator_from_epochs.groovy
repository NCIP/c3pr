class DropColumnAccrualIndicatorFromEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	dropColumn('epochs','accrual_indicator')
	}

	void down() {
    }
}
