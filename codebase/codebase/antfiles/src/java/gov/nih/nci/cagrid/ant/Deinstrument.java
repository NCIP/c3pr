/*
 * Created on Apr 28, 2006
 */
package gov.nih.nci.cagrid.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class Deinstrument
	extends Task
{
	private List artifactList;
	private File tempDir;

	public Deinstrument()
	{
		super();
		
		this.artifactList = new ArrayList();
	}
	
	public File getTempDir() 
	{
		return tempDir;
	}
	
	public void setTempDir(File tempDir) 
	{
		this.tempDir = tempDir;
	}

	public void addConfiguredArtifact(Artifact artifact) 
	{
		this.artifactList.add(artifact);
	}
	
	public void execute() 
		throws BuildException 
	{
		super.execute();
		
		for (int i = 0; i < artifactList.size(); i++) {			
			Artifact artifact = (Artifact) artifactList.get(i);
			
			File[] artifactFiles = Utils.getFiles(getProject(), artifact.getFileSetList());
			for (int j = 0; j < artifactFiles.length; j++) {
				File artifactFile = artifactFiles[j];

				// mkdir temp dir
				File tempDir = Instrument.getTempDir(this.tempDir, artifact, artifactFile);

				// create utility dirs
				File libDir = new File(tempDir, "lib");

				// files
				File copyFile = new File(libDir, artifactFile.getName());
				
				try {
					Utils.copy(copyFile, artifactFile);
				} catch (Exception e) {
					throw new BuildException(e);
				}
				System.out.println("deinstrumented " + artifactFile.getName());
			}
		}
	}
}