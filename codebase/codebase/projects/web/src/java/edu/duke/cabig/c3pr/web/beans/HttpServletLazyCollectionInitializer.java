package edu.duke.cabig.c3pr.web.beans;

import javax.servlet.http.HttpServletRequest;

public interface HttpServletLazyCollectionInitializer extends LazyCollectionInitializer {

    public void lazilyInitializeCollections(HttpServletRequest request) throws Exception;

    public void addAssociator(Class c, String methodName);

}
