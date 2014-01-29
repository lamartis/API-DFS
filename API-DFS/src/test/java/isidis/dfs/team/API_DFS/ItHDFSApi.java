package isidis.dfs.team.API_DFS;

import static org.junit.Assert.*;
import java.net.URISyntaxException;
import isidis.dfs.team.api.dfs.exceptions.*;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import isidis.dfs.team.api.dfs.implementation.API_HDFS_Impl;
import isidis.dfs.team.api.dfs.interfaces.API_HDFS;

public class ItHDFSApi {
    
    public static final String hdfsURL = "hdfs://192.168.0.41:9000";
    public static final String systemUserName = "hduser";
    public static final String fileName = "/user/fileForIntegration7";
    public static final String texte = "This is a test";
    
    @Test
    public void testAPI_HDFS_Impl() {
        
        API_HDFS api = null;
        try {
            api = new API_HDFS_Impl(hdfsURL, systemUserName);
        } catch (URISyntaxException e1) {
            Assert.fail("URI Syntax Error");
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
        }
        
        /***
         * Testing ReadFile method
         **/

        try {
            assertTrue(texte.equals(new String(api.readFile(fileName)).trim()));
        } catch (SystemUserPermissionException e1) {
            Assert.fail("permession error");
        } catch (FileNotFoundException e1) {
            Assert.fail("file not found");
        } catch (EndpointNotReacheableException e1) {
            Assert.fail("endpoint not reachbear");
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
            assertEquals(texte,(new String(api.readFile(fileName))));
            Assert.fail("File have to not exist [Exception not thrown]");
        } catch (SystemUserPermissionException e1) {
            Assert.fail("permession error");
        } catch (FileNotFoundException e1) {
            System.out.println("Exception is throwen");
        } catch (EndpointNotReacheableException e1) {
            Assert.fail("endpoint not reachbear");
        }
        
        
    }

}
