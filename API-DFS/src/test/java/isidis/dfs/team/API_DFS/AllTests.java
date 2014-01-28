package isidis.dfs.team.API_DFS;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(API_HDFSTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
