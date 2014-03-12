package isidis.dfs.team.api1.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api.dfs.common.implementation.ApiGenericImpl;
import isidis.dfs.team.api.dfs.common.implementation.MyHdfsClient;
import isidis.dfs.team.api1.dfs.interfaces.ApiHDFS;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.hdfs.DFSInputStream;
import org.apache.hadoop.security.AccessControlException;
/***
 * @author saad
 */
public class ApiHDFSImpl extends ApiGenericImpl implements ApiHDFS{
	
	public static final Logger logger = Logger.getLogger(ApiHDFSImpl.class);
	/**
	 * Creating a HDFS Provider
	 * @throws URISyntaxException
	 * @throws EndpointNotReacheableException 
	 */
	public ApiHDFSImpl() throws URISyntaxException, EndpointNotReacheableException {
		super();
	}

	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeExceedsFixedThreshold {
		
		if (securityChecker.isNormalFile(sourceFileName)) 
			throw new FileSizeExceedsFixedThreshold();
		
		byte[] arr = new byte[(int)512L];
		DFSInputStream dfsInputStream = null;

		try {
			if (!myHdfsClient.getDFSClient().exists(sourceFileName)){ 
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();}

			dfsInputStream = myHdfsClient.getDFSClient().open(sourceFileName);
			dfsInputStream.read(arr, 0, (int)myHdfsClient.getBlockSizeInOctet());
			logger.log(Level.INFO,"File found and readed with success [" + sourceFileName + "]");
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
		
		if (content.length > myHdfsClient.getBlockSizeInOctet()) {
			logger.info(content.length + " > " + myHdfsClient.getBlockSizeInOctet());
			throw new FileSizeExceedsFixedThreshold();
		}
		OutputStream outputStream = null;
		try {
			outputStream = myHdfsClient.getDFSClient().create(destinationFileName, false);
			outputStream.write(content);
			logger.log(Level.INFO,"File wroten with success [" + destinationFileName + "]");
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

}
