package isidis.dfs.team.API_DFS.interfaces;

import java.io.IOException;

import isidis.dfs.team.API_DFS.exceptions.FileNotFoundException;

public interface API_HDFS {
	public byte[] readFile(String sourceFileName) throws FileNotFoundException, IOException;
	public void writeFile(byte[] content, String destinationFileName);
	public void deleteFile(String sourceFileName) throws FileNotFoundException, IOException;
}
