package edu.duke.cabig.c3pr.web.beans;

import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.web.beans.HttpServletLazyCollectionInitializer;

public interface LazyListBindable {
	public void initLazyListBinder(HttpServletRequest request, HttpServletLazyCollectionInitializer lazyCollectionInitializer) throws Exception ;
}
