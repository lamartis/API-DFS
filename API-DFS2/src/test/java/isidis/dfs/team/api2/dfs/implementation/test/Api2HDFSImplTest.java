package isidis.dfs.team.api2.dfs.implementation.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api.dfs.common.tools.SecurityChecker;
import isidis.dfs.team.api2.dfs.implementation.Api2HDFSImpl;
import isidis.dfs.team.api2.dfs.implementation.RemoteIteratorReader;
import isidis.dfs.team.api2.dfs.implementation.RemoteIteratorWriter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DFSClient;
import org.junit.Before;
import org.junit.Test;

public class Api2HDFSImplTest {
	
	public Api2HDFSImpl toTest = null;
	DFSClient client = mock(DFSClient.class);
	public SecurityChecker securityChecker = mock(SecurityChecker.class);
	public RemoteIteratorReader remoteIteratorReader = mock(RemoteIteratorReader.class);
	public RemoteIteratorWriter remoteIteratorWriter = mock(RemoteIteratorWriter.class);
	public File file = mock(File.class);
	
	@Before
	public void before() throws URISyntaxException, EndpointNotReacheableException {
		toTest = spy( new Api2HDFSImpl()); 
	}
	
	@Test
	public void readFile() throws IOException, EndpointNotReacheableException, FileNotFoundException, SystemUserPermissionException, FileSizeThresholdNotRespected {
		when(client.exists(anyString())).thenReturn(true);
		doReturn(client).when(toTest).getDFSClient();

		when(securityChecker.isNormalFile(anyString())).thenReturn(false);
		doReturn(securityChecker).when(toTest).getSecurityChecker();

		doReturn(remoteIteratorReader).when(toTest).getRIR(anyLong(), anyString());
		
		RemoteIterator<byte[]> ri = toTest.readFile(anyLong(), anyString());
		
	}
	
	@Test
	public void writeFile() throws IOException, EndpointNotReacheableException, SystemUserPermissionException, FileNotFoundException, FileSizeThresholdNotRespected {
		when(client.exists(anyString())).thenReturn(false);
		doReturn(client).when(toTest).getDFSClient();

		when(securityChecker.isNormalFile(anyString())).thenReturn(false);
		doReturn(securityChecker).when(toTest).getSecurityChecker();

		when(file.length()).thenReturn((long)11 * 1024 * 1024);
		doReturn(remoteIteratorWriter).when(toTest).getRIW(file, 0, "file1");
		
		
		RemoteIterator<Void> ri = toTest.writeFile(file, 0, "file1");
	}
	
}
