class UpdateCosentsMandatoryIndicator extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("update consents set mandatory_indicator ='1'");
	}
	void down() {
    }
}
