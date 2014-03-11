package isidis.dfs.team.api1.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api.dfs.common.implementation.ApiGenericImpl;
import isidis.dfs.team.api.dfs.common.implementation.MyHdfsClient;
import isidis.dfs.team.api1.dfs.interfaces.ApiHDFS;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import org.apache.log4j.Level;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.hdfs.DFSInputStream;
import org.apache.hadoop.security.AccessControlException;
/***
 * @author saad
 */
public class ApiHDFSImpl extends ApiGenericImpl implements ApiHDFS{

	/**
	 * Creating a HDFS Provider
	 * @throws URISyntaxException
	 * @throws EndpointNotReacheableException 
	 */
	public ApiHDFSImpl() throws URISyntaxException, EndpointNotReacheableException {
		super();
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

}
