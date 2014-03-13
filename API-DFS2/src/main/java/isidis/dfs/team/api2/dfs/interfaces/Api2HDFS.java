package isidis.dfs.team.api2.dfs.interfaces;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.common.interfaces.ApiGeneric;
import java.io.File;
import org.apache.hadoop.fs.FileAlreadyExistsException;

/**
 * 
 * @author saad
 * Interface describes all methods which can be used to interact with HDFS EndPoint.
 * 	
 * Constraints:
 * This API can treat just big files which their size exceed 128Mo
 *
 */

public interface Api2HDFS extends ApiGeneric {
	/**
	 * Reading the file from HDFS with the path in param
	 * @param fileLocation
	 * the path/name of the file you want to read from HDFS
	 * @return
	 * RemoteIterator which can be used to iterate between file's parts
	 * @throws FileNotFoundException
	 * @throws EndpointNotReacheableException
	 * @throws SystemUserPermissionException
	 * @throws FileSizeThresholdNotRespected
	 */
	RemoteIterator<byte[]> readFile(String fileLocation) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeThresholdNotRespected;
	
	/**
	 * Writing a file into HDFS
	 * @param file
	 * file refers to the local file which you want to save into your HDFS.
	 * @param destinationFileLocation
	 * The path where the file will be wrote on HDFS
	 * @return
	 * RemoteIterator which can be used to iterate to save all file's parts
	 * @throws SystemUserPermissionException
	 * @throws EndpointNotReacheableException
	 * @throws FileAlreadyExistsException
	 * @throws FileSizeThresholdNotRespected
	 */
	RemoteIterator<Void> writeFile(File file, String destinationFileLocation) throws SystemUserPermissionException, EndpointNotReacheableException, FileAlreadyExistsException, FileSizeThresholdNotRespected;
}
