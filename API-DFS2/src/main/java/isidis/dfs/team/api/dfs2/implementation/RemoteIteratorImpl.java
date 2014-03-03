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
			position++;
			return true;
		}
		return false;
	}

	public byte[] next() throws IOException {
		//Terminer.
		byte[] arr = new byte[(int)512L];

		DFSInputStream dfsInputStream = client.open("/user/file0");
		dfsInputStream.read(5, arr, 0, 10);
		System.out.println(new String(arr));
		
		return arr;
	}

}
