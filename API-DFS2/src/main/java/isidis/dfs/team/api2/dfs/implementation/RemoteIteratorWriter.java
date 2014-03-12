package isidis.dfs.team.api2.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

public class RemoteIteratorWriter extends RemoteIteratorAbstract<Void>{
	
	private OutputStream outputStream = null;

	public RemoteIteratorWriter(File file, String destinationFileLocation) throws IOException, EndpointNotReacheableException, URISyntaxException {
		super();
		logger = Logger.getLogger(RemoteIteratorWriter.class);
		
		this.fileLocation = destinationFileLocation;
		this.inputStream = new FileInputStream(file);

		/**
		 * Instantiation Of OutputStream Object
		 */
		outputStream = client.create(this.fileLocation, true);
		
		/**
		 * Getting file size.
		 */
		fileSize = file.length();
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

	public Void next() throws IOException {
		
		if ((lastBlockSize != 0) && (position == numberOfBlocks-1)) 
			blockSizeInOctet = lastBlockSize;

		bytes = new byte[(int)blockSizeInOctet];

		inputStream.read(bytes, 0, (int)blockSizeInOctet);
		logger.info("Getting block: " + blockSizeInOctet + " Octets");
		
		outputStream.write(bytes);
		logger.info("Saving block N° " + (position+1) + "/" + numberOfBlocks + " from localy with success");
		
		if (position == numberOfBlocks-1) {
			inputStream.close();
			outputStream.close();
		}
			
		position++;
		
		return null;
	}


}
