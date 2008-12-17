class mailSenderConfig extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    execute("update configuration set value='smtp.gmail.com' where prop='outgoingMailServer'")
	    execute("update configuration set value='465' where prop='outgoingMailServerPort'")
	    execute("update configuration set value='c3prproject@gmail.com' where prop='outgoingMailUsername'")
	    execute("update configuration set value='semanticbits' where prop='outgoingMailPassword'")
	    execute("update configuration set value='c3prproject@gmail.com' where prop='outgoingMailFromAddress'")
	    execute("update configuration set value='c3prproject@gmail.com' where prop='outgoingMailFromAddress'")
	    execute("update configuration set value='true' where prop='outgoingMailAuth'")
	    execute("update configuration set value='true' where prop='smtpSSLAuth'")
	    execute("update configuration set value='smtps' where prop='smtpProtocol'")
    }

    void down() {
        
    }
}
