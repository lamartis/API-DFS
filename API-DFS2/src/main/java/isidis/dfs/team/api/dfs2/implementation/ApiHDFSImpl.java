package isidis.dfs.team.api.dfs2.implementation;

import isidis.dfs.team.api.dfs2.interfaces.ApiHDFS;
import isidis.dfs.team.api.dfs2.interfaces.RemoteIterator;

import java.io.File;
import java.io.IOException;
import org.apache.hadoop.fs.UnresolvedLinkException;

public class ApiHDFSImpl implements ApiHDFS {
	
	public ApiHDFSImpl() {

	}

	public RemoteIterator<byte[]> readFile(String fileLocation) {
		RemoteIterator<byte[]> remoteIterator = null;
		try {
			remoteIterator = new RemoteIteratorReader(fileLocation);
		} catch (UnresolvedLinkException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return remoteIterator;
	}

	public RemoteIterator<Void> writeFile(File file, String destinationFileLocation) throws IOException {

		/**
		 * Instantiation of distributed iterator which save each block on the OutputStream object.
		 */
		RemoteIterator<Void> remoteIterator = new RemoteIteratorWriter(file, destinationFileLocation);
		return remoteIterator;
	}

}
