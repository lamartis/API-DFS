package isidis.dfs.team.api.dfs2.implementation;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSClient;

/**
 *
 * @author dfs-one
 * I have to replace hdfsURL, systemUserName to variables on propreties file.
 *
 */

public class MyHdfsClient {
	public static MyHdfsClient myHdfsClient = null;
	
	private final static String hdfsURL = "hdfs://192.168.0.41:9000/";
	private final static String systemUserName = "hduser";
	
	private Configuration conf = null;
	public static DFSClient client = null;
	
	private MyHdfsClient() {
		conf = new Configuration();
		conf.set("fs.defaultFS", hdfsURL);
		System.setProperty("HADOOP_USER_NAME", systemUserName);
		try {
			client = new DFSClient(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DFSClient getInstance(){
		if (client == null) {
			myHdfsClient = new MyHdfsClient();
			return client; 
		}
		return client; 
	}
}
