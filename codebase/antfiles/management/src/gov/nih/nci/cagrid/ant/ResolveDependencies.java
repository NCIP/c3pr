package gov.nih.nci.cagrid.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.CallTarget;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;


/**
 * @author oster
 */
public class ResolveDependencies extends Task {

	private List artifactList;
	private File extDir;
	private File targetDir;


	public ResolveDependencies() {
		this.artifactList = new ArrayList();
	}


	public File getExtDir() {
		return extDir;
	}


	/**
	 * Set the base directory in which artifacts will be copied based on the
	 * types of artifacts. Ignored if targetDir is set.
	 * 
	 * @param extDir
	 */
	public void setExtDir(File extDir) {
		this.extDir = extDir;
	}


	/**
	 * Add an artifact dependency to resolve
	 * 
	 * @param art
	 */
	public void addConfiguredArtifact(Artifact art) {
		this.artifactList.add(art);
	}


	public void execute() throws BuildException {
		super.execute();
		for (int i = 0; i < artifactList.size(); i++) {

			Artifact artifact = (Artifact) artifactList.get(i);
			CallTarget antCall = artifact.getAntCall();
			if (antCall != null) {
				// see if we call this target yet, in this run, if so, skip it
				String prop = "called.antcall.of." + artifact.getIdentifer();
				String calledProperty = this.getProject().getProperty(prop);
				if (calledProperty != null) {
					System.out.println("Skipping dependent artifact's ant call["+antCall.getTaskName()+"], as property was set:" + prop);
				} else {
					System.out.println("Calling dependent artifact's ant call["+antCall.getTaskName()+"] for first time; setting property:" + prop);
					this.getProject().setProperty(prop, "true");
					antCall.setProject(this.getProject());
					antCall.execute();
				}
			}

			// configure a copy task to send the created artifacts where they
			// need to go
			Copy copyTask = new Copy();
			copyTask.setProject(this.getProject());
			copyTask.setOverwrite(true);
			// add the files from the artifact
			for (int j = 0; j < artifact.getFileSetList().size(); j++) {
				FileSet fileset = (FileSet) artifact.getFileSetList().get(j);
				copyTask.addFileset(fileset);
			}
			// figure out where to copy artifacts
			if (getTargetDir() == null) {

				// pick the track
				String track = "";
				if (artifact.getTrack().equals(Artifact.TEST_TRACK)) {
					track = File.separator + "test";
				} else if (artifact.getTrack().equals(Artifact.ENDORSED_TRACK)) {
					track = File.separator + "endorsed";
				}

				// decide where to put it, based on the type
				if (artifact.getType().equals(Artifact.JAR_TYPE)) {
					copyTask.setTodir(new File(getExtDir().getAbsolutePath() + track + File.separator + "lib"));
				} else if (artifact.getType().equals(Artifact.SCHEMAS_TYPE)) {
					copyTask.setTodir(new File(getExtDir().getAbsolutePath() + track + File.separator + "schema"));
				} else if (artifact.getType().equals(Artifact.DLL_TYPE)) {
					copyTask.setTodir(new File(getExtDir().getAbsolutePath() + track + File.separator + "dll"));
				} else if (artifact.getType().equals(Artifact.R_TYPE)) {
					copyTask.setTodir(new File(getExtDir().getAbsolutePath() + track + File.separator + "r"));
				} else if (artifact.getType().equals(Artifact.ANTFILES_TYPE)) {
					copyTask.setTodir(new File(getExtDir().getAbsolutePath() + track + File.separator + "antfiles"));
				} else if (artifact.getType().equals(Artifact.MAPPINGS_TYPE)) {
					copyTask.setTodir(new File(getExtDir().getAbsolutePath()));
				} else if (artifact.getType().equals(Artifact.RESOURCES_TYPE)) {
					copyTask.setTodir(new File(getExtDir().getAbsolutePath() + File.separator + "resources"));
				} else {
					throw new BuildException(artifact.getType() + ": not an valid artifact type");
				}

			} else {
				copyTask.setTodir(getTargetDir());
			}
			copyTask.execute();
		}
	}


	public File getTargetDir() {
		return targetDir;
	}


	/**
	 * Explicitly set the directory to copy all artifacts to. This overrides the
	 * default artifact locations that would be used, relative to the extDir.
	 * 
	 * @param targetDir
	 */
	public void setTargetDir(File targetDir) {
		this.targetDir = targetDir;
	}

}
