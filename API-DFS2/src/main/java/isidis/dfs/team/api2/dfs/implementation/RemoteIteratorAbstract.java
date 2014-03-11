package isidis.dfs.team.api2.dfs.implementation;

import java.io.IOException;
import java.net.URISyntaxException;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.implementation.MyHdfsClient;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;

import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Logger;

public abstract class RemoteIteratorAbstract<E> implements RemoteIterator<E> {
	protected final long Mo = 64;
	protected long blockSizeInOctet = Mo * 1024 * 1024;
	
	protected byte[] bytes = null;
	protected long numberOfBlocks = -1;
	protected long lastBlockSize = -1;
	protected String fileLocation = null;
	protected int position = 0;
	protected long fileSize = -1;	
	protected DFSClient client = null;
	protected static Logger logger = Logger.getLogger(RemoteIteratorAbstract.class);
	
	public RemoteIteratorAbstract() throws EndpointNotReacheableException, URISyntaxException {
		client = MyHdfsClient.getInstance();
	}
	
	public long getNumberOfBlocks() {
		return numberOfBlocks;
	}
	
	public void calculNumberOfBlocks() {
		this.numberOfBlocks = fileSize / blockSizeInOctet;
	}
	
	public void calculSizeOfLatestBlock() {
		lastBlockSize = fileSize % blockSizeInOctet;
	}
	
	public boolean hasNext() throws IOException {
		if (position < numberOfBlocks) {
			return true;
		}
		return false;
	}
}
