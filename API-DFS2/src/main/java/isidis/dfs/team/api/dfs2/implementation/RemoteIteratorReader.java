package isidis.dfs.team.api.dfs2.implementation;

import java.io.IOException;
import org.apache.hadoop.fs.UnresolvedLinkException;
import org.apache.hadoop.hdfs.DFSInputStream;

public class RemoteIteratorReader extends RemoteIteratorAbstract<byte[]> {

	private DFSInputStream dfsInputStream = null;
	
	public RemoteIteratorReader(String fileLocation) throws UnresolvedLinkException, IOException{
		
		this.fileLocation = fileLocation;
		dfsInputStream = client.open(fileLocation);
		
		/**
		 * Getting file size.
		 */
		fileSize = client.getFileInfo(fileLocation).getLen();
		System.out.println("File size: " + fileSize + " Octets, which can be devised by: " + blockSizeInOctet + " Octets");
		
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
			blockSizeInOctet = lastBlockSize;

		bytes = new byte[(int)blockSizeInOctet];

		System.out.println("Try to get: " + blockSizeInOctet + " Octets");
		dfsInputStream.read(bytes, 0, (int)blockSizeInOctet);
		System.out.println("Getting block N° " + (position+1) + "/" + numberOfBlocks + " remotely with success");
		
		if (position == numberOfBlocks-1)
			dfsInputStream.close();
			
		position++;
		return bytes;
	}

}
