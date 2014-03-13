package isidis.dfs.team.tools;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api1.dfs.implementation.Api1HDFSImpl;

import java.net.URISyntaxException;

public class DFSProvider {
	public static Api1HDFSImpl instance = null;

	public static Api1HDFSImpl getInstance() throws URISyntaxException, EndpointNotReacheableException{
		if (instance == null) {
			instance = new Api1HDFSImpl();
			return instance;
		}
		return instance;

	}

}
