package isidis.dfs.team.api1.dfs;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api1.dfs.interfaces.Api1HDFS;

import java.io.IOException;

import org.apache.hadoop.fs.FileAlreadyExistsException;

import static org.mockito.Mockito.*;
import junit.framework.Assert;
import junit.framework.TestCase;

public class API_HDFSTestCase {
	Api1HDFS spyapi = mock(Api1HDFS.class);
	
	public void testWriteFile() {
		try {
			spyapi.writeFile("This is a test".getBytes(), "test.txt");
			verify(spyapi).writeFile("This is a test".getBytes(), "test.txt");
		} catch (SystemUserPermissionException e) {
			e.printStackTrace();
		} catch (FileAlreadyExistsException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (FileSizeExceedsFixedThreshold e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testReadFile() {
		try {
			when(spyapi.readFile("test.txt")).thenReturn("This is a test".getBytes());
			Assert.assertTrue("This is a test".equals(new String(spyapi.readFile( "test.txt")).trim()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (FileSizeExceedsFixedThreshold e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemUserPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testDeleteFile() {
		try {
			spyapi.deleteFile("test.txt");
			verify(spyapi).deleteFile("test.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (SystemUserPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
