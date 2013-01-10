/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.repository;




import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jcr.NamespaceRegistry;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;

import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.drools.repository.JackrabbitRepositoryConfigurator;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryAdministrator;
import org.drools.repository.RulesRepositoryException;
import org.drools.repository.StateItem;

/**
 * 
 * @author Sujith Vellat Thayyilthodi
 */
public class RulesRepositoryEx extends RulesRepository {

    private static final Logger log = Logger.getLogger(RulesRepository.class.getName());

    public static final String DROOLS_EXTENSION_URI = "http://www.jboss.org/drools-repository/ex/1.0";

    /**
     * The name of the rulepackage area of the repository
     */
    public final static String COMPILED_RULE_PACKAGE_AREA = "droolsex:compiled_rulepackage_area";

    /**
     * The name of the rule area of the repository
     */
    public final static String COMPILED_RULE_AREA = "droolsex:compiled_rule_area";

    private Map areaNodeCache = new HashMap();

    private Session session;

    public RulesRepositoryEx(Session session) {
        super(session);
        this.session = session;
    }

    /***********************************************************************************************
     * When going without JSR 94 we don't need to know the bindUri. The package name itself is a
     * unique information.
     */
    public CompiledPackageItem createCompiledPackage(String name, Object package1)
                    throws RulesRepositoryException {
        return createCompiledPackage(null, name, package1);
    }

    /**
     * 
     * @param bindUri
     *                The uri with which we need to register the RuleExecutionSet as per JSR-94
     */
    public CompiledPackageItem createCompiledPackage(String bindUri, String name, Object package1)
                    throws RulesRepositoryException {

        if (bindUri != null) name = bindUri;

        Node folderNode = this.getAreaNode(COMPILED_RULE_PACKAGE_AREA);
        // create the node - see section 6.7.22.6 of the spec
        try {
            Node rulePackageDeployNode = folderNode.addNode(name,
                            CompiledPackageItem.RULE_PACKAGE_TYPE_NAME);
            CompiledPackageItem compiledPackageItem = new CompiledPackageItem(this,
                            rulePackageDeployNode);
            compiledPackageItem.updateBinaryContent(getObjectInputStrream(package1));

            rulePackageDeployNode.setProperty(CompiledPackageItem.TITLE_PROPERTY_NAME, name);
            rulePackageDeployNode.setProperty(CompiledPackageItem.FORMAT_PROPERTY_NAME,
                            CompiledPackageItem.PACKAGE_FORMAT);
            Calendar lastModified = Calendar.getInstance();
            rulePackageDeployNode.setProperty(CompiledPackageItem.LAST_MODIFIED_PROPERTY_NAME,
                            lastModified);
            save();
            return compiledPackageItem;
        } catch (RepositoryException e) {
            throw new RulesRepositoryException(e.getMessage(), e);
        }

    }

    private Node getAreaNode(String areaName) throws RulesRepositoryException {
    	
    	try {
			Iterator it = session.getRootNode().getNodes();
			while (it.hasNext()){
				Node n = (Node)it.next();
				System.out.println(n.getPath());
				Iterator it2 = n.getNodes();
				
				while (it2.hasNext()) {
					Node n1 = (Node)it2.next();
					System.out.println("-" +n1.getPath());
				}
			}
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
        if (areaNodeCache.containsKey(areaName)) {
            return (Node) areaNodeCache.get(areaName);
        } else {
            Node folderNode = null;
            int tries = 0;
            while (folderNode == null && tries < 2) {
                try {
                    tries++;
                    folderNode = this.session.getRootNode().getNode(RULES_REPOSITORY_NAME + "/" + areaName);
                } catch (PathNotFoundException e) {
                    if (tries == 1) {
                        // hmm..repository must have gotten screwed up. set it up again
                        log.log(Level.WARNING,"The repository appears to have become corrupted. It will be re-setup now.");
                        throw new RulesRepositoryException("Unable to get the main rule repo node. Repository is not setup correctly.",
                                        e);
                    } else {
                        log.log(Level.SEVERE, "Unable to correct repository corruption");
                    }
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Caught Exception", e);
                    throw new RulesRepositoryException(
                                    "Caught exception " + e.getClass().getName(), e);
                }
            }
            if (folderNode == null) {
                String message = "Could not get a reference to a node for " + RULES_REPOSITORY_NAME
                                + "/" + areaName;
                log.log(Level.SEVERE, message);
                throw new RulesRepositoryException(message);
            }
            areaNodeCache.put(areaName, folderNode);
            return folderNode;
        }
    }

    public byte[] convertObjectToByteArray(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public Object convertByteArrayToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }

    private InputStream getObjectInputStrream(Object object) {
        ByteArrayInputStream byteArrayInputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(convertObjectToByteArray(object));
            return byteArrayInputStream;
            // return new ObjectInputStream(byteArrayInputStream);
        } catch (IOException e) {
            throw new RulesRepositoryException(e.getMessage(), e);
        }
    }

