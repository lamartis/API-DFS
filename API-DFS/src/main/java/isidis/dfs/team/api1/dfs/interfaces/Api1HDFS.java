package isidis.dfs.team.api1.dfs.interfaces;

import org.apache.hadoop.fs.FileAlreadyExistsException;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.common.interfaces.ApiGeneric;
/**
 * 
 * @author saad
 * Interface describes all methods which can be used to interact with HDFS EndPoint.
 * 
 * Constraints:
 * This API can treat just normal files which their size do not exceed 128Mo
 *
 */

public interface Api1HDFS extends ApiGeneric {
	/**
	 * Reading the file from HDFS with the path in param
	 * @param sourceFileName
	 * the path/name of the file you want to read from HDFS
	 * @return
	 * byte with the content of the file
	 * @throws FileNotFoundException
	 * @throws EndpointNotReacheableException
	 * @throws SystemUserPermissionException
	 * @throws FileSizeThresholdNotRespected
	 */
	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeThresholdNotRespected;
	/**
	 * Writing a file into HDFS
	 * @param content
	 * the content that you want to write
	 * @param destinationFileName
	 * The path where the file will be wrote on HDFS
	 * @throws SystemUserPermissionException
	 * @throws EndpointNotReacheableException
	 * @throws FileAlreadyExistsException
	 * @throws FileSizeThresholdNotRespected
	 */
	public void writeFile(byte[] content, String destinationFileName) throws SystemUserPermissionException, EndpointNotReacheableException, FileAlreadyExistsException, FileSizeThresholdNotRespected;

}
