/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.common;

import gov.nih.nci.cabig.c3pr.rules.brxml.Column;
import gov.nih.nci.cabig.c3pr.rules.brxml.Condition;
import gov.nih.nci.cabig.c3pr.rules.brxml.FieldConstraint;
import gov.nih.nci.cabig.c3pr.rules.brxml.LiteralRestriction;

/**
 * @author Sujith Vellat Thayyilthodi
 */
public class BRXMLHelper {

    public static Condition newCondition() {
        Condition condition = new Condition();
        Column column = newColumn();
        condition.getColumn().add(column);
        return condition;
    }

    public static Column newColumn() {
        Column column = new Column();
        FieldConstraint fieldConstraint = newFieldConstraint();
        column.getFieldConstraint().add(fieldConstraint);
        return column;
    }

    public static FieldConstraint newFieldConstraint() {
        FieldConstraint fieldConstraint = new FieldConstraint();
        LiteralRestriction literalRestriction = new LiteralRestriction();
        fieldConstraint.getLiteralRestriction().add(literalRestriction);
        return fieldConstraint;
    }
}
