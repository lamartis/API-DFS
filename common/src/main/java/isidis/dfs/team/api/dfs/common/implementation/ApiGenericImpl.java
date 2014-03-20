package isidis.dfs.team.api.dfs.common.implementation;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.common.interfaces.ApiGeneric;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;

/**
 * 
 * @author saad
 * This class implements all common features between API1 & API2. Like delete() method or close() method.
 */
public class ApiGenericImpl implements ApiGeneric {

	public SecurityChecker securityChecker = null;
	public MyHdfsClient myHdfsClient = null;
	public static final Logger logger = Logger.getLogger(ApiGenericImpl.class);
	
	/**
	 * Constructor
	 * @throws EndpointNotReacheableException
	 * @throws URISyntaxException
	 */
	public ApiGenericImpl() throws EndpointNotReacheableException, URISyntaxException {
		PropertyConfigurator.configure(ApiGenericImpl.class.getClassLoader().getResource("log4j.properties"));
		myHdfsClient = MyHdfsClient.getInstance();
		
		securityChecker = SecurityChecker.getInstance();
	}
	
	@Override
	public void deleteFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException{
		try {
			if (!myHdfsClient.getDFSClient().exists(sourceFileName)){
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();
			}
			myHdfsClient.getDFSClient().delete(sourceFileName);
			logger.log(Level.INFO,"File deleted with success [" + sourceFileName + "]");
		} catch (RemoteException e){
			logger.log(Level.ERROR, "SystemUserPermissionException reached");
			throw new SystemUserPermissionException();
		} catch (IOException | IllegalArgumentException e) {
			logger.log(Level.ERROR, "EndpointNotReacheableException reached");
			throw new EndpointNotReacheableException();
		}
	}
	
	public HdfsFileStatus getFileInfo(String fileLocation) throws IOException {
		return myHdfsClient.getDFSClient().getFileInfo(fileLocation);
	}
	
	public long getRemainingCapacity() throws EndpointNotReacheableException {
		long remainingCapacity = 0;
		try {
			remainingCapacity = myHdfsClient.getDFSClient().getDiskStatus().getRemaining();
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
		return remainingCapacity;
	}
	
	@Override
	public void close() throws EndpointNotReacheableException {
		try {
			myHdfsClient.getDFSClient().close();
			logger.info("Connection is closed");
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
	}
}
