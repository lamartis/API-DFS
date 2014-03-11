package isidis.dfs.team.api.dfs.common.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api.dfs.tools.SecurityChecker;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSClient;

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

	private SecurityChecker securityChecker = SecurityChecker.getInstance();
	private Configuration conf = null;
	public static DFSClient client = null;
	
	private MyHdfsClient() throws EndpointNotReacheableException, URISyntaxException {
		if (!securityChecker.urlSyntaxIsCorrect(hdfsURL))
			throw new URISyntaxException("", "");
		
		conf = new Configuration();
		conf.set("fs.defaultFS", hdfsURL);
		System.setProperty("HADOOP_USER_NAME", systemUserName);
		try {
			client = new DFSClient(conf);
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
