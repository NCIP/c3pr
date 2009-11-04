package edu.duke.cabig.c3pr.ant;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.Path;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;
import gov.nih.nci.cagrid.ant.Utils;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 2, 2007
 * Time: 2:46:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class UseCaseTraceabilityReport extends Task {
    private final Logger logger = Logger.getLogger(UseCaseTraceabilityReport.class);

    private File srcDir;
    private File destDir;
    private String projectName;

    private String useCasesAnnotationClassName;

    Vector<Path> classpaths  = new Vector<Path>();

    /**
     * The path to the apt executable
     *
     * @parameter default-value="${java.home}/bin/apt"
     */
    private String aptBin;

    /**
     * Classpath to be included
     * @param classpath
     */
    public void addClasspath(Path classpath) {
        if (!classpaths.contains(classpath)) {
            classpaths.add(classpath);
        }

    }


    public void execute() throws BuildException {
        logger.info("Executing ant task");

        File testCaseList,classpath;
        try {
            testCaseList = buildTestCasesListFile();
        } catch (IOException e) {
            throw new BuildException("Failed to build test cases list", e);
        }
        try {
            classpath = buildClasspathFile();
        } catch (IOException e) {
            throw new BuildException("Failed to build test cases list", e);
        }


        String[] cmd =  {
                getAptBin(),
                "-nocompile",
                "-factory", UseCaseTraceabilityAnnotationProcessorFactory.class.getName(),
                "-d", destDir.getAbsolutePath(),
                UseCaseTraceabilityAnnotationProcessorFactory.PROJECT_NAME_OPT + projectName,
                UseCaseTraceabilityAnnotationProcessorFactory.USE_CASES_ANNOTATION_CLASS_NAME_OPT
                        + useCasesAnnotationClassName,
                '@' + classpath.getAbsolutePath(),
                '@' + testCaseList.getAbsolutePath()
        };

        try {
            logger.info("Executing apt as " + Arrays.asList(cmd));
            Process process = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            IOUtils.copy(process.getInputStream(), System.out);
            process.waitFor();
            if (process.exitValue() != 0) {
                throw new BuildException("apt failed (returned non-zero exit code)");
            }
        } catch (IOException e) {
            throw new BuildException("IO problem while executing apt", e);
        } catch (InterruptedException e) {
            throw new BuildException("Interrupted while waiting for apt", e);
        }

        if (!logger.isDebugEnabled()) {
            testCaseList.delete();

        } else {
            logger.debug("Retained test case list for debugging: " + testCaseList);

        }

    }

    private File buildTestCasesListFile() throws IOException {
        File listFile = File.createTempFile("uctrace-tclist-", "");
        FileWriter fw = new FileWriter(listFile);
        logger.debug("Writing test cases list to " + listFile);

        logger.debug("Looking for tests in " + srcDir);
        for (File f : findJava(srcDir, new LinkedList<File>())) {
            fw.write(f.getAbsolutePath());
            fw.write("\n");
        }


        fw.close();
        logger.debug("Finished writing test cases list");
        return listFile;
    }


    private File buildClasspathFile() throws IOException, BuildException {
        File cpFile = File.createTempFile("uctrace-classpath-", "");
        FileWriter fw = new FileWriter(cpFile);
        logger.debug("Writing classpath to " + cpFile);
        fw.write("-cp\n");
        fw.write(Utils.join(buildClasspath().iterator(), File.pathSeparator));
        fw.close();
        logger.debug("Finished writing test cases list");
        return cpFile;
    }



    private Set<String> buildClasspath(){

        Set<String> cp = new LinkedHashSet<String>();

        for (Path classpath : classpaths) {
            cp.add(classpath.toString());
        }
        return cp;
    }


    private List<File> findJava(File root, List<File> accumulator) {
        if (!root.exists()) {
            logger.debug(" " + root + " does not exist");
            return accumulator;
        }
        logger.debug(" recursing into " + root);
        for (File file : root.listFiles()) {
            if (file.getName().startsWith(".")) {
                // skip hidden files
            } else if (file.isDirectory()) {
                findJava(file, accumulator);
            } else {
                if (file.getName().endsWith(".java")) {
                    accumulator.add(file);
                }
            }
        }
        return accumulator;
    }


    public File getSrcDir() {
        return srcDir;
    }

    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    public File getDestDir() {
        return destDir;
    }

    public void setDestDir(File destDir) {
        this.destDir = destDir;
    }

    public String getUseCasesAnnotationClassName() {
        return useCasesAnnotationClassName;
    }

    public void setUseCasesAnnotationClassName(String useCasesAnnotationClassName) {
        this.useCasesAnnotationClassName = useCasesAnnotationClassName;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAptBin() {
        if(aptBin==null){
            return System.getenv("JAVA_HOME") + File.separator + "bin" + File.separator + "apt";
        }
        return aptBin;
    }

    public void setAptBin(String aptBin) {
        this.aptBin = aptBin;
    }


}
