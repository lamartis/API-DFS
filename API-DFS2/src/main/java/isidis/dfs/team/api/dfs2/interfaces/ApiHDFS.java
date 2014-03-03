package isidis.dfs.team.api.dfs2.interfaces;

import org.apache.hadoop.fs.RemoteIterator;

public interface ApiHDFS {
	RemoteIterator<byte[]> readFile(String fileLocation);
}
