package isidis.dfs.team.api.dfs2.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import isidis.dfs.team.api.dfs2.interfaces.ApiHDFS;
import junit.framework.TestCase;

public class ApiHDFSImplTest extends TestCase {
	
	public void testWriteFile() throws IOException {
		ApiHDFS api = new ApiHDFSImpl();
		api.writeFile(new FileInputStream(new File("/z")), "/user/writerFilm");
	}
	
}
