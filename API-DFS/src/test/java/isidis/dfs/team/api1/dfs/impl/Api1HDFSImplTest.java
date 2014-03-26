package isidis.dfs.team.api1.dfs.impl;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.common.implementation.MyHdfsClient;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;
import isidis.dfs.team.api1.dfs.implementation.Api1HDFSImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import junit.framework.Assert;
import static org.mockito.Mockito.*;

import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSInputStream;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.junit.Before;
import org.junit.Test;

public class Api1HDFSImplTest {

	DFSClient client = mock(DFSClient.class);
	MyHdfsClient myHdfsClient = mock(MyHdfsClient.class);
	HdfsFileStatus hdfsFileStatus = mock(HdfsFileStatus.class);

	public Api1HDFSImpl toTest = null;
	public SecurityChecker securityChecker = mock(SecurityChecker.class);
	public DFSInputStream dfsInputStream = mock(DFSInputStream.class);

	public OutputStream outputStream = mock(OutputStream.class);
	
	@Before
	public void before() throws URISyntaxException, EndpointNotReacheableException {
		toTest = spy( new Api1HDFSImpl()); 
	}

	public void readFile() throws IOException, FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeThresholdNotRespected, URISyntaxException {
		/**
		 * Verifying if the returned array size is equals to the declared array size.
		 */
		when(client.exists(anyString())).thenReturn(true);
		doReturn(client).when(toTest).getDFSClient();

		when(securityChecker.isNormalFile(anyString())).thenReturn(true);
		doReturn(securityChecker).when(toTest).getSecurityChecker();

		when(securityChecker.getLen(anyString())).thenReturn((long)1111);

		when(client.open(anyString())).thenReturn(dfsInputStream);
		doReturn(myHdfsClient).when(toTest).getMyHdfsClient();
		when(myHdfsClient.getBlockSizeInOctet()).thenReturn((long) 111);

		byte[] arr = toTest.readFile(anyString());
		Assert.assertEquals(arr.length, 1111);

	}

	@Test
	public void writeFile() throws IOException, FileNotFoundException, EndpointNotReacheableException, SystemUserPermissionException, FileSizeThresholdNotRespected, URISyntaxException {
		/**
		 * Verifying if exception will be thrown when file size exceeds 10 * 1024 (Mo).
		 */
		doReturn(myHdfsClient).when(toTest).getMyHdfsClient();
		when(myHdfsClient.getBlockSizeInOctet()).thenReturn((long) 10 * 1024);

		try {
			toTest.writeFile(new byte[100000000], "sss");
			Assert.fail("Exception have to be thrown !!");
		} catch (FileSizeThresholdNotRespected e) {
			System.out.println("Good Job! ");
		}
		
		/**
		 * Testing a write method.
		 */
		doReturn(myHdfsClient).when(toTest).getMyHdfsClient();
		when(myHdfsClient.getBlockSizeInOctet()).thenReturn((long) 10 * 1024);
		
		when(client.create("sss", false)).thenReturn(outputStream);
		doReturn(client).when(toTest).getDFSClient();
		
		toTest.writeFile("6bytes".getBytes(), "fileLocation");

	}
}