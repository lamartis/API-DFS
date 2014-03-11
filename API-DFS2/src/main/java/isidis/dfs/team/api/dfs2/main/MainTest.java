package isidis.dfs.team.api.dfs2.main;

import isidis.dfs.team.api.dfs2.implementation.MyHdfsClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Logger;

public class MainTest {

	public final static long Mo = 128;
	public static long blockSizeInOctet = Mo * 1024 * 1024;
	public final static String fileLocation = "/home/dfs-one/Téléchargements/Trauma.2013.FRENCH.DVDRiP.avi";
	//public final static String fileLocation2 = "/home/dfs-one/Téléchargements/file0";
	public static final Logger logger = Logger.getLogger(MainTest.class);

	public static void main(String[] args) throws IOException {
		/**
		 * Getting file size.
		 */
		long fileSize = new File(fileLocation).length();
		System.out.println("File size: " + fileSize + ", block size: " + blockSizeInOctet);

		/**
		 * Tracking size number of file's blocks
		 */
		
		long blocksNumber = fileSize / blockSizeInOctet;
		
		/**
		 * Getting last block's size.
		 */
		
		long lastBlockSize = fileSize % blockSizeInOctet;
		System.out.println(lastBlockSize);

		//Normalement ici je devrais avoir une sorte d'itérateur,
		//qui me permettera de basculer de block à block.

		/**
		 * Ecrire un Gros fichier sur le DFS. 
		 * 
		 */
		final String destinationFileName = "/user/traumaEcriture";
		InputStream fileInputStream = new FileInputStream(new File(fileLocation));
		byte[] t = null;

		DFSClient client = MyHdfsClient.getInstance();
		OutputStream outputStream = client.create(destinationFileName, true);

		for (int i = 0; i <= blocksNumber; i++) {
			if (i == blocksNumber) {
				blockSizeInOctet = lastBlockSize;
			}

			t = new byte[(int)blockSizeInOctet];

			System.out.println("Try to get: " + blockSizeInOctet + " Octets");
			fileInputStream.read(t, 0, (int)blockSizeInOctet);

			System.out.println("Getting block N° " + i + " from localy");
			outputStream.write(t);
			System.out.println("Writing block N° " + i + " in HDFS");
			t = null;
		}

		fileInputStream.close();
		outputStream.close();
		System.out.println("OK");
	}
}
