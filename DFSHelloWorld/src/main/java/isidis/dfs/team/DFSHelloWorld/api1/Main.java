package isidis.dfs.team.DFSHelloWorld.api1;

import java.net.URISyntaxException;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeExceedsFixedThreshold;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api1.dfs.implementation.Api1HDFSImpl;
import isidis.dfs.team.api1.dfs.interfaces.Api1HDFS;

public class Main {

	public static final String file = "/user/file6";
	public static final String content = "Ha ana message diali buhdi! ";
	
	public static void main(String[] args) {
		/**
		 * Getting new instance.
		 */
		Api1HDFS api1 = null;
		try {
			api1 = new Api1HDFSImpl();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		}
		

		/**
		 * Writing file.
		 */
		try {
			api1.writeFile(content.getBytes(), file);
		} catch (FileAlreadyExistsException e) {
			e.printStackTrace();
		} catch (SystemUserPermissionException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (FileSizeExceedsFixedThreshold e) {
			e.printStackTrace();
		}
		
		/**
		 * Reading file.
		 */
		try {
			System.out.println(new String(api1.readFile(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (SystemUserPermissionException e) {
			e.printStackTrace();
		} catch (FileSizeExceedsFixedThreshold e) {
			e.printStackTrace();
		}
		
		/**
		 * Deleting file.
		 */
		try {
			api1.deleteFile(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (SystemUserPermissionException e) {
			e.printStackTrace();
		}
		
		
		/**
		 * Closing connection.
		 */
		try {
			api1.close();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		}

	}
}
