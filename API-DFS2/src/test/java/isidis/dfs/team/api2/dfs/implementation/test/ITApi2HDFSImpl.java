package isidis.dfs.team.api2.dfs.implementation.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.junit.Test;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api2.dfs.implementation.Api2HDFSImpl;
import isidis.dfs.team.api2.dfs.interfaces.Api2HDFS;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;

public class ITApi2HDFSImpl {

	public final static String fileToRead = "/user2/beck";
	public final static String destionationFile = "/user/trauma2";
	
	
	@Test
	public void testIT() {

		/**
		 * Getting new instance.
		 */
		Api2HDFS api2 = null;
		try {
			api2 = new Api2HDFSImpl();
		} catch (EndpointNotReacheableException e) {
			Assert.fail("EndpointNotReacheableException");
		} catch (URISyntaxException e) {
			Assert.fail("URISyntaxException");
		}


		/**
		 * Reading file.
		 */
		RemoteIterator<byte[]> remoteIterator2 = null;
		try {
			remoteIterator2 = api2.readFile(0, fileToRead);
		} catch (FileNotFoundException e1) {
			Assert.fail("FileNotFoundException");
		} catch (EndpointNotReacheableException e1) {
			Assert.fail("EndpointNotReacheableException");
		} catch (SystemUserPermissionException e1) {
			Assert.fail("SystemUserPermissionException");
		} catch (FileSizeThresholdNotRespected e1) {
			Assert.fail("FileSizeThresholdNotRespected");
		}

		/**
		 * Getting file blocks number:
		 * long blocksNumber = remoteIterator2.getNumberOfBlocks();
		 * This can be useful to calculate downloading progress for example.
		 */
		
		FileOutputStream fos = null;
		File file = new File("trauma");
		try {
			fos = new FileOutputStream(file);
		} catch (java.io.FileNotFoundException e1) {
			Assert.fail("FileNotFoundException");
		}

		try {
			while (remoteIterator2.hasNext()) {
				fos.write(remoteIterator2.next());
			}
			fos.close();
		} catch (IOException e1) {
			Assert.fail("IOException");
		}
		
		
		/**
		 * Writing file.
		 */

		RemoteIterator<Void> remoteIterator = null;
		try {
			remoteIterator = api2.writeFile(new File("trauma"), 0, destionationFile);

		} catch (SystemUserPermissionException e) {
			Assert.fail("SystemUserPermissionException");
		} catch (EndpointNotReacheableException e) {
			Assert.fail("EndpointNotReacheableException");
		} catch (FileSizeThresholdNotRespected e) {
			Assert.fail("FileSizeThresholdNotRespected");
		} catch (FileAlreadyExistsException e) {
			Assert.fail("FileAlreadyExistsException");
		}

		/**
		 * Getting file blocks number:
		 * long blocksNumber = remoteIterator.getNumberOfBlocks();
		 * This can be useful to calculate downloading progress for example.
		 */
		
		try {
			while (remoteIterator.hasNext()) {
				remoteIterator.next();
			}
		} catch (IOException e) {
			Assert.fail("IOException");
		}

		

		/**
		 * Deleting file.
		 */
		try {
			api2.delete(destionationFile, false);
		} catch (FileNotFoundException e) {
			Assert.fail("FileNotFoundException");
		} catch (EndpointNotReacheableException e) {
			Assert.fail("EndpointNotReacheableException");
		} catch (SystemUserPermissionException e) {
			Assert.fail("SystemUserPermissionException");
		}
		
		/**
		 * Reading file.
		 */
		remoteIterator2 = null;
		try {
			remoteIterator2 = api2.readFile(0, fileToRead);
		} catch (FileNotFoundException e1) {
			System.out.println("TEST OK");
		} catch (EndpointNotReacheableException e1) {
			Assert.fail("EndpointNotReacheableException");
		} catch (SystemUserPermissionException e1) {
			Assert.fail("SystemUserPermissionException");
		} catch (FileSizeThresholdNotRespected e1) {
			Assert.fail("FileSizeThresholdNotRespected");
		}
		
		

		/**
		 * Closing connection.
		 */
		try {
			api2.close();
		} catch (EndpointNotReacheableException e) {
			Assert.fail("EndpointNotReacheableException");
		}
		
		/**
		 * Delete file from localy
		 */
		System.out.println("Deleted from localy : " + file.delete());
	}
}
