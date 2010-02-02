class DeletConfigurationFromAddress extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("delete from configuration where prop='outgoingMailFromAddress'");
	}
	void down() {
    }
}
