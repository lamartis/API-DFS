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
import org.apache.log4j.pattern.FileLocationPatternConverter;

/**
 * 
 * @author saad
 * @see
 * This class implements all methods exposed on Api2HDFS, and Heritates from ApiGenericImpl.
 * 
 */
public class Api2HDFSImpl extends ApiGenericImpl implements Api2HDFS {

	public static final Logger logger = Logger.getLogger(Api2HDFSImpl.class);

	public Api2HDFSImpl() throws EndpointNotReacheableException, URISyntaxException {
		super();
	}

	public RemoteIterator<byte[]> readFile(long bytesAlreadyReceived, String fileLocation) throws FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeThresholdNotRespected {
		RemoteIterator<byte[]> remoteIterator = null;

		/**
		 * Verification system.
		 */

		try {
			if (!getDFSClient().exists(fileLocation)){ 
				logger.log(Level.ERROR, "FileNotFoundException reached");
				throw new FileNotFoundException();
			}

			if (getSecurityChecker().isNormalFile(fileLocation)) {
				logger.log(Level.ERROR, "FileSizeExceedsFixedThreshold reached");
				throw new FileSizeThresholdNotRespected();
			}

			remoteIterator = getRIR(bytesAlreadyReceived, fileLocation);
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
		return remoteIterator;
	}

	public RemoteIterator<Void> writeFile(File file, long bytesAlreadySended, String destinationFileLocation) throws FileAlreadyExistsException, EndpointNotReacheableException, FileSizeThresholdNotRespected, SystemUserPermissionException {

		/**
		 * Verification system.
		 */
		RemoteIterator<Void> remoteIterator = null;

		try {

			if (getDFSClient().exists(destinationFileLocation)) { 
				logger.log(Level.ERROR, "File Already existed reached");
				throw new FileAlreadyExistsException();
			}

			if (file.length() < getSecurityChecker().maximumThresholdForAPI1) {
				logger.log(Level.ERROR, "FileSizeThresholdNotRespected reached");
				throw new FileSizeThresholdNotRespected();
			}

			/**
			 * Instantiation of distributed iterator which save each block on the OutputStream object.
			 */

			remoteIterator = getRIW(file, bytesAlreadySended, destinationFileLocation);
		} catch (FileAlreadyExistsException e) {
			throw new FileAlreadyExistsException();
		} catch (FileSizeThresholdNotRespected e) {
			throw new FileSizeThresholdNotRespected();
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}

		return remoteIterator;
	}

	public RemoteIteratorReader getRIR(long bytesAlreadyReceived, String FileLocation) throws IOException, EndpointNotReacheableException, SystemUserPermissionException {
		return new RemoteIteratorReader(bytesAlreadyReceived, FileLocation);
	}
	
	public RemoteIteratorWriter getRIW(File file, long bytesAlreadySended, String destinationFileLocation) throws IOException, EndpointNotReacheableException, SystemUserPermissionException {
		return new RemoteIteratorWriter(file, bytesAlreadySended, destinationFileLocation);
	}

}
