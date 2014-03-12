package isidis.dfs.team.api.dfs.common.implementation;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.hdfs.DFSClient;
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
	public DFSClient client = null;
	public long fileLenght = -1;
	public static final Logger logger = Logger.getLogger(ApiGenericImpl.class);
	
	public ApiGenericImpl() throws EndpointNotReacheableException, URISyntaxException {
		PropertyConfigurator.configure(ApiGenericImpl.class.getClassLoader().getResource("log4j.properties"));
		securityChecker = SecurityChecker.getInstance();
		fileLenght = securityChecker.blockSizeInOctet;
		client = MyHdfsClient.getInstance();
	}
	
	@Override
	public void deleteFile(String sourceFileName) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException{
		try {
			if (!client.exists(sourceFileName)){
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();
			}
			client.delete(sourceFileName);
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
			client.close();
			logger.info("Connection is closed");
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
	}
}
