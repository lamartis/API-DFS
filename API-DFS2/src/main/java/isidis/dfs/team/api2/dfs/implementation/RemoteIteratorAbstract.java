package isidis.dfs.team.api2.dfs.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.implementation.MyHdfsClient;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;

import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Logger;

public abstract class RemoteIteratorAbstract<E> implements RemoteIterator<E> {
	protected SecurityChecker securityChecker = null;
	
	protected byte[] bytes = null;
	protected long numberOfBlocks = -1;
	protected long lastBlockSize = -1;
	protected String fileLocation = null;
	protected int position = 0;
	protected long fileSize = -1;	
	protected InputStream inputStream = null;
	protected static Logger logger = null;
	protected long blockSizeInOctet = -1;
	protected DFSClient client = null;
	
	public RemoteIteratorAbstract() throws EndpointNotReacheableException {
		try {
			client = MyHdfsClient.getInstance().getDFSClient();
			blockSizeInOctet = MyHdfsClient.getInstance().getBlockSizeInOctet();
			securityChecker = SecurityChecker.getInstance();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
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
