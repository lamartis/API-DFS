package isidis.dfs.team.api.dfs.tools;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
/**
 * 
 * @author saad
 *
 */

public class SecurityChecker {

	public static Logger logger = Logger.getLogger(SecurityChecker.class);

	private static SecurityChecker instance = null;
	
	private SecurityChecker() {
		instance = new SecurityChecker();
	}
	
	public static SecurityChecker getInstance(){
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

}
