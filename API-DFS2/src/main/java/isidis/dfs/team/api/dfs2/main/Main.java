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
	public static void main(String[] args) throws IOException{
		/**
		 * Lecture de fichier de grande taille.
		 */
		ApiHDFS api = new ApiHDFSImpl();
		RemoteIterator<byte[]> ri = null;
		
		//Récupération du RemoteIterator.
		ri = api.readFile("/user/tauraFilmwriten2");
		
		File file = new File("traumaS");
		FileOutputStream fos = new FileOutputStream(file);
		
		while (ri.hasNext()) {
			fos.write(ri.next());
		}
		fos.close();
		System.out.println("OK");
	}
}
