package isidis.dfs.team.api.dfs2.interfaces;

import java.io.InputStream;

import org.apache.hadoop.fs.RemoteIterator;

public interface ApiHDFS {
	RemoteIterator<byte[]> readFile(String fileLocation);
	void writeFile(InputStream stream, String destinationFileLocation);
}
