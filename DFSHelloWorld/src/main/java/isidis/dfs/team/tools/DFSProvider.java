package isidis.dfs.team.tools;

import java.net.URISyntaxException;
import isidis.dfs.team.api.dfs.implementation.API_HDFS_Impl;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

public class DFSProvider {
	public static API_HDFS_Impl instance = null;

	public static API_HDFS_Impl getInstance(String hdfsURL, String user) throws URISyntaxException{
		instance = new API_HDFS_Impl(hdfsURL, user);
		return instance;

	}

	public static API_HDFS_Impl getInstance(){
		return instance;
	}
}
