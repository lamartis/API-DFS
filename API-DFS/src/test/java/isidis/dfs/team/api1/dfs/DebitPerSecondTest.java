package isidis.dfs.team.api1.dfs;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api1.dfs.implementation.Api1HDFSImpl;
import isidis.dfs.team.api1.dfs.interfaces.Api1HDFS;

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
		Api1HDFS api = new Api1HDFSImpl();
		long start = System.currentTimeMillis();
		try {
			api.readFile("/user/beck");
		} catch (FileSizeThresholdNotRespected e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemUserPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		System.out.println("OK | Realized: " + elapsedTimeMillis/1000F + "s");
	}
}
