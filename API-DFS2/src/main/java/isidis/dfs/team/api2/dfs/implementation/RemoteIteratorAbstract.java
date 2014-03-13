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

/**
 * 
 * @author saad
 * @see
 * This class is used to organize the two remoteIterator, the writer et the reader one.
 * @param <E>
 * This parameter depends of what we would like to iterate.
 */
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
	
	/**
	 * Constructor of this class
	 * @throws EndpointNotReacheableException
	 */
	public RemoteIteratorAbstract() throws EndpointNotReacheableException {
		try {
			client = MyHdfsClient.getInstance().getDFSClient();
			blockSizeInOctet = MyHdfsClient.getInstance().getBlockSizeInOctet();
			securityChecker = SecurityChecker.getInstance();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * This method is used to return the number of blocks
	 */
	public long getNumberOfBlocks() {
		return numberOfBlocks;
	}
	
	public void calculNumberOfBlocks() {
		this.numberOfBlocks = fileSize / blockSizeInOctet;
		logger.info("number of blocks = " + getNumberOfBlocks());
	}
	
	public void calculSizeOfLatestBlock() {
		lastBlockSize = fileSize % blockSizeInOctet;
	}
	
	/**
	 * This method allows clients to verify if there is next value or not
	 */
	public boolean hasNext() throws IOException {
		if (position < numberOfBlocks) {
			return true;
		}
		return false;
	}
}
