class AlterIndicatorFieldsStringToBoolean extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
    
    if (databaseMatches('postgres')){
    	 execute("update studies set multi_institution_indicator = 'TRUE' where multi_institution_indicator = 'Yes' or multi_institution_indicator = 'true'")
    	 execute("update studies set multi_institution_indicator = 'FALSE' where multi_institution_indicator = 'No' or multi_institution_indicator = 'false'")
    	 execute("alter table studies alter column multi_institution_indicator Type boolean USING CASE WHEN multi_institution_indicator = 'FALSE' THEN FALSE WHEN multi_institution_indicator = 'TRUE' THEN TRUE ELSE NULL END")
    	 
    	 execute("update studies set blinded_indicator = 'TRUE' where blinded_indicator = 'Yes' or blinded_indicator = 'true'")
    	 execute("update studies set blinded_indicator = 'FALSE' where blinded_indicator = 'No' or blinded_indicator = 'false'")
    	 execute("alter table studies alter column blinded_indicator Type boolean USING CASE WHEN blinded_indicator = 'FALSE' THEN FALSE WHEN blinded_indicator = 'TRUE' THEN TRUE ELSE NULL END")
    	 
    	 execute("update studies set randomized_indicator = 'TRUE' where randomized_indicator = 'Yes' or randomized_indicator = 'true'")
    	 execute("update studies set randomized_indicator = 'FALSE' where randomized_indicator = 'No' or randomized_indicator = 'false'")
    	 execute("alter table studies alter column randomized_indicator Type boolean USING CASE WHEN randomized_indicator = 'FALSE' THEN FALSE WHEN randomized_indicator = 'TRUE' THEN TRUE ELSE NULL END")
    	 
    	 execute("update epochs set reservation_indicator = 'TRUE' where reservation_indicator = 'Yes' or reservation_indicator = 'true'")
    	 execute("update epochs set reservation_indicator = 'FALSE' where reservation_indicator = 'No' or reservation_indicator = 'false'")
    	 execute("alter table epochs alter column reservation_indicator Type boolean USING CASE WHEN reservation_indicator = 'FALSE' THEN FALSE WHEN reservation_indicator = 'TRUE' THEN TRUE ELSE NULL END")
    	 
    	 execute("update epochs set enrollment_indicator = 'TRUE' where enrollment_indicator = 'Yes' or enrollment_indicator = 'true'")
    	 execute("update epochs set enrollment_indicator = 'FALSE' where enrollment_indicator = 'No' or enrollment_indicator = 'false'")
    	 execute("alter table epochs alter column enrollment_indicator Type boolean USING CASE WHEN enrollment_indicator = 'FALSE' THEN FALSE WHEN enrollment_indicator = 'TRUE' THEN TRUE ELSE NULL END")
    	 
    	 execute("update epochs set accrual_indicator = 'TRUE' where accrual_indicator = 'Yes' or accrual_indicator = 'true'")
    	 execute("update epochs set accrual_indicator = 'FALSE' where accrual_indicator = 'No' or accrual_indicator = 'false'")
    	 execute("alter table epochs alter column accrual_indicator Type boolean USING CASE WHEN accrual_indicator = 'FALSE' THEN FALSE WHEN accrual_indicator = 'TRUE' THEN TRUE ELSE NULL END")
    	 
    	 execute("update scheduled_arms set eligibility_indicator = 'TRUE' where eligibility_indicator = 'Yes' or eligibility_indicator = 'true'")
    	 execute("update scheduled_arms set eligibility_indicator = 'FALSE' where eligibility_indicator = 'No' or eligibility_indicator = 'false'")
    	 execute("alter table scheduled_arms alter column eligibility_indicator Type boolean USING CASE WHEN eligibility_indicator = 'FALSE' THEN FALSE WHEN eligibility_indicator = 'TRUE' THEN TRUE ELSE NULL END")
    	 }
    	
    	if (databaseMatches('oracle')){
    	execute("update studies set multi_institution_indicator = '1' where multi_institution_indicator = 'Yes' or multi_institution_indicator = 'true'")
    	execute("update studies set multi_institution_indicator = '0' where multi_institution_indicator = 'No' or multi_institution_indicator = 'false'")
    	
    	execute("update studies set blinded_indicator = '1' where blinded_indicator = 'Yes' or blinded_indicator = 'true'")
    	execute("update studies set blinded_indicator = '0' where blinded_indicator = 'No' or blinded_indicator = 'false'")
    	
    	execute("update studies set randomized_indicator = '1' where randomized_indicator = 'Yes' or randomized_indicator = 'true'")
    	execute("update studies set randomized_indicator = '0' where randomized_indicator = 'No' or randomized_indicator = 'false'")
    	
    	execute("update epochs set reservation_indicator = '1' where reservation_indicator = 'Yes' or reservation_indicator = 'true'")
    	execute("update epochs set reservation_indicator = '0' where reservation_indicator = 'No' or reservation_indicator = 'false'")
    	
    	execute("update epochs set enrollment_indicator = '1' where enrollment_indicator = 'Yes' or enrollment_indicator = 'true'")
    	execute("update epochs set enrollment_indicator = '0' where enrollment_indicator = 'No' or enrollment_indicator = 'false'")
    	
    	execute("update epochs set accrual_indicator = '1' where accrual_indicator = 'Yes' or accrual_indicator = 'true'")
    	execute("update epochs set accrual_indicator = '0' where accrual_indicator = 'No' or accrual_indicator = 'false'")
    	
    	execute("update scheduled_arms set eligibility_indicator = '1' where eligibility_indicator = 'Yes' or eligibility_indicator = 'true'")
    	execute("update scheduled_arms set eligibility_indicator = '0' where eligibility_indicator = 'No' or eligibility_indicator = 'false'")
    	 }
   	 }

    void down() {
	}
	
}