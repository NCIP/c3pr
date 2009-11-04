package edu.duke.cabig.c3pr.infrastructure;

import com.semanticbits.coppa.infrastructure.cache.RemoteCacheManager;

import junit.framework.TestCase;

public class RemoteEntityManagerTest extends TestCase {

	/**
	 * Test load time based caching properties from coppa_cache.props in core class path.
	 */
	public void testLoadTimeBasedCachingProperties(){
		assertEquals(15*60, RemoteCacheManager.CACHE_TIME_OUT_VALUE.intValue());
		assertEquals("true", RemoteCacheManager.isTimeBasedCacheEnabled);
	}
}
