/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility methods to operate on W3C DOM elements.
 * 
 * @author Denis G. Krylov
 * @version 1.0
 * 
 */
public final class XMLUtils {

	private static final Logger log = Logger
			.getLogger(XMLUtils.class.getName());

	/**
	 * Does deep comparison of two DOM trees represented by the given
	 * {@link Element}s. Elements are considered root nodes of the trees. <br>
	 * <br>
	 * This version of the method will <b>only</b> process elements of types
	 * {@link Node#ELEMENT_NODE},{@link Node#TEXT_NODE},
	 * {@link Node#CDATA_SECTION_NODE}; others will be ignored. Content of text
	 * nodes is normalized before comparison. <br>
	 * <br>
	 * When comparing two elements, their local names, namespaces, and
	 * attributes (except <code>xmlns* and xsi:type</code>) are accounted for. <br>
	 * <br>
	 * Due to issues with using
	 * <code>setIgnoringElementContentWhitespace(true)</code>, which does not
	 * seem to work, this method will throw out empty text nodes when comparing
	 * children.
	 * 
	 * @since 1.0
	 * @param e1
	 * @param e2
	 * @return
	 */
	public static boolean isDeepEqual(Node n1, Node n2) {
		if (!StringUtils.equals(n1.getLocalName(), n2.getLocalName())) {
			log.info("Node local names are not equal: " + n1.getLocalName()
					+ " " + n2.getLocalName());
			return false;
		}
		if (!StringUtils.equals(n1.getNamespaceURI(), n2.getNamespaceURI())) {
			log.info("Node namespaces are not equal: " + n1.getNamespaceURI()
					+ " " + n2.getNamespaceURI());
			return false;
		}
		if (n1.getNodeType() != n2.getNodeType()) {
			log.info("Node types are not equal: " + n1.getNodeType() + " "
					+ n2.getNodeType());
			return false;
		}
		// check attributes equality.
		NamedNodeMap attrs1 = n1.getAttributes();
		NamedNodeMap attrs2 = n2.getAttributes();
		if (!isEqual(attrs1, attrs2)) {
			return false;
		}

		short nodeType = n1.getNodeType();
		switch (nodeType) {
		case Node.ELEMENT_NODE:
			return isDeepEqual(filterOutEmptyTextNodes(n1.getChildNodes()),
					filterOutEmptyTextNodes(n2.getChildNodes()));
		case Node.TEXT_NODE:
			return isTextNodeEqual(n1, n2);
		case Node.CDATA_SECTION_NODE:
			return isTextNodeEqual(n1, n2);
		default:
			break;
		}
		return true;
	}

	/**
	 * @param n1
	 * @param n2
	 * @return
	 */
	private static boolean isTextNodeEqual(Node n1, Node n2) {
		final String v1 = StringUtils.trim(normalize(n1.getNodeValue() + ""));
		final String v2 = StringUtils.trim(normalize(n2.getNodeValue() + ""));
		final boolean eq = StringUtils.equals(v1, v2);
		if (!eq) {
			log.info("Text nodes are not equal: " + v1 + " and " + v2);
		}
		return eq;

	}

	private static String normalize(String s) {
		return s.replaceAll("\\s+", " ");
	}

	/**
	 * <a href="http://www.thatsjava.com/java-enterprise/13775/">http://www.
	 * thatsjava.com/java-enterprise/13775/</a>
	 * 
	 * @param nl
	 * @return
	 */
	private static NodeList filterOutEmptyTextNodes(final NodeList nl) {
		return new FilteringNodeList(nl);
	}

	private static boolean isDeepEqual(NodeList nl1, NodeList nl2) {
		if (nl1 == null && nl2 == null) {
			return true;
		}
		if (nl1 == null || nl2 == null) {
			log.info("One of the NodeList is null while other is not. Hence, unequal.");
			return false;
		}
		if (nl1.getLength() != nl2.getLength()) {
			log.info("Different number of child nodes. Hence, unequal. " + nl1
					+ " " + nl2);
			return false;
		}
		// order of the children matters. Might need to refactor later to
		// relax this requirement.
		for (int i = 0; i < nl1.getLength(); i++) {
			if (!isDeepEqual(nl1.item(i), nl2.item(i))) {
				return false;
			}
		}
		return true;

	}

	private static boolean isEqual(NamedNodeMap attrs1, NamedNodeMap attrs2) {
		if (attrs1 == null && attrs2 == null) {
			return true;
		}
		if (attrs1 == null || attrs2 == null) {
			log.info("One of the NamedNodeMaps is null while other is not. Hence, unequal.");
			return false;
		}
		List<Node> attrs1List = filterOutIgnorableAttrs(attrs1);
		List<Node> attrs2List = filterOutIgnorableAttrs(attrs2);
		if (attrs1List.size() != attrs2List.size()) {
			log.info("Different number of attributes. Hence, unequal.");
			return false;
		}
		// order of the attributes matters. Might need to refactor later to
		// relax this requirement.
		for (int i = 0; i < attrs1List.size(); i++) {
			final String v1 = attrs1List.get(i).getNodeValue();
			final String v2 = attrs2List.get(i).getNodeValue();
			if (!v1.equals(v2)) {
				log.info("Different attribute values: " + v1 + ", " + v2);
				return false;
			}
		}
		return true;
	}

	private static List<Node> filterOutIgnorableAttrs(NamedNodeMap map) {
		List<Node> list = new ArrayList<Node>();
		for (int i = 0; i < map.getLength(); i++) {
			Node attr = map.item(i);
			if (!attr.getNodeName().startsWith("xmlns")
					&& !"xsi:type".equals(attr.getNodeName())) {
				list.add(attr);
			}
		}
		return list;
	}

	/**
	 * @author Denis G. Krylov
	 * 
	 */
	private static final class FilteringNodeList implements NodeList {

		private List<Node> nodes = new ArrayList<Node>();

		public FilteringNodeList(NodeList nList) {
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (!isIgnorableWhitespace(node)) {
					nodes.add(node);
				}
			}
		}

		/**
		 * @param index
		 * @return
		 * @see org.w3c.dom.NodeList#item(int)
		 */
		public Node item(int index) {
			return nodes.get(index);
		}

		/**
		 * @return
		 * @see org.w3c.dom.NodeList#getLength()
		 */
		public int getLength() {
			return nodes.size();
		}

		@Override
		public String toString() {
			return nodes.toString();
		}
	}

	private static boolean isIgnorableWhitespace(Node node) {
		return node.getNodeType() == Node.TEXT_NODE
				&& StringUtils.isBlank(node.getNodeValue());
	}

}
