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
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

public class Utils
{
	private Utils() { super(); }
	
	public static File[] getFiles(Project project, List fileSetList)
	{
		ArrayList<File> fileList = new ArrayList<File>();
		
		Iterator fileSets = fileSetList.iterator();
		while (fileSets.hasNext()) {
			FileSet fileSet = (FileSet) fileSets.next();
			
			DirectoryScanner ds = fileSet.getDirectoryScanner(project);
			String[] files = ds.getIncludedFiles();
			for (int i = 0; i < files.length; i++) {
				fileList.add(new File(ds.getBasedir(), files[i]));
			}
		}
		
		return fileList.toArray(new File[0]);
	}
	
	public static void copy(File src, File tgt)
		throws IOException
	{
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(src));
		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(tgt));
		
		byte[] buf = new byte[10240];
		int count = 0;
		while ((count = is.read(buf)) != -1) os.write(buf, 0, count);
		
		os.flush();
		os.close();
		is.close();
	}

}
