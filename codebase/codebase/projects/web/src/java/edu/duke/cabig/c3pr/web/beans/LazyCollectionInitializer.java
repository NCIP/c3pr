package edu.duke.cabig.c3pr.web.beans;

import java.lang.reflect.Field;

public interface LazyCollectionInitializer {

    public void processLazyInitialization(Object object, int key, String collectionType)
                    throws Exception;

    public void laziliyInitialize(Object collection, Field collectionField, int index)
                    throws Exception;
}
