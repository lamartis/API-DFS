package isidis.dfs.team.api.dfs.common.implementation;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.common.interfaces.ApiGeneric;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;

public class ApiGenericImpl implements ApiGeneric {

	public SecurityChecker securityChecker = null;
	public MyHdfsClient myHdfsClient = null;
	public static final Logger logger = Logger.getLogger(ApiGenericImpl.class);
	
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
