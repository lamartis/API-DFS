package isidis.dfs.team.api.dfs2.implementation;

import java.io.IOException;

import isidis.dfs.team.api.dfs2.implementation.MyHdfsClient;
import isidis.dfs.team.api.dfs2.interfaces.RemoteIterator;

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
	protected DFSClient client = MyHdfsClient.getInstance();
	protected static Logger logger = Logger.getLogger(RemoteIteratorAbstract.class);
	
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
