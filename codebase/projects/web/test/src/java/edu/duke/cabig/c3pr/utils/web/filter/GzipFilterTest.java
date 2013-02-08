/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.filter;

import edu.duke.cabig.c3pr.web.WebTestCase;
/**
 * This class is copied from ehcache web constructs, so no testcase for most of the methods.... 
 * 
 * The only method overriden is acceptsEncoding, for which testcases is provided. 
 * 
 * @author Biju Joseph
 *
 */
public class GzipFilterTest extends WebTestCase {
	GzipFilter filter;
	protected void setUp() throws Exception {
		super.setUp();
		filter = new GzipFilter();
	}

	public void testAcceptsEncodingIE6() {
		request.addHeader("User-Agent", "MSIE 6.0");
		request.addHeader("Accept-Encoding", "deflated,gzip");
		boolean accepts = filter.acceptsEncoding(request, "gzip");
		assertFalse(accepts);
		accepts = filter.acceptsEncoding(request, "crap");
		assertFalse(accepts);
	}
	
	public void testAcceptsEncodingIE7() {
		request.addHeader("User-Agent", "MSIE 7.0");
		request.addHeader("Accept-Encoding", "deflated,gzip");
		boolean accepts = filter.acceptsEncoding(request, "gzip");
		assertTrue(accepts);
		accepts = filter.acceptsEncoding(request, "crap");
		assertFalse(accepts);
	}
	

	public void testAcceptsEncodingIE8() {
		request.addHeader("User-Agent", "MSIE 8.0");
		request.addHeader("Accept-Encoding", "deflated,gzip");
		boolean accepts = filter.acceptsEncoding(request, "gzip");
		assertTrue(accepts);
		accepts = filter.acceptsEncoding(request, "crap");
		assertFalse(accepts);
	}
	

	public void testAcceptsEncodingFireFox() {
		request.addHeader("User-Agent", "Firefox");
		request.addHeader("Accept-Encoding", "deflated,gzip");
		boolean accepts = filter.acceptsEncoding(request, "gzip");
		assertTrue(accepts);
		accepts = filter.acceptsEncoding(request, "crap");
		assertFalse(accepts);
	}
	
	public void testAcceptsEncodingSafari() {
		request.addHeader("User-Agent", "Safari");
		request.addHeader("Accept-Encoding", "deflated,gzip");
		boolean accepts = filter.acceptsEncoding(request, "gzip");
		assertFalse(accepts);
		accepts = filter.acceptsEncoding(request, "crap");
		assertFalse(accepts);
	}
}
