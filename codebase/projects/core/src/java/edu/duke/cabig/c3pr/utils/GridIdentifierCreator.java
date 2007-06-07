package edu.duke.cabig.c3pr.utils;

/**
 * Interface to have multiple implementations of Grid Identifiers
 * (local/site)
 *
 * @author Priyatam
 */
public interface GridIdentifierCreator {
    String getGridIdentifier(String uniqueObjectId);
}
