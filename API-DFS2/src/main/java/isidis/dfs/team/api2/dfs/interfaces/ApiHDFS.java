package isidis.dfs.team.api2.dfs.interfaces;

import isidis.dfs.team.api.dfs.common.interfaces.ApiGeneric;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author saad
 * Interface describes all methods which can be used to interact with HDFS EndPoint.
 * 	
 * Constraints:
 * This API can treat just big files which their size exceed 128Mo
 *
 */

public interface ApiHDFS extends ApiGeneric {
	/**
	 * Reading the file from HDFS with the path in param
	 * @param fileLocation
	 * the path/name of the file you want to read from HDFS
	 * @return
	 * RemoteIterator which can be used to iterate between file's parts
	 */
	RemoteIterator<byte[]> readFile(String fileLocation);
	
	/**
	 * Writing a file into HDFS
	 * @param file
	 * file refers to the local file which you want to save into your HDFS.
	 * @param destinationFileLocation
	 * The path where the file will be wrote on HDFS
	 * @return
	 * RemoteIterator which can be used to iterate to save all file's parts
	 * @throws IOException
	 */
	RemoteIterator<Void> writeFile(File file, String destinationFileLocation) throws IOException;
}
