package isidis.dfs.team.api.dfs.common.implementation;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.PathIsNotDirectoryException;
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

	@Override
	public HdfsFileStatus getFileInfo(String fileLocation) throws FileNotFoundException, EndpointNotReacheableException {
		HdfsFileStatus hdfsFileStatus = null;

		try {
			if (!myHdfsClient.getDFSClient().exists(fileLocation))
				throw new FileNotFoundException();

			hdfsFileStatus = myHdfsClient.getDFSClient().getFileInfo(fileLocation);
			logger.log(Level.INFO,"File info returned with success [" + fileLocation + "]");
		}catch (IOException e ) {
			logger.log(Level.ERROR,"End point not recheable Exception");
			throw new EndpointNotReacheableException();
		}
		return hdfsFileStatus;
	}

	@Override
	public void mkdirs(String absoluteDirectory) throws EndpointNotReacheableException {
		try {
			myHdfsClient.getDFSClient().mkdirs(absoluteDirectory);
			logger.log(Level.INFO,"Path created with success");
		} catch (IOException e) {
			logger.log(Level.ERROR,"End point not recheable Exception");
			throw new EndpointNotReacheableException();
		}	

	}

	@Override
	public HdfsFileStatus[] listPaths(String path) throws PathIsNotDirectoryException, EndpointNotReacheableException{	
		HdfsFileStatus[] files = null;

		try {
			if (!myHdfsClient.getDFSClient().getFileInfo(path).isDir())
				throw new PathIsNotDirectoryException(path);

			files = myHdfsClient.getDFSClient().listPaths(path, HdfsFileStatus.EMPTY_NAME, true).getPartialListing();
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
			remainingCapacity = myHdfsClient.getDFSClient().getDiskStatus().getRemaining();
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
		return remainingCapacity;
	}

	@Override 
	public void rename(String src, String dst) throws EndpointNotReacheableException, FileNotFoundException {
		try{
			if (!myHdfsClient.getDFSClient().exists(src))
				throw new FileNotFoundException();

			myHdfsClient.getDFSClient().rename(src, dst);
		} catch (IOException e) {
			logger.log(Level.ERROR,"End point not recheable Exception");
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
