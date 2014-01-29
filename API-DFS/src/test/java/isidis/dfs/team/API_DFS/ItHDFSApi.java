package isidis.dfs.team.API_DFS;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import isidis.dfs.team.api.dfs.exceptions.*;

import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;

import isidis.dfs.team.api.dfs.implementation.API_HDFS_Impl;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

public class ItHDFSApi {

	@Test
	public void testAPI_HDFS_Impl() {
		String hdfsURL = "hdfs://192.168.0.41:9000/";
		String systemUserName = "hduser";
		try {
			API_HDFS IT_api = new API_HDFS_Impl(hdfsURL, systemUserName);
			IT_api.writeFile("This is a test".getBytes(), "test.txt");
			IT_api.readFile("test.txt");
			IT_api.deleteFile("test.txt");
			IT_api.readFile("test.txt");
		} catch (URISyntaxException e) {
			Assert.fail("URISyntaxException");
		} catch (SystemUserPermissionException e) {
			Assert.fail("SystemUserPermissionException");
		} catch (FileAlreadyExistsException e) {
			Assert.fail("FileAlreadyExistsException");
		} catch (EndpointNotReacheableException e) {
			Assert.fail("EndpointNotReacheableException");
		} catch (FileNotFoundException e) {
			Assert.fail("FileNotFoundException");
		}
		
	}

}
