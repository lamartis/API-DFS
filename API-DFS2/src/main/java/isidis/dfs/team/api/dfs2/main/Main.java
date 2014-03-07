package isidis.dfs.team.api.dfs2.main;

import isidis.dfs.team.api.dfs2.implementation.ApiHDFSImpl;
import isidis.dfs.team.api.dfs2.interfaces.ApiHDFS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.hadoop.fs.RemoteIterator;

/**
 *
 */
public class Main {
	
	public final static String localFile = "/home/dfs-one/Téléchargements/Trauma.2013.FRENCH.DVDRiP.avi";
	public final static String remoteFile = "/user/tauraFilmwriten2";
			
	public static void main(String[] args) throws IOException{
		
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
