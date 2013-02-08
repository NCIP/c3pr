/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.common;

public enum RuleType {

    AE_ASSESMENT_RULES("AE Assesment Rules", "Rules regarding adverse event assesments"), REPORT_SCHEDULING_RULES(
                    "SAE Reporting Rules", "The rules regarding identifying the reporting periods"), MANDATORY_SECTIONS_RULES(
                    "Mandatory Sections Rules",
                    "The rules regarding identifying the mandatory sections");

    private String name;

    private String desc;

    RuleType(String name, String desc) {
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
