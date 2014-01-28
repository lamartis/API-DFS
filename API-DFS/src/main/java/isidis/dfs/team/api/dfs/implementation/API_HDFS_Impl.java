package isidis.dfs.team.api.dfs.implementation;

import isidis.dfs.team.api.dfs.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSInputStream;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.security.AccessControlException;
/***
 * 
 * @author saad
 * 
 */
public class API_HDFS_Impl implements API_HDFS{

	private URI hdfsURI = null;
	private long fileLenght = 512L;
	Configuration conf = null;
	DFSClient client = null;

	public API_HDFS_Impl(String hdfsURL, String systemUserName) throws URISyntaxException {

		this.hdfsURI = new URI(hdfsURL);
		if((this.hdfsURI.getHost() == null) || (this.hdfsURI.getPort()==-1) || (!this.hdfsURI.getScheme().equals("hdfs")))
			throw new URISyntaxException("", "");

		conf = new Configuration();
		conf.set("fs.defaultFS", hdfsURL);
		System.setProperty("HADOOP_USER_NAME", systemUserName);
	}


	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException {
		byte[] arr = new byte[(int)fileLenght];
		DFSInputStream dfsInputStream = null;

		try {
			client = new DFSClient(this.hdfsURI, conf);

			if (!client.exists(sourceFileName)) 
				throw new FileNotFoundException();

			dfsInputStream = client.open(sourceFileName);
			dfsInputStream.read(arr, 0, (int)fileLenght);
		} catch (AccessControlException e){
			throw new SystemUserPermissionException();
		} catch (IOException |IllegalArgumentException e) {
			throw new EndpointNotReacheableException();
		} finally {
			try {
				if (dfsInputStream != null)
					dfsInputStream.close();

				if (client != null)
					client.close();
			} catch (IOException e) {
				throw new EndpointNotReacheableException();
			}
		}

		return arr;
	}


	public void writeFile(byte[] content, String destinationFileName) throws SystemUserPermissionException, EndpointNotReacheableException, FileAlreadyExistsException {
		OutputStream outputStream = null;
		try {
			client = new DFSClient(this.hdfsURI, conf);

			outputStream = client.create(destinationFileName, false);
			outputStream.write(content);

		} catch (FileAlreadyExistsException e){
			throw new FileAlreadyExistsException();
		} catch (AccessControlException e){
			throw new SystemUserPermissionException();
		} catch (IOException | IllegalArgumentException e) {
			throw new EndpointNotReacheableException();
		} finally {
			try {
				if (outputStream != null)
					outputStream.close();

				if (client != null)
					client.close();
			} catch (IOException e) {
				throw new EndpointNotReacheableException();
			}
		}


	}


	public void deleteFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException{
		try {
			client = new DFSClient(this.hdfsURI, conf);
			if (!client.exists(sourceFileName))
				throw new FileNotFoundException();

			client.delete(sourceFileName);
		} catch (RemoteException e){
			throw new SystemUserPermissionException();
		} catch (IOException | IllegalArgumentException e) {
			throw new EndpointNotReacheableException();
		} finally {
			try{
				if (client != null)
					client.close();
			} catch (IOException e) {
				throw new EndpointNotReacheableException();
			}
		}
	}



}
