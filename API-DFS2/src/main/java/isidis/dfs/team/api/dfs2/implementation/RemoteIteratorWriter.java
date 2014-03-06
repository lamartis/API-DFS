package isidis.dfs.team.api.dfs2.implementation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import isidis.dfs.team.api.dfs2.interfaces.RemoteIterator;

public class RemoteIteratorWriter extends RemoteIterator<Byte[]>{
	public final static long Mo = 128;
	public final static long blockSizeInOctet = Mo * 1024 * 1024;
	
	private long numberOfBlocks = -1;
	private long lastBlockSize = -1;
	
	public RemoteIteratorWriter(InputStream stream, String destinationFileLocation) {
		this.fileLocation = destinationFileLocation;
		
		/**
		 * Getting file size.
		 */
		long fileSize = new File(fileLocation).length();
		System.out.println("File size: " + fileSize + ", block size: " + blockSizeInOctet);
		
		/**
		 * Tracking size number of file's blocks
		 */
		this.numberOfBlocks = fileSize / blockSizeInOctet;
		
		/**
		 * Getting last block's size.
		 */
		lastBlockSize = fileSize % blockSizeInOctet;
		System.out.println("Last block size: " + lastBlockSize + " Octets");
		
		if (lastBlockSize != 0)
			numberOfBlocks++;
	}
	
	public boolean hasNext() throws IOException {
		if (position < numberOfBlocks) {
			return true;
		}
		return false;
	}
	
	public Byte[] next() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
