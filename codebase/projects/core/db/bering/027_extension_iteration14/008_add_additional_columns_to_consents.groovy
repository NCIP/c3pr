class AddAdditionalColumnsToConsents extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('consents', 'mandatory_indicator', 'boolean',nullable: false,defaultValue: 0);
    	addColumn('consents', 'version_id', 'string');
	}
	void down() {
    }
}
