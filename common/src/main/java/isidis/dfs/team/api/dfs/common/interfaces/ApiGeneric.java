package isidis.dfs.team.api.dfs.common.interfaces;

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
	 * @throws EndpointNotReacheableException
	 */
	public long getRemainingCapacity() throws EndpointNotReacheableException;
	
	/**
	 * Closing connection with DFS.
	 * @throws EndpointNotReacheableException
	 */
	public void close() throws EndpointNotReacheableException;
}
