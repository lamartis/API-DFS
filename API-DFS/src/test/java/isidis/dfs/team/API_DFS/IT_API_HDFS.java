package isidis.dfs.team.API_DFS;

import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileAlreadyExistsException;

import isidis.dfs.team.api.dfs.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.implementation.API_HDFS_Impl;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

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
			API_HDFS IT_api = new API_HDFS_Impl();
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
		}
		return rep;
	}
}
