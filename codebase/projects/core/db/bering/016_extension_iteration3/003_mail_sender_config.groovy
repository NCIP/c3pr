class mailSenderConfig extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    execute("update configuration set value='' where prop='outgoingMailUsername'")
	    execute("update configuration set value='' where prop='outgoingMailPassword'")
	    execute("update configuration set value='' where prop='outgoingMailFromAddress'")
    }

    void down() {
        
    }
}
