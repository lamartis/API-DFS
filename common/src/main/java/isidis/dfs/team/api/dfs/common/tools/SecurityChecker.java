package isidis.dfs.team.api.dfs.common.tools;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api.dfs.common.implementation.MyHdfsClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @author saad
 *
 */
public class SecurityChecker {

	public static Logger logger = Logger.getLogger(SecurityChecker.class);
	public long maximumThresholdForAPI1 = 128 * 1024 * 1024;
	
	public DFSClient client = null;
	private static SecurityChecker instance = null;

	private SecurityChecker() {
		try {
			client = MyHdfsClient.getInstance().getDFSClient();
		} catch (EndpointNotReacheableException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static SecurityChecker getInstance() throws EndpointNotReacheableException, URISyntaxException{
		if (instance == null) {
			instance = new SecurityChecker();
			return instance; 
		}
		return instance; 
	}

	public boolean urlSyntaxIsCorrect(String url) {
		URI hdfsURI = null;

		try {
			hdfsURI = new URI(url);
		} catch (URISyntaxException e) {
			return false;
		}

		if((hdfsURI.getHost() == null) || (hdfsURI.getPort()==-1) || (!hdfsURI.getScheme().equals("hdfs"))){
			logger.log(Level.ERROR, "URISyntaxException reached");
			return false;
		}

		return true;

	}

	public boolean isNormalFile(String sourceFileName) throws EndpointNotReacheableException {
		
		try {
			if (client.getFileInfo(sourceFileName).getLen() > maximumThresholdForAPI1)
				return false;
		} catch (IOException e) {
			throw new EndpointNotReacheableException();
		}

		return true;

	}


}
