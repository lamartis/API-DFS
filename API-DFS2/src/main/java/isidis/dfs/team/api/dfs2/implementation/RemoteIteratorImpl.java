package isidis.dfs.team.api.dfs2.implementation;

import java.io.IOException;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.UnresolvedLinkException;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSInputStream;

public class RemoteIteratorImpl implements RemoteIterator<byte[]> {
	/**
	 * à terminer.
	 * 
	 */
	public BlockLocation[] blocks;
	public String fileLocation = null;
	public int position = 0;
	private DFSClient client = null;

	public RemoteIteratorImpl(String fileLocation) throws UnresolvedLinkException, IOException{
		this.fileLocation = fileLocation;
		client = MyHdfsClient.getInstance();

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
