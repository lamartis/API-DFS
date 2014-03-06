package isidis.dfs.team.api.dfs2.implementation;

import isidis.dfs.team.api.dfs2.interfaces.ApiHDFS;
import isidis.dfs.team.api.dfs2.interfaces.RemoteIterator;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.hadoop.fs.UnresolvedLinkException;
import org.apache.hadoop.hdfs.DFSClient;

public class ApiHDFSImpl implements ApiHDFS {
	
	DFSClient client = MyHdfsClient.getInstance();
	
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

	public void writeFile(InputStream stream, String destinationFileLocation) throws IOException {
		/**
		 * Instantiation Of OutputStream Object
		 */
		OutputStream outputStream = client.create(destinationFileLocation, true);
		
		/**
		 * Instantiation of distributed iterator which save each block on the OutputStream object.
		 */
		RemoteIterator<byte[]> remoteIterator = new RemoteIteratorWriter(stream, destinationFileLocation);
		
		/**
		 * Saving each block on the OutputStream object.
		 */
		while (remoteIterator.hasNext()) {
			remoteIterator.next();
			//outputStream.write(remoteIterator.next());
		}

		outputStream.close();
	}

}
