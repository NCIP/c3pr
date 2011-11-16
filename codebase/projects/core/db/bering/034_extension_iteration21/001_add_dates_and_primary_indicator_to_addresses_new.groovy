class AddDatesAndPrimaryIndicatorToAddresses extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('addresses','start_date','date');
		addColumn('addresses','end_date','date');
		addColumn('addresses','primary_indicator','boolean',defaultValue: '0');
		
		execute("update addresses set primary_indicator = '0'");
	}
	void down() {
    }
}
