package isidis.dfs.team.api1.dfs;

import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileAlreadyExistsException;

import isidis.dfs.team.api.dfs.common.exceptions.*;
import isidis.dfs.team.api1.dfs.implementation.ApiHDFSImpl;
import isidis.dfs.team.api1.dfs.interfaces.ApiHDFS;

public class IT_API_HDFS {

	public static void main(String[] args) {
		if(check_IT())
			System.out.println("Integration succeful");
		else
			System.out.println("Integration failed");
	}
	public static boolean check_IT()
	{
		boolean rep =  false;
		try {
			ApiHDFS IT_api = new ApiHDFSImpl();
			IT_api.writeFile("This is a test".getBytes(), "test.txt");
			IT_api.readFile("test.txt");
			IT_api.deleteFile("test.txt");
			if(IT_api.readFile("test.txt") == null)
				rep = true;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemUserPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EndpointNotReacheableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileSizeExceedsFixedThreshold e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rep;
	}
}
