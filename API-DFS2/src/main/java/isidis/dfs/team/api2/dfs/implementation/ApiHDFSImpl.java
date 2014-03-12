package isidis.dfs.team.api2.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.implementation.ApiGenericImpl;
import isidis.dfs.team.api2.dfs.interfaces.ApiHDFS;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.UnresolvedLinkException;
import org.apache.log4j.Logger;

public class ApiHDFSImpl extends ApiGenericImpl implements ApiHDFS {
	
	public static final Logger logger = Logger.getLogger(ApiHDFSImpl.class);
	
	public ApiHDFSImpl() throws EndpointNotReacheableException, URISyntaxException {
		super();
	}

	public RemoteIterator<byte[]> readFile(String fileLocation) {
		RemoteIterator<byte[]> remoteIterator = null;
		try {
			remoteIterator = new RemoteIteratorReader(fileLocation);
		} catch (UnresolvedLinkException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return remoteIterator;
	}

	public RemoteIterator<Void> writeFile(File file, String destinationFileLocation) throws IOException {

		/**
		 * Instantiation of distributed iterator which save each block on the OutputStream object.
		 */
		RemoteIterator<Void> remoteIterator = null;
		try {
			remoteIterator = new RemoteIteratorWriter(file, destinationFileLocation);
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return remoteIterator;
	}

}
