package isidis.dfs.team.API_DFS;

import java.io.IOException;
import java.net.URISyntaxException;

import isidis.dfs.team.API_DFS.exceptions.FileNotFoundException;
import isidis.dfs.team.API_DFS.implementation.API_HDFS_Impl;
import isidis.dfs.team.API_DFS.interfaces.API_HDFS;
import junit.framework.Assert;
import junit.framework.TestCase;

public class API_HDFSTestCase extends TestCase {
	public final static String hdfsURL = "hdfs://192.168.217.128:9000";
	public final static String systemUserName = "hduser";
	
	public void testWriteFile() {
		try {
			API_HDFS_Impl apihdfs = new API_HDFS_Impl(hdfsURL, systemUserName);
			apihdfs.writeFile("This is a test".getBytes(), "test.txt");
			Assert.fail("Le test est complet");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testWriteFileFail() {
		try {
			API_HDFS_Impl apihdfs = new API_HDFS_Impl(hdfsURL, systemUserName);
			apihdfs.writeFile("This is a test".getBytes(), "test");
			Assert.fail("Le test est complet");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testReadFile() {
		try {
			API_HDFS_Impl apihdfs = new API_HDFS_Impl(hdfsURL, systemUserName);
			Assert.assertEquals("This is a test".getBytes(), apihdfs.readFile("test.txt"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testReadFileFail() {
		try {
			API_HDFS_Impl apihdfs = new API_HDFS_Impl(hdfsURL, systemUserName);
			Assert.assertEquals("This is a test".getBytes(), apihdfs.readFile("testfile.txt"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testDeleteFile() {
		try {
			API_HDFS_Impl apihdfs = new API_HDFS_Impl(hdfsURL, systemUserName);
			apihdfs.deleteFile("test.txt");
			Assert.fail("Le test est complet");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testDeleteFileFail() {
		try {
			API_HDFS_Impl apihdfs = new API_HDFS_Impl(hdfsURL, systemUserName);
			apihdfs.deleteFile("testfile.txt");
			Assert.fail("Le test est complet");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
