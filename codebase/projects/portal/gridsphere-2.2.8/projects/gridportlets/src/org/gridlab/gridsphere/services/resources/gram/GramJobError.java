package org.gridlab.gridsphere.services.resources.gram;

import java.util.Hashtable;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramJobError.java,v 1.1.1.1 2007-02-01 20:41:05 kherm Exp $
 * Very quick, cheap, dirty solution to get useful error information from
 * GRAM error codes. This should be really be handled with a properties file
 * but there were so many error codes and so little time...
 */

public class GramJobError {

    private static Hashtable errorMessages = new Hashtable(163);

    static {
        errorMessages.put("0","success");
        errorMessages.put("1","one of the RSL parameters is not supported");
        errorMessages.put("2","the RSL length is greater than the maximum allowed");
        errorMessages.put("3","an I/O operation failed");
        errorMessages.put("4","jobmanager unable to set default to the directory requested");
        errorMessages.put("5","the executable does not exist");
        errorMessages.put("6","of an unused INSUFFICIENT_FUNDS");
        errorMessages.put("7","authentication with the remote server failed");
        errorMessages.put("8","the user cancelled the job");
        errorMessages.put("9","the system cancelled the job");
        errorMessages.put("10","data transfer to the server failed");
        errorMessages.put("11","the stdin file does not exist");
        errorMessages.put("12","the connection to the server failed (check host and port);");
        errorMessages.put("13","the provided RSL 'maxtime' value is not an integer");
        errorMessages.put("14","the provided RSL 'count' value is not an integer");
        errorMessages.put("15","the job manager received an invalid RSL");
        errorMessages.put("16","the job manager failed in allowing others to make contact");
        errorMessages.put("17","the job failed when the job manager attempted to run it");
        errorMessages.put("18","an invalid paradyn was specified");
        errorMessages.put("19","the provided RSL 'jobtype' value is invalid");
        errorMessages.put("20","the provided RSL 'myjob' value is invalid");
        errorMessages.put("21","the job manager failed to locate an internal script argument file");
        errorMessages.put("22","the job manager failed to create an internal script argument file");
        errorMessages.put("23","the job manager detected an invalid job state");
        errorMessages.put("24","the job manager detected an invalid script response");
        errorMessages.put("25","the job manager detected an invalid script status");
        errorMessages.put("26","the provided RSL 'jobtype' value is not supported by this job manager");
        errorMessages.put("27","unused ERROR_UNIMPLEMENTED");
        errorMessages.put("28","the job manager failed to create an internal script submission file");
        errorMessages.put("29","the job manager cannot find the user proxy");
        errorMessages.put("30","the job manager failed to open the user proxy");
        errorMessages.put("31","the job manager failed to cancel the job as requested");
        errorMessages.put("32","system memory allocation failed");
        errorMessages.put("33","the interprocess job communication initialization failed");
        errorMessages.put("34","the interprocess job communication setup failed");
        errorMessages.put("35","the provided RSL 'host count' value is invalid");
        errorMessages.put("36","one of the provided RSL parameters is unsupported");
        errorMessages.put("37","the provided RSL 'queue' parameter is invalid");
        errorMessages.put("38","the provided RSL 'project' parameter is invalid");
        errorMessages.put("39","the provided RSL string includes variables that could not be identified");
        errorMessages.put("40","the provided RSL 'environment' parameter is invalid");
        errorMessages.put("41","the provided RSL 'dryrun' parameter is invalid");
        errorMessages.put("42","the provided RSL is invalid (an empty string);");
        errorMessages.put("43","the job manager failed to stage the executable");
        errorMessages.put("44","the job manager failed to stage the stdin file");
        errorMessages.put("45","the requested job manager type is invalid");
        errorMessages.put("46","the provided RSL 'arguments' parameter is invalid");
        errorMessages.put("47","the gatekeeper failed to run the job manager");
        errorMessages.put("48","the provided RSL could not be properly parsed");
        errorMessages.put("49","there is a version mismatch between GRAM components");
        errorMessages.put("50","the provided RSL 'arguments' parameter is invalid");
        errorMessages.put("51","the provided RSL 'count' parameter is invalid");
        errorMessages.put("52","the provided RSL 'directory' parameter is invalid");
        errorMessages.put("53","the provided RSL 'dryrun' parameter is invalid");
        errorMessages.put("54","the provided RSL 'environment' parameter is invalid");
        errorMessages.put("55","the provided RSL 'executable' parameter is invalid");
        errorMessages.put("56","the provided RSL 'host_count' parameter is invalid");
        errorMessages.put("57","the provided RSL 'jobtype' parameter is invalid");
        errorMessages.put("58","the provided RSL 'maxtime' parameter is invalid");
        errorMessages.put("59","the provided RSL 'myjob' parameter is invalid");
        errorMessages.put("60","the provided RSL 'paradyn' parameter is invalid");
        errorMessages.put("61","the provided RSL 'project' parameter is invalid");
        errorMessages.put("62","the provided RSL 'queue' parameter is invalid");
        errorMessages.put("63","the provided RSL 'stderr' parameter is invalid");
        errorMessages.put("64","the provided RSL 'stdin' parameter is invalid");
        errorMessages.put("65","the provided RSL 'stdout' parameter is invalid");
        errorMessages.put("66","the job manager failed to locate an internal script");
        errorMessages.put("67","the job manager failed on the system call pipe();");
        errorMessages.put("68","the job manager failed on the system call fcntl();");
        errorMessages.put("69","the job manager failed to create the temporary stdout filename");
        errorMessages.put("70","the job manager failed to create the temporary stderr filename");
        errorMessages.put("71","the job manager failed on the system call fork();");
        errorMessages.put("72","the executable file permissions do not allow execution");
        errorMessages.put("73","the job manager failed to open stdout");
        errorMessages.put("74","the job manager failed to open stderr");
        errorMessages.put("75","the cache file could not be opened in order to relocate the user proxy");
        errorMessages.put("76","cannot access cache files in ~/.globus/.gass_cache, check permissions, quota, and disk space");
        errorMessages.put("77","the job manager failed to insert the contact in the client contact list");
        errorMessages.put("78","the contact was not found in the job manager's client contact list");
        errorMessages.put("79","connecting to the job manager failed.  Possible reasons: job terminated, invalid job contact, network problems, ...");
        errorMessages.put("80","the syntax of the job contact is invalid");
        errorMessages.put("81","the executable parameter in the RSL is undefined");
        errorMessages.put("82","the job manager service is misconfigured.  condor arch undefined");
        errorMessages.put("83","the job manager service is misconfigured.  condor os undefined");
        errorMessages.put("84","the provided RSL 'min_memory' parameter is invalid");
        errorMessages.put("85","the provided RSL 'max_memory' parameter is invalid");
        errorMessages.put("86","the RSL 'min_memory' value is not zero or greater");
        errorMessages.put("87","the RSL 'max_memory' value is not zero or greater");
        errorMessages.put("88","the creation of a HTTP message failed");
        errorMessages.put("89","parsing incoming HTTP message failed");
        errorMessages.put("90","the packing of information into a HTTP message failed");
        errorMessages.put("91","an incoming HTTP message did not contain the expected information");
        errorMessages.put("92","the job manager does not support the service that the client requested");
        errorMessages.put("93","the gatekeeper failed to find the requested service");
        errorMessages.put("94","the jobmanager does not accept any new requests (shutting down);");
        errorMessages.put("95","the client failed to close the listener associated with the callback URL");
        errorMessages.put("96","the gatekeeper contact cannot be parsed");
        errorMessages.put("97","the job manager could not find the 'poe' command");
        errorMessages.put("98","the job manager could not find the 'mpirun' command");
        errorMessages.put("99","the provided RSL 'start_time' parameter is invalid");
        errorMessages.put("100","the provided RSL 'reservation_handle' parameter is invalid");
        errorMessages.put("101","the provided RSL 'max_wall_time' parameter is invalid");
        errorMessages.put("102","the RSL 'max_wall_time' value is not zero or greater");
        errorMessages.put("103","the provided RSL 'max_cpu_time' parameter is invalid");
        errorMessages.put("104","the RSL 'max_cpu_time' value is not zero or greater");
        errorMessages.put("105","the job manager is misconfigured, a scheduler script is missing");
        errorMessages.put("106","the job manager is misconfigured, a scheduler script has invalid permissions");
        errorMessages.put("107","the job manager failed to signal the job");
        errorMessages.put("108","the job manager did not recognize/support the signal type");
        errorMessages.put("109","the job manager failed to get the job id from the local scheduler");
        errorMessages.put("110","the job manager is waiting for a commit signal");
        errorMessages.put("111","the job manager timed out while waiting for a commit signal");
        errorMessages.put("112","the provided RSL 'save_state' parameter is invalid");
        errorMessages.put("113","the provided RSL 'restart' parameter is invalid");
        errorMessages.put("114","the provided RSL 'two_phase' parameter is invalid");
        errorMessages.put("115","the RSL 'two_phase' value is not zero or greater");
        errorMessages.put("116","the provided RSL 'stdout_position' parameter is invalid");
        errorMessages.put("117","the RSL 'stdout_position' value is not zero or greater");
        errorMessages.put("118","the provided RSL 'stderr_position' parameter is invalid");
        errorMessages.put("119","the RSL 'stderr_position' value is not zero or greater");
        errorMessages.put("120","the job manager restart attempt failed");
        errorMessages.put("121","the job state file doesn't exist");
        errorMessages.put("122","could not read the job state file");
        errorMessages.put("123","could not write the job state file");
        errorMessages.put("124","old job manager is still alive");
        errorMessages.put("125","job manager state file TTL expired");
        errorMessages.put("126","it is unknown if the job was submitted");
        errorMessages.put("127","the provided RSL 'remote_io_url' parameter is invalid");
        errorMessages.put("128","could not write the remote io url file");
        errorMessages.put("129","the standard output/error size is different");
        errorMessages.put("130","the job manager was sent a stop signal (job is still running);");
        errorMessages.put("131","the user proxy expired (job is still running);");
        errorMessages.put("132","the job was not submitted by original jobmanager");
        errorMessages.put("133","the job manager is not waiting for that commit signal");
        errorMessages.put("134","the provided RSL scheduler specific parameter is invalid");
        errorMessages.put("135","the job manager could not stage in a file");
        errorMessages.put("136","the scratch directory could not be created");
        errorMessages.put("137","the provided 'gass_cache' parameter is invalid");
        errorMessages.put("138","the RSL contains attributes which are not valid for job submission");
        errorMessages.put("139","the RSL contains attributes which are not valid for stdio update");
        errorMessages.put("140","the RSL contains attributes which are not valid for job restart");
        errorMessages.put("141","the provided RSL 'file_stage_in' parameter is invalid");
        errorMessages.put("142","the provided RSL 'file_stage_in_shared' parameter is invalid");
        errorMessages.put("143","the provided RSL 'file_stage_out' parameter is invalid");
        errorMessages.put("144","the provided RSL 'gass_cache' parameter is invalid");
        errorMessages.put("145","the provided RSL 'file_cleanup' parameter is invalid");
        errorMessages.put("146","the provided RSL 'scratch_dir' parameter is invalid");
        errorMessages.put("147","the provided scheduler specific RSL parameter is invalid");
        errorMessages.put("148","a required RSL attribute was not defined in the RSL spec");
        errorMessages.put("149","the gass_cache attribute points to an invalid cache directory");
        errorMessages.put("150","the provided RSL 'save_state' parameter has an invalid value");
        errorMessages.put("151","the job manager could not open the RSL attribute validation file");
        errorMessages.put("152","the  job manager could not read the RSL attribute validation file");
        errorMessages.put("153","the provided RSL 'proxy_timeout' is invalid");
        errorMessages.put("154","the RSL 'proxy_timeout' value is not greater than zero");
        errorMessages.put("155","the job manager could not stage out a file");
        errorMessages.put("156","the job contact string does not match any which the job manager is handling");
        errorMessages.put("157","proxy delegation failed");
        errorMessages.put("158","the job manager could not lock the state lock file");
        errorMessages.put("159","an invalid globus_io_clientattr_t was used.");
        errorMessages.put("160","an null parameter was passed to the gram library");
        errorMessages.put("161","the job manager is still streaming output");
        errorMessages.put("162","");
    }

    public static String getErrorMessage(int errorCode) {
        String message = (String)errorMessages.get(String.valueOf(errorCode));
        if (message == null) {
            message = "";
        }
        return message;
    }
}
