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

	public final static String fileToRead = "/user/trauma";
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
			Assert.fail("");
		} catch (URISyntaxException e) {
			Assert.fail("");
		}


		/**
		 * Reading file.
		 */
		RemoteIterator<byte[]> remoteIterator2 = null;
		try {
			remoteIterator2 = api2.readFile(0, fileToRead);
		} catch (FileNotFoundException e1) {
			Assert.fail("");
		} catch (EndpointNotReacheableException e1) {
			Assert.fail("");
		} catch (SystemUserPermissionException e1) {
			Assert.fail("");
		} catch (FileSizeThresholdNotRespected e1) {
			Assert.fail("");
		}

		/**
		 * Getting file blocks number:
		 * long blocksNumber = remoteIterator2.getNumberOfBlocks();
		 * This can be useful to calculate downloading progress for example.
		 */
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("trauma"));
		} catch (java.io.FileNotFoundException e1) {
			Assert.fail("");
		}

		try {
			while (remoteIterator2.hasNext()) {
				fos.write(remoteIterator2.next());
			}
			fos.close();
		} catch (IOException e1) {
			Assert.fail("");
		}
		
		
		/**
		 * Writing file.
		 */

		RemoteIterator<Void> remoteIterator = null;
		try {
			remoteIterator = api2.writeFile(new File("trauma"), 0, destionationFile);

		} catch (SystemUserPermissionException e) {
			Assert.fail("");
		} catch (EndpointNotReacheableException e) {
			Assert.fail("");
		} catch (FileSizeThresholdNotRespected e) {
			Assert.fail("");
		} catch (FileAlreadyExistsException e) {
			Assert.fail("");
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
			Assert.fail("");
		}

		

		/**
		 * Deleting file.
		 */
		try {
			api2.delete(destionationFile, false);
		} catch (FileNotFoundException e) {
			Assert.fail("");
		} catch (EndpointNotReacheableException e) {
			Assert.fail("");
		} catch (SystemUserPermissionException e) {
			Assert.fail("");
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
			Assert.fail("");
		} catch (SystemUserPermissionException e1) {
			Assert.fail("");
		} catch (FileSizeThresholdNotRespected e1) {
			Assert.fail("");
		}
		
		

		/**
		 * Closing connection.
		 */
		try {
			api2.close();
		} catch (EndpointNotReacheableException e) {
			Assert.fail("");
		}
	}
}
