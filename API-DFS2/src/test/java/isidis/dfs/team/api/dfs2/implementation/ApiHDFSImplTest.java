package isidis.dfs.team.api.dfs2.implementation;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.hdfs.DFSClient;

import isidis.dfs.team.api.dfs2.interfaces.ApiHDFS;
import junit.framework.TestCase;

public class ApiHDFSImplTest extends TestCase {
	
	public final static String file = "/home/dfs-one/Téléchargements/Trauma.2013.FRENCH.DVDRiP.avi";
	public void testWriteFile() throws IOException {
		ApiHDFS api = new ApiHDFSImpl();
		api.writeFile(new File(file), "/user/tauraFilmwriten2");
	}
	
	public void testreadFile() throws IOException {
		DFSClient client = MyHdfsClient.getInstance();
		System.out.println(client.getFileInfo("/user/tauraFilmwriten2").getLen());
	}
}
