/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * 
 * HTML filtering utility for protecting against XSS (Cross Site Scripting).
 * 
 * This code is licensed under a Creative Commons Attribution-ShareAlike 2.5
 * License http://creativecommons.org/licenses/by-sa/2.5/
 * 
 * This code is a Java port of the original work in PHP by Cal Hendersen.
 * http://code.iamcal.com/php/lib_filter/
 * 
 * The trickiest part of the translation was handling the differences in regex
 * handling between PHP and Java. These resources were helpful in the process:
 * 
 * http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html
 * http://us2.php.net/manual/en/reference.pcre.pattern.modifiers.php
 * http://www.regular-expressions.info/modifiers.html
 * 
 * A note on naming conventions: instance variables are prefixed with a "v";
 * global constants are in all caps.
 * 
 * Sample use: String input = ... String clean = new HTMLInputFilter().filter(
 * input );
 * 
 * If you find bugs or have suggestions on improvement (especially regarding
 * perfomance), please contact me at the email below. The latest version of this
 * source can be found at
 * 
 * http://josephoconnell.com/java/xss-html-filter/
 * 
 * @author Joseph O'Connell <joe.oconnell at gmail dot com>
 * @version 1.0
 */
public class HTMLInputCleaner {
	/**
	 * flag determining whether to try to make tags when presented with
	 * "unbalanced" angle brackets (e.g. "<b text </b>" becomes
	 * "<b> text </b>"). If set to false, unbalanced angle brackets will be html
	 * escaped.
	 */
	protected static final boolean ALWAYS_MAKE_TAGS = true;

	/**
	 * flag determing whether comments are allowed in input String.
	 */
	protected static final boolean STRIP_COMMENTS = true;

	/** regex flag union representing /si modifiers in php **/
	protected static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL;

	/**
	 * set of allowed html elements, along with allowed attributes for each
	 * element
	 **/
	protected Map<String, List<String>> vAllowed;

	/** counts of open tags for each (allowable) html element **/
	protected Map<String, Integer> vTagCounts;

	/** html elements which must always be self-closing (e.g. "<img />") **/
	protected String[] vSelfClosingTags;

	/**
	 * html elements which must always have separate opening and closing tags
	 * (e.g. "<b></b>")
	 **/
	protected String[] vNeedClosingTags;

	/** attributes which should be checked for valid protocols **/
	protected String[] vProtocolAtts;

	/** allowed protocols **/
	protected String[] vAllowedProtocols;

	/**
	 * tags which should be removed if they contain no content (e.g. "<b></b>"
	 * or "<b />")
	 **/
	protected String[] vRemoveBlanks;

	/** entities allowed within html markup **/
	protected String[] vAllowedEntities;

	//Adding logger
	private Logger log = Logger.getLogger(HTMLInputCleaner.class);
	
	public HTMLInputCleaner() {

		vAllowed = new HashMap<String, List<String>>();
		vTagCounts = new HashMap<String, Integer>();

		ArrayList<String> a_atts = new ArrayList<String>();
		a_atts.add("href");
		a_atts.add("target");
		vAllowed.put("a", a_atts);

		ArrayList<String> img_atts = new ArrayList<String>();
		img_atts.add("src");
		img_atts.add("width");
		img_atts.add("height");
		img_atts.add("alt");
		vAllowed.put("img", img_atts);

		ArrayList<String> no_atts = new ArrayList<String>();
		vAllowed.put("b", no_atts);
		vAllowed.put("strong", no_atts);
		vAllowed.put("i", no_atts);
		vAllowed.put("em", no_atts);

		vSelfClosingTags = new String[] { "img" };
		vNeedClosingTags = new String[] { "a", "b", "strong", "i", "em" };
		vAllowedProtocols = new String[] { "http", "mailto" }; // no ftp.
		vProtocolAtts = new String[] { "src", "href" };
		vRemoveBlanks = new String[] { "a", "b", "strong", "i", "em" };
		vAllowedEntities = new String[] { "amp", "gt", "lt", "quot" };
	}

	protected void reset() {
		vTagCounts = new HashMap<String, Integer>();
	}

	
	//Changing the debug to logger
	protected void debug(String msg) {
		log.debug(msg);
	}

	// ---------------------------------------------------------------
	// my versions of some PHP library functions

	public static String chr(int decimal) {
		return String.valueOf((char) decimal);
	}

	public static String htmlSpecialChars(String s) {
		s = s.replaceAll("&", "&amp;");
		s = s.replaceAll("\"", "&quot;");
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll(">", "&gt;");
		return s;
	}

