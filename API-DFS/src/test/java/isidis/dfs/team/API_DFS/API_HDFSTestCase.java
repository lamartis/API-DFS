package isidis.dfs.team.API_DFS;

import isidis.dfs.team.api.dfs.exceptions.*;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileAlreadyExistsException;

import static org.mockito.Mockito.*;
import junit.framework.Assert;
import junit.framework.TestCase;

public class API_HDFSTestCase extends TestCase {
	API_HDFS mockedapi = mock(API_HDFS.class);
	API_HDFS spyapi = spy(mockedapi);
	
	public void testWriteFile() {
		try {
			doReturn(null).when(spyapi).writeFile("This is a test".getBytes(), anyString());
			spyapi.writeFile("This is a test".getBytes(), "test.txt");
			verify(spyapi).writeFile("This is a test".getBytes(), "test.txt");
		} catch (SystemUserPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testReadFile() {
		try {
			when(spyapi.readFile("test.txt")).thenReturn("This is a test".getBytes());
			Assert.assertEquals("This is a test".getBytes(), spyapi.readFile( "test.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testDeleteFile() {
		try {
			doReturn(null).when(spyapi).deleteFile(anyString());
			spyapi.deleteFile("test.txt");
			verify(spyapi).deleteFile("test.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
