/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The Class ScheduledArm.
 */
@Entity
@Table(name = "SCHEDULED_ARMS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SCHEDULED_ARMS_ID_seq") })
public class ScheduledArm extends AbstractMutableDeletableDomainObject {

    /** The start date. */
    private Date startDate;

    /** The eligibility indicator. */
    private Boolean eligibilityIndicator;

    /** The eligibility waiver reason text. */
    private String eligibilityWaiverReasonText;

    /** The arm. */
    private Arm arm;

    /** The kit number. */
    private String kitNumber;

    /**
     * Instantiates a new scheduled arm.
     */
    public ScheduledArm() {
        this.startDate = new Date();
        this.eligibilityIndicator = Boolean.TRUE;
    }

    /**
     * Gets the arm.
     * 
     * @return the arm
     */
    @ManyToOne
    @JoinColumn(name = "ARM_ID")
    public Arm getArm() {
        return arm;
    }

    /**
     * Sets the arm.
     * 
     * @param arm the new arm
     */
    public void setArm(Arm arm) {
        this.arm = arm;
    }

    /**
     * Gets the eligibility indicator.
     * 
     * @return the eligibility indicator
     */
    public Boolean getEligibilityIndicator() {
        return eligibilityIndicator;
    }

    /**
     * Sets the eligibility indicator.
     * 
     * @param eligibilityIndicator the new eligibility indicator
     */
    public void setEligibilityIndicator(Boolean eligibilityIndicator) {
        this.eligibilityIndicator = eligibilityIndicator;
    }

    /**
     * Gets the start date.
     * 
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     * 
     * @param startDate the new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the eligibility waiver reason text.
     * 
     * @return the eligibility waiver reason text
     */
    public String getEligibilityWaiverReasonText() {
        return eligibilityWaiverReasonText;
    }

    /**
     * Sets the eligibility waiver reason text.
     * 
     * @param eligibilityWaiverReasonText the new eligibility waiver reason text
     */
    public void setEligibilityWaiverReasonText(String eligibilityWaiverReasonText) {
        this.eligibilityWaiverReasonText = eligibilityWaiverReasonText;
    }

    /**
     * Gets the kit number.
     * 
     * @return the kit number
     */
    public String getKitNumber() {
        return kitNumber;
    }

    /**
     * Sets the kit number.
     * 
     * @param kitNumber the new kit number
     */
    public void setKitNumber(String kitNumber) {
        this.kitNumber = kitNumber;
    }
}
