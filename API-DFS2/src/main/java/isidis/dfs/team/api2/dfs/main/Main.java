package isidis.dfs.team.api2.dfs.main;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api2.dfs.implementation.ApiHDFSImpl;
import isidis.dfs.team.api2.dfs.interfaces.ApiHDFS;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 */
public class Main {
	
	public final static String localFile = "/home/dfs-one/Téléchargements/Trauma.2013.FRENCH.DVDRiP.avi";
	public final static String remoteFile = "/user/tauraFilmwriten2";
			
	public static void main(String[] args) throws IOException, EndpointNotReacheableException, URISyntaxException{
		
		ApiHDFS api = new ApiHDFSImpl();
		
		/**
		 * Lecture de fichier de grande taille.
		 *
		
		RemoteIterator<byte[]> ri = api.readFile(remoteFile);
		
		FileOutputStream fos = new FileOutputStream(new File("traumaS"));
		
		while (ri.hasNext()) {
			fos.write(ri.next());
		}
		fos.close();
		
		/**
		 * Ecriture de fichier de grande taille.
		 */
		RemoteIterator<Void> ri = api.writeFile(new File(localFile), "/user/halima");
		while (ri.hasNext()) {
			ri.next();
		}

		System.out.println("OK");
	}
}
