package edu.duke.cabig.c3pr.rules.common;

import edu.duke.cabig.c3pr.rules.exception.RuleException;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.lang.descr.PackageDescr;
import org.drools.rule.Package;
import org.drools.rule.builder.dialect.java.JavaDialectConfiguration;
import org.drools.xml.XmlPackageReader;

public class XMLUtil {
    private static final Log log = LogFactory.getLog(XMLUtil.class);

    public static Object unmarshal(String xml) throws RuleException{
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(
                            "edu.duke.cabig.c3pr.rules.brxml").createUnmarshaller();
            log.debug("reading the rule:" + xml);
            return unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            throw new RuleException(e.getMessage(), e);
        }
    }

    public static String marshal(Object object) throws RuleException{
        StringWriter writer = new StringWriter();
        try {
            Marshaller marshaller = JAXBContext.newInstance("edu.duke.cabig.c3pr.rules.brxml")
                            .createMarshaller();
            marshaller.marshal(object, writer);
            log.debug("Before writing:" + writer.toString());
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuleException(e.getMessage(), e);
        }
    }

    /**
     * This utility method will convert the rule-xml to a Drools Package object
     *
     * @param xml
     * @return
     */
    public static Package unmarshalToPackage(String xml) {
    	//System.out.println(xml);
    	//Properties properties = new Properties();
    	//properties.setProperty( "drools.dialect.java.compiler","ECLIPSE" );
    	PackageBuilderConfiguration cfg = new PackageBuilderConfiguration( );
    	JavaDialectConfiguration javaConf = (JavaDialectConfiguration) cfg.getDialectConfiguration( "java" );
    	javaConf.setCompiler( JavaDialectConfiguration.ECLIPSE );
        javaConf.setJavaLanguageLevel("1.5");

    	Reader ruleReader;
    	PackageBuilder packageBuilder = new PackageBuilder(cfg);
		try {
			ruleReader = new StringReader(xml);
			final XmlPackageReader xmlPackageReader = new XmlPackageReader();
	    	PackageDescr pd = xmlPackageReader.read(ruleReader);

	    	packageBuilder.addPackage(pd);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packageBuilder.getPackage();






    	//Package droolsPackage = new Package();
    	/*

    	Properties properties = new Properties();
    	properties.setProperty( "drools.dialect.java.compiler","JANINO" );
    	PackageBuilderConfiguration conf = new PackageBuilderConfiguration(properties);

       // JavaDialectConfiguration javaConf = (JavaDialectConfiguration) conf.getDialectConfiguration( "java" );

        //javaConf.setCompiler( JavaDialectConfiguration.ECLIPSE );
        //javaConf.setJavaLanguageLevel("1.5");


 //       conf.setCompiler(PackageBuilderConfiguration.ECLIPSE);
  //      conf.setJavaLanguageLevel("1.5");
        Package droolsPackage = new Package();

        // merge the rule xml into the package
        try {
            Reader ruleReader = null;
            //ruleReader = new StringReader(xml);
            ruleReader = new FileReader("/Users/sakkala/temp/temp.xml");
            PackageBuilder packageBuilder = new PackageBuilder();
            //packageBuilder.addPackageFromXml( new InputStreamReader( getClass().getResourceAsStream( "/Users/sakkala/temp/temp.xml" ) ) );
            packageBuilder.addPackageFromXml(ruleReader);
            droolsPackage = packageBuilder.getPackage();
            //System.out.print("XXXXXX" + xml);
        } catch (Exception e) {
            log.error("Error while converting xml form of rules into package\r\n XML rules :\r\n"
                            + xml, e);
            throw new RuleException(e.getMessage(), e);
        }
        */

    }

    /*
     * public static String marshal(Rule rule) { StringWriter writer = new StringWriter(); try {
     * Marshaller marshaller =
     * JAXBContext.newInstance("edu.duke.cabig.c3pr.rules.brxml").createMarshaller();
     * marshaller.marshal( new JAXBElement( new
     * QName("http://caaers.cabig.nci.nih.gov/rules/brxml","rule"), Rule.class, rule ), writer);
     * return writer.toString(); } catch (JAXBException e) { throw new RuleException(e.getMessage(),
     * e); } }
     */

    /*
     * //REFERENCE THIS IF ANY DAY YOU WANT TO USE JIBX AS MECHANISM FOR DATABINDING public static
     * String toXML(Object rootElement) { IMarshallingContext mctx = null; StringWriter writer = new
     * StringWriter(); try { IBindingFactory bfact = BindingDirectory
     * .getFactory(rootElement.getClass()); mctx = bfact.createMarshallingContext();
     * mctx.setOutput(writer); mctx.marshalDocument(rootElement); } catch (JiBXException e) { throw
     * new RuleException(e.getMessage(), e); } //String returnString = "<?xml version=\"1.0\"
     * encoding=\"UTF-8\"?>" + writer.toString(); //return "<?xml version=\"1.0\"
     * encoding=\"UTF-8\"?>" + writer.toString(); return writer.toString(); }
     *
     * public static Object toObject(String xml){ IUnmarshallingContext uctx = null; Object obj =
     * null; try { obj = uctx.unmarshalDocument(new StringReader(xml)); } catch (JiBXException e) {
     * throw new RuleException(e.getMessage(), e); } return obj; }
     *
     * //REFERENCE THIS IF ANY DAY YOU WANT TO USE XSTREAM AS MECHANISM FOR DATABINDING
     *
     * public static String getXML(Object obj) { XStream xstream = new XStream(new DomDriver());
     * updateAliases(xstream); //xstream.aliasField("testField", Test.class, "test"); return
     * xstream.toXML(obj); }
     *
     * public static Object getObject(String xml) { XStream xstream = new XStream(new DomDriver());
     * updateAliases(xstream); //xstream.aliasField("testField", Test.class, "test"); return
     * xstream.fromXML(xml); }
     *
     * private static void updateAliases(XStream xstream) { xstream.alias("ruleSet",
     * edu.duke.cabig.c3pr.rules.brxml.RuleSet.class); xstream.alias("rule",
     * edu.duke.cabig.c3pr.rules.brxml.Rule.class); xstream.alias("category",
     * edu.duke.cabig.c3pr.rules.brxml.Category.class); xstream.alias("ruleAttribute",
     * edu.duke.cabig.c3pr.rules.brxml.RuleAttribute.class); xstream.alias("metaData",
     * edu.duke.cabig.c3pr.rules.brxml.MetaData.class); xstream.alias("action",
     * edu.duke.cabig.c3pr.rules.brxml.Action.class); xstream.alias("column",
     * edu.duke.cabig.c3pr.rules.brxml.Column.class); }
     */

}
