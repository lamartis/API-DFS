package isidis.dfs.team.api.dfs2.implementation;

import isidis.dfs.team.api.dfs2.interfaces.ApiHDFS;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.UnresolvedLinkException;
import org.apache.hadoop.hdfs.DFSClient;

public class ApiHDFSImpl implements ApiHDFS {

	public ApiHDFSImpl() throws IOException{
		
	}
	
	public RemoteIterator<byte[]> readFile(String fileLocation) {
		RemoteIterator<byte[]> ri = null;
		try {
			ri = new RemoteIteratorImpl(fileLocation);
		} catch (UnresolvedLinkException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ri;
	}
	
	public static void main(String[] args) throws IOException{
		ApiHDFS api = new ApiHDFSImpl();
		RemoteIterator<byte[]> ri = null;
		
		//Récupération du RemoteIterator.
		ri = api.readFile("/user/film0");
		
		while (ri.hasNext()) {
			// Il va falloir récupérer partie par partie et les stocker.
			ri.next();
		}
	}

}
