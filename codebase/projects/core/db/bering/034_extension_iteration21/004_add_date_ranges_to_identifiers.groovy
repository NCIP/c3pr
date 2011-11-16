class AddDatesRangesToIdentifiers extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('identifiers','start_date','date');
		addColumn('identifiers','end_date','date');
	}
	void down() {
    }
}
