class MailSenderConfig extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"smtpSSLAuth", value:"true" ], primaryKey: false)
			insert('configuration', [ prop:"smtpProtocol", value:"smtps" ], primaryKey: false)
	}

	void down(){
			delete('configuration', [ prop:"smtpSSLAuth" ])
         	delete('configuration', [ prop:"smtpProtocol" ])
	}
}
