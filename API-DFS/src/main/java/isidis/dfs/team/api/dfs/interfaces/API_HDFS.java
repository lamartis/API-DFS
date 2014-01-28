package isidis.dfs.team.api.dfs.interfaces;

import java.io.IOException;

import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;

public interface API_HDFS {
	public byte[] readFile(String sourceFileName) throws FileNotFoundException, IOException;
	public void writeFile(byte[] content, String destinationFileName);
	public void deleteFile(String sourceFileName) throws FileNotFoundException, IOException;
	
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
