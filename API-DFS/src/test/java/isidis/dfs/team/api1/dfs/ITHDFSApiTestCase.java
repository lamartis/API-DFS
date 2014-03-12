package isidis.dfs.team.api1.dfs;

import java.net.URISyntaxException;

import junit.framework.TestCase;
import isidis.dfs.team.api.dfs.common.exceptions.*;

import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.junit.Assert;

import isidis.dfs.team.api1.dfs.implementation.ApiHDFSImpl;
import isidis.dfs.team.api1.dfs.interfaces.ApiHDFS;

public class ITHDFSApiTestCase {
    
    public static final String fileName = "/user/fileForIntegration7";
    public static final String texte = "This is a test";
    
    public void testAPI_HDFS_Impl() {
        
        ApiHDFS api = null;
        try {
            api = new ApiHDFSImpl();
        } catch (URISyntaxException e1) {
            Assert.fail("URI Syntax Error");
        } catch (EndpointNotReacheableException e) {
        	Assert.fail("Endpoint No Reacheable Exception");
		}

        /***
         * Testing WriteFile method
         **/
        try {
            api.writeFile(texte.getBytes(), fileName);
        } catch (SystemUserPermissionException e) {
            Assert.fail("permission error");
        } catch (FileAlreadyExistsException e) {
            Assert.fail("file already exist error");
        } catch (EndpointNotReacheableException e) {
            Assert.fail("Endpoint not reachable");
        } catch (FileSizeExceedsFixedThreshold e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /***
         * Testing ReadFile method
         **/

        try {
            Assert.assertTrue(texte.equals(new String(api.readFile(fileName)).trim()));
        } catch (SystemUserPermissionException e1) {
            Assert.fail("permession error");
        } catch (FileNotFoundException e1) {
            Assert.fail("file not found");
        } catch (EndpointNotReacheableException e1) {
            Assert.fail("endpoint not reachbear");
        } catch (FileSizeExceedsFixedThreshold e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        /***
         * Testing deleteFile method
         */

        try {
            api.deleteFile(fileName);
        } catch (SystemUserPermissionException e) {
            Assert.fail("permission error");
        } catch (FileNotFoundException e) {
            Assert.fail("file not found");
        } catch (EndpointNotReacheableException e) {
            Assert.fail("Endpoint not reachable");
        }
        
        
        /***
         * Testing ReadFile method
         **/

        try {
            Assert.assertEquals(texte,(new String(api.readFile(fileName))));
            Assert.fail("File have to not exist [Exception not thrown]");
        } catch (SystemUserPermissionException e1) {
            Assert.fail("permession error");
        } catch (FileNotFoundException e1) {
            System.out.println("Exception is throwen");
        } catch (EndpointNotReacheableException e1) {
            Assert.fail("endpoint not reachbear");
        } catch (FileSizeExceedsFixedThreshold e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }

}
