package isidis.dfs.team.api1.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api.dfs.common.implementation.ApiGenericImpl;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;
import isidis.dfs.team.api1.dfs.interfaces.Api1HDFS;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.hdfs.DFSInputStream;
import org.apache.hadoop.security.AccessControlException;
/***
 * @author saad
 * @see
 * This class implements all methods which are defined on Api1HDFS interface.
 */
public class Api1HDFSImpl extends ApiGenericImpl implements Api1HDFS{
	
	public static final Logger logger = Logger.getLogger(Api1HDFSImpl.class);
	/**
	 * The constructor of this class
	 * @throws URISyntaxException
	 * @throws EndpointNotReacheableException 
	 */
	public Api1HDFSImpl() {
		super();
	}

	@Override
	public byte[] readFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeThresholdNotRespected {
		
		byte[] arr = null;
		DFSInputStream dfsInputStream = null;

		try {
			if (!getDFSClient().exists(sourceFileName)){ 
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();
			}
			
			if (!getSecurityChecker().isNormalFile(sourceFileName)) {
				logger.log(Level.ERROR, "FileSizeExceedsFixedThreshold reached");
				throw new FileSizeThresholdNotRespected();
			}
			
			arr = new byte[(int) getSecurityChecker().getLen(sourceFileName)];
			dfsInputStream = getDFSClient().open(sourceFileName);
			dfsInputStream.read(arr, 0, (int)getMyHdfsClient().getBlockSizeInOctet());
			logger.log(Level.INFO,"File found and readed with success [" + sourceFileName + "] [Size: " + getSecurityChecker().getLen(sourceFileName) + "]");
		} catch (AccessControlException e){
			logger.log(Level.ERROR, "SystemUserPermissionException reached");
			throw new SystemUserPermissionException();
		} catch (IOException |IllegalArgumentException | URISyntaxException e) {
			logger.log(Level.ERROR, "EndpointNotReacheableException / IOException / URISyntaxException reached");
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

	@Override
	public void writeFile(byte[] content, String destinationFileName) throws SystemUserPermissionException, EndpointNotReacheableException, FileAlreadyExistsException, FileSizeThresholdNotRespected {
		
		if (content.length > getSecurityChecker().maximumThresholdForAPI1) {
			logger.error(content.length + " > " + getMyHdfsClient().getBlockSizeInOctet());
			throw new FileSizeThresholdNotRespected();
		}
		
		OutputStream outputStream = null;
		try {
			outputStream = getDFSClient().create(destinationFileName, false);
			if (outputStream == null)
				System.out.println("=null");
			outputStream.write(content);
			logger.log(Level.INFO,"File wroten with success [" + destinationFileName + "] [Size: " + content.length + "]");
		} catch (FileAlreadyExistsException e){
			logger.log(Level.ERROR, "FileAlreadyExistsException reached");
			throw new FileAlreadyExistsException();
		} catch (AccessControlException e){
			logger.log(Level.ERROR, "SystemUserPermissionException reached");
			throw new SystemUserPermissionException();
		} catch (IOException | IllegalArgumentException e) {
			logger.log(Level.ERROR, "EndpointNotReacheableException reached");
			e.getStackTrace();
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
