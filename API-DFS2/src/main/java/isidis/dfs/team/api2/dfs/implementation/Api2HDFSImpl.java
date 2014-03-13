package isidis.dfs.team.api2.dfs.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.common.implementation.ApiGenericImpl;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;
import isidis.dfs.team.api2.dfs.interfaces.Api2HDFS;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Api2HDFSImpl extends ApiGenericImpl implements Api2HDFS {

	public static final Logger logger = Logger.getLogger(Api2HDFSImpl.class);

	public Api2HDFSImpl() throws EndpointNotReacheableException, URISyntaxException {
		super();
	}

	public RemoteIterator<byte[]> readFile(String fileLocation) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeThresholdNotRespected {
		RemoteIterator<byte[]> remoteIterator = null;

		/**
		 * Verification system.
		 */
		
		try {
			if (!myHdfsClient.getDFSClient().exists(fileLocation)){ 
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();
			}

			if (securityChecker.isNormalFile(fileLocation)) {
				logger.log(Level.ERROR, "FileSizeExceedsFixedThreshold reached");
				throw new FileSizeThresholdNotRespected();
			}

			remoteIterator = new RemoteIteratorReader(fileLocation);
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
		return remoteIterator;
	}

	public RemoteIterator<Void> writeFile(File file, String destinationFileLocation) throws FileAlreadyExistsException, EndpointNotReacheableException, FileSizeThresholdNotRespected, SystemUserPermissionException {

		/**
		 * Verification system.
		 */
		RemoteIterator<Void> remoteIterator = null;

		try {

			if (myHdfsClient.getDFSClient().exists(destinationFileLocation)) { 
				logger.log(Level.ERROR, "File Already existed reached");
				throw new FileAlreadyExistsException();
			}

			if (file.length() < SecurityChecker.maximumThresholdForAPI1) {
				logger.log(Level.ERROR, "FileSizeThresholdNotRespected reached");
				throw new FileSizeThresholdNotRespected();
			}

			/**
			 * Instantiation of distributed iterator which save each block on the OutputStream object.
			 */

			remoteIterator = new RemoteIteratorWriter(file, destinationFileLocation);
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}

		return remoteIterator;
	}

}
