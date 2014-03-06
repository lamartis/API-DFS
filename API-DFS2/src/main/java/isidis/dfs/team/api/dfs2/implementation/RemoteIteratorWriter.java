package isidis.dfs.team.api.dfs2.implementation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import isidis.dfs.team.api.dfs2.interfaces.RemoteIterator;

public class RemoteIteratorWriter extends RemoteIterator<byte[]>{
	public final static long Mo = 128;
	public static long blockSizeInOctet = Mo * 1024 * 1024;

	private long numberOfBlocks = -1;
	private long lastBlockSize = -1;
	private InputStream inputStream = null;
	byte[] bytes = null;

	public RemoteIteratorWriter(InputStream stream, String destinationFileLocation) throws IOException {
		this.fileLocation = destinationFileLocation;
		this.inputStream = stream;

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
		if (position <= numberOfBlocks) {
			return true;
		}
		return false;
	}

	public byte[] next() throws IOException {
		/**
		 * 
		 */
		if ((lastBlockSize == 0) && (position == numberOfBlocks)) 
			blockSizeInOctet = lastBlockSize;

		bytes = new byte[(int)blockSizeInOctet];

		System.out.println("Try to get: " + blockSizeInOctet + " Octets");
		inputStream.read(bytes, 0, (int)blockSizeInOctet);

		System.out.println("Getting block N° " + position + "/" + numberOfBlocks + " from localy");
		
		return bytes;
	}


}
