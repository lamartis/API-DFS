package isidis.dfs.team.api1.dfs.implementation.test;

import static org.junit.Assert.*;

import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api1.dfs.implementation.Api1HDFSImpl;
import isidis.dfs.team.api1.dfs.interfaces.Api1HDFS;

public class ITApi1HDFSImpl {

	public static final String fileName = "/user/fileForIntegration";
	public static final String texte = "This is a test";

	@Test
	public void ITApi1HDFSImplTest() {

		Api1HDFS api = new Api1HDFSImpl();
		
		/***
		 * Testing WriteFile method
		 **/
		try {
			api.writeFile(texte.getBytes(), fileName);
		} catch (FileAlreadyExistsException e) {
			Assert.fail("File Already Exists Exception");
		} catch (SystemUserPermissionException e) {
			Assert.fail("permession error");
		} catch (EndpointNotReacheableException e) {
			Assert.fail("endpoint not reacheable");
		} catch (FileSizeThresholdNotRespected e) {
			Assert.fail("FileSizeThresholdNotRespected");
		}


		/***
		 * Testing ReadFile method
		 **/

        try {
			assertTrue(texte.equals(new String(api.readFile(fileName)).trim()));
		} catch (FileNotFoundException e) {
			Assert.fail("File Not Found Exception");
		} catch (EndpointNotReacheableException e) {
			Assert.fail("endpoint not reacheable");
		} catch (SystemUserPermissionException e) {
			Assert.fail("permession error");
		} catch (FileSizeThresholdNotRespected e) {
			Assert.fail("FileSizeThresholdNotRespected");
		}
        


        /***
		 * Testing deleteFile method
		 */

     
            try {
				api.delete(fileName, false);
			} catch (FileNotFoundException e) {
				Assert.fail("File Not Found Exception");
			} catch (EndpointNotReacheableException e) {
				Assert.fail("endpoint not reacheable");
			} catch (SystemUserPermissionException e) {
				 Assert.fail("permession error");
			}
     


        /***
		 * Testing ReadFile method
		 */

        try {
            assertEquals(texte,(new String(api.readFile(fileName))));
            Assert.fail("File have to not exist [Exception not thrown]");
        } catch (SystemUserPermissionException e1) {
            Assert.fail("permession error");
        } catch (FileNotFoundException e1) {
            System.out.println("Exception is throwen");
        } catch (EndpointNotReacheableException e1) {
            Assert.fail("endpoint not reacheable");
        } catch (FileSizeThresholdNotRespected e) {
        	Assert.fail("FileSizeThresholdNotRespected");
		}
        
	}
}