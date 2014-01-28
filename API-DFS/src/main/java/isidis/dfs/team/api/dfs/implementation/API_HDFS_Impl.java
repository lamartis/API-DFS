package isidis.dfs.team.api.dfs.implementation;

import isidis.dfs.team.api.dfs.exceptions.*;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
	private static final Logger logger = Logger.getLogger(API_HDFS_Impl.class);
	/**
	 * Creating a HDFS Provider
	 * @param hdfsURL
	 * URL of the HDFS targeted
	 * @param systemUserName
	 * Name of User in the HDFS
	 * @throws URISyntaxException
	 */
	public API_HDFS_Impl(String hdfsURL, String systemUserName) throws URISyntaxException {

		this.hdfsURI = new URI(hdfsURL);
		if((this.hdfsURI.getHost() == null) || (this.hdfsURI.getPort()==-1) || (!this.hdfsURI.getScheme().equals("hdfs"))){
			logger.log(Level.ERROR, "URISyntaxException reached");
			throw new URISyntaxException("", "");}

		conf = new Configuration();
		conf.set("fs.defaultFS", hdfsURL);
		System.setProperty("HADOOP_USER_NAME", systemUserName);
		logger.log(Level.INFO,"Connection to the HDFS successful");
	}


	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException {
		byte[] arr = new byte[(int)fileLenght];
		DFSInputStream dfsInputStream = null;

		try {
			client = new DFSClient(this.hdfsURI, conf);
			if (!client.exists(sourceFileName)){ 
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();}

			dfsInputStream = client.open(sourceFileName);
			dfsInputStream.read(arr, 0, (int)fileLenght);
			logger.log(Level.INFO,"File found");
		} catch (AccessControlException e){
			logger.log(Level.ERROR, "SystemUserPermissionException reached");
			throw new SystemUserPermissionException();
		} catch (IOException |IllegalArgumentException e) {
			logger.log(Level.ERROR, "EndpointNotReacheableException reached");
			throw new EndpointNotReacheableException();
		} finally {
			try {
				if (dfsInputStream != null)
					dfsInputStream.close();

				if (client != null)
					client.close();
			} catch (IOException e) {
				logger.log(Level.ERROR, "EndpointNotReacheableException reached");
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
			logger.log(Level.INFO,"File wrote");
		} catch (FileAlreadyExistsException e){
			logger.log(Level.ERROR, "FileAlreadyExistsException reached");
			throw new FileAlreadyExistsException();
		} catch (AccessControlException e){
			logger.log(Level.ERROR, "SystemUserPermissionException reached");
			throw new SystemUserPermissionException();
		} catch (IOException | IllegalArgumentException e) {
			logger.log(Level.ERROR, "EndpointNotReacheableException reached");
			throw new EndpointNotReacheableException();
		} finally {
			try {
				if (outputStream != null)
					outputStream.close();

				if (client != null)
					client.close();
			} catch (IOException e) {
				logger.log(Level.ERROR, "EndpointNotReacheableException reached");
				throw new EndpointNotReacheableException();
			}
		}


	}


	public void deleteFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException{
		try {
			client = new DFSClient(this.hdfsURI, conf);
			if (!client.exists(sourceFileName)){
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();
			}
			client.delete(sourceFileName);
			logger.log(Level.INFO,"File deleted");
		} catch (RemoteException e){
			logger.log(Level.ERROR, "SystemUserPermissionException reached");
			throw new SystemUserPermissionException();
		} catch (IOException | IllegalArgumentException e) {
			logger.log(Level.ERROR, "EndpointNotReacheableException reached");
			throw new EndpointNotReacheableException();
		} finally {
			try{
				if (client != null)
					client.close();
			} catch (IOException e) {
				logger.log(Level.ERROR, "EndpointNotReacheableException reached");
				throw new EndpointNotReacheableException();
			}
		}
	}



}
