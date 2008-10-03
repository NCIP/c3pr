class AddMultisiteConfigDefaultProperty extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('configuration', [ prop:"jms.brokerUrl", value:"tcp://localhost:61616" ], primaryKey: false)
	}

	void down(){

	}
}