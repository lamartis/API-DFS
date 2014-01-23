package isidis.dfs.team.DFSHelloWorld;

import java.io.IOException;
import java.net.URISyntaxException;

import isidis.dfs.team.API_DFS.exceptions.FileNotFoundException;
import isidis.dfs.team.API_DFS.implementation.API_HDFS_Impl;

public class Client {
	
	public final static String hdfsURL = "hdfs://192.168.217.128:9000";
	public final static String systemUserName = "hduser";
	
	public static void main(String[] args){
		API_HDFS_Impl api = null;
		try {
			api = new API_HDFS_Impl(Client.hdfsURL, systemUserName);
		} catch (URISyntaxException e1) {
			System.out.println("URL format not correct");
		}
		
		/***
		 * Testing ReadFile method
		 */
		try {
			System.out.println(new String(api.readFile("/user/file1")));
		} catch (FileNotFoundException | IOException e) {
			System.out.println("File Not Found on the DFS Or IOException");
		} 
		
		
		/***
		 * Testing WriteFile method
		 */
	//	api.writeFile("mon texte que je veux ecrire".getBytes(), "/user/file1");
		
		/***
		 * Testing deleteFile method
		 */
/*		try {
			api.deleteFile("/user/file1");
		} catch (FileNotFoundException | IOException e) {
			e.printStackTrace();
		}*/
		
	}
}
