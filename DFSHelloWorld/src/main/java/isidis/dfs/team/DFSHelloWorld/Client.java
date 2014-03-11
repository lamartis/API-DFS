package isidis.dfs.team.DFSHelloWorld;

import isidis.dfs.team.api.dfs.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.implementation.ApiHDFSImpl;
import isidis.dfs.team.api.dfs.interfaces.ApiHDFS;

import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileAlreadyExistsException;

public class Client {

	public final static String hdfsURL = "hdfs://192.168.0.41:9000/";
	public final static String systemUserName = "hduser";

	public static void main(String[] args){
		ApiHDFS api = null;
		try {
			api = new ApiHDFSImpl(Client.hdfsURL, systemUserName);
		} catch (URISyntaxException e1) {
			System.out.println("URL format not correct");
			System.exit(0);
		}

		/***
		 * Testing ReadFile method
		 **/

		try {
			System.out.println(new String(api.readFile("/user/file1")));
		} catch (SystemUserPermissionException e1) {
			System.out.println("permession error");
		} catch (FileNotFoundException e1) {
			System.out.println("file not found");
		} catch (EndpointNotReacheableException e1) {
			System.out.println("endpoint not reachbear");
		}
		 


		/***
		 * Testing WriteFile method
		 **
		try {
			api.writeFile("mon texte que je veux ecrire".getBytes(), "/user/file2");
		} catch (SystemUserPermissionException e) {
			System.out.println("permission error");
		} catch (FileAlreadyExistsException e) {
			System.out.println("file already exist error");
		} catch (EndpointNotReacheableException e) {
			System.out.println("Endpoint not reachable");
		}
		 

		/***
		 * Testing deleteFile method
		 *

		try {
			api.deleteFile("/user/file1");
		} catch (SystemUserPermissionException e) {
			System.out.println("permission error");
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (EndpointNotReacheableException e) {
			System.out.println("Endpoint not reachable");
		}*/


	}
}
