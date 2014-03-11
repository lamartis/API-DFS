package isidis.dfs.team.API_DFS;

import isidis.dfs.team.api.dfs.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.implementation.API_HDFS_Impl;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author saad
 *
 */

public class DebitPerSecondTest {
	
	public static void main(String[] args) throws IOException, URISyntaxException, FileNotFoundException, EndpointNotReacheableException{
		/**
		 * 
		 * Télécharger un fichier de 128Mo.
		 */
		API_HDFS api = new API_HDFS_Impl();
		long start = System.currentTimeMillis();
		api.readFile("/user/beck");
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		System.out.println("OK | Realized: " + elapsedTimeMillis/1000F + "s");
	}
}
