package isidis.dfs.team.api.dfs2.implementation;

import isidis.dfs.team.api.dfs2.interfaces.ApiHDFS;
import isidis.dfs.team.api.dfs2.interfaces.RemoteIterator;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.UnresolvedLinkException;

public class ApiHDFSImpl implements ApiHDFS {

	public ApiHDFSImpl() throws IOException{

	}

	public RemoteIterator<byte[]> readFile(String fileLocation) {
		RemoteIterator<byte[]> ri = null;
		try {
			ri = new RemoteIteratorReader(fileLocation);
		} catch (UnresolvedLinkException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ri;
	}

	public void writeFile(InputStream stream, String destinationFileLocation) {
		
	}

}
