package isidis.dfs.team.api.dfs2.interfaces;

import java.io.IOException;

import isidis.dfs.team.api.dfs2.implementation.MyHdfsClient;

import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Logger;

public abstract class RemoteIterator<E> implements org.apache.hadoop.fs.RemoteIterator<E> {
	protected final long Mo = 64;
	protected long blockSizeInOctet = Mo * 1024 * 1024;
	
	protected byte[] bytes = null;
	protected long numberOfBlocks = -1;
	protected long lastBlockSize = -1;
	protected String fileLocation = null;
	protected int position = 0;
	protected DFSClient client = MyHdfsClient.getInstance();
	protected static Logger logger = Logger.getLogger(RemoteIterator.class);
	
	public long getNumberOfBlocks() {
		return numberOfBlocks;
	}
	
	public boolean hasNext() throws IOException {
		if (position < numberOfBlocks) {
			return true;
		}
		return false;
	}
}
