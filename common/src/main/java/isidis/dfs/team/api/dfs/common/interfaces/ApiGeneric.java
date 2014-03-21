package isidis.dfs.team.api.dfs.common.interfaces;

import java.io.IOException;

import org.apache.hadoop.fs.PathExistsException;
import org.apache.hadoop.fs.PathIsNotDirectoryException;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;

public interface ApiGeneric {
	/**
	 * Deleting a file method
	 * @param sourceFileName
	 * The path/name of the file that you want to delete
	 * @throws FileNotFoundException
	 * @throws EndpointNotReacheableException
	 * @throws SystemUserPermissionException
	 */
	public void deleteFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException;

	/**
	 * Returning the number of remaining bytes on the file system
	 * @return
	 * long variable which represents the number of remaining bytes
	 * @throws EndpointNotReacheableException
	 */
	public long getRemainingCapacity() throws EndpointNotReacheableException;
	
	/**
	 * Get the file info for a specific file or directory.
	 * @param fileLocation
	 * The path/name of the file that you want to get its informations
	 * @return
	 * HdfsFileStatus Object which contains all file's info.
	 * @throws EndpointNotReacheableException
	 * @throws FileNotFoundException
	 */
	public HdfsFileStatus getFileInfo(String fileLocation) throws FileNotFoundException, EndpointNotReacheableException;
	
	/**
	 * Create a directory (or hierarchy of directories) with the given name.
	 * @param absoluteDirectory
	 * The path of the directory being created
	 * @throws EndpointNotReacheableException
	 * @throws PathExistsException 
	 */
	public void mkdirs(String absoluteDirectory) throws EndpointNotReacheableException, PathExistsException;
	
	/**
	 * List path in order to show all files and directories.
	 * @param path
	 * The path of the directory being listed
	 * @return
	 * Array of HdfsFileStatus which contains all file's info.
	 * @throws EndpointNotReacheableException
	 */
	public HdfsFileStatus[] listPaths(String path) throws PathIsNotDirectoryException, EndpointNotReacheableException;
	
	/**
	 * Rename file or directory. It can be used also to move file or directory to an other location.
	 * @param src
	 * Source
	 * @param dst
	 * Destination
	 * @throws EndpointNotReacheableException
	 * @throws FileNotFoundException
	 */
	public void rename(String src, String dst) throws EndpointNotReacheableException, FileNotFoundException;
	
	/**
	 * Closing connection with DFS.
	 * @throws EndpointNotReacheableException
	 */
	public void close() throws EndpointNotReacheableException;

}
