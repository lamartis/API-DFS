package isidis.dfs.team.api.dfs.common.implementation;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Logger;

/**
 *
 * @author saad
 * This class allows to keep one DFSClient instance on the life of the application.
 * It allows also to load basic properties like hdfsURL and systemUserName from config.properties
 *
 */

public class MyHdfsClient {
	
	public static MyHdfsClient myHdfsClient = null;
	/**
	 * Blocks will be splited into 200Ko.
	 */
	public Long Ko = (long) 200;
	public Long blockSizeInOctet = Ko * 1024;

	public static String hdfsURL = null;
	public static String systemUserName = null;

	public SecurityChecker securityChecker = null;
	public Configuration conf = null;
	public DFSClient client = null;
	public static Logger logger = Logger.getLogger(MyHdfsClient.class);

	private MyHdfsClient() throws EndpointNotReacheableException, URISyntaxException {

		/**
		 * Getting hdfsUrl & systemUserName from properties file.
		 */
		Properties properties = new Properties();
		URL url = MyHdfsClient.class.getClassLoader().getResource("config.properties");
		try {
			InputStream istream = url.openConnection().getInputStream();
			properties.load(istream);
		} catch (IOException e1) {
			logger.error("Error reading config.properties");
		}
		hdfsURL = properties.getProperty("hdfsURL");
		systemUserName = properties.getProperty("systemUserName");

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

	public static MyHdfsClient getInstance() throws EndpointNotReacheableException, URISyntaxException{
		if (myHdfsClient == null) {
			myHdfsClient = new MyHdfsClient();
			return myHdfsClient; 
		}
		return myHdfsClient; 
	}

	public DFSClient getDFSClient() {
		return client;
	}

	public long getBlockSizeInOctet() {
		return blockSizeInOctet;
	}


}
