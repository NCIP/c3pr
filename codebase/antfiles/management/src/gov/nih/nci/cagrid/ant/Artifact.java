package gov.nih.nci.cagrid.ant;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.CallTarget;
import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.types.FileSet;


/**
 * @author oster
 */
public class Artifact extends DataType {
	public static final String JAR_TYPE = "jars";
	public static final String SCHEMAS_TYPE = "schemas";
	public static final String RESOURCES_TYPE = "resources";
	public static final String ANTFILES_TYPE = "antfiles";
	public static final String DLL_TYPE = "dll";
	public static final String R_TYPE = "r";
	public static final String MAPPINGS_TYPE = "mappings";
	public static final String TEST_TRACK = "test";
	public static final String ENDORSED_TRACK = "endorsed";
	public static final String MAIN_TRACK = "main";

	private String track;
	private String type;
	private List fileSetList;
	private CallTarget antcall;


	public Artifact() {
		this.fileSetList = new ArrayList();
	}


	public String getIdentifer() {
		return getRefid().getRefId();
	}


	protected Artifact getReferencedArtifact() {
		Object o = getRefid().getReferencedObject(this.getProject());
		return (Artifact) o;
	}


	public String getTrack() {
		if (isReference()) {
			return getReferencedArtifact().getTrack();
		}
		return track;
	}


	public void setTrack(String track) {
		if (isReference()) {
			throw new BuildException("Cannont use other attributes and refid.");
		}
		this.track = track;
	}


	public String getType() {
		if (isReference()) {
			return getReferencedArtifact().getType();
		}
		return type;
	}


	public void setType(String type) {
		if (isReference()) {
			throw new BuildException("Cannont use other attributes and refid.");
		}
		this.type = type;
	}


	public List getFileSetList() {
		if (isReference()) {
			return getReferencedArtifact().getFileSetList();
		}
		return this.fileSetList;
	}


	public void addConfiguredFileSet(FileSet s) {
		if (isReference()) {
			throw new BuildException("Cannont use other attributes and refid.");
		}
		this.fileSetList.add(s);
	}


	public CallTarget getAntCall() {
		if (isReference()) {
			return getReferencedArtifact().getAntCall();
		}
		return this.antcall;
	}


	public void addConfiguredAntCall(CallTarget antcall) {
		if (isReference()) {
			throw new BuildException("Cannont use other attributes and refid.");
		}
		this.antcall = antcall;
	}
}
