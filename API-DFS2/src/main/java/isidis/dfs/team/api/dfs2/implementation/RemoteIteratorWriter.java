package isidis.dfs.team.api.dfs2.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import isidis.dfs.team.api.dfs2.interfaces.RemoteIterator;

public class RemoteIteratorWriter extends RemoteIterator<Void>{
	
	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	public RemoteIteratorWriter(File file, String destinationFileLocation) throws IOException {
		
		this.fileLocation = destinationFileLocation;
		this.inputStream = new FileInputStream(file);

		/**
		 * Instantiation Of OutputStream Object
		 */
		outputStream = client.create(this.fileLocation, true);
		
		/**
		 * Getting file size.
		 */
		long fileSize = file.length();
		System.out.println("File size: " + fileSize + " Octets, which can be devised by: " + blockSizeInOctet + " Octets");

		/**
		 * Tracking size number of file's blocks
		 */
		this.numberOfBlocks = fileSize / blockSizeInOctet;
		
		/**
		 * Getting last block's size.
		 */
		lastBlockSize = fileSize % blockSizeInOctet;
		
		if (lastBlockSize != 0)
			numberOfBlocks++;
		
		System.out.println("Number of blocks: " + this.numberOfBlocks);
		System.out.println("Lastest block size: " + lastBlockSize + " Octets \n");
	}

	public boolean hasNext() throws IOException {
		if (position < numberOfBlocks) {
			return true;
		}
		return false;
	}

	public Void next() throws IOException {
		
		if ((lastBlockSize != 0) && (position == numberOfBlocks-1)) 
			blockSizeInOctet = lastBlockSize;

		bytes = new byte[(int)blockSizeInOctet];

		inputStream.read(bytes, 0, (int)blockSizeInOctet);
		System.out.println("Getting block: " + blockSizeInOctet + " Octets");
		
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
