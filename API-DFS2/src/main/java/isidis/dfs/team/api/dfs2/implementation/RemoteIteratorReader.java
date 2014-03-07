package isidis.dfs.team.api.dfs2.implementation;

import isidis.dfs.team.api.dfs2.interfaces.RemoteIterator;
import java.io.IOException;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.UnresolvedLinkException;
import org.apache.hadoop.hdfs.DFSInputStream;

public class RemoteIteratorReader extends RemoteIterator<byte[]> {
	
	public BlockLocation[] blocks;

	public RemoteIteratorReader(String fileLocation) throws UnresolvedLinkException, IOException{
		this.fileLocation = fileLocation;

		//Getting all BlockLocations' File.
		blocks = client.getBlockLocations(fileLocation, 0, Long.MAX_VALUE);
	}

	public boolean hasNext() throws IOException {
		if (position < blocks.length) {
			return true;
		}
		return false;
	}

	public byte[] next() throws IOException {
		BlockLocation blockLocation = blocks[position];
		byte[] arr = new byte[(int)blockLocation.getLength()] ;

		System.out.println("["+ (int)blockLocation.getOffset() +" , "+(int)blockLocation.getLength()+" ]");
		DFSInputStream dfsInputStream = client.open(fileLocation);
		dfsInputStream.read((int)blockLocation.getOffset(), arr, 0, (int)blockLocation.getLength());

		position++;
		return arr;
	}

}
