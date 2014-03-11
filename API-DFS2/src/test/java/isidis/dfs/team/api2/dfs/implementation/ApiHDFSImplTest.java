package isidis.dfs.team.api2.dfs.implementation;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.hdfs.DFSClient;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.implementation.MyHdfsClient;
import isidis.dfs.team.api2.dfs.implementation.ApiHDFSImpl;
import isidis.dfs.team.api2.dfs.interfaces.ApiHDFS;
import junit.framework.TestCase;

public class ApiHDFSImplTest extends TestCase {
	
	public final static String file = "/home/dfs-one/Téléchargements/Trauma.2013.FRENCH.DVDRiP.avi";
	public void testWriteFile() throws IOException, EndpointNotReacheableException, URISyntaxException {
		ApiHDFS api = new ApiHDFSImpl();
		api.writeFile(new File(file), "/user/tauraFilmwriten2");
	}
	
	public void testreadFile() throws IOException, EndpointNotReacheableException, URISyntaxException {
		DFSClient client = MyHdfsClient.getInstance();
		System.out.println(client.getFileInfo("/user/tauraFilmwriten2").getLen());
	}
}
