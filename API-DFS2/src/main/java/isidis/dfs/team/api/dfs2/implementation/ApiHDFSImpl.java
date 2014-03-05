package isidis.dfs.team.api.dfs2.implementation;

import isidis.dfs.team.api.dfs2.interfaces.ApiHDFS;
import isidis.dfs.team.api.dfs2.tools.ByteTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
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
		ByteTools byteTools = new ByteTools();
		
		RemoteIterator<byte[]> ri = null;
		
		//Récupération du RemoteIterator.
		ri = api.readFile("/user/film0");
		
		int i = 1;
		File file = new File("outputfile.txt");
		FileOutputStream fos = null;
		
		fos = new FileOutputStream(file);
		
		while (ri.hasNext()) {
			fos.write(ri.next());
			System.out.println(i++);
		}
		fos.close();
		System.out.println("OK");
	}
	
}
