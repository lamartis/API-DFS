package isidis.dfs.team.DFSHelloWorld.api1;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.implementation.ApiGenericImpl;

public class Main3 {
	public static void main(String[] args) throws EndpointNotReacheableException, URISyntaxException, IOException {
		ApiGenericImpl api = new ApiGenericImpl();
		api.mkdirs("/user/s3ida");
		for (HdfsFileStatus f : api.listPaths("/user"))
			System.out.println(f.getLocalName());
	}
}
