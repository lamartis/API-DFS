package isidis.dfs.team.api.dfs2.main;

import isidis.dfs.team.api.dfs.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.exceptions.FileSizeExceedsFixedThreshold;
import isidis.dfs.team.api.dfs.implementation.API_HDFS_Impl;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;

import org.apache.hadoop.fs.RemoteIterator;

/**
 * 
 * @author dfs-one
 *
 */

public class Main {
    
	public static void main(String[] args) throws IOException, URISyntaxException, FileNotFoundException, EndpointNotReacheableException, FileSizeExceedsFixedThreshold{
		API_HDFS api = new API_HDFS_Impl();
		long start = System.currentTimeMillis();
		api.readFile("/user/beck");
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		System.out.println("OK | Realized: " + elapsedTimeMillis/1000F + "s");
	}
}
