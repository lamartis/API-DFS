package isidis.dfs.team.api.dfs.common.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author saad
 * I have to replace hdfsURL, systemUserName to variables on propreties file.
 *
 */

public class MyHdfsClient {
	public static MyHdfsClient myHdfsClient = null;

	private final static String hdfsURL = "hdfs://192.168.0.41:9000/";
	private final static String systemUserName = "hduser";

	public SecurityChecker securityChecker = null;
	public Configuration conf = null;
	public static DFSClient client = null;
	public static Logger logger = Logger.getLogger(MyHdfsClient.class);
	
	private MyHdfsClient() throws EndpointNotReacheableException, URISyntaxException {
		
		securityChecker = SecurityChecker.getInstance();
		
		if (!securityChecker.urlSyntaxIsCorrect(hdfsURL))
			throw new URISyntaxException("", "");
		
		conf = new Configuration();
		conf.set("fs.defaultFS", hdfsURL);
		System.setProperty("HADOOP_USER_NAME", systemUserName);
		try {
			client = new DFSClient(conf);
			logger.info("New connection established to DFS");
			//System.out.println("New connection to DFS");
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}
	}
	
	public static DFSClient getInstance() throws EndpointNotReacheableException, URISyntaxException{
		if (client == null) {
			myHdfsClient = new MyHdfsClient();
			return client; 
		}
		return client; 
	}
	
}
