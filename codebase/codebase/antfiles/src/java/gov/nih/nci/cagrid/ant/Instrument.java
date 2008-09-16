/*
 * Created on Apr 28, 2006
 */
package gov.nih.nci.cagrid.ant;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import net.sourceforge.cobertura.instrument.Main;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class Instrument
	extends Task
{
	private List artifactList;
	private File tempDir;
	private File datafile;

	public Instrument()
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
	
	public File getDatafile() 
	{
		return datafile;
	}
	
	public void setDatafile(File datafile) 
	{
		this.datafile = datafile;
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
				File tempDir = getTempDir(this.tempDir, artifact, artifactFile);
				tempDir.mkdirs();

				// create utility dirs
				File classDir = new File(tempDir, "intstrumented-classes");
				File libDir = new File(tempDir, "lib");
				File ilibDir = new File(tempDir, "instrumented-lib");

				classDir.mkdir();
				libDir.mkdir();
				ilibDir.mkdir();

				// files
				File copyFile = new File(libDir, artifactFile.getName());
				File instrumentedFile = new File(ilibDir, artifactFile.getName());
				
				// instrument
				try {
					Utils.copy(artifactFile, copyFile);
					unjar(copyFile, classDir);
					instrument(classDir);
					jar(classDir, instrumentedFile);
					Utils.copy(instrumentedFile, artifactFile);
				} catch (Exception e) {
					//e.printStackTrace();
					throw new BuildException(e);
				}
				System.out.println("instrumented " + artifactFile.getName());
			}
		}
	}
	
	public static File getTempDir(File rootTempDir, Artifact artifact, File artifactFile)
	{
		return new File(rootTempDir, artifact.getIdentifer() + File.separator + artifactFile.getName() + "-dir");
	}

	private void unjar(File jarFile, File destDir) 
		throws ZipException, IOException
	{
        ZipFile zipfile = new ZipFile(jarFile);
		Enumeration e = zipfile.entries();
		while (e.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			File outFile = new File(destDir, entry.getName());
			if (entry.isDirectory()) {
				outFile.mkdir();
				continue;
			}
			outFile.getParentFile().mkdirs();
			
			BufferedInputStream is = new BufferedInputStream(zipfile.getInputStream(entry));
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outFile));

			byte data[] = new byte[10240];
			int count = -1;
			while ((count = is.read(data)) != -1) os.write(data, 0, count);

			os.flush();
			os.close();
			is.close();
		}
	}
	
	private void instrument(File classDir)
	{
		System.out.println(classDir);
		Main.main(new String[] {
			//"--basedir", classDir.toString(),
			"--datafile", datafile.toString(),
			classDir.toString(),
		});
	}
	
	private void jar(File dir, File destFile) 
		throws IOException, InterruptedException
	{
		String jar = System.getenv("JAVA_HOME") + File.separator + "bin" + File.separator + "jar";
		
		String[] files = dir.list();
		String[] cmd = new String[] {
			jar,
			"cf", destFile.getAbsolutePath(),
			"-C", dir.getAbsolutePath(),
			"*",
		};
		System.out.println(flatten(cmd));
		Process p = Runtime.getRuntime().exec(cmd, null, dir);
		p.waitFor();
		
		/*
		try {
			Jar jar = new Jar();
			jar.setBasedir(dir);
			FileSet fs = new FileSet();
			//NameEntry ne = fs.createInclude();
			//ne.setName("**\/*");
			fs.createPatternSet().createInclude().setName("**\/*");
			jar.addFileset(fs);
			jar.setDestFile(destFile);
			jar.init();
			jar.setIncludes("**\/*");
			jar.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		*/
		
		/*
		JarOutputStream os = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)), new Manifest());
		jar(dir, dir, os);
		os.flush();
		os.close();
		*/
	}
	
	private String flatten(String[] cmd)
	{
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < cmd.length; i++) {
			if (i > 0) buf.append(' ');
			buf.append(cmd[i]);
		}
		return buf.toString();
	}
	
	private void jar(File baseDir, File file, JarOutputStream os) 
		throws IOException
	{
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				jar(baseDir, files[i], os);
			}
		} else if (file.getName().startsWith("Manifest")) {
			return;
		} {
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));

			String jarName = getJarName(baseDir, file);
			JarEntry entry = new JarEntry(jarName);
			os.putNextEntry(entry);
			byte data[] = new byte[10240];
			int count = -1;
			while ((count = is.read(data)) != -1) os.write(data, 0, count);

			is.close();
			os.closeEntry();
		}
	}
	
	private String getJarName(File baseDir, File file)
	{
		return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
	}
}
