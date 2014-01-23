package isidis.dfs.team.API_DFS.implementation;

import isidis.dfs.team.API_DFS.exceptions.FileNotFoundException;
import isidis.dfs.team.API_DFS.interfaces.API_HDFS;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSInputStream;
/***
 * 
 * @author saad
 * Demander au prof la signature des méthode si c correcte.
 * 
 */
public class API_HDFS_Impl implements API_HDFS{

	private URI hdfsURI = null;
	private long fileLenght = 512L;
	Configuration conf = null;

	public API_HDFS_Impl(String hdfsURL, String systemUserName) throws URISyntaxException {
		this.hdfsURI = new URI(hdfsURL);
		conf = new Configuration();
		conf.set("fs.defaultFS", hdfsURL);
		System.setProperty("HADOOP_USER_NAME", systemUserName);
	}

	@Override
	public byte[] readFile(String sourceFileName) throws FileNotFoundException, IOException {
		byte[] arr = new byte[(int)fileLenght];

		DFSClient client = new DFSClient(this.hdfsURI, conf);
		DFSInputStream dfsInputStream = null;

		try {
			if (!client.exists(sourceFileName)) 
				throw new FileNotFoundException();

			dfsInputStream = client.open(sourceFileName);
			dfsInputStream.read(arr, 0, (int)fileLenght);

		} finally {
			if (dfsInputStream != null)
				dfsInputStream.close();

			if (client != null)
				client.close();

		}

		return arr;
	}

	@Override
	public void writeFile(byte[] content, String destinationFileName) {

		try{
			OutputStream outputStream = null;
			DFSClient client = null;
			try {
				client = new DFSClient(this.hdfsURI, conf);

				outputStream = client.create(destinationFileName, false);
				outputStream.write(content);
				client.close();

			} finally {
				if (outputStream != null)
					outputStream.close();

				if (client != null)
					client.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteFile(String sourceFileName) throws FileNotFoundException, IOException{
		DFSClient client = null;
		try {
			client = new DFSClient(this.hdfsURI, conf);
			if (!client.exists(sourceFileName))
				throw new FileNotFoundException();

			client.delete(sourceFileName);
		} finally {
			client.close();
		}
	}

}
