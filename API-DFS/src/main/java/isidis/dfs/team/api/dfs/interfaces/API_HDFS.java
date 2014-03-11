package isidis.dfs.team.api.dfs.interfaces;

import org.apache.hadoop.fs.FileAlreadyExistsException;

import isidis.dfs.team.api.dfs.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.exceptions.FileSizeExceedsFixedThreshold;
import isidis.dfs.team.api.dfs.exceptions.SystemUserPermissionException;

public interface API_HDFS {
	/**
	 * Reading the file with the URL in param
	 * @param sourceFileName
	 * the name/path of the file you want to read
	 * @return
	 * byte with the content of the file
	 * @throws FileNotFoundException
	 * @throws EndpointNotReacheableException
	 * @throws SystemUserPermissionException
	 * @throws FileSizeExceedsFixedThreshold
	 */
	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeExceedsFixedThreshold;
	/**
	 * Writing a file
	 * @param content
	 * the content who want to write
	 * @param destinationFileName
	 * The path where the file will be wrote
	 * @throws SystemUserPermissionException
	 * @throws EndpointNotReacheableException
	 * @throws FileAlreadyExistsException
	 * @throws FileSizeExceedsFixedThreshold
	 */
	public void writeFile(byte[] content, String destinationFileName) throws SystemUserPermissionException, EndpointNotReacheableException, FileAlreadyExistsException, FileSizeExceedsFixedThreshold;
	/**
	 * Deleting a file
	 * @param sourceFileName
	 * The name/path of the file who want to delete
	 * @throws FileNotFoundException
	 * @throws EndpointNotReacheableException
	 * @throws SystemUserPermissionException
	 */
	public void deleteFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException;
	
	/**
	 * Closing connection with DFS.
	 * @throws EndpointNotReacheableException
	 */
	public void close() throws EndpointNotReacheableException;

}
