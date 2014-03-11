package isidis.dfs.team.API_DFS;

import isidis.dfs.team.api.dfs.exceptions.*;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

import java.io.IOException;

import org.apache.hadoop.fs.FileAlreadyExistsException;

import static org.mockito.Mockito.*;
import junit.framework.Assert;
import junit.framework.TestCase;

public class API_HDFSTestCase extends TestCase {
	API_HDFS spyapi = mock(API_HDFS.class);
	
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		} catch (FileSizeExceedsFixedThreshold e) {
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			e.printStackTrace();
		}
	}
}
