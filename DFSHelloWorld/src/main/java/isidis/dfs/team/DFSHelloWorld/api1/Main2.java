package isidis.dfs.team.DFSHelloWorld.api1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api2.dfs.implementation.ApiHDFSImpl;
import isidis.dfs.team.api2.dfs.interfaces.ApiHDFS;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;

public class Main2 {
	
	public final static String fileLocaly = "/home/dfs-one/Téléchargements/Trauma.2013.FRENCH.DVDRiP.avi";
	public final static String destionationFile = "/user/api2/testWritingTrauma";
	
	public final static String fileToRead = "/user/trauma";
	
	public static void main(String[] args) throws IOException {
		
		/**
		 * Getting new instance.
		 */
		ApiHDFS api2 = null;
		try {
			api2 = new ApiHDFSImpl();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		/**
		 * Writing file.
		 *
		
		RemoteIterator<Void> remoteIterator = null;
		try {
			remoteIterator = api2.writeFile(new File(fileLocaly), destionationFile);
			
			while (remoteIterator.hasNext()) {
				remoteIterator.next();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		/**
		 * Reading file.
		 */
		RemoteIterator<byte[]> remoteIterator2 = api2.readFile(fileToRead);
		FileOutputStream fos = new FileOutputStream(new File("traumaS"));
		
		while (remoteIterator2.hasNext()) {
			fos.write(remoteIterator2.next());
		}
		fos.close();
		
		/**
		 * Deleting file.
		 *
		try {
			api2.deleteFile(destionationFile);
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
			api2.close();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		}
	}
}