	// ---------------------------------------------------------------

	/**
	 * given a user submitted input String, filter out any invalid or restricted
	 * html.
	 * 
	 * @param input
	 *            text (i.e. submitted by a user) than may contain html
	 * @return "clean" version of input, with only valid, whitelisted html
	 *         elements allowed
	 */
	public synchronized String filter(String input) {
		reset();
		String s = input;

		debug("************************************************");
		debug("              INPUT: " + input);

		s = escapeComments(s);
		debug("     escapeComments: " + s);

		s = balanceHTML(s);
		debug("        balanceHTML: " + s);

		s = checkTags(s);
		debug("          checkTags: " + s);

		s = processRemoveBlanks(s);
		debug("processRemoveBlanks: " + s);

		s = validateEntities(s);
		debug("    validateEntites: " + s);

		debug("************************************************\n\n");
		return s;
	}

	protected String escapeComments(String s) {
		Pattern p = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);
		Matcher m = p.matcher(s);
		StringBuffer buf = new StringBuffer();
		if (m.find()) {
			String match = m.group(1); // (.*?)
			m.appendReplacement(buf, "<!--" + htmlSpecialChars(match) + "-->");
		}
		m.appendTail(buf);

		return buf.toString();
	}

	protected String balanceHTML(String s) {
		if (ALWAYS_MAKE_TAGS) {
			//
			// try and form html
			//
			s = regexReplace("^>", "", s);
			s = regexReplace("<([^>]*?)(?=<|$)", "<$1>", s);
			s = regexReplace("(^|>)([^<]*?)(?=>)", "$1<$2", s);

		} else {
			//
			// escape stray brackets
			//
			s = regexReplace("<([^>]*?)(?=<|$)", "&lt;$1", s);
			s = regexReplace("(^|>)([^<]*?)(?=>)", "$1$2&gt;<", s);

			//
			// the last regexp causes '<>' entities to appear
			// (we need to do a lookahead assertion so that the last bracket can
			// be used in the next pass of the regexp)
			//
			s = s.replaceAll("<>", "");
		}

		return s;
	}

	protected String checkTags(String s) {
		Pattern p = Pattern.compile("<(.*?)>", Pattern.DOTALL);
		Matcher m = p.matcher(s);

		StringBuffer buf = new StringBuffer();
		while (m.find()) {
			String replaceStr = m.group(1);
			replaceStr = processTag(replaceStr);
			m.appendReplacement(buf, replaceStr);
		}
		m.appendTail(buf);

		s = buf.toString();

		// these get tallied in processTag
		// (remember to reset before subsequent calls to filter method)
		for (String key : vTagCounts.keySet()) {
			for (int ii = 0; ii < vTagCounts.get(key); ii++) {
				s += "</" + key + ">";
			}
		}

		return s;
	}

	protected String processRemoveBlanks(String s) {
		for (String tag : vRemoveBlanks) {
			s = regexReplace("<" + tag + "(\\s[^>]*)?></" + tag + ">", "", s);
			s = regexReplace("<" + tag + "(\\s[^>]*)?/>", "", s);
		}

		return s;
	}

	protected String regexReplace(String regex_pattern, String replacement,
			String s) {
		Pattern p = Pattern.compile(regex_pattern);
		Matcher m = p.matcher(s);
		return m.replaceAll(replacement);
	}

	protected String processTag(String s) {
		// ending tags
		Pattern p = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);
		Matcher m = p.matcher(s);
		if (m.find()) {
			String name = m.group(1).toLowerCase();
			if (vAllowed.containsKey(name)) {
				if (!inArray(name, vSelfClosingTags)) {
					if (vTagCounts.containsKey(name)) {
						vTagCounts.put(name, vTagCounts.get(name) - 1);
						return "</" + name + ">";
					}
				}
			}
		}

		// starting tags
		p = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
		m = p.matcher(s);
		if (m.find()) {
			String name = m.group(1).toLowerCase();
			String body = m.group(2);
			String ending = m.group(3);

			// debug( "in a starting tag, name='" + name + "'; body='" + body +
			// "'; ending='" + ending + "'" );
			if (vAllowed.containsKey(name)) {
				String params = "";

				Pattern p2 = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2",
						REGEX_FLAGS_SI);
				Pattern p3 = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)",
						REGEX_FLAGS_SI);
				Matcher m2 = p2.matcher(body);
				Matcher m3 = p3.matcher(body);
				List<String> paramNames = new ArrayList<String>();
				List<String> paramValues = new ArrayList<String>();
				while (m2.find()) {
					paramNames.add(m2.group(1)); // ([a-z0-9]+)
					paramValues.add(m2.group(3)); // (.*?)
				}
				while (m3.find()) {
					paramNames.add(m3.group(1)); // ([a-z0-9]+)
					paramValues.add(m3.group(3)); // ([^\"\\s']+)
				}

				String paramName, paramValue;
				for (int ii = 0; ii < paramNames.size(); ii++) {
					paramName = paramNames.get(ii).toLowerCase();
					paramValue = paramValues.get(ii);

					// debug( "paramName='" + paramName + "'" );
					// debug( "paramValue='" + paramValue + "'" );
					// debug( "allowed? " + vAllowed.get( name ).contains(
					// paramName ) );

					if (vAllowed.get(name).contains(paramName)) {
						if (inArray(paramName, vProtocolAtts)) {
							paramValue = processParamProtocol(paramValue);
						}
						params += " " + paramName + "=\"" + paramValue + "\"";
					}
				}

				if (inArray(name, vSelfClosingTags)) {
					ending = " /";
				}

				if (inArray(name, vNeedClosingTags)) {
					ending = "";
				}

				if (ending == null || ending.length() < 1) {
					if (vTagCounts.containsKey(name)) {
						vTagCounts.put(name, vTagCounts.get(name) + 1);
					} else {
						vTagCounts.put(name, 1);
					}
				} else {
					ending = " /";
				}
				return "<" + name + params + ending + ">";
			} else {
				return "";
			}
		}

		// comments
		p = Pattern.compile("^!--(.*)--$", REGEX_FLAGS_SI);
		m = p.matcher(s);
		if (m.find()) {
			String comment = m.group();
			if (STRIP_COMMENTS) {
				return "";
			} else {
				return "<" + comment + ">";
			}
		}

		return "";
	}

	protected String processParamProtocol(String s) {
		s = decodeEntities(s);
		Pattern p = Pattern.compile("^([^:]+):", REGEX_FLAGS_SI);
		Matcher m = p.matcher(s);
		if (m.find()) {
			String protocol = m.group(1);
			if (!inArray(protocol, vAllowedProtocols)) {
				// bad protocol, turn into local anchor link instead
				s = "#" + s.substring(protocol.length() + 1, s.length());
				if (s.startsWith("#//"))
					s = "#" + s.substring(3, s.length());
			}
		}

		return s;
	}

	protected String decodeEntities(String s) {
		StringBuffer buf = new StringBuffer();

		Pattern p = Pattern.compile("&#(\\d+);?");
		Matcher m = p.matcher(s);
		while (m.find()) {
			String match = m.group(1);
			int decimal = Integer.decode(match).intValue();
			m.appendReplacement(buf, chr(decimal));
		}
		m.appendTail(buf);
		s = buf.toString();

		buf = new StringBuffer();
		p = Pattern.compile("&#x([0-9a-f]+);?");
		m = p.matcher(s);
		while (m.find()) {
			String match = m.group(1);
			int decimal = Integer.decode(match).intValue();
			m.appendReplacement(buf, chr(decimal));
		}
		m.appendTail(buf);
		s = buf.toString();

		buf = new StringBuffer();
		p = Pattern.compile("%([0-9a-f]{2});?");
		m = p.matcher(s);
		while (m.find()) {
			String match = m.group(1);
			int decimal = Integer.decode(match).intValue();
			m.appendReplacement(buf, chr(decimal));
		}
		m.appendTail(buf);
		s = buf.toString();

		s = validateEntities(s);
		return s;
	}

	protected String validateEntities(String s) {
		// validate entities throughout the string
		Pattern p = Pattern.compile("&([^&;]*)(?=(;|&|$))");
		Matcher m = p.matcher(s);
		if (m.find()) {
			String one = m.group(1); // ([^&;]*)
			String two = m.group(2); // (?=(;|&|$))
			s = checkEntity(one, two);
		}

		// validate quotes outside of tags
		p = Pattern.compile("(>|^)([^<]+?)(<|$)", Pattern.DOTALL);
		m = p.matcher(s);
		StringBuffer buf = new StringBuffer();
		if (m.find()) {
			String one = m.group(1); // (>|^)
			String two = m.group(2); // ([^<]+?)
			String three = m.group(3); // (<|$)
			m.appendReplacement(buf, one + two.replaceAll("\"", "&quot;")
					+ three);
		}
		m.appendTail(buf);

		return s;
	}

	protected String checkEntity(String preamble, String term) {
		if (!term.equals(";")) {
			return "&amp;" + preamble;
		}

		if (isValidEntity(preamble)) {
			return "&" + preamble;
		}

		return "&amp;" + preamble;
	}

	protected boolean isValidEntity(String entity) {
		return inArray(entity, vAllowedEntities);
	}

	private boolean inArray(String s, String[] array) {
		for (String item : array)
			if (item != null && item.equals(s))
				return true;

		return false;
	}

	// ============================================ START-UNIT-TEST
	// =========================================
	public static class HTMLInputCleanerTest extends TestCase {
		protected HTMLInputCleaner vFilter;

		protected void setUp() {
			vFilter = new HTMLInputCleaner();
		}

		protected void tearDown() {
			vFilter = null;
		}

		private void t(String input, String result) {
			Assert.assertEquals(result, vFilter.filter(input));
		}

		public void test_basics() {
			t("", "");
			t("hello", "hello");
		}

		public void test_balancing_tags() {
			t("<b>hello", "<b>hello</b>");
			t("<b>hello", "<b>hello</b>");
			t("hello<b>", "hello");
			t("hello</b>", "hello");
			t("hello<b/>", "hello");
			t("<b><b><b>hello", "<b><b><b>hello</b></b></b>");
			t("</b><b>", "");
		}

		public void test_end_slashes() {
			t("<img>", "<img />");
			t("<img/>", "<img />");
			t("<b/></b>", "");
		}

		public void test_balancing_angle_brackets() {
			if (ALWAYS_MAKE_TAGS) {
				t("<img src=\"foo\"", "<img src=\"foo\" />");
				t("i>", "");
				t("<img src=\"foo\"/", "<img src=\"foo\" />");
				t(">", "");
				t("foo<b", "foo");
				t("b>foo", "<b>foo</b>");
				t("><b", "");
				t("b><", "");
				t("><b>", "");
			} else {
				t("<img src=\"foo\"", "&lt;img src=\"foo\"");
				t("b>", "b&gt;");
				t("<img src=\"foo\"/", "&lt;img src=\"foo\"/");
				t(">", "&gt;");
				t("foo<b", "foo&lt;b");
				t("b>foo", "b&gt;foo");
				t("><b", "&gt;&lt;b");
				t("b><", "b&gt;&lt;");
				t("><b>", "&gt;");
			}
		}

		public void test_attributes() {
			t("<img src=foo>", "<img src=\"foo\" />");
			t("<img asrc=foo>", "<img />");
			t("<img src=test test>", "<img src=\"test\" />");
		}

		public void test_disallow_script_tags() {
			t("<script>", "");
			if (ALWAYS_MAKE_TAGS) {
				t("<script", "");
			} else {
				t("<script", "&lt;script");
			}
			t("<script/>", "");
			t("</script>", "");
			t("<script woo=yay>", "");
			t("<script woo=\"yay\">", "");
			t("<script woo=\"yay>", "");
			t("<script woo=\"yay<b>", "");
			t("<script<script>>", "");
			t("<<script>script<script>>", "script");
			t("<<script><script>>", "");
			t("<<script>script>>", "");
			t("<<script<script>>", "");
		}

		public void test_protocols() {
			t("<a href=\"http://foo\">bar</a>",
					"<a href=\"http://foo\">bar</a>");
			// we don't allow ftp. t("<a href=\"ftp://foo\">bar</a>",
			// "<a href=\"ftp://foo\">bar</a>");
			t("<a href=\"mailto:foo\">bar</a>",
					"<a href=\"mailto:foo\">bar</a>");
			t("<a href=\"javascript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"java script:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"java\tscript:foo\">bar</a>",
					"<a href=\"#foo\">bar</a>");
			t("<a href=\"java\nscript:foo\">bar</a>",
					"<a href=\"#foo\">bar</a>");
			t("<a href=\"java" + chr(1) + "script:foo\">bar</a>",
					"<a href=\"#foo\">bar</a>");
			t("<a href=\"jscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"vbscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"view-source:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		}

		public void test_self_closing_tags() {
			t("<img src=\"a\">", "<img src=\"a\" />");
			t("<img src=\"a\">foo</img>", "<img src=\"a\" />foo");
			t("</img>", "");
		}

		public void test_comments() {
			if (STRIP_COMMENTS) {
				t("<!-- a<b --->", "");
			} else {
				t("<!-- a<b --->", "<!-- a&lt;b --->");
			}
		}

	}
	// ============================================ END-UNIT-TEST
	// ===========================================
}
