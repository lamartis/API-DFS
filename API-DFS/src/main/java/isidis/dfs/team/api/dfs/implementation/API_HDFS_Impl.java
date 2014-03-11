package isidis.dfs.team.api.dfs.implementation;

import isidis.dfs.team.api.dfs.exceptions.*;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;
import isidis.dfs.team.api.dfs.tools.SecurityChecker;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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

	private SecurityChecker securityChecker = SecurityChecker.getInstance();
	private long fileLenght = securityChecker.blockSizeInOctet;
	DFSClient client = null;
	private static final Logger logger = Logger.getLogger(API_HDFS_Impl.class);
	
	/**
	 * Creating a HDFS Provider
	 * @throws URISyntaxException
	 * @throws EndpointNotReacheableException 
	 */
	public API_HDFS_Impl() throws URISyntaxException, EndpointNotReacheableException {
		client = MyHdfsClient.getInstance();
	}

	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeExceedsFixedThreshold {
		
		if (securityChecker.isNormalFile(sourceFileName)) 
			throw new FileSizeExceedsFixedThreshold();
		
		byte[] arr = new byte[(int)fileLenght];
		DFSInputStream dfsInputStream = null;

		try {
			if (!client.exists(sourceFileName)){ 
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();}

			dfsInputStream = client.open(sourceFileName);
			dfsInputStream.read(arr, 0, (int)fileLenght);
			logger.log(Level.INFO,"File found and readed with success");
			
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
			} catch (IOException e) {
				logger.log(Level.ERROR, "EndpointNotReacheableException reached");
				throw new EndpointNotReacheableException();
			}
		}

		return arr;
	}


	public void writeFile(byte[] content, String destinationFileName) throws SystemUserPermissionException, EndpointNotReacheableException, FileAlreadyExistsException, FileSizeExceedsFixedThreshold {
		
		if (content.length < securityChecker.blockSizeInOctet) 
			throw new FileSizeExceedsFixedThreshold();
		
		OutputStream outputStream = null;
		try {
			outputStream = client.create(destinationFileName, false);
			outputStream.write(content);
			logger.log(Level.INFO,"File wroten with success");
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
			} catch (IOException e) {
				logger.log(Level.ERROR, "EndpointNotReacheableException reached");
				throw new EndpointNotReacheableException();
			}
		}


	}


	public void deleteFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException{
		try {
			if (!client.exists(sourceFileName)){
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();
			}
			client.delete(sourceFileName);
			logger.log(Level.INFO,"File deleted with success");
		} catch (RemoteException e){
			logger.log(Level.ERROR, "SystemUserPermissionException reached");
			throw new SystemUserPermissionException();
		} catch (IOException | IllegalArgumentException e) {
			logger.log(Level.ERROR, "EndpointNotReacheableException reached");
			throw new EndpointNotReacheableException();
		}
	}


	@Override
	public void close() throws EndpointNotReacheableException {
		try {
			client.close();
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
	}



}
