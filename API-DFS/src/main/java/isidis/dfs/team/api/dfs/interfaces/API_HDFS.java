package isidis.dfs.team.api.dfs.interfaces;

import org.apache.hadoop.fs.FileAlreadyExistsException;
import isidis.dfs.team.api.dfs.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;
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
	 */
	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException;
	/**
	 * Writing a file
	 * @param content
	 * the content who want to write
	 * @param destinationFileName
	 * The path where the file will be wrote
	 * @throws SystemUserPermissionException
	 * @throws EndpointNotReacheableException
	 * @throws FileAlreadyExistsException
	 */
	public void writeFile(byte[] content, String destinationFileName) throws SystemUserPermissionException, EndpointNotReacheableException, FileAlreadyExistsException;
	/**
	 * Deleting a file
	 * @param sourceFileName
	 * The name/path of the file who want to delete
	 * @throws FileNotFoundException
	 * @throws EndpointNotReacheableException
	 * @throws SystemUserPermissionException
	 */
	public void deleteFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException;
	
	/***
	 * 
	 * //new idea for interface fonctions
	public void copyfileIntoHDFS(String filetocopy, String destinationPath)throws IOException;
	public void copyfileFromHDFS(String filename, String destinationPath)throws FileNotFoundException, IOException;
	public void createDirectory(String directoryPath);
	public void deleteDirectory(String directoryPath, boolean suppressContent)throws IOException;
	public String[] getfileInfo(String filename)throws FileNotFoundException, IOException;
	
	*/
}
