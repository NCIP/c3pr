class UpdateCCTSDemoUserPassword extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
			execute("update csm_user set password='56nit3oKigG8j2uyHEABIQ==' where user_id=-1001")
    }

    void down() {

    }
}