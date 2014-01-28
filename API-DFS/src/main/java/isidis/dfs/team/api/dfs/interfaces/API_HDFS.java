package isidis.dfs.team.api.dfs.interfaces;

import org.apache.hadoop.fs.FileAlreadyExistsException;
import isidis.dfs.team.api.dfs.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.exceptions.SystemUserPermissionException;

public interface API_HDFS {
	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException;
	public void writeFile(byte[] content, String destinationFileName) throws SystemUserPermissionException, EndpointNotReacheableException, FileAlreadyExistsException;
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
