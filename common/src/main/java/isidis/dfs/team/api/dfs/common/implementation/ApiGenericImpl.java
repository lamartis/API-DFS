package isidis.dfs.team.api.dfs.common.implementation;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.PathExistsException;
import org.apache.hadoop.fs.PathIsNotDirectoryException;
import org.apache.hadoop.hdfs.DFSClient;
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
	public ApiGenericImpl() {
		PropertyConfigurator.configure(ApiGenericImpl.class.getClassLoader().getResource("log4j.properties"));
		securityChecker = SecurityChecker.getInstance();
	}
	
	public MyHdfsClient getMyHdfsClient() {
		MyHdfsClient myHdfsClient = null;
		try {
			myHdfsClient = MyHdfsClient.getInstance();
		} catch (EndpointNotReacheableException e) {
			logger.log(Level.ERROR, "EndpointNotReacheableException reached");
		} catch (URISyntaxException e) {
			logger.log(Level.ERROR, "URISyntaxException reached");
		}
		return myHdfsClient;
	}
	
	public DFSClient getDFSClient() {
		return getMyHdfsClient().getDFSClient();
	}
	
	public SecurityChecker getSecurityChecker() {
		return securityChecker;
	}

	@Override
	public void delete(String sourceFileName, boolean recursive) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException{
		try {
			if (!getDFSClient().exists(sourceFileName)){
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();
			}
			getDFSClient().delete(sourceFileName, recursive);
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
	public HdfsFileStatus getFileInfo(String fileLocation) throws FileNotFoundException, EndpointNotReacheableException {
		HdfsFileStatus hdfsFileStatus = null;

		try {
			if (!getDFSClient().exists(fileLocation))
				throw new FileNotFoundException();

			hdfsFileStatus = getDFSClient().getFileInfo(fileLocation);
			logger.log(Level.INFO,"File info returned with success [" + fileLocation + "]");
		}catch (IOException e ) {
			logger.log(Level.ERROR,"End point not recheable Exception");
			throw new EndpointNotReacheableException();
		}
		return hdfsFileStatus;
	}

	@Override
	public void mkdirs(String absoluteDirectory) throws EndpointNotReacheableException, PathExistsException {
		try {
			if (getDFSClient().exists(absoluteDirectory))
				throw new PathExistsException(absoluteDirectory);
			
			getDFSClient().mkdirs(absoluteDirectory);
			logger.log(Level.INFO,"Path created with success");
		} catch (PathExistsException p) {
			logger.log(Level.ERROR,"Path already exists");
			throw new PathExistsException(absoluteDirectory);
	 	} catch (IOException e) {
			logger.log(Level.ERROR,"End point not recheable Exception");
			throw new EndpointNotReacheableException();
		}	

	}

	@Override
	public HdfsFileStatus[] listPaths(String path) throws PathIsNotDirectoryException, FileNotFoundException, EndpointNotReacheableException{	
		HdfsFileStatus[] files = null;

		try {
			if (!getDFSClient().exists(path))
				throw new FileNotFoundException();
				
			if (!getDFSClient().getFileInfo(path).isDir())
				throw new PathIsNotDirectoryException(path);

			files = getDFSClient().listPaths(path, HdfsFileStatus.EMPTY_NAME, true).getPartialListing();
			logger.log(Level.INFO,"All elements are returned with success");
		} catch (PathIsNotDirectoryException e) {
			logger.log(Level.ERROR,"Path is not a directory exception");
			throw new PathIsNotDirectoryException(path);
		} catch (IOException e) {
			logger.log(Level.ERROR,"End point not recheable Exception");
			throw new EndpointNotReacheableException();
		}
		return files;
	}

	@Override
	public long getRemainingCapacity() throws EndpointNotReacheableException {
		long remainingCapacity = 0;
		try {
			remainingCapacity = getDFSClient().getDiskStatus().getRemaining();
			logger.log(Level.INFO,"Remining Capacity is calculated with success");
		} catch (IOException e) {
			logger.log(Level.ERROR,"End point not recheable Exception");
			throw new EndpointNotReacheableException();
		}
		return remainingCapacity;
	}

	@Override 
	public void rename(String src, String dst) throws EndpointNotReacheableException, FileNotFoundException {
		try{
			if (!getDFSClient().exists(src))
				throw new FileNotFoundException();

			getDFSClient().rename(src, dst);
			logger.log(Level.INFO,"Rename action is executed with success");
		} catch (IOException e) {
			logger.log(Level.ERROR,"End point not recheable Exception");
			throw new EndpointNotReacheableException();
		}
	}

	@Override
	public void close() throws EndpointNotReacheableException {
		try {
			getDFSClient().close();
			logger.info("Connection is closed");
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
	}
}
