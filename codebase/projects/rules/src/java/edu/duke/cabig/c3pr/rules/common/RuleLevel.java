/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.common;

public enum RuleLevel {
    Sponsor("Sponsor", "Rules for Sponsor"), Institution("Institution", "Rules for Institution"), SponsorDefinedStudy(
                    "SponsorDefinedStudy", "Rules for Sponsor Defined Study"), InstitutionDefinedStudy(
                    "InstitutionDefinedStudy", "Rules for Institution Defined Study");

    private String name;

    private String desc;

    RuleLevel(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return desc;
    }
}
