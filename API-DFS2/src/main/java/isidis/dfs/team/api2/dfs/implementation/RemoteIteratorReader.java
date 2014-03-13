package isidis.dfs.team.api2.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import java.io.IOException;
import org.apache.hadoop.security.AccessControlException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class RemoteIteratorReader extends RemoteIteratorAbstract<byte[]> {

	public RemoteIteratorReader(String fileLocation) throws IOException, EndpointNotReacheableException, SystemUserPermissionException {
		super();
		logger = Logger.getLogger(RemoteIteratorWriter.class);

		try { 
			this.fileLocation = fileLocation;
			inputStream = client.open(fileLocation);
		} catch (AccessControlException e){
			logger.log(Level.ERROR, "SystemUserPermissionException reached");
			throw new SystemUserPermissionException();
		}
		/**
		 * Getting file size.
		 */
		fileSize = client.getFileInfo(fileLocation).getLen();
		logger.info("File size: " + fileSize + " Octets, which can be devised by: " + blockSizeInOctet + " Octets");

		/**
		 * Tracking size number of file's blocks
		 */
		this.calculNumberOfBlocks();

		/**
		 * Getting last block's size.
		 */
		this.calculSizeOfLatestBlock();

		if (lastBlockSize != 0)
			numberOfBlocks++;

		logger.info("Number of blocks: " + this.numberOfBlocks);
		logger.info("Lastest block size: " + lastBlockSize + " Octets \n");
	}

	public byte[] next() throws IOException {

		if ((lastBlockSize != 0) && (position == numberOfBlocks-1)) 
			blockSizeInOctet = lastBlockSize;

		bytes = new byte[(int)blockSizeInOctet];

		logger.info("Try to get: " + blockSizeInOctet + " Octets");
		inputStream.read(bytes, 0, (int)blockSizeInOctet);
		logger.info("Getting block N° " + (position+1) + "/" + numberOfBlocks + " remotely with success");

		if (position == numberOfBlocks-1)
			inputStream.close();

		position++;
		return bytes;
	}

}
