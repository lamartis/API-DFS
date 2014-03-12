package isidis.dfs.team.api2.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.UnresolvedLinkException;
import org.apache.hadoop.hdfs.DFSInputStream;

public class RemoteIteratorReader extends RemoteIteratorAbstract<byte[]> {
	
	public RemoteIteratorReader(String fileLocation) throws UnresolvedLinkException, IOException, EndpointNotReacheableException, URISyntaxException{
		super();
		
		this.fileLocation = fileLocation;
		inputStream = client.open(fileLocation);
		
		/**
		 * Getting file size.
		 */
		fileSize = client.getFileInfo(fileLocation).getLen();
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

	public byte[] next() throws IOException {
		
		if ((lastBlockSize != 0) && (position == numberOfBlocks-1)) 
			securityChecker.blockSizeInOctet = lastBlockSize;

		bytes = new byte[(int)securityChecker.blockSizeInOctet];

		System.out.println("Try to get: " + securityChecker.blockSizeInOctet + " Octets");
		inputStream.read(bytes, 0, (int)securityChecker.blockSizeInOctet);
		System.out.println("Getting block N° " + (position+1) + "/" + numberOfBlocks + " remotely with success");
		
		if (position == numberOfBlocks-1)
			inputStream.close();
			
		position++;
		return bytes;
	}

}