    public CompiledPackageItem loadCompiledPackage(String bindUri) {
        try {
            Node folderNode = this.getAreaNode(COMPILED_RULE_PACKAGE_AREA);
            Node rulePackageNode = folderNode.getNode(bindUri);
            return new CompiledPackageItem(this, rulePackageNode);
        } catch (Exception e) {
            // log.log(Level.SEVERE, "Unable to load a compiled rule package. ", e); --vinay
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RulesRepositoryException("Unable to load a compiled rule package. ", e);
            }
        }
    }

    public void removeCompiledPackage(String bindUri) {
        Node folderNode = this.getAreaNode(COMPILED_RULE_PACKAGE_AREA);
        try {
            Node rulePackageNode = folderNode.getNode(bindUri);
            rulePackageNode.remove();
            session.save();
        } catch (PathNotFoundException e) {
            throw new RulesRepositoryException(e.getMessage(), e);
        } catch (RepositoryException e) {
            throw new RulesRepositoryException(e.getMessage(), e);
        }

    }

    static class RepositoryConfiguratorEx extends JackrabbitRepositoryConfigurator {
    	public void setupRulesRepository(Session session) throws RulesRepositoryException {
            System.out.println("Setting up the repository, registering node types etc.");
            try {
                Node root = session.getRootNode();
                Workspace ws = session.getWorkspace();
                NamespaceRegistry nsr = ws.getNamespaceRegistry();
                
                String[] uriArray = nsr.getURIs();
                
                //no need to set it up again, skip it if it has.
                boolean registered = false ;
                //RulesRepositoryAdministrator.isNamespaceRegistered( session );

                
                
                if (!registered) {
                    ws.getNamespaceRegistry().registerNamespace("drools", RulesRepository.DROOLS_URI);
                    ws.getNamespaceRegistry().registerNamespace("droolsex",
                            RulesRepositoryEx.DROOLS_EXTENSION_URI);
                    
                    //Note, the order in which they are registered actually does matter !
                    this.registerNodeTypesFromCndFile("/node_type_definitions/tag_node_type.cnd", ws);
                    this.registerNodeTypesFromCndFile("/node_type_definitions/state_node_type.cnd", ws);
                    this.registerNodeTypesFromCndFile("/node_type_definitions/versionable_node_type.cnd", ws);
                    this.registerNodeTypesFromCndFile("/node_type_definitions/versionable_asset_folder_node_type.cnd", ws);
                    
                    this.registerNodeTypesFromCndFile("/node_type_definitions/rule_node_type.cnd", ws);
                    this.registerNodeTypesFromCndFile("/node_type_definitions/rulepackage_node_type.cnd", ws);
                    
                    this.registerNodeTypesFromCndFile(
                            "/rules/node_type_definitions/compiled_state_node_type.cnd", ws);
            this
                            .registerNodeTypesFromCndFile(
                                            "/rules/node_type_definitions/compiled_versionable_node_type.cnd",
                                            ws);
            this
                            .registerNodeTypesFromCndFile(
                                            "/rules/node_type_definitions/compiled_versionable_asset_folder_node_type.cnd",
                                            ws);
            this
                            .registerNodeTypesFromCndFile(
                                            "/rules/node_type_definitions/compiled_rulepackage_node_type.cnd",
                                            ws);
                 
                }
                
                // Setup the rule repository node
                Node repositoryNode = RulesRepository.addNodeIfNew(root, RulesRepository.RULES_REPOSITORY_NAME, "nt:folder");
                        

                
                // Setup the RulePackageItem area        
                RulesRepository.addNodeIfNew(repositoryNode, RulesRepository.RULE_PACKAGE_AREA, "nt:folder");
                
                // Setup the Snapshot area        
                RulesRepository.addNodeIfNew(repositoryNode, RulesRepository.PACKAGE_SNAPSHOT_AREA, "nt:folder");
                            
                //Setup the Cateogry area                
                RulesRepository.addNodeIfNew(repositoryNode, RulesRepository.TAG_AREA, "nt:folder");
                
                //Setup the State area                
                RulesRepository.addNodeIfNew(repositoryNode, RulesRepository.STATE_AREA, "nt:folder");
                
                RulesRepository.addNodeIfNew(repositoryNode, COMPILED_RULE_PACKAGE_AREA, "nt:folder");
                
                RulesRepository.addNodeIfNew(repositoryNode, COMPILED_RULE_AREA, "nt:folder");
                
                //and we need the "Draft" state
                RulesRepository.addNodeIfNew( repositoryNode.getNode( RulesRepository.STATE_AREA ), StateItem.DRAFT_STATE_NAME, StateItem.STATE_NODE_TYPE_NAME );
                
                session.save();                        
            }
            catch(Exception e) {
                //log.error("Caught Exception", e);
                System.err.println(e.getMessage());
                throw new RulesRepositoryException(e);
            }
        }

    	 private void registerNodeTypesFromCndFile(String cndFileName, Workspace ws) throws RulesRepositoryException, InvalidNodeTypeDefException {
    	        try {
    	            //Read in the CND file
    	            Reader in = new InputStreamReader(this.getClass().getResourceAsStream( cndFileName ));
    	            
    	            // Create a CompactNodeTypeDefReader
    	            CompactNodeTypeDefReader cndReader = new CompactNodeTypeDefReader(in, cndFileName);
    	            
    	            // Get the List of NodeTypeDef objects
    	            List ntdList = cndReader.getNodeTypeDefs();
    	            
    	            // Get the NodeTypeManager from the Workspace.
    	            // Note that it must be cast from the generic JCR NodeTypeManager to the
    	            // Jackrabbit-specific implementation.
    	            NodeTypeManagerImpl ntmgr = (NodeTypeManagerImpl)ws.getNodeTypeManager();
    	            
    	            // Acquire the NodeTypeRegistry
    	            NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();
    	            
    	            // Loop through the prepared NodeTypeDefs
    	            for(Iterator i = ntdList.iterator(); i.hasNext();) {                               
    	                // Get the NodeTypeDef...
    	                NodeTypeDef ntd = (NodeTypeDef)i.next();                                        
    	                
    	                //log.debug("Attempting to regsiter node type named: " + ntd.getName());
    	                
    	                // ...and register it            
    	                ntreg.registerNodeType(ntd);
    	            }
    	        }
    	        catch(InvalidNodeTypeDefException e) {
    	            //log.warn("InvalidNodeTypeDefinitionException caught when trying to add node from CND file: " + cndFileName + ". This will happen if the node type was already registered. " + e);
    	            throw e;
    	        }
    	        catch(Exception e) {
    	            //log.error("Caught Exception", e);
    	            throw new RulesRepositoryException(e);
    	        }
    	    }    
    
    }

}
