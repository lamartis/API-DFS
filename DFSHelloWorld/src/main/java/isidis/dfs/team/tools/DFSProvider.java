package isidis.dfs.team.tools;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api1.dfs.implementation.ApiHDFSImpl;

import java.net.URISyntaxException;

public class DFSProvider {
	public static ApiHDFSImpl instance = null;

	public static ApiHDFSImpl getInstance() throws URISyntaxException, EndpointNotReacheableException{
		if (instance == null) {
			instance = new ApiHDFSImpl();
			return instance;
		}
		return instance;

	}

}
