package isidis.dfs.team.tools;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api1.dfs.implementation.Api1HDFSImpl;
import isidis.dfs.team.api1.dfs.interfaces.Api1HDFS;
import isidis.dfs.team.api2.dfs.implementation.Api2HDFSImpl;
import isidis.dfs.team.api2.dfs.interfaces.Api2HDFS;

import java.net.URISyntaxException;

public class DFSProvider {
	public static Api1HDFS instance1 = null;
	public static Api2HDFS instance2 = null;
	
	public static Api1HDFS getInstance1() throws URISyntaxException, EndpointNotReacheableException{
		if (instance1 == null) {
			instance1 = new Api1HDFSImpl();
			return instance1;
		}
		return instance1;
	}
	
	public static Api2HDFS getInstance2() throws URISyntaxException, EndpointNotReacheableException{
		if (instance2 == null) {
			instance2 = new Api2HDFSImpl();
			return instance2;
		}
		return instance2;
	}
}
