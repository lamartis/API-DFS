package isidis.dfs.team.api.dfs2.interfaces;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.fs.RemoteIterator;

public interface ApiHDFS {
	RemoteIterator<byte[]> readFile(String fileLocation);
	RemoteIterator<Void> writeFile(File file, String destinationFileLocation) throws IOException;
}
