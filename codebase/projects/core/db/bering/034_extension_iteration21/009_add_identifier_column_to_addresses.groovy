class AddIdentifierColumnToAddresses extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('addresses','identifier','string');
	}
	void down() {
    }
}
