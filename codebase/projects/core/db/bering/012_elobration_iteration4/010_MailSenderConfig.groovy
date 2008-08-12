class MailSenderConfig extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('configuration', [ key:"smtpSSLAuth", value:"true" ], primaryKey: false)
		insert('configuration', [ key:"smtpProtocol", value:"smtps" ], primaryKey: false)
	}

	void down(){
         delete('configuration', [ key:"smtpSSLAuth" ])
         delete('configuration', [ key:"smtpProtocol" ])
	}
}
