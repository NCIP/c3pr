/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.common.adapter;

import gov.nih.nci.cabig.c3pr.rules.brxml.Column;
import gov.nih.nci.cabig.c3pr.rules.brxml.Rule;
import gov.nih.nci.cabig.c3pr.rules.brxml.RuleSet;
import edu.duke.cabig.c3pr.rules.common.XMLUtil;
import edu.duke.cabig.c3pr.rules.exception.RuleException;
import edu.duke.cabig.c3pr.utils.XsltTransformer;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.rule.Package;

public class CaAERSJBossXSLTRuleAdapter implements RuleAdapter {

    private static final Log log = LogFactory.getLog(CaAERSJBossXSLTRuleAdapter.class);

    public Package adapt(RuleSet ruleSet) throws RuleException {

        /*
         * Add adverseEventEvaluationResult here and remove it from everywhere else since this is a
         * hidden column, it should not be used for authoring. It is used only at runtime. So it
         * make sense to add to the condition here. IMP: This is only required for caAERS project.
         */
        List<Rule> rules = ruleSet.getRule();
        for (Rule r : rules) {
            Column column_fixed = new Column();
            column_fixed
                            .setObjectType("edu.duke.cabig.c3pr.rules.common.AdverseEventEvaluationResult");
            column_fixed.setIdentifier("adverseEventEvaluationResult");
            r.getCondition().getColumn().add(column_fixed);

        }

        List<String> imports = ruleSet.getImport();
        if (log.isDebugEnabled()) {
            log.debug("Size of imports:" + imports.size());
            for (String s : imports) {
                log.debug("each import :" + s);
            }
        }

        // marshal and transform
        String xml1 = XMLUtil.marshal(ruleSet);
        log.debug("Marshalled, Before transforming using [jboss-rules-intermediate.xsl]:\r\n"
                        + xml1);

        XsltTransformer xsltTransformer = new XsltTransformer();
        String xml = "";

        try {
            xml = xsltTransformer.toXml(xml1, "jboss-rules-intermediate.xsl");
            log.debug("After transforming using [jboss-rules-intermediate.xsl] :\n\r" + xml);
        } catch (Exception e) {
            log.error("Exception while transforming to New Scheme: " + e.getMessage(), e);
        }

        StringWriter writer = new StringWriter();

        System.setProperty("javax.xml.transform.TransformerFactory",
                        "org.apache.xalan.processor.TransformerFactoryImpl");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        try {

            Templates translet = transformerFactory.newTemplates(new StreamSource(Thread
                            .currentThread().getContextClassLoader().getResourceAsStream(
                                            "new_jobss_rules.xsl")));
            Transformer transformer = translet.newTransformer();

            if (log.isDebugEnabled()) {
                log.debug("Before transforming using [new_jobss_rules.xsl] :\r\n" + xml);
                StringWriter strWriter = new StringWriter();
                transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(
                                strWriter));
                log.debug("After transforming using [new_jboss_rules.xsl]:\r\n"
                                + strWriter.toString());
            }

            transformer
                            .transform(new StreamSource(new StringReader(xml)), new StreamResult(
                                            writer));

        } catch (Exception e) {
            log.error("Error while transforming using new_jboss_rules.xsl", e);
            throw new RuleException("Unable to transform using new_jboss_rules.xsl", e);
        }

        // create the rules package
        return XMLUtil.unmarshalToPackage(writer.toString());
    }

}
