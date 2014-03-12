package isidis.dfs.team.api2.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class RemoteIteratorWriter extends RemoteIteratorAbstract<Void>{
	
	private OutputStream outputStream = null;

	public RemoteIteratorWriter(File file, String destinationFileLocation) throws IOException, EndpointNotReacheableException, URISyntaxException {
		super();
		
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
		System.out.println("File size: " + fileSize + " Octets, which can be devised by: " + securityChecker.blockSizeInOctet + " Octets");

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
		
		System.out.println("Number of blocks: " + this.numberOfBlocks);
		System.out.println("Lastest block size: " + lastBlockSize + " Octets \n");
	}

	public Void next() throws IOException {
		
		if ((lastBlockSize != 0) && (position == numberOfBlocks-1)) 
			securityChecker.blockSizeInOctet = lastBlockSize;

		bytes = new byte[(int)securityChecker.blockSizeInOctet];

		inputStream.read(bytes, 0, (int)securityChecker.blockSizeInOctet);
		System.out.println("Getting block: " + securityChecker.blockSizeInOctet + " Octets");
		
		outputStream.write(bytes);
		System.out.println("Saving block N° " + (position+1) + "/" + numberOfBlocks + " from localy with success");
		
		if (position == numberOfBlocks-1) {
			inputStream.close();
			outputStream.close();
		}
			
		position++;
		
		return null;
	}


}
